import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
/*
public class switches implements parser{
    public static void main(String[] args) throws Exception{
        ServerSocketChannel listenChannel = ServerSocketChannel.open();

        //NEED PORT FOR BINDING
        listenChannel.bind(new InetSocketAddress());

        //main while loop
        while (true) {
            SocketChannel serveChannel = listenChannel.accept();
            
            String hostMessage = getUserInput(serveChannel);
        }
    }

    static void printCache() throws IOException{

    }

    static void getLink() {

    }

    static String getUserInput(SocketChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int bytesRead = channel.read(buffer);
        buffer.flip();
        byte[] byteArray = new byte[bytesRead];
        buffer.get(byteArray);
        return new String(byteArray);
    }
}


 */