package kz.project.carrental.base;

import junit.framework.TestCase;
import kz.project.carrental.base.impl.ConnectionPool;
import kz.project.carrental.manager.ConfigurationManager;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.Queue;

public class ConnectionPoolTest extends TestCase {

    private static final String POOL_SIZE = "poolSize";

    public ConnectionPoolTest(String name) {
        super(name);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        ConfigurationManager.getInstance().init("src\\resource\\config.properties");
        ConnectionPool.init(ConfigurationManager.getInstance());
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        ConnectionPool.getInstance().dispose();
    }

    public void testGetConnection() throws Exception {
        Connection connection = ConnectionPool.getInstance().getConnection();
        assertNotNull(connection);
        ConnectionPool.getInstance().closeConnection(connection);
    }

    public void testGetAllConnections() throws Exception {
        Queue<Connection> connections = new LinkedList<Connection>();
        int size = 0;

        size = ConnectionPool.getInstance().getSize();
        for (int i = 0; i < size; i++) {
            connections.add(ConnectionPool.getInstance().getConnection());
        }

        size = connections.size();
        for (int i = 0; i < size; i++) {
            ConnectionPool.getInstance().closeConnection(connections.poll());
        }

    }

}
