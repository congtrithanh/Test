package server;
import java.io.*;
import java.math.BigInteger;
import java.net.*;
public class MidtermServer {
    public static void main(String[] args) throws Exception {
        int port = 6789;
        if (args.length >= 1) port = Integer.parseInt(args[0]);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Accepted connection from " + socket.getRemoteSocketAddress());
                new Thread(new ClientHandler(socket)).start();
            }
        }
    }
    static class ClientHandler implements Runnable {
        private final Socket socket;
        ClientHandler(Socket s){ this.socket = s; }
        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true)
            ) {
                String first = in.readLine();
                if (first != null) {
                    BigInteger id = new BigInteger(first);
                    BigInteger result = id.multiply(BigInteger.valueOf(4));
                    out.println(result.toString());
                }
                String line;
                while ((line = in.readLine()) != null) {
                    if (isPositiveInteger(line)) {
                        BigInteger n = new BigInteger(line);
                        BigInteger p4 = n.pow(4);
                        out.println(p4.toString());
                    } else {
                        out.println(line);
                    }
                }
            } catch (Exception e) {
                try { socket.close(); } catch (IOException ex) {}
            }
        }
        private boolean isPositiveInteger(String s) {
            if (!s.matches("\\d+")) return false;
            if (s.length() == 0) return false;
            if (s.equals("0")) return false;
            return true;
        }
    }
}
