package kz.project.carrental.dao.impl.mysql;

import kz.project.carrental.dao.exception.DAOException;
import kz.project.carrental.entity.Entity;
import kz.project.carrental.dao.AbstractDAO;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class MySqlDAO<T extends Entity> implements AbstractDAO<T> {

    private static final Logger LOGGER = Logger.getLogger(MySqlDAO.class);

    //message
    private static final String NULL_STATEMENT_TO_CLOSE = "Null statement to close";
    private static final String NULL_RESULT_SET_TO_CLOSE = "Null result set to close";
    private static final String ERROR_WHILE_CHECK_CONNECTION = "Error while check connection";

    protected static final String ERROR_NO_CONNECTION = "No connection";
    protected static final String ERROR_DELETE = "Error delete ";
    protected static final String IN_BASE = " in base ";
    protected static final String NULL_PREPARED_STATEMENT_TO_FILL = "Null prepared statement to fill.";

    //SQL Query - General operation
    protected String SQL_FIND_ALL;
    protected String SQL_FIND_BY_ID;
    protected String SQL_FIND_BY_DATA;
    protected String SQL_CREATE;
    protected String SQL_DELETE_BY_ID;
    protected String SQL_UPDATE;

    protected Connection connection;

    /**
     * Creates DAO with null data base connection.
     */
    protected MySqlDAO() {

    }

    /**
     * Creates DAO which will be used passed connection to work with data base.
     *
     * @param connection to use.
     */
    protected MySqlDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Returns the current connection is used by the DAO.
     *
     * @return current connection.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Establishes a connection to be used by DAO.
     *
     * @param connection to use.
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<T> findAll() throws DAOException {
        List<T> entities = new ArrayList<T>();
        Statement st = null;
        ResultSet rs = null;
        if (connectionIsAlive()) {
            try {
                st = connection.createStatement();
                rs = st.executeQuery(SQL_FIND_ALL);
                while (rs.next()) {
                    entities.add(build(rs));
                }
            } catch (SQLException e) {
                throw new DAOException(SQL_FIND_ALL, e);
            } finally {
                close(rs);
                close(st);
            }
        } else {
            throw new DAOException(ERROR_NO_CONNECTION);
        }
        return entities;
    }

    @Override
    public T findById(int id) throws DAOException {
        T entity = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        if (connectionIsAlive()) {
            try {
                ps = connection.prepareStatement(SQL_FIND_BY_ID);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    entity = build(rs);
                }
            } catch (SQLException e) {
                throw new DAOException(SQL_FIND_BY_ID, e);
            } finally {
                close(rs);
                close(ps);
            }
        } else {
            throw new DAOException(ERROR_NO_CONNECTION);
        }
        return entity;
    }

    @Override
    public boolean delete(int id) throws DAOException {
        boolean result = false;
        PreparedStatement ps = null;
        if (connectionIsAlive()) {
            try {
                ps = connection.prepareStatement(SQL_DELETE_BY_ID);
                ps.setInt(1, id);
                result = (ps.executeUpdate() > 0);
            } catch (SQLException e) {
                throw new DAOException(SQL_DELETE_BY_ID, e);
            } finally {
                close(ps);
            }
        } else {
            throw new DAOException(ERROR_NO_CONNECTION);
        }
        return result;
    }

    @Override
    public boolean delete(T entity) throws DAOException {
        if (entity == null) {
            return false;
        }
        boolean result = false;
        T entityInBase = findById(entity.getId());
        if (entityInBase != null && entityInBase.equals(entity)) {
            try {
                connection.setAutoCommit(false);
                beforeDelete(entity);
                result = delete(entity.getId());
                afterDelete(entity);
                connection.commit();
            } catch (SQLException | DAOException e) {
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException eRollback) {
                        throw new DAOException(eRollback);
                    }
                }
                throw new DAOException(ERROR_DELETE, e);
            }
        } else {
            throw new DAOException(ERROR_DELETE + "\n" + entity + IN_BASE + "\n" + entityInBase);
        }
        return result;
    }

    @Override
    public T create(T entity) throws DAOException {
        T createdEntity = null;
        PreparedStatement ps = null;
        if (connectionIsAlive()) {
            try {
                connection.setAutoCommit(false);
                beforeCreate(entity);
                ps = connection.prepareStatement(SQL_CREATE);
                ps = fillStatementFullData(entity, ps);
                ps.execute();
                createdEntity = findByData(entity);
                afterCreate(createdEntity);
                connection.commit();
            } catch (SQLException | DAOException e) {
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException eRollback) {
                        throw new DAOException(eRollback);
                    }
                }
                throw new DAOException(SQL_CREATE, e);
            } finally {
                close(ps);
            }
        } else {
            throw new DAOException(ERROR_NO_CONNECTION);
        }
        return createdEntity;
    }

    @Override
    public T update(T entity) throws DAOException {
        T entityInBase = null;
        PreparedStatement ps = null;
        if (connectionIsAlive()) {
            try {
                entityInBase = findById(entity.getId());
                ps = connection.prepareStatement(SQL_UPDATE);
                ps = fillStatementUpdate(entity, ps);
                if (ps.executeUpdate() > 0) {
                    entityInBase = entity;
                }
            } catch (SQLException e) {
                throw new DAOException(SQL_UPDATE, e);
            } finally {
                close(ps);
            }
        } else {
            throw new DAOException(ERROR_NO_CONNECTION);
        }
        return entityInBase;
    }

    @Override
    public T findByData(T entity) throws DAOException {
        T entityInBase = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        if (connectionIsAlive()) {
            try {
                ps = connection.prepareStatement(SQL_FIND_BY_DATA);
                ps = fillStatementFullData(entity, ps);
                rs = ps.executeQuery();
                if (rs.next()) {
                    entityInBase = build(rs);
                }
            } catch (SQLException e) {
                throw new DAOException(SQL_FIND_BY_DATA, e);
            } finally {
                close(rs);
                close(ps);
            }
        } else {
            throw new DAOException(ERROR_NO_CONNECTION);
        }
        return entityInBase;
    }

    @Override
    public T save(T entity) throws DAOException {
        T subject = findById(entity.getId());
        return (subject == null) ? (create(entity)) : (update(entity));
    }

    /**
     * Closes passed statement. If passed statement is null, method returns null and write error log message.
     *
     * @param statement to close.
     * @return true - if statement was closed, or else false.
     */
    public boolean close(Statement statement) {
        boolean result = false;
        if (statement != null) {
            try {
                statement.close();
                result = true;
            } catch (SQLException e) {
                LOGGER.error(null, e);
            }
        } else {
            DAOException ex = new DAOException(NULL_STATEMENT_TO_CLOSE);
            LOGGER.error(ex);
        }
        return result;
    }

    /**
     * Closes passed prepared statement. If passed statement is null, method returns null and write error message to log.
     *
     * @param statement to close.
     * @return true - if statement was closed, or else false.
     */
    public boolean close(PreparedStatement statement) {
        boolean result = false;
        if (statement != null) {
            try {
                statement.close();
                result = true;
            } catch (SQLException e) {
                LOGGER.error(null, e);
            }
        } else {
            DAOException ex = new DAOException(NULL_STATEMENT_TO_CLOSE);
            LOGGER.error(ex);
        }
        return result;
    }

    /**
     * Closes passed result set. It passed result set is null, method returns null and write error message to log.
     *
     * @param resultSet to close.
     * @return true - if result set was closed, or else false.
     */
    public boolean close(ResultSet resultSet) {
        boolean result = false;
        if (resultSet != null) {
            try {
                resultSet.close();
                result = true;
            } catch (SQLException e) {
                LOGGER.error(null, e);
            }
        } else {
            DAOException ex = new DAOException(NULL_RESULT_SET_TO_CLOSE);
            LOGGER.error(ex);
        }
        return result;
    }


    /**
     * Checks status of current connection.
     *
     * @return true - if connection is open, or else false.
     */
    public boolean connectionIsAlive() {
        boolean result = false;
        try {
            if (connection != null && !connection.isClosed()) {
                result = true;
            }
        } catch (SQLException e) {
            DAOException ex = new DAOException(ERROR_WHILE_CHECK_CONNECTION, e);
            LOGGER.error(ex);
        }
        return result;
    }

    /**
     * Checks relationship between passed entity and all other entities referenced by passed entity.
     * <p/>
     * <pre>
     * <b>For example:</b>
     * class AEntity(){
     * }
     *
     * class BEntity(){
     *     AEntity related;
     * }
     *
     * <b>DAOBEntity may use checkRelatedEntity method follows:</b>
     *
     * AEntity relatedEntity = checkRelatedEntity(related, new DAOAEntity());
     *
     * <b>And subsequently assigned related entity:</b>
     *
     * BEntity bObject = new BEntity();
     * ...
     * AEntity aObject = bObject.getRelated();
     * bObject.setRelated(checkRelatedEntity(related, new DAOAEntity()));
     *
     * </pre>
     *
     * @param entity whose reference to check.
     * @param dao    to work with related entity.
     * @param <E>    type of related entity.
     * @return if passed entity exists - return this entity
     * @throws DAOException - if while execute method occurs problem.
     */
    protected <E extends Entity> E checkRelatedEntity(E entity, MySqlDAO<E> dao) throws DAOException {
        E result;
        if (entity != null && dao != null) {
            dao.setConnection(connection);
            result = dao.save(entity);
        } else {
            throw new DAOException("Error while check related entity, entity or dao are null. Entity =" + entity
                    + " DAO=" + dao);
        }
        return result;
    }

    /**
     * Same as checkRelatedEntity(Entity, MySqlDAO) but for the list of entities.
     *
     * @param entities list of entities whose reference to check.
     * @param dao      to work with related entity.
     * @param <E>      type of related entity.
     * @return if passed list entities exists - return list of entities
     * @throws DAOException - if while execute method occurs problem.
     * @see MySqlDAO#checkRelatedEntity(kz.project.carrental.entity.Entity, MySqlDAO)
     */
    protected <E extends Entity> List<E> checkRelatedEntity(List<E> entities, MySqlDAO<E> dao) throws DAOException {
        List<E> result = new ArrayList<>();
        if (entities != null && dao != null) {
            dao.setConnection(connection);
            for (E entity : entities) {
                result.add(dao.save(entity));
            }
        } else {
            throw new DAOException("Error while check related entity, entity or dao are null. Entities =" + entities
                    + " DAO=" + dao);
        }
        return result;
    }

    /**
     * This method executes every time before executes create method. Means to perform any pre-action with created entity.
     *
     * @param entity which will be created.
     * @throws DAOException - if while execute method occurs problem.
     */
    protected void beforeCreate(T entity) throws DAOException {

    }

    ;

    /**
     * This method executes every time after executes create method. Means to perform any post-action with created entity.
     *
     * @param entity which will be created.
     * @throws DAOException - if while execute method occurs problem.
     */
    protected void afterCreate(T entity) throws DAOException {

    }

    ;


    protected void beforeDelete(T entity) throws DAOException {

    }

    ;

    protected void afterDelete(T entity) throws DAOException {

    }

    ;

    /**
     * Fills passed prepares statement, data from the entity.
     * This method is used to get prepared statement in the following methods:
     * create()
     * findByData()
     *
     * @param entity data which are used to fill.
     * @param ps     prepared statement to fill.
     * @return filled prepared statement.
     * @throws SQLException - if while filling statement occurs problem.
     * @throws DAOException - if passed prepared statement is null.
     */
    protected abstract PreparedStatement fillStatementFullData(T entity, PreparedStatement ps) throws SQLException, DAOException;

    /**
     * Fills passed prepares statement, data from the entity.
     * This method is used to get prepared statement in the following methods:
     * update()
     *
     * @param entity data which are used to fill.
     * @param ps     prepared statement to fill.
     * @return filled prepared statement.
     * @throws SQLException - if while filling statement occurs problem.
     * @throws DAOException - if passed prepared statement is null.
     */
    protected abstract PreparedStatement fillStatementUpdate(T entity, PreparedStatement ps) throws SQLException, DAOException;

    /**
     * Build entity by passed result set.
     *
     * @param resultSet which contains necessary data to create entity.
     * @return created entity.
     * @throws SQLException - if occurs problem while work with result set.
     * @throws DAOException - if occurs problem while work with other necessary DAO.
     */
    protected abstract T build(ResultSet resultSet) throws SQLException, DAOException;

}
