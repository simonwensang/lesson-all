package com.simon.zookeeper.config;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

/**
 * Created by sang on 2019/5/22.
 */
public class StringSerializer implements ZkSerializer {

    public byte[] serialize(Object o) throws ZkMarshallingError {
        try{
            if(o instanceof  String){
                return ((String)((String) o)).getBytes("UTF-8");
            }
        }catch (Exception e){}
        return new byte[0];
    }

    public Object deserialize(byte[] bytes) throws ZkMarshallingError {

        try{
            return  new String(bytes,"UTF-8");
        }catch (Exception e){}

        return  new Object();

    }

}
