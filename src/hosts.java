import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class hosts implements parser{
    public static void main(String[] args) throws Exception{
        //machine ID specified at boot
        String hostID = args[0];

        Scanner keyboard = new Scanner(System.in);
        
        //message pre buffer
        String msg;

        //subject to change, bool to check if message is being sent
        boolean message;

        //boolean for process kill
        boolean close = false;

        //main loop
        while (true) {
            //listen loop
            do { 
              System.out.println("Enter message ({Sender ID};{Recipient ID};{Message})");
              //TODO: add syntax check
              msg = keyboard.nextLine();
            } while (message);
            //open channel
            SocketChannel channel = SocketChannel.open();

            //TODO: get connection address from config via parser
            channel.connect();

            //message buffer
            ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

            channel.write(buffer);

            //wait loop; exit when channel recieves message
            do {

            } while (!message);

            //kill when we flip close
            if (close) {
                channel.close();
            }
        }
    }
}
