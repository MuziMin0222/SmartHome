package com.teacher.logger;

import org.apache.log4j.Logger;

/**
 * @program:SmartHome
 * @package:com.teacher.logger
 * @filename:EmdLogger.java
 * @create:2019.09.20.11:14:12
 * @auther:李煌民
 * @description:.
 **/
public class EmdLogger {
    public static Logger getLog(){
        Logger logger = Logger.getRootLogger();
        return logger;
    }
}
