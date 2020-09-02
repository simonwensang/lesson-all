package nio;

import java.nio.ByteBuffer;

/**
 * Created by sang on 2020/7/7.
 */
public class MyByteBuffer {

    public static void main(String[] args) {

        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put((byte)1);
        buffer.put((byte)2);
        System.out.println("limit"+buffer.limit());
        buffer.flip();
        System.out.println("limit"+buffer.limit());
        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }

        System.out.println(buffer.capacity()+"+"+buffer.position()+"+"+buffer.limit());
        buffer.compact();
        System.out.println(buffer.capacity()+"+"+buffer.position()+"+"+buffer.limit());
       /* buffer.put((byte)3);
        buffer.put((byte)4);
        buffer.put((byte)5);
        System.out.println(buffer.capacity()+"+"+buffer.position()+"+"+buffer.limit());*/
        //buffer.rewind();
        buffer.flip();
        while (buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }

}
