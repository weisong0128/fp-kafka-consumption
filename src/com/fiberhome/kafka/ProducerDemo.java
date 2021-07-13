package com.fiberhome.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.io.*;
import java.util.Properties;


/**
 * description: 生产及FP消费测试用例
 * author: ws
 * time: 2019/12/12 15:07
 */
class First_thread extends ProducerDemo implements Runnable{
    private static Producer<String, byte[]> producer01;

    public void run() {
        init();	//static 初始化方法
        long startTime = System.currentTimeMillis();
        producerMethod();	// 一个线程执行一个完整的方法
        long endTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "耗时：" + (endTime - startTime) + "ms");
    }

    private static void init() {
        Properties props = new Properties();
//        props.put("bootstrap.servers", kafkaIpPort);
        props.put("bootstrap.servers", "172.16.108.6:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
        props.put("acks", "1");
        props.put("retries", "0");
        props.put("batch.size", "20971520");
        props.put("linger.ms", "0");
        props.put("buffer.memory", "33554432");
        props.put("max.request.size", "20971520");
        props.put("request.timeout.ms", "300000");
        props.put("max.block.ms", "10000");
/*        props.put("sasl.kerberos.service.name", "kafka");
        props.put("sasl.mechanism", "GSSAPI");
        props.put("security.protocol", "SASL_PLAINTEXT");*/

        producer01 =new KafkaProducer<>(props);
    }

    public void producerMethod(){
        int messageNo = 0;
        //创建源
//        File file = new File(inFilePath);
        File file = new File("test_20w.bcp");
        //选择流
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = null;
            long startTime = System.currentTimeMillis();
            while((line=reader.readLine())!=null){
                /**
                 * bcp数据转成相应json格式
                 */
                String[] strArray = null;
                strArray = line.split(",");
                StringBuilder sb = new StringBuilder();

                sb.append("{\"tablename\":\"part_kafka_0228_B\",\"partition\":\"20200402\",\"data\":{\"s_high\":\"");
                sb.append(strArray[0]).append("\",\"s_middle\":\"").append(strArray[0]).append("\",\"s_low\":\"")
                        .append(strArray[0]).append("\",\"l_high\":\"").append(strArray[0]).append("\",\"l_middle\":\"")
                        .append(strArray[0]).append("\",\"l_low\":\"").append(strArray[0]).append("\"}}");

                byte[] a = sb.toString().getBytes();
                ProducerRecord record = new ProducerRecord<>("autotest_fp_0228", a);
                producer01.send(record);
                messageNo++;
				System.out.println(messageNo + "-->" + Thread.currentThread().getName());
            }
            long endTime = System.currentTimeMillis();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(null!=reader) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


public class ProducerDemo {
//    static String kafkaIpPort = System.getProperty("kafkaIpPort");
//    static String inFilePath = System.getProperty("inFilePath");

    public static void main(String[] args) {
        First_thread one = new First_thread();
        new Thread(one,"线程一").start();
        /*Second_thread two = new Second_thread();
        new Thread(two,"线程二").start();*/

    }

}
