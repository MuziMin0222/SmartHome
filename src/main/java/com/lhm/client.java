package com.lhm;

import com.teacher.bean.Environment;
import com.teacher.gather.Gather;
import com.teacher.gather.GatherImpl;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;

/**
 * @program:SmartHome
 * @package:com.lhm
 * @filename:client.java
 * @create:2019.09.17.14:15:49
 * @auther:李煌民
 * @description:.网络模块的客户端
 **/
public class client {
    public static void main(String[] args) throws IOException {
        Socket cs = new Socket("127.0.0.1", 12345);

        Gather gather = new GatherImpl();
        List<Environment> list = gather.gather("D:\\code\\workspace_IdeaUi\\SmartHome\\src\\main\\resources\\data.txt");

        OutputStream os = cs.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);

        oos.writeObject(list);
        oos.flush();

        os.close();
        oos.close();
    }
}
