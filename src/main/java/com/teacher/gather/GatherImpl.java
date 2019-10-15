package com.teacher.gather;

import com.teacher.backup.Backup;
import com.teacher.backup.BackupImpl;
import com.teacher.bean.DataType;
import com.teacher.bean.Environment;
import com.teacher.config.ConfigFactory;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @program:SmartHome
 * @package:com.teacher.gather
 * @filename:GatherImpl.java
 * @create:2019.09.17.09:32:19
 * @auther:李煌民
 * @description:.采集程序的实现类
 **/
public class GatherImpl implements Gather {
    private static Properties pre = ConfigFactory.getPre();
    private Backup backup = ConfigFactory.getBackup();
    private static Logger logger = Logger.getRootLogger();

    public GatherImpl() {}

    public void setData(Environment env, String[] strs){
        env.setSrcId(strs[0]);
        env.setDistId(strs[1]);
        env.setDevId(strs[2]);
        env.setSensorId(strs[3]);
        env.setSensorCounter(Integer.parseInt(strs[4]));
        env.setCmdType(strs[5]);
        env.setStatus(Integer.parseInt(strs[7]));
        env.setTimestamp(Long.parseLong(strs[8]));
    }

    @Override
    public List<Environment> gather(String filePath) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            String line;
            List<Environment> envs = new ArrayList<>();

            //在采集之前，读取备份文件，转化为行数，然后跳过该行数再进行采集
            Object o = this.backup.load(pre.getProperty("gatheredLines"));
            int gatheredLines = 0;
            if (o != null){
                if (o instanceof  Integer){
                    gatheredLines = (int)o;
                }
            }

            System.out.println("需要跳过的行数" + gatheredLines);

            int count = gatheredLines;
            int currentGatherLines = 0;
            int count_16 = 0;
            int count_256 = 0;
            int count_1280 = 0;
            while ((line = br.readLine()) !=null){
                //跳过已经采集过的数据
                if (count-- > 0){
                    continue;
                }

                //记录本次采集过的行数
                currentGatherLines++;

                String[] strs = line.split("[|]");

                if ("16".equals(strs[3])){
                    //温度
                    Environment env = new Environment();
                    this.setData(env,strs);
                    env.setDataType(DataType.TMP);
                    String s = strs[6].substring(0, 4);
                    float value = (float) (Integer.parseInt(s, 16) * 0.00268127 - 46.85);
                    String data = String.format("%.2f", value);
                    env.setDate(data);
                    envs.add(env);

                    //湿度
                    Environment env1 = new Environment();
                    this.setData(env1,strs);
                    env1.setDataType(DataType.HUM);
                    value = (float) (Integer.parseInt(strs[6].substring(4,8),16) * 0.00190735 - 6);
                    //格式化输出，保留float后两位小数
                    data = String.format("%.2f",value);
                    env1.setDate(data);
                    envs.add(env1);

                    count_16++;
                }
                if ("256".equals(strs[3])) {
                    //光照强度
                    Environment env = new Environment();
                    this.setData(env,strs);
                    env.setDataType(DataType.ILL);
                    env.setDate(String.valueOf(Integer.parseInt(strs[6],16)));
                    envs.add(env);

                    count_256++;
                }
                if ("1280".equals(strs[3])){
                    //二氧化碳浓度
                    Environment env = new Environment();
                    this.setData(env,strs);
                    env.setDataType(DataType.CO2);
                    env.setDate(String.valueOf(Integer.parseInt(strs[6],16)));
                    envs.add(env);

                    count_1280++;
                }
            }

            logger.info("温度和湿度，传感器为16的数据有：" + count_16);
            logger.info("光照强度，传感器为256的数据有：" + count_256);
            logger.info("二氧化碳浓度，传感器为1280的数据有：" + count_1280);

            //备份行数数据,gatheredLines + currentGatheredLines
            int allLines = gatheredLines+currentGatherLines;
            this.backup.backup(allLines,pre.getProperty("gatheredLines"));

            return envs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
