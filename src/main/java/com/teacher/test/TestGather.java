package com.teacher.test;

import com.teacher.bean.Environment;
import com.teacher.config.ConfigFactory;
import com.teacher.gather.Gather;
import com.teacher.gather.GatherImpl;
import org.junit.Test;

import java.util.List;

/**
 * @program:SmartHome
 * @package:com.teacher.test
 * @filename:TestGather.java
 * @create:2019.09.17.10:02:51
 * @auther:李煌民
 * @description:.
 **/
public class TestGather {

    @Test
    public void testGather(){
        Gather gather = ConfigFactory.getGather();
        List<Environment> envs = gather.gather(ConfigFactory.getPre().getProperty("radwtmp"));
        System.out.println(envs.size());
        int count = 0;

//        for (Environment env : envs) {
//            count++;
//            System.out.println(count + "===" + env);
//        }
    }
}
