package com.teacher.test;

import com.teacher.bean.Environment;
import com.teacher.gather.Gather;
import com.teacher.gather.GatherImpl;
import com.teacher.store.Store;
import com.teacher.store.StoreImpl;
import org.junit.Test;

import java.util.List;

/**
 * @program:SmartHome
 * @package:com.teacher.test
 * @filename:TestStore.java
 * @create:2019.09.17.11:29:23
 * @auther:李煌民
 * @description:.测试入库
 **/
public class TestStore {

    @Test
    public void testStore(){
        Gather gather = new GatherImpl();

        List<Environment> list = gather.gather("D:\\code\\workspace_IdeaUi\\SmartHome\\src\\main\\resources\\data.txt");

        Store store = new StoreImpl();
        store.store(list);
    }
}
