package com.simon.zookeeper.config;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by sang on 2019/5/21.
 */
public class DisConfig {

    private ZkClient zkClient ;

    public DisConfig(ZkClient zkClient){
        this.zkClient = zkClient;
    }

    public void setProperty(String  path ,String value){

        if(zkClient.readData(path)==null){
            zkClient.createPersistent(path,value);
        }
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("setProperty date change "+s+"="+o);
            }

            public void handleDataDeleted(String s) throws Exception {
                System.out.println("setProperty date delete "+s);
            }
        });
    }

    public void setFileProperty(String  path ,String filePath){

        File file = new File(filePath);

        if(!file.exists()){
            System.out.println("file not exit");
            return;
        }

        byte[] fileData = new byte[(int)file.length()];
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            inputStream.read(fileData);
        }catch (FileNotFoundException e){}
        catch (IOException e){}
        finally {
             if(inputStream!=null) try {inputStream.close();}catch (Exception e){}
        }
        if(zkClient.readData(path)==null){
            zkClient.createPersistent(path,fileData);
        }
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("setProperty date change "+s+"="+o);
            }

            public void handleDataDeleted(String s) throws Exception {
                System.out.println("setProperty date delete "+s);
            }
        });

    }

    public static void main(String[] args) throws Exception {

        ZkClient zkClient = new ZkClient("127.0.0.1:2181");
        zkClient.setZkSerializer(new StringSerializer());
        DisConfig disConfig = new DisConfig(zkClient);

        disConfig.setProperty("/sang","981");

        while (true){
            Thread.sleep(2000L);
        }

    }


}
