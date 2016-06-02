package kz.project.carrental.dao.impl.mysql;

import junit.framework.TestCase;
import kz.project.carrental.base.impl.ConnectionPool;
import kz.project.carrental.entity.Entity;
import kz.project.carrental.manager.ConfigurationManager;

import java.sql.Connection;

public abstract class MySqlDaoTest<T extends Entity> extends TestCase {

    protected Connection connection;
    protected T testEntity;
    protected MySqlDAO<T> abstractDAO;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        ConfigurationManager.getInstance().init("src\\resource\\config.properties");
        ConnectionPool.init(ConfigurationManager.getInstance());

        connection = ConnectionPool.getInstance().getConnection();
        abstractDAO.setConnection(connection);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        ConnectionPool.getInstance().closeConnection(connection);
        ConnectionPool.getInstance().dispose();
    }

    public void testCreate() throws Exception {
        T createEntity = abstractDAO.create(testEntity);
        abstractDAO.delete(createEntity);
    }

    public void testSave() throws Exception {
        T createEntity = abstractDAO.save(testEntity);
        abstractDAO.delete(createEntity);
    }

    public void testUpdate() throws Exception {
        T createEntity = abstractDAO.create(testEntity);
        createEntity = abstractDAO.update(createEntity);
        abstractDAO.delete(createEntity);
    }

    public void testFindById() throws Exception {
        T createEntity = abstractDAO.create(testEntity);
        T findVendor = abstractDAO.findById(createEntity.getId());
        abstractDAO.delete(createEntity);
        assertEquals(createEntity, findVendor);
    }
}
