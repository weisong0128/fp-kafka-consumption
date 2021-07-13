package com.fiberhome.kafka;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * description: 描述
 * author: ws
 * time: 2019/12/12 15:14
 */
public class StartConsumption {

    static String JAVA_HOME = System.getProperty("JAVA_HOME");


    public static String exeCmd(String commandStr)
    {
        String result = null;
        try {
            String[] cmd = { "/bin/sh", "-c", commandStr };
            Process ps = Runtime.getRuntime().exec(cmd);    //执行cmd中的命令

            BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null)
            {
                sb.append(line).append("\n");
            }

            result = sb.toString();
        }
        catch (Exception localException) {
        }
        return result;
    }

    public static void main(String[] args) {

        String data_path_commd = "data_path=`grep '^export CL_HDFS_PATH=' /opt/software/lsql/config/site/lsql-env.sh |cut -d '=' -f 2`";
        exeCmd(data_path_commd);



//        init();
        long startTime = System.currentTimeMillis();
//        producerMethod();	// 一个线程执行一个完整的方法
        long endTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + "耗时：" + (endTime - startTime) + "ms");



    }
}
