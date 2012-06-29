package edu.ecnu.bugIR.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @description: 日志
 * @author: "Song Leyi" 2012-6-28
 * @version: 1.0
 * @modify:
 */
public class TestLoggerFactory {

    private static Map<String, Logger> loggerMap = new ConcurrentHashMap<String, Logger>();

    /**
     * 根据数据源获取日志工厂
     * @param
     * @return Logger
     * 
     */
    public static Logger getLogger(String serviceName) {

        Logger logger = loggerMap.get(serviceName);

        if (logger == null) {
            logger = LoggerFactory.getLogger(serviceName);
            loggerMap.put(serviceName, logger);
        }

        return logger;
    }

    /**
     * 获取通用的日志工厂
     * @param
     * @return Logger
     * 
     */
    public static Logger getLogger() {

        Logger logger = loggerMap.get("common");

        if (logger == null) {
            logger = LoggerFactory.getLogger("testLogger");
            loggerMap.put("common", logger);
        }

        return logger;
    }

}
