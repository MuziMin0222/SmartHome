package com.teacher.store;

import com.teacher.backup.Backup;
import com.teacher.backup.BackupImpl;
import com.teacher.bean.Environment;
import com.teacher.config.ConfigFactory;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * @program:SmartHome
 * @package:com.teacher.store
 * @filename:StoreImpl.java
 * @create:2019.09.17.11:06:02
 * @auther:李煌民
 * @description:.入库程序的实现类
 **/
public class StoreImpl implements  Store {
    private Backup backup = ConfigFactory.getBackup();
    private static Properties pre = ConfigFactory.getPre();
    private static Logger logger = Logger.getRootLogger();

    public StoreImpl(){}

    @Override
    public void store(List<Environment> envs) {
        Connection conn = null;
        try {
            Class.forName(pre.getProperty("driver"));

            String url = pre.getProperty("url");
            String user = pre.getProperty("user");
            String passwd = pre.getProperty("password");
            conn = DriverManager.getConnection(url, user, passwd);

            logger.info("与数据库建立连接成功。。。。");

            String sql = "insert into emdc.envs value(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            int count = 0;

            //需要在jdbc中设置手动提交事务
            conn.setAutoCommit(false);

            //保存到数据库之前，需要从备份文件中加载上次未保存成功的集合对象。然后将集合对象和本次
            //需要保存的集合对象合并到一起，然后保存到数据库
            Object o = backup.load(pre.getProperty("storeEnvs"));
            if (o != null){
                if (o instanceof List){
                    List<Environment> backup = (List<Environment>) o;
                    envs.addAll(backup);
                }
            }

            for (Environment env : envs){
                ps.setString(1,env.getSrcId());
                ps.setString(2,env.getDistId());
                ps.setString(3,env.getDevId());
                ps.setString(4,env.getSensorId());
                ps.setInt(5,env.getSensorCounter());
                ps.setString(6,env.getCmdType());
                ps.setString(7,env.getDataType().toString());
                ps.setString(8,env.getDate());
                ps.setInt(9,env.getStatus());
                ps.setLong(10,env.getTimestamp());

                count++;
                ps.addBatch();

                if (count % Integer.parseInt(pre.getProperty("batchSize")) == 0) {
                    ps.executeBatch();
                    conn.commit();
                }
            }
            ps.executeBatch();

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();

            //备份
            this.backup.backup(envs,pre.getProperty("storeEnvs"));

            //事务回滚,已经提交的事务不能进行回滚
            if (conn != null){
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
