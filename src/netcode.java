import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class netcode {
    private final DatagramSocket socket;

    public record Data(String message, String sender, int senderPort) {};

    public netcode(int port) throws SocketException{
        this.socket = new DatagramSocket(port);
    }

    public void send(String message, String reciever, int recieverPort) throws IOException {
        byte[] buffer = message.getBytes();
        InetAddress addr = InetAddress.getByName(reciever);
        DatagramPacket pckt = new DatagramPacket(buffer, buffer.length, addr, recieverPort);
        socket.send(pckt);
        System.out.println("psend");
    }

    public Data receive() throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket pckt = new DatagramPacket(buffer, buffer.length);
        socket.receive(pckt);
        String message = new String(pckt.getData(), 0, pckt.getLength());
        String sender = pckt.getAddress().getHostAddress();
        int senderPort = pckt.getPort();

        System.out.println("pget");

        return new Data(message, sender, senderPort);
    }

    public void close() {
        socket.close();
    }
}
