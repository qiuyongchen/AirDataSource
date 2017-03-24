package com.ilvoeqyc.ds;

import com.ilvoeqyc.ds.item.AirDBParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * User: qiuyongchen Nicolas.David
 * Date: 2017/3/22
 * Time: 下午11:47
 * Usage: 简单的数据源
 */
@Slf4j
public class AirDataSource implements DataSource {

    // 连接池的具体容器
    private LinkedList<Connection> pool = new LinkedList<Connection>();
    private String url = null;
    private String db = null;
    private String user = null;
    private String password = null;

    AirDataSource(AirDBParam dbParam) {
        String driver = dbParam.getDriver();
        url = dbParam.getUrl();
        db = dbParam.getDatabase();
        user = dbParam.getUser();
        password = dbParam.getPassword();
        int initPoolSize = dbParam.getInitPoolSize();

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            log.error("find driver fail, driver: {}", driver, e);
        }

        for (int i = 0; i < initPoolSize; i++) {
            try {
                Connection conn = getNewConnection();
                pool.add(conn);
                log.info("init pool: create connection success, conn: {}", conn);
            } catch (SQLException e) {
                log.error("init pool: create new connection fail", e);
            }
        }
        log.info("the size of pool: {}", pool.size());
    }

    private Connection getNewConnection() throws SQLException {
        return DriverManager.getConnection(url + db, user, password);
    }

    /**
     * <p>Attempts to establish a connection with the data source that
     * this <code>DataSource</code> object represents.
     *
     * @return a connection to the data source
     * @throws SQLException if a database access error occurs
     */
    public Connection getConnection() throws SQLException {
        if (CollectionUtils.isNotEmpty(pool)) {
            final Connection conn = pool.removeLast();
            log.info("will return a connection from pool, conn: {}", conn);

            // 创建代理对象需要：
            // 1.类加载器
            // 2.对象的接口
            // 3.对象调用的handle方法
            return (Connection) Proxy.newProxyInstance(AirDataSource.class.getClassLoader(),
                    new Class[]{Connection.class}, new InvocationHandler() {

                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            // 如果吃瓜群众调用了Connection.close()方法，则把连接归还给池
                            if (method.getName().equals("close")) {
                                pool.addFirst(conn);
                                log.info("connection.close() is invoked, conn: {}, the size of pool: {}",
                                        conn, pool.size());
                                return null;
                            }
                            log.info("invoke other method: ({}) of conn: {}, the size fo pool: {}",
                                    method.getName(), conn, pool.size());
                            return method.invoke(conn, args);
                        }
                    });
        } else {
            log.error("pool is empty, get new connection for you");
            return getNewConnection();
        }
    }

    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    public void setLoginTimeout(int seconds) throws SQLException {

    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

}
