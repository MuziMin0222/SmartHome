package com.teacher.client;

import com.teacher.bean.Environment;

import java.util.List;

/**
 * @program:SmartHome
 * @package:com.teacher.client
 * @filename:Client.java
 * @create:2019.09.18.09:08:45
 * @auther:李煌民
 * @description:.客户端接口
 **/
public interface Client {
    void send(List<Environment> envs);

    void close();

}
