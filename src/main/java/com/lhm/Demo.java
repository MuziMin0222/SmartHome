package com.lhm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

//将数据写入到mysql数据库中

public class Demo {
    public static void main(String[] args) throws IOException, SQLException {
        InputStream is = Demo.class.getClassLoader().getResourceAsStream("data.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        ArrayList<Environment> list = new ArrayList<>();

        jdbcUtil util = jdbcUtil.getInstance();
        String sql = "insert into smarthome values(default,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = util.gePreparedStatement(sql);

        String line;
        while ((line = br.readLine()) != null){
            Environment en = new Environment();
            String[] StrArr = line.split("\\|");
            for (int i = 0; i < StrArr.length; i++) {
                switch (i){
                    case 0: en.setSrc_id(StrArr[0]);
                        ps.setString(1,StrArr[0]);
                        break;
                    case 1: en.setDist_id(StrArr[1]);
                        ps.setString(2,StrArr[1]);
                        break;
                    case 2: en.setDev_id(StrArr[2]);
                        ps.setString(3,StrArr[2]);
                        break;
                    case 3: en.setSensor_id(StrArr[3]);
                        ps.setString(4,StrArr[3]);
                        break;
                    case 4: en.setCounter(StrArr[4]);
                        ps.setString(5,StrArr[4]);
                        break;
                    case 5: en.setCmd_type(StrArr[5]);
                        ps.setString(6,StrArr[5]);
                        break;
                    case 6: en.setDate(StrArr[6]);
                        ps.setString(7,StrArr[6]);
                        break;
                    case 7: en.setStatus(StrArr[7]);
                        ps.setString(8,StrArr[7]);
                        break;
                    case 8: en.setGather(StrArr[8]);
                        ps.setString(9,StrArr[8]);
                        break;
                }
            }
            ps.addBatch();
            ps.executeBatch();
            ps.executeUpdate();
            list.add(en);
        }

        list.forEach(environment -> {
            System.out.println(environment);
        });
    }
}
