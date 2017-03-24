package com.ilvoeqyc.ds;

import com.ilvoeqyc.ds.item.AirDBParam;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.sql.Connection;

/**
 * User: qiuyongchen Nicolas.David
 * Date: 2017/3/24
 * Time: 下午1:01
 * Usage: xxx
 */

@Slf4j
public class AirDataSourceFactoryTest {
    @Test
    public void getDataSource() throws Exception {
        AirDBParam param = new AirDBParam();
        param.setUrl("jdbc:mysql://10.1.77.21:3306/");
        param.setDatabase("OverseasProduct");
        param.setUser("overseasproduct");
        param.setPassword("dp!@k5nYT92XJ");
        AirDataSource ds = AirDataSourceFactory.getDataSource(param);
        if (ds != null) {
            for (int i = 0; i < 6; i++) {
                Connection conn = ds.getConnection();
                log.info("conn: {}", conn);
            }
        }
    }

}