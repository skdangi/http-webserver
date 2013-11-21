http webserver
==============
This is a simple webserver which only uses normal jdk classes.

- It implements a multithreaded http web server.  
- For the same, threadpool and blocking queue is also implemented.  
- Request objects are pushed on blocking queue and worker threads dequeue from queue and process the request.   
- HTTP request handling.
  Client sends http post request with a file asking the server to store it.

Test
=====

- ## Start server 
$ java SimpleHttpServer 9080 /tmp  
This command starts a simple HTTP server that listens on port 9080 and writes uploaded files to /tmp. On the client side, use an HTTP client like curl or wget to upload a file to the HTTP server. 
The uploaded file should be identical to the original.

- ## Send http request to upload a file  
$curl -v --request POST --data-binary @Death.mp3 http://localhost:9080

- ## Check if the file stored is same as you sent.  
$diff original.mp3 /tmp/test.mp3

