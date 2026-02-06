import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class hosts implements parser{
    private netcode netcode;
    private final String hostID;
    private final String addr;

    private hosts(String hostID) {
    this.addr = hostID;
    this.hostID = hostID;
    }
    
     private void start(String id) throws IOException {
        HashMap<String, String[]> idHash = parser.parseConfig(id);

        System.out.println("Host "+id+"initialized @"+idHash.get("IP")+idHash.get("Port"));
        try (ExecutorService ex = Executors.newFixedThreadPool(2)) {
            ex.submit(this::send);
            ex.submit(this::receive);
        }
    }

    private void send() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Enter Message: {Sender ID},{Reciever ID},{Message}");
            String msg = sc.nextLine();

            try {
                netcode.send(msg, msg, 0);
            } catch (IOException e) {
                System.out.println("Failed to send frame");
            }
        }
    }

    private void receive() {
        while (true) {
            try {
                netcode.Data data = netcode.receive();
                String message = data.message();
                String sender = data.sender();

                String[] sMessage = parser.getRouteFromMessage(message);
                String reciever = sMessage[1];

                if (reciever.equals(addr)) {
                    System.out.println(sender+": "+message);
                } else {
                    System.out.println("Flooded message: "+message);
                }
            } catch (IOException e) {
                System.out.println("Switch error");
            }
        }
    }
    
    public static void main(String[] args) {
        String hostID = args[0];
        hosts host = new hosts(hostID);
        try {
            host.start(hostID);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
