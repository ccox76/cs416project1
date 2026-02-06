import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class switches implements parser {
    private netcode netcode;
    Map<String, String> switchTable;
    Map<String, String> virtualPorts;

    public switches() {
        this.virtualPorts = new HashMap<>();
        this.switchTable = new HashMap<>();
    }

    private void getData(netcode.Data data) {
        String message = data.message();
        String sender = data.sender();
        int senderPort = data.senderPort();
        String[] sMessage = parser.getRouteFromMessage(message);
        String reciever = sMessage[1];

        sendData(message, reciever);
    }

    private void sendData(String message, String reciever) {
        try {
            netcode.send(message, reciever, 0);
        } catch (IOException e) {
            System.err.println("Error sending frame");
        }
    }
    
    private void start(String id) throws IOException {
        HashMap<String, String[]> idHash = parser.parseConfig(id);

        System.out.println("Swtich "+id+" initialized @"+ Arrays.toString(idHash.get("IP")) + Arrays.toString(idHash.get("Port")));

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
        switches sw = new switches();
        try {
            sw.start(switchId);
        } catch (IOException e) {
            System.err.println("Failed to start");
        }
    }
}
