package client;
import java.io.*;
import java.net.*;
public class MidtermClient {
    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port = 6789;
        if (args.length >= 1) host = args[0];
        if (args.length >= 2) port = Integer.parseInt(args[1]);
        try (
            Socket socket = new Socket(host, port);
            BufferedReader netIn = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            PrintWriter netOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in, "UTF-8"))
        ) {
            System.out.print("Enter Student_ID: ");
            String studentId = userIn.readLine();
            netOut.println(studentId);
            String reply = netIn.readLine();
            if (reply != null) System.out.println("Server: " + reply);
            while (true) {
                String msg = userIn.readLine();
                if (msg == null) break;
                netOut.println(msg);
                String r = netIn.readLine();
                if (r == null) break;
                System.out.println("Server: " + r);
            }
        }
    }
}
