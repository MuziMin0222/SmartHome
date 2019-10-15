package com.teacher.config;

import com.teacher.backup.Backup;
import com.teacher.client.Client;
import com.teacher.client.ClientImpl;
import com.teacher.gather.Gather;
import com.teacher.server.Server;
import com.teacher.store.Store;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @program:SmartHome
 * @package:com.teacher.config
 * @filename:ConfigFactory.java
 * @create:2019.09.20.09:15:17
 * @auther:李煌民
 * @description:.使用工厂模式创建各个类的对象
 **/
public class ConfigFactory {
    private static Map<String,Object> map = new HashMap<>();
    private static Properties pre = new Properties();

    static {
        try {
            //使用Dom4j解析XML文件
            SAXReader reader = new SAXReader();

            Document doc = reader.read(ConfigFactory.class.getResourceAsStream("/emdc.xml"));

            List<Element> es = doc.getRootElement().elements();

            for (Element e : es) {
                List<Element> e1s = e.elements();
                for (Element e1 : e1s) {
                    String name = e1.getName();
                    String stringValue = e1.getStringValue();
                    pre.setProperty(name,stringValue);
                }
                Attribute attr = e.attribute("class");
                String value = attr.getStringValue();
                Object o = Class.forName(value).newInstance();

                map.put(e.getName(),o);
            }
//            map.forEach((k,v)->{
//                System.out.println(k+ "----" + v);
//            });
//
//            System.out.println("==========================");
//            pre.forEach((k,v)->{
//                System.out.println(k+ "====" + v);
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Properties getPre(){
        return pre;
    }

    public static Client getClient(){
        return (Client) map.get("client");
    }

    public static Server getServer(){
        return (Server) map.get("server");
    }

    public static Gather getGather(){
        return (Gather)map.get("gather");
    }

    public static Store getStore(){
        return (Store)map.get("store");
    }

    public static Backup getBackup(){
        return (Backup)map.get("backup");
    }
}
