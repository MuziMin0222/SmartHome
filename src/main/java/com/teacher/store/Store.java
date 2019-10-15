package com.teacher.store;

import com.teacher.bean.Environment;

import java.util.List;

/**
 * @program:SmartHome
 * @package:com.teacher.store
 * @filename:Store.java
 * @create:2019.09.17.11:05:05
 * @auther:李煌民
 * @description:.入库程序接口
 **/
public interface Store {
    void store(List<Environment> envs);
}
