package com.notfound.pool;

import com.notfound.config.Config;
import com.notfound.tools.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Properties;

/**
 * 连接池
 *
 * @author 404NotFoundx
 * @version 1.0.0
 * @date 2019/11/11 16:31
 * @since 1.8
 */
public class ConnectionPool {

    /**
     * 驱动对象
     */
    private static Driver driver;

    /**
     * 单例模式---实例对象
     */
    private static ConnectionPool instance;

    /**
     * 连接池最小空间
     */
    private static int MIN_SIZE = Config.getMinSize();

    /**
     * 最大空间
     */
    private static int MAX_SIZE = Config.getMaxSize();

    private static LinkedList<Connection> conns = new LinkedList<>();

    static {
        for (int i = 0; i < MIN_SIZE; i++) {
            conns.add(createConnection());
        }
    }

    public static ConnectionPool getPool() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    /**
     * 获取一个连接
     * @return
     */
    public Connection getConnection() {
        synchronized (conns) {
            if (!conns.isEmpty()) {
                final Connection connection = conns.removeFirst();
                return (Connection) Proxy.newProxyInstance(ConnectionPool.class.getClassLoader(),
                        connection.getClass().getInterfaces(),
                        new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                if (!"close".equals(method.getName())) {
                                    return method.invoke(proxy, args);
                                } else {
                                    conns.add(connection);
                                    return null;
                                }
                            }
                        });
            } else {
                throw new RuntimeException("数据库链接繁忙，请稍后再试。");
            }
        }
    }

    /**
     * 创建连接
     * @return
     */
    private static Connection createConnection() {
        // 连接对象
        Connection connection = null;
        try {
            if (driver == null) {
                driver = (Driver) Class.forName(Config.getDriver()).newInstance();
            }
            // 创建连接
            Properties info = new Properties();
            info.setProperty("user", Config.getUsername());
            info.setProperty("password", Config.getPassword());
            connection = driver.connect(Config.getUrl(), info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void check() {

    }

}
