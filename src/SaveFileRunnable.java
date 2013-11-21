

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SaveFileRunnable implements Runnable {
	private Socket client;
	private String dir;
	private static String CONTENT_LENGTH = "Content-Length";
	private static String HEADER_SEPARATOR = ":";

	public SaveFileRunnable(Socket socket, String dir) {
		this.client = socket;
		this.dir = dir;
	}

	public void run() {
		String line;
		int reqHeadSize = 0;
		int contentLen = 0;
		FileOutputStream fos = null;
		PrintWriter socketWriter = null;
		boolean valid = true;
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			line = in.readLine();
			String[] firstLine = line.split(" ");
			int tempIndex;
			String key;
			String value;
			if (firstLine.length == 3 && "POST".equals(firstLine[0])
					&& "HTTP/1.1".equals(firstLine[2])) { // for testing keep 1.1 check
				while ((line = in.readLine()) != null) {
					//break when body starts
					if ("".equals(line)) {
						reqHeadSize = reqHeadSize + 1;
						break; 
					} else {
						//parse headers for body size
						reqHeadSize = reqHeadSize + line.length() + 1;
						tempIndex = line.indexOf(HEADER_SEPARATOR);
						key = line.substring(0, tempIndex);
						value = line.substring(tempIndex + 1).trim();
						if (CONTENT_LENGTH.equals(key)) {
							contentLen = Integer.parseInt(value);
						}
					}
				}

				byte[] body = new byte[contentLen];
				InputStream stream = client.getInputStream();
				stream.read(body, 0, contentLen);
				String fileName = dir+"/"+firstLine[1].replace("/", "");
				System.out.println("file "+fileName);
				File file = new File(fileName);
				if(!file.exists()){
					fos = new FileOutputStream(file);
					fos.write(body);
				}else{
					valid = false;
					System.out.println("file already exists: "+fileName);
				}
			} else {
				valid = false;
			}

			socketWriter = new PrintWriter(new BufferedWriter(
					new OutputStreamWriter(client.getOutputStream())), true);
			socketWriter.write(valid ? "HTTP/1.0 200 OK\r\n"
					: "HTTP/1.0 400 Bad Request\r\n");
			socketWriter.flush();

		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (socketWriter != null) {
					socketWriter.close();
				}
				client.close();
				//System.out.println("Closed client");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
