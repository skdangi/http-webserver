http-webserver
==============
This is a simple webserver which only uses normal jdk classes.

It implements a multithreads http web server.
For the same, threadpool and blocking queue is also implements.
Request objects are pushed on blocking queue and worker threads from the queue dequeue and process the request. 

HTTP request handling.
Client send http post request with a file asking the server to store it.



$ java SimpleHttpServer 9080 /tmp
This command starts a simple HTTP server that listens on port 9080 and writes uploaded files to /tmp. On the client side, use an HTTP client like curl or wget to upload a file to the HTTP server. 
The uploaded file should be identical to the original.

$curl -v --request POST --data-binary @Death.mp3 http://localhost:9080
$diff original.mp3 /tmp/test.mp3

