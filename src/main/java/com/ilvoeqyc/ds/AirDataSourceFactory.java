package com.ilvoeqyc.ds;

import com.ilvoeqyc.ds.item.AirDBParam;
import lombok.extern.slf4j.Slf4j;

/**
 * User: qiuyongchen Nicolas.David
 * Date: 2017/3/23
 * Time: 上午1:21
 * Usage: 简单数据源的工厂类
 */
@Slf4j
public class AirDataSourceFactory {

    private static AirDataSource ds;

    private AirDataSourceFactory() {

    }

    /**
     * 获取数据源
     *
     * @param dbParam 数据库连接参数
     * @return 数据源
     */
    public static AirDataSource getDataSource(AirDBParam dbParam) {
        if (!isParamValid(dbParam)) {
            return null;
        }
        if (ds == null) {
            initAirDataSource(dbParam);
        }
        return ds;
    }

    private static void initAirDataSource(AirDBParam dbParam) {
        synchronized (AirDataSourceFactory.class) {
            if (ds == null) {
                ds = new AirDataSource(dbParam);
            }
        }
    }

    /**
     * 验证数据库的连接参数
     *
     * @param dbParam 参数
     * @return 是否合法
     */
    private static boolean isParamValid(AirDBParam dbParam) {
        if (dbParam == null) {
            log.error("AirDBParam is null");
            return false;
        }

        if (dbParam.getDriver().equals("")) {
            log.error("driver is invalid");
            return false;
        }

        if (dbParam.getUrl().equals("")) {
            log.error("url is invalid");
            return false;
        }

        if (dbParam.getDatabase() == null) {
            log.error("database is null");
            return false;
        }

        if (dbParam.getUser() == null) {
            log.error("user is null");
            return false;
        }

        if (dbParam.getPassword() == null) {
            log.error("user is null");
            return false;
        }

        if (dbParam.getInitPoolSize() <= 0) {
            log.error("initPoolSize is invalid");
            return false;
        }

        return true;
    }
}
