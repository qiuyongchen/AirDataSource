package com.ilvoeqyc.ds.item;

import lombok.Data;

/**
 * User: qiuyongchen Nicolas.David
 * Date: 2017/3/24
 * Time: 下午1:06
 * Usage: 数据库的连接参数
 */
@Data
public class AirDBParam {

    /**
     * 数据库驱动
     *   默认使用com.mysql.jdbc.Driver
     */
    private String driver = "com.mysql.jdbc.Driver";

    /**
     * 数据库连接url
     *   默认使用jdbc:mysql://localhost:3306/
     */
    private String url = "jdbc:mysql://localhost:3306/";

    /**
     * 数据库名称
     *   必填
     */
    private String database;

    /**
     * 用户名
     *   必填
     */
    private String user;

    /**
     * 密码
     *   必填
     */
    private String password;

    /**
     * 数据库连接池初始大小
     *   默认大小为5
     *
     */
    private int initPoolSize = 5;

}
