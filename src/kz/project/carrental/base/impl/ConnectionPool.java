package kz.project.carrental.base.impl;

import kz.project.carrental.base.exception.ConnectionException;
import kz.project.carrental.manager.ConfigurationManager;
import kz.project.carrental.base.ConnectionSource;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool implements ConnectionSource {

    private static final Logger LOGGER = Logger.getLogger(ConnectionPool.class);

    //message
    private static final String RETURN_CONNECTION_NOT_ADD = "return.connection.not.add";
    private static final String TRYING_RETURN_CLOSED_CONNECTION = "trying.return.closed.connection";
    private static final String ERROR_APPEND_NEW_CONNECTION = "Error append new connection";
    private static final String ERROR_LOAD_CLASS_DRIVER = "Error load class driver";
    private static final String ERROR_CREATE_CONNECTION = "Error create connection";

    private static final String USER = "user";
    private static final String PASSWORD = "password";
    private static final String URL = "url";
    private static final String POOL_SIZE = "poolSize";

    private BlockingQueue<Connection> connections;

    private static String url;
    private static Properties prop;

    private static ConnectionPool instance = new ConnectionPool();

    static {
        String classDriver = "com.mysql.jdbc.Driver";
        try {
            Class.forName(classDriver);
        } catch (ClassNotFoundException e) {
            LOGGER.error(new ConnectionException(ERROR_LOAD_CLASS_DRIVER + classDriver));
        }


    }

    private ConnectionPool() {
    }

    /**
     * Retunr size of pool.
     *
     * @return size of pool.
     */
    public int getSize() {
        int size = 0;
        if (connections != null) {
            size = connections.size();
        }
        return size;
    }

    /**
     * Initializes the connection pool on passed properties.
     *
     * @param properties to initialize.
     */
    public static void init(Properties properties) {
        ConnectionPool.prop = new Properties();
        ConnectionPool.prop.put(USER, properties.getProperty(USER));
        ConnectionPool.prop.put(PASSWORD, properties.getProperty(PASSWORD));
        ConnectionPool.url = properties.getProperty(URL);
        int poolSize = Integer.valueOf(ConfigurationManager.getInstance().getProperty(POOL_SIZE));
        ConnectionPool.getInstance().connections = new ArrayBlockingQueue<Connection>(poolSize);
    }

    /**
     * Returns instance of connection pool.
     *
     * @return instance of connection pool.
     */
    public static ConnectionPool getInstance() {
        return instance;
    }

    @Override
    public Connection getConnection() throws ConnectionException {
        Connection connection = null;
        try {
            connection = connections.poll(); //TODO poll is not thread safe
            if (connection == null) {
                connection = appendNewConnection();
            } else if (connection.isClosed()) {
                connections.remove(connection);
                connection = appendNewConnection();
            }
        } catch (SQLException e) {
            throw new ConnectionException(e);
        }
        return connection;
    }

    /**
     * Appends new connection into the connection pool.
     *
     * @return appended connection.
     * @throws ConnectionException - if can not append new connection.
     */
    private Connection appendNewConnection() throws ConnectionException {
        Connection connection = createConnection();
        if (connection != null) {
            connections.offer(connection);
        } else {
            throw new ConnectionException(ERROR_APPEND_NEW_CONNECTION + connection);
        }
        return connection;
    }


    /**
     * Create new connection.
     *
     * @return new connection.
     * @throws ConnectionException - if can not create new connection.
     */
    private Connection createConnection() throws ConnectionException {
        Connection resultConn = null;
        try {
            resultConn = DriverManager.getConnection(url, prop);
        } catch (SQLException e) {
            throw new ConnectionException(ERROR_CREATE_CONNECTION, e);
        }
        return resultConn;
    }

    @Override
    public boolean closeConnection(Connection connection) {
        boolean result = false;
        boolean closed = true;

        if (connection == null) {
            return false;
        }
        try {
            closed = connection.isClosed();
        } catch (SQLException e) {
            ConnectionException ex = new ConnectionException(e);
            LOGGER.error(ex);
        }

        //TODO ???
        if (!closed) {
            if (!connections.offer(connection)) {
                ConnectionException ex = new ConnectionException(RETURN_CONNECTION_NOT_ADD + connection);
                LOGGER.error(ex);
            } else {
                result = true;
            }
        } else {
            ConnectionException ex = new ConnectionException(TRYING_RETURN_CLOSED_CONNECTION + connection);
            LOGGER.error(ex);
        }

        return result;
    }

    /**
     * Dispose of all resources used by connection pool. Closes all connection.
     */
    public void dispose() {
        Connection connection;
        while ((connection = connections.poll()) != null) {
            try {
                //TODO auto commit on close connection?
                if (!connection.getAutoCommit()) {
                    connection.commit();
                }
                connection.close();
            } catch (SQLException e) {
                ConnectionException ex = new ConnectionException(e);
                LOGGER.error(ex);
            }
        }
    }
}
