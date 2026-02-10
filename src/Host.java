import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Host {
    private final String hostID;
    private final String MacAdress;
    private String switchIP;
    Map<String, String[]> neighbors;
    private int switchPort;
    private netcode netcode;


    private Host(String hostID) {
        this.hostID = hostID;
        this.MacAdress = hostID;
        this.neighbors = new HashMap<>();
    }

    @SuppressWarnings("SameParameterValue")
    private void initialize(String configFile, String id) throws IOException {

        //load up config
        parser parse = new parser();
        HashMap<String, String[]> parsedHashMap = parser.parseConfig(id);
        String myIp = parsedHashMap.get("IP")[0];
        int myPort = Integer.parseInt(parsedHashMap.get("Port")[0]);


        HashMap<String, String[]> tempHash;

        for (String link : parsedHashMap.get("Links")) {
            tempHash = parse.parseConfig(link);
            String[] list = {tempHash.get("IP")[0], tempHash.get("Port")[0]};
            neighbors.put(link, list);
        }
        String switchId = neighbors.get(); //TODO: pull neighboring switch ID from hash
        //grab the config and set up everything
        switchIP = neighbors.get(switchId)[0];
        switchPort = Integer.parseInt(neighbors.get(switchId)[1]);
        netcode = new netcode(myPort);
        System.out.println("Host " + hostID + " initialized on " +
                myIp + ":" + myPort);

        try (ExecutorService executor = Executors.newFixedThreadPool(2)) {
            executor.submit(this::sender);
            executor.submit(this::receiver);
        }
    }

    //sender
    @SuppressWarnings("InfiniteLoopStatement")
    private void sender() {
        Scanner scanner = new Scanner(System.in);
        while (true) { //tey to send message


            System.out.print("Destination MacAdress Address: ");
            String destMacAdress = scanner.nextLine();
            System.out.print("Message: ");
            String message = scanner.nextLine();
            String frame = MacAdress + ":" + destMacAdress + ":" + message;
            try {//switch try statement
                netcode.send(frame, switchIP, switchPort);
            } catch (IOException e) {
                System.out.println("Host " + hostID + " Failed to send frame");
            }
        }
    }

    //receiver
    @SuppressWarnings("InfiniteLoopStatement")
    private void receiver() {
        while (true) {
            try { //TODO: format for input 
                netcode.Data data = netcode.receive();
                String[] parts = data.Data().split(",", 3);
                if (parts.length < 3){
                    continue;
                }

                String srcMacAdress = parts[0];
                String destMacAdress = parts[1];
                String message = parts[2];

                if (destMacAdress.equals(MacAdress)) {
                    System.out.println("Message from " + srcMacAdress + ": " + message);
                } else {
                    System.out.println("Debug: MacAdress address mismatch - received " + destMacAdress + "_ own MacAdress: " + MacAdress + ". (Flooded frame)");
                }
            } catch (IOException e) {
                System.out.println("Receive error");
            }
        }
    }
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Java Host <hostID>");
            return;
        }
        Host host = new Host(args[0]);
        try {
            host.initialize("config.json", args[0]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
