import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class switches implements parser {
    private netcode netcode;
    Map<String, String> switchTable;//key: virtual addr [ip+port]
    Map<String, String[]> virtualPorts; //key: Link name value: List [ip][port]

    public switches() {
        this.virtualPorts = new HashMap<>();
        this.switchTable = new HashMap<>();
    }

    private void getData(netcode.Data data) {
        String message = data.message();
        String[] sMessage = parser.getRouteFromMessage(message);
        String sender = sMessage[0];
        String reciever = sMessage[1];
        String fMessage = sMessage[2];

        if (!switchTable.containsKey(sender)) {
            switchTable.put(sender, (virtualPorts.get(sender)[0]+":"+virtualPorts.get(sender)[1]));
            printCache();
        }

        System.out.println("Frame recieved\n");

        sendData(fMessage, reciever);
    }

    private void sendData(String message, String reciever) {
        try {
            if (!virtualPorts.containsKey(reciever)) { //NOT NEIGHBOR
                for (Map.Entry<String, String[]> k : virtualPorts.entrySet()) {
                    netcode.send(message, reciever, Integer.parseInt(k.getValue()[1]));
                }
            } else { //IS NEIGHBOR
                netcode.send(message, reciever, Integer.parseInt(virtualPorts.get(reciever)[1]));
            }
        } catch (IOException e) {
            System.err.println("Error sending frame");
        }
    }

    private void printCache() {
        System.out.println("Cache:");
        for (Map.Entry<String, String> element : switchTable.entrySet()) {
            System.out.println(switchTable.get(element));
        }
    }
    
    private void start(String id) throws IOException {
        HashMap<String, String[]> idHash = parser.parseConfig(id);

        System.out.println("Switch "+id+" initialized @"+ Arrays.toString(idHash.get("IP")) + Arrays.toString(idHash.get("Port")));

        HashMap<String, String[]> tempHash;

        for (String link : idHash.get("Links")) {
            tempHash = parser.parseConfig(link);
            String[] list = {tempHash.get("IP")[0], tempHash.get("Port")[0]};
            virtualPorts.put(link, list);  
        }

        System.out.println("Neighbors Found\n");

        this.netcode = new netcode(Integer.parseInt(idHash.get("Port")[0]));

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
