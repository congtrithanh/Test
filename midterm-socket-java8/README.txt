Build (Java 8):
  javac -d out src/server/MidtermServer.java src/client/MidtermClient.java

Run server (default port 6789):
  java -cp out server.MidtermServer
  or with custom port:
  java -cp out server.MidtermServer 9000

Run client (default host 127.0.0.1, port 6789):
  java -cp out client.MidtermClient
  or specify host and port:
  java -cp out client.MidtermClient 127.0.0.1 9000

Workflow:
  1) Start server.
  2) Start client, input Student_ID.
  3) Client prints first reply (4 x Student_ID).
  4) Continue typing messages. If positive integer (>=1) -> server returns number^4. Otherwise server echoes.
