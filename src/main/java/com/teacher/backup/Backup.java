package com.teacher.backup;

/**
 * @program:SmartHome
 * @package:com.teacher.backup
 * @filename:Backup.java
 * @create:2019.09.19.09:09:08
 * @auther:李煌民
 * @description:.备份程序的接口
 **/
public interface Backup {
    //将对象o备份到文件中
    void backup(Object o,String fileName);

    //从文件中加载备份数据对象
    Object load(String fileName);
}
