package com.lhm;

import com.teacher.bean.Environment;
import com.teacher.store.Store;
import com.teacher.store.StoreImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @program:SmartHome
 * @package:com.lhm
 * @filename:server.java
 * @create:2019.09.17.14:25:30
 * @auther:李煌民
 * @description:.网络模块的服务端
 **/
public class server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(12345);
        while (true){
            Socket ss = serverSocket.accept();

            new Thread(()->{
                try {
                    ObjectInputStream ois = new ObjectInputStream(ss.getInputStream());

                    Object o = ois.readObject();
                    List<com.teacher.bean.Environment> envs = null;
                    if (o instanceof List){
                        envs = (List<Environment>) o;
                    }

                    StoreImpl store = new StoreImpl();
                    store.store(envs);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
