package com.lhm;

import java.io.Serializable;

public class Environment implements Serializable{

    private String src_id;
    private String dist_id;
    private String dev_id;
    private String sensor_id;
    private String counter;
    private String cmd_type;
    private String date;
    private String status;
    private String gather;

    public String getSrc_id() {
        return src_id;
    }

    public void setSrc_id(String src_id) {
        this.src_id = src_id;
    }

    public String getDist_id() {
        return dist_id;
    }

    public void setDist_id(String dist_id) {
        this.dist_id = dist_id;
    }

    public String getDev_id() {
        return dev_id;
    }

    public void setDev_id(String dev_id) {
        this.dev_id = dev_id;
    }

    public String getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(String sensor_id) {
        this.sensor_id = sensor_id;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getCmd_type() {
        return cmd_type;
    }

    public void setCmd_type(String cmd_type) {
        this.cmd_type = cmd_type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGather() {
        return gather;
    }

    public void setGather(String gather) {
        this.gather = gather;
    }

    @Override
    public String toString() {
        return "Environment{" +
                "src_id='" + src_id + '\'' +
                ", dist_id='" + dist_id + '\'' +
                ", dev_id='" + dev_id + '\'' +
                ", sensor_id='" + sensor_id + '\'' +
                ", counter='" + counter + '\'' +
                ", cmd_type='" + cmd_type + '\'' +
                ", date='" + date + '\'' +
                ", status='" + status + '\'' +
                ", gather='" + gather + '\'' +
                '}';
    }
}
