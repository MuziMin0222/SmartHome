package com.teacher.bean;

import java.io.Serializable;

/**
 * @program:SmartHome
 * @package:com.teacher.bean
 * @filename:Environment.java
 * @create:2019.09.17.09:14:37
 * @auther:李煌民
 * @description:.这是用于封装原始数据的类
 **/
public class Environment implements Serializable {
    //100|101|2|16|1|3|5d686f8802|1|1516323599977
    //发送端ID
    private String srcId;
    //树莓派id
    private String distId;
    //试验箱区域模块id1-8
    private String devId;
    //模块上的传感器地址
    private String sensorId;
    //传感器个数
    private Integer sensorCounter;
    //指令标号（3表示接收指令，16表示发送指令）
    private String cmdType;
    //数据类型
    private DataType dataType;
    //数据 16 进制,需要转换成 10 进制(如果是 传感器地址是16 前两个字节是温度数据,中间两个字节是湿度数据。如果不是 16 前两个字节就是对应的数据)
    private String date;
    //状态标示(默认为 1 表示成功)
    private Integer status;
    //采集时间(单位毫秒)
    private Long timestamp;

    public Environment() {
    }

    public String getSrcId() {
        return srcId;
    }

    public void setSrcId(String srcId) {
        this.srcId = srcId;
    }

    public String getDistId() {
        return distId;
    }

    public void setDistId(String distId) {
        this.distId = distId;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public Integer getSensorCounter() {
        return sensorCounter;
    }

    public void setSensorCounter(Integer sensorCounter) {
        this.sensorCounter = sensorCounter;
    }

    public String getCmdType() {
        return cmdType;
    }

    public void setCmdType(String cmdType) {
        this.cmdType = cmdType;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Environment{" +
                "srcId='" + srcId + '\'' +
                ", distId='" + distId + '\'' +
                ", devId='" + devId + '\'' +
                ", sensorId='" + sensorId + '\'' +
                ", sensorCounter=" + sensorCounter +
                ", cmdType='" + cmdType + '\'' +
                ", dataType=" + dataType.getName() +
                ", date='" + date + '\'' +
                ", status=" + status +
                ", timestamp=" + timestamp +
                '}';
    }

    public Environment(String srcId, String distId, String devId, String sensorId, Integer sensorCounter,
                       String cmdType, DataType dataType, String date, Integer status, Long timestamp) {
        this.srcId = srcId;
        this.distId = distId;
        this.devId = devId;
        this.sensorId = sensorId;
        this.sensorCounter = sensorCounter;
        this.cmdType = cmdType;
        this.dataType = dataType;
        this.date = date;
        this.status = status;
        this.timestamp = timestamp;
    }
}
