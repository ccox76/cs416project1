import java.io.*;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

import netcode.Data;

public class switches implements parser {
    private netcode netcode;
    Map<String, String> switchTable = new HashMap<>();
    Map<String, String> virtualPorts = new HashMap<>();

    private void getData(netcode.Data data) {
        String message = data.message();
        String sender = data.sender();
        int senderPort = data.senderPort();
        String[] sMessage = parser.getRouteFromMessage(message);
        String reciever = sMessage[2];

        sendData(message, reciever);
    }
    
    private void start(String id) throws IOException {
        HashMap<String, String[]> idHash = parser.parseConfig(id);

        System.out.println("Switch"+id+"initialized @"+idHash.get("IP")+idHash.get("Port"));

        while (true) {
            try {
                netcode.Data data = netcode.receive();
                getData(data);
            } catch (IOException e) {
                System.err.println("Failed to recieve frame");
            }
        }
    }
    public static void main(String[] args) {
        String switchId = args[0];
        try {
            
        } catch (IOException e) {
            // TODO: handle exception
        }
    }
}
