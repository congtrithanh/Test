package server;
import java.net.*;
import java.math.BigInteger;
import java.io.*;
import java.util.*;

public class UdpServer {
    public static void main(String[] args) throws Exception {
        int port = 6790;
        if (args.length >= 1) port = Integer.parseInt(args[0]);
        DatagramSocket socket = new DatagramSocket(port);
        Map<String, Boolean> seen = new HashMap<>();
        byte[] buf = new byte[8192];
        while (true) {
            DatagramPacket p = new DatagramPacket(buf, buf.length);
            socket.receive(p);
            String s = new String(p.getData(), p.getOffset(), p.getLength(), "UTF-8");
            String key = p.getAddress().getHostAddress() + ":" + p.getPort();
            boolean first = !seen.getOrDefault(key, false);
            String reply;
            if (first) {
                BigInteger id = new BigInteger(s);
                reply = id.multiply(BigInteger.valueOf(4)).toString();
                seen.put(key, true);
            } else {
                if (isPositiveInteger(s)) {
                    BigInteger n = new BigInteger(s);
                    reply = n.pow(4).toString();
                } else reply = s;
            }
            byte[] out = reply.getBytes("UTF-8");
            DatagramPacket r = new DatagramPacket(out, out.length, p.getAddress(), p.getPort());
            socket.send(r);
        }
    }
    static boolean isPositiveInteger(String s) {
        if (!s.matches("\\d+")) return false;
        if (s.length()==0) return false;
        if (s.equals("0")) return false;
        return true;
    }
}
