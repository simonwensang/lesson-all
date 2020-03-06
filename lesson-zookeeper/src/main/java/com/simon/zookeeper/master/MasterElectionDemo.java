package com.simon.zookeeper.master;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;

import java.util.concurrent.CountDownLatch;

/**
 * Created by sang on 2019/5/22.
 */
public class MasterElectionDemo {

    static class Server {

        private String cluster, name, address;

        private final String path, value;

        private String master ;

        public Server(String cluster,String name,String address){
            this.cluster=cluster;
            this.name = name;
            this.address = address;
            path = "/"+cluster+"Master";
            value="name:"+name+" address:"+address;
            //选举
            final ZkClient zkClient = new ZkClient("127.0.0.1:2181");
            new Thread(()->{
                 electionMaster(zkClient);
            }).start();
        }

        //选举
        public void electionMaster(ZkClient zkClient){
            try {
                zkClient.createEphemeral(path, value);
                master = zkClient.readData(path);
                System.out.println(value + " 主节点创建成功 成为Master");
            }catch (ZkNodeExistsException e){
                master = zkClient.readData(path);
                System.out.println( "Master 主节点 ："+ master);
            }

            CountDownLatch countDownLatch = new CountDownLatch(1);

            IZkDataListener zkDataListener  = new IZkDataListener() {
                public void handleDataChange(String s, Object o) throws Exception {
                }
                public void handleDataDeleted(String s) throws Exception {
                    System.out.println("主节点已经删除，开始选举。。。");
                    countDownLatch.countDown();
                }
            };

            zkClient.subscribeDataChanges(path,zkDataListener );

            if(zkClient.exists(path)){
                try {
                    countDownLatch.await();
                }catch (InterruptedException e){}
            }

            zkClient.unsubscribeDataChanges(path,zkDataListener);

            electionMaster(zkClient);
        }

    }


    public static void main(String[] args) {
        //Server server1 = new Server("cluster1","server1","192.168.1.1:1001");
        //Server server2 = new Server("cluster1","server2","192.168.1.1:1002");
        Server server3 = new Server("cluster1","server3","192.168.1.1:1003");
    }


}
