package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Created by sang on 2020/7/7.
 */
public class MyNioClient {

    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect( new InetSocketAddress("127.0.0.1",8080));

            while (!socketChannel.finishConnect()) {
                Thread.yield();
            }

            Scanner scanner  = new Scanner(System.in);
            System.out.println("");

            String msg = scanner.nextLine();
            ByteBuffer msgByteBuffer = ByteBuffer.wrap(msg.getBytes());
            while(msgByteBuffer.hasRemaining()){
                socketChannel.write(msgByteBuffer);
            }

            System.out.println("");
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            while(socketChannel.isOpen() && socketChannel.read(byteBuffer)!=-1){
                if(byteBuffer.position()>0){ break;}
            }
            byteBuffer.flip();
            byte[] bytes = new byte[byteBuffer.limit()];
            byteBuffer.get(bytes);
            System.out.println(new String(bytes));

            scanner.close();
            socketChannel.close();

        }catch ( IOException e){
            e.printStackTrace();

        }
    }
}
