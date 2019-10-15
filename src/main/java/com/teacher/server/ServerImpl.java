package com.teacher.server;

import com.teacher.bean.Environment;
import com.teacher.config.ConfigFactory;
import com.teacher.store.Store;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Properties;

/**
 * @program:SmartHome
 * @package:com.teacher.server
 * @filename:ServerImpl.java
 * @create:2019.09.18.09:23:41
 * @auther:李煌民
 * @description:.服务器端程序的实现类
 **/
public class ServerImpl implements Server {
    private static Properties pre = ConfigFactory.getPre();
    private static Logger logger = Logger.getRootLogger();

    @Override
    public List<Environment> receive(ObjectInputStream ois) {
        try {
            Object o = ois.readObject();

            if (o instanceof List){
                List<Environment> envs = (List<Environment>) o;
                return envs;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {

    }

    public static void main(String[] args) throws IOException {
        logger.info("等待客户端连接。。。。");
        ServerSocket ss = new ServerSocket(Integer.parseInt(pre.getProperty("port")));
        System.out.println("服务器已启动。。。。");

        while (true){
            Socket cs = ss.accept();
            logger.info("客户端接收连接。。。准备接收数据");
            System.out.println(cs.getInetAddress().getHostAddress() + "链接进来啦");

            new Thread(()->{
                try {
                    InputStream is = cs.getInputStream();
                    ObjectInputStream ois = new ObjectInputStream(is);

                    List<Environment> envs = new ServerImpl().receive(ois);

                    Store store = ConfigFactory.getStore();
                    store.store(envs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
