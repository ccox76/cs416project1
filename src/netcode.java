import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class netcode {
    private final DatagramSocket socket;

    public void send(String message, String reciever, int recieverPort) throws IOException {
        byte[] buffer = message.getBytes();
        InetAddress addr = InetAddress.getByName(reciever);
        DatagramPacket pckt = new DatagramPacket(buffer, buffer.length, addr, recieverPort);
        socket.send(pckt);
    }

    
}
