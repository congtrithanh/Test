package client;
import java.net.*;
import java.io.*;

public class UdpClient {
    public static void main(String[] args) throws Exception {
        String host = "127.0.0.1";
        int port = 6790;
        if (args.length >= 1) host = args[0];
        if (args.length >= 2) port = Integer.parseInt(args[1]);
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(0);
        BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
        System.out.print("Enter Student_ID: ");
        String id = userIn.readLine();
        sendRecv(socket, host, port, id);
        while (true) {
            String line = userIn.readLine();
            if (line == null) break;
            sendRecv(socket, host, port, line);
        }
    }
    static void sendRecv(DatagramSocket socket, String host, int port, String msg) throws Exception {
        byte[] data = msg.getBytes("UTF-8");
        DatagramPacket p = new DatagramPacket(data, data.length, InetAddress.getByName(host), port);
        socket.send(p);
        byte[] buf = new byte[8192];
        DatagramPacket r = new DatagramPacket(buf, buf.length);
        socket.receive(r);
        String rep = new String(r.getData(), r.getOffset(), r.getLength(), "UTF-8");
        System.out.println("Server: " + rep);
    }
}
