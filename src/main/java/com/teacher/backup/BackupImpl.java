package com.teacher.backup;

import com.teacher.config.ConfigFactory;
import com.teacher.logger.EmdLogger;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Properties;

/**
 * @program:SmartHome
 * @package:com.teacher.backup
 * @filename:BackupImpl.java
 * @create:2019.09.19.09:12:35
 * @auther:李煌民
 * @description:.备份程序的实现类
 **/
public class BackupImpl implements Backup{
    private static Properties pre = ConfigFactory.getPre();
    ////定义备份文件的目录位置
    private static final String ROOT_PATH = pre.getProperty("rootpath");
    private File rootDir;
    private static Logger logger = Logger.getRootLogger();

    public BackupImpl() {
        this.rootDir = new File(ROOT_PATH);
        if (!rootDir.exists()){
            //目录不存在创建多级目录
            rootDir.mkdirs();
        }
    }

    @Override
    public void backup(Object o, String fileName) {
        if (o == null){
            System.out.println("需要备份的对象不能为空");
            throw new RuntimeException("需要备份的对象不能为空");
        }
        File file = new File(this.rootDir,fileName);

        FileOutputStream fos=null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);

            oos.writeObject(o);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                oos.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object load(String fileName) {
        File file = new File(this.rootDir,fileName);

        if (!file.exists()){
            return null;
        }

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);

            Object o = ois.readObject();
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if (ois!=null && fis!=null){
                try {
                    ois.close();
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
