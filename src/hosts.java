import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Host {
    private final String hostID;
    private final String MacAdress;
    private String switchIP;
    private int switchPort;

    private NetworkLayer networkLayer;

    private Host(String hostID) {
        this.hostID = hostID;
        this.MacAdress = hostID;
    }

    @SuppressWarnings("SameParameterValue")
    private void initialize(String configFile) throws IOException {

        //load up config
        Config config = new Config(configFile);
        String myIp = config.getIp(hostID);
        int myPort = config.getPort(hostID);

        String switchId = config.getNeighbors(hostID).getFirst();
        //grab the config and set up everything
        switchIP = config.getIp(switchId);
        switchPort = config.getPort(switchId);
        networkLayer = new NetworkLayer(myPort);
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
                networkLayer.send(frame, switchIP, switchPort);
            } catch (IOException e) {
                System.out.println("Host " + hostID + " Failed to send frame");
            }
        }
    }

    //receiver
    @SuppressWarnings("InfiniteLoopStatement")
    private void receiver() {
        while (true) {
            try {
                NetworkLayer.Data data = networkLayer.receive();
                String[] parts = data.frame().split(":", 3);
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
            host.initialize("config.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
