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

        //init scanner for reading message
        Scanner keyboard = new Scanner(System.in);
        
        //message pre buffer
        String msg;

        //subject to change, bool to check if message is being sent
        boolean sending;

        //boolean for process kill
        boolean close = false;

        //main loop
        while (true) {
            //listen loop
            do { 
              System.out.println("Enter message ({Sender ID};{Recipient ID};{Message})");
              //TODO: add syntax check
              msg = keyboard.nextLine();
            } while (!sending);
            //open channel
            SocketChannel channel = SocketChannel.open();

            //TODO: get connection address from config via parser
            channel.connect(new InetSocketAddress());

            //message buffer
            ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

            channel.write(buffer);

            //AWAIT REPONSE

            //kill when we flip close
            if (close) {
                channel.close();
            }
        }
    }

    static void serverOutput(SocketChannel channel) throws IOException {
       ByteBuffer replyBuffer = ByteBuffer.allocate(1024);
       int bytesRead = channel.read(replyBuffer);
       replyBuffer.flip();
       byte[] a = new byte[bytesRead];
       replyBuffer.get(a);
       System.out.println(new String(a));
   }
}
