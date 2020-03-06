package com.simon.lesson6.client;

import com.alibaba.fastjson.JSON;
import com.simon.lesson6.utils.CommandSignUtil;
import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Random;

/**
 * Created by sang on 2018/12/27.
 */
public class DubboClient {

    public static void main(String[] args) {
        //1 zkclient 查询提供者
        ZkClient client = new ZkClient("127.0.0.1:8090");
        List<String> providers = client.getChildren("/dubbo/com.simon.lesson5.service.DemoService/providers");
        if(providers.isEmpty()){
            System.out.println("providers.isEmpty");
            return;
        }

        try {
            //2 拿到所有服务提供者信息 随机策略
            int index = new Random().nextInt(providers.size());
            String provider = URLDecoder.decode(providers.get(index),"utf-8");
            System.out.println("provider="+provider);
            String[] ipPost =  provider.split(":");
            if(ipPost.length!=2){
                System.out.println("ipPost.length!=2");
                return;
            }
            String host = ipPost[0];
            int port = Integer.valueOf(ipPost[1]);

            SocketChannel dubboClient = SocketChannel.open();
            dubboClient.connect(new InetSocketAddress(host,port));
            // body信息
            StringBuffer bodyString = new StringBuffer();
            //rpc协议版本
            bodyString.append(JSON.toJSONString("2.0.1")).append("\r\n");
            //接口地址
            bodyString.append(JSON.toJSONString("com.simon.lesson5.service.DemoService")).append("\r\n");
            //接口版本
            bodyString.append(JSON.toJSONString("1.0.0")).append("\r\n");
            //具体方法名
            bodyString.append(JSON.toJSONString("test")).append("\r\n");
            //方法参数类型
            bodyString.append(JSON.toJSONString("Ljava/lang/String;")).append("\r\n");
            //参数值
            bodyString.append(JSON.toJSONString("helllo")).append("\r\n");
            //附加参数
            bodyString.append("{}").append("\r\n");

            byte[] body = bodyString.toString().getBytes();
            System.out.println(bodyString);
            // 头信息
            byte[] header = new byte[16];

            byte[] magic = Hex.decodeHex( CommandSignUtil.getPatternBit(CommandSignUtil.PATTERN_FOUR,Integer.toHexString((int)0xdabb)).toCharArray());
            System.arraycopy(magic,0,header,0,2);
            header[2] = (byte)0xC6;
            header[3] = 0x00;
            byte[] messageId = Hex.decodeHex( CommandSignUtil.getPatternBit(CommandSignUtil.PATTERN_EIGHT,Long.toHexString(1L)) .toCharArray());
            System.arraycopy(messageId,0,header,4,8);
            byte[] bodyLength = Hex.decodeHex(CommandSignUtil.getPatternBit(CommandSignUtil.PATTERN_FOUR,Integer.toHexString(body.length)).toCharArray());
            System.arraycopy(bodyLength,0,header,12,4);

            byte[] request = new byte[header.length+body.length];
            System.arraycopy(header,0,request,0,header.length);
            System.arraycopy(body,0,request,16,body.length);

            dubboClient.write(ByteBuffer.wrap(request));
            ByteBuffer response = ByteBuffer.allocate(1024);
            dubboClient.read(response);
            System.out.println(new String(response.array()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }catch (DecoderException e){
            e.printStackTrace();
        }
    }


}
