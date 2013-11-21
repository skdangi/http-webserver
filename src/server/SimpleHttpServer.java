package server;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleHttpServer {
	public static void main(String[] args) throws IOException {
		if(args ==null || args.length<2){
			throw new IllegalArgumentException();
		}
		int port = Integer.parseInt(args[0]);
		String dir = args[1];
		Executor executor = new ExecutorImpl(5,5);
		ServerSocket socket = new ServerSocket(port);
		while(true){
			final Socket connection =socket.accept();
			new File(dir).mkdirs();
			Runnable task = new SaveFileRunnable(connection, dir);
			executor.submit(task);	
		}
	}
}	
