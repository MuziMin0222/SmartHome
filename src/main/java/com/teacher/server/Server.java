package com.teacher.server;

import com.teacher.bean.Environment;

import java.io.ObjectInputStream;
import java.util.List;

/**
 * @program:SmartHome
 * @package:com.teacher.server
 * @filename:Server.java
 * @create:2019.09.18.09:22:39
 * @auther:李煌民
 * @description:.服务器端程序接口
 **/
public interface Server {
    List<Environment> receive(ObjectInputStream ois);

    void close();
}
