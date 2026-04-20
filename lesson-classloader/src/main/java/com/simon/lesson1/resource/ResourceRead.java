package com.simon.lesson1.resource;

import org.apache.commons.codec.binary.Hex;
import sun.misc.Launcher;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by sang on 2019/4/16.
 */
public class ResourceRead {

    public static void main(String[] args) {
        String var1 = System.getProperty("java.class.path");
        System.out.println(var1);
        String var2 = System.getProperty("user.dir");
        System.out.println(var2);
        String var3 = ResourceRead.class.getResource("/").getPath();
        System.out.println("3=="+var3);
        String var4 = ResourceRead.class.getResource("").getPath();
        System.out.println(var4);
        String var5 = ResourceRead.class.getClassLoader().getResource("").getPath();
        System.out.println("5=="+var5);

       /* String var6 = Thread.currentThread().getContextClassLoader().getResource("YJ_2019_04_16.bin").getPath();
        System.out.println(var6);

        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("YJ_2019_04_16.bin");
        *//*BufferedReader buffer = new BufferedReader(new InputStreamReader(in));
        String s = "";
        while ((s = buffer.readLine()) != null) {
            System.out.println(s);
        }*//*
        FileInputStream fileInputStream = null;
        FileChannel channel = null;
        try {
            fileInputStream = new FileInputStream(new File(var6));
            channel = fileInputStream.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);//ByteBuffer buffer = ByteBuffer.wrap(new byte[1024]);
            int i = 0;
            while (channel.read(byteBuffer) > 0) {
                byteBuffer.flip();
                *//*while (byteBuffer.hasRemaining()){
                   // System.out.println(Integer.toHexString(0xFF & byteBuffer.get()) + "-->");
                    System.out.println(HexString.toHexString(  new byte[]{byteBuffer.get()}) + "-->");
                }*//*
                System.out.println(Hex.encodeHexString(byteBuffer.array()) + "-->");
                byteBuffer.clear();
                i++;
            }
            System.out.println(i);
            //FileChannel   fc = new RandomAccessFile(var6, "r").getChannel();

            *//*byte[] read = new byte[150];
            while(in.read(read)!=-1){
                System.out.println( Hex.encodeHexString(read)+"-->");
            }*//*

        } catch (IOException e) {

        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException e) {
            }
            try {
                if (channel != null) channel.close();
            } catch (IOException e) {
            }
            try {
                if (fileInputStream != null) fileInputStream.close();
            } catch (IOException e) {
            }
        }*/

    }

    /**
     * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static byte[] toByteArray(String filename) throws IOException {

        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(filename, "r").getChannel();
            MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size()).load();
            System.out.println(byteBuffer.isLoaded());
            byte[] result = new byte[(int) fc.size()];
            if (byteBuffer.remaining() > 0) {
                // System.out.println("remain");
                byteBuffer.get(result, 0, byteBuffer.remaining());
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                fc.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
