import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class hosts implements parser{
    public static void main(String[] args) throws Exception{
        //PLACEHOLDER FOR PARSER ARG CHECK
        int hostID;

        //init scanner for reading message
        Scanner keyboard = new Scanner(System.in);
        
        String msg;

        //await input from user, kill when command entered
        while (true) {
            do { 
              //TAKE MESSAGES HERE, CHECK FOR PROPER SYNTAX IN WHILE
            } while ();
            //open channel
            SocketChannel channel = SocketChannel.open();

            //PLACEHOLDER FOR PARSER TO GET SWITCH ADDR
            channel.connect(new InetSocketAddress());

            //message buffer
            ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

            channel.write(buffer);

            //AWAIT REPONSE

            channel.close();
        }
    }
}
