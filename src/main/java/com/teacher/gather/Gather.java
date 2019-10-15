package com.teacher.gather;

import com.teacher.bean.Environment;

import java.util.List;

/**
 * @program:SmartHome
 * @package:com.teacher.gather
 * @filename:Gather.java
 * @create:2019.09.17.09:30:32
 * @auther:李煌民
 * @description:.采集接口
 **/
public interface Gather {
    public abstract List<Environment> gather(String filePath);
}
