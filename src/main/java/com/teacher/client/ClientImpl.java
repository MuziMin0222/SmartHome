package com.teacher.client;

import com.teacher.backup.Backup;
import com.teacher.backup.BackupImpl;
import com.teacher.bean.Environment;
import com.teacher.config.ConfigFactory;
import com.teacher.gather.Gather;
import com.teacher.gather.GatherImpl;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Properties;

/**
 * @program:SmartHome
 * @package:com.teacher.client
 * @filename:ClientImpl.java
 * @create:2019.09.18.09:10:12
 * @auther:李煌民
 * @description:.客户端的实现类
 **/
public class ClientImpl implements Client{
    private Socket cs;
    private Backup backup = ConfigFactory.getBackup();
    private static Properties pre = ConfigFactory.getPre();
    private static Logger logger = Logger.getRootLogger();

    public ClientImpl(){}

    @Override
    public void send(List<Environment> envs) {
        OutputStream os = null;
        ObjectOutputStream oos = null;
        try {
            logger.info("正在努力建立连接。。。。");
            this.cs = new Socket(pre.getProperty("ip"),Integer.parseInt(pre.getProperty("port")));
            logger.info("客户端和服务器连接成功,正在发送数据...");
            //发送之前，需要从文件中读取备份的集合对象，和当前需要发送的集合对象合并到一起发送到服务器
            Object o = this.backup.load(pre.getProperty("clientSendEnvs"));
            if (o != null){
                if (o instanceof List){
                    List<Environment> backupEnvs = (List<Environment>) o;
                    envs.addAll(backupEnvs);
                }
            }

            os = this.cs.getOutputStream();
            oos = new ObjectOutputStream(os);

            oos.writeObject(envs);
            oos.flush();
            logger.info("客户端数据发送完成");
        } catch (Exception e) {
            e.printStackTrace();
            //需要备份当前正在发送的list集合
            this.backup.backup(envs,pre.getProperty("clientSendEnvs"));
        }finally {
            try {
                oos.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void close() {
        if (cs != null) {
            try {
                this.cs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Client client=ConfigFactory.getClient();
        Gather gather=ConfigFactory.getGather();
        List<Environment> envs=gather.gather(pre.getProperty("radwtmp"));
        client.send(envs);
        client.close();
    }
}
