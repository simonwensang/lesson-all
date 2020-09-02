package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by sang on 2020/7/7.
 */
public class MyNioServer {

    public static void main(String[] args) {
        try {
            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.socket().bind(new InetSocketAddress(8080)) ;
            while(true){
                SocketChannel socketChannel =  server.accept();

                if(socketChannel!=null){
                    System.out.println("收到新链接："+socketChannel.getRemoteAddress());
                    socketChannel.configureBlocking(false);
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                    while(socketChannel.isOpen() && socketChannel.read(byteBuffer)!=-1){
                        if(byteBuffer.position()>0) break;
                    }
                    if(byteBuffer.position()==0)  continue;
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.limit()];

                    byteBuffer.get(bytes);
                    System.out.println(new String(bytes));
                    System.out.println("收到消息来自"+socketChannel.getRemoteAddress());

                    //响应结果200
                    String response = "HTTP/1.1 200 OK\r\n" +
                            "Content-Length: 11\r\n\r\n"+
                            "hello word";
                    ByteBuffer responseBuffer = ByteBuffer.wrap(response.getBytes());
                    while(responseBuffer.hasRemaining()){
                        socketChannel.write(responseBuffer);
                    }
                }
            }
        }catch ( IOException e){
            e.printStackTrace();

        }
    }
}
