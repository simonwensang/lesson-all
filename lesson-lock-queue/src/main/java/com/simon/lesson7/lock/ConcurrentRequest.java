package com.simon.lesson7.lock;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sang on 2020/11/2.
 */
public class ConcurrentRequest {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(2000);
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultSocketConfig(SocketConfig.custom().setTcpNoDelay(true).build());
        connectionManager.setDefaultMaxPerRoute(2000);
        connectionManager.setMaxTotal(2000); // 总的最大连接数
        CloseableHttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connectionManager).build();
        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            for (int i=0 ; i<2000 ;i++) {
                executor.execute(() -> {
                    try {
                        System.out.println("start..");
                        countDownLatch.countDown();
                        countDownLatch.await();
                        HttpPost post = new HttpPost("https://qh-cms.mklmall.com/api-h5/cmsActivity/getCmsActivityList");
                        post.setEntity(new StringEntity("{\"mallCode\":\"1009\",\"activityTypeList\":[2,3,4,5,9]}", ContentType.APPLICATION_JSON));
                        CloseableHttpResponse response = httpClient.execute(post);
                        String result = EntityUtils.toString(response.getEntity());
                        System.out.println("result..");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            }
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            executor.shutdown();
        }
    }
}
