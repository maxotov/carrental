package kz.project.carrental.dao.impl.mysql;

import kz.project.carrental.dao.exception.DAOException;
import kz.project.carrental.entity.Access;
import kz.project.carrental.entity.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccessDAO extends MySqlDAO<Access> {

    private static final Logger LOGGER = Logger.getLogger(AccessDAO.class);

    //Column names
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String VALUE = "value";

    //Init general SQL query
    {
        SQL_FIND_ALL = "SELECT * FROM access";
        SQL_CREATE = "INSERT INTO access (name, value) VALUES(?,?)";
        SQL_DELETE_BY_ID = "DELETE FROM access WHERE id=? LIMIT 1";
        SQL_UPDATE = "UPDATE access SET name=?, value=? WHERE id=?";
        SQL_FIND_BY_ID = "SELECT * FROM access WHERE id=?";
        SQL_FIND_BY_DATA = "SELECT * FROM access WHERE name=? AND value=?";
    }

    //SQL Query
    private static final String SQL_FIND_BY_USER_ID = "SELECT access.* FROM user_access JOIN access " +
            "ON user_access.access_id=access.id WHERE user_access.user_id = ?";

    /**
     * Creates DAO with null data base connection.
     */
    public AccessDAO() {
    }

    /**
     * Creates DAO which will be used passed connection to work with data base.
     *
     * @param connection to use.
     */
    public AccessDAO(Connection connection) {
        super(connection);
    }

    /**
     * Finds all accesses of passed user.
     *
     * @param user is access to retrieve.
     * @return list of all access for user.
     * @throws DAOException - if while execute method occurs problem.
     */
    public List<Access> findByUser(User user) throws DAOException {
        List<Access> accesses = new ArrayList<Access>();
        if (user != null) {
            accesses = findByUserId(user.getId());
        }
        return accesses;
    }

    /**
     * Finds all accesses of passed user.
     *
     * @param id of user.
     * @return list of all access for user.
     * @throws DAOException - if while execute method occurs problem.
     */
    public List<Access> findByUserId(int id) throws DAOException {
        List<Access> accesses = new ArrayList<Access>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        if (connectionIsAlive()) {
            try {
                ps = connection.prepareStatement(SQL_FIND_BY_USER_ID);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                while (rs.next()) {
                    accesses.add(build(rs));
                }
            } catch (SQLException e) {
                DAOException ex = new DAOException(SQL_FIND_BY_USER_ID, e);
                throw ex;
            } finally {
                close(rs);
                close(ps);
            }
        } else {
            throw new DAOException(ERROR_NO_CONNECTION);
        }

        return accesses;
    }

    @Override
    protected Access build(ResultSet resultSet) throws SQLException, DAOException {
        Access access = new Access();
        access.setId(resultSet.getInt(ID));
        access.setName(resultSet.getString(NAME));
        access.setValue(resultSet.getString(VALUE));
        return access;
    }

    @Override
    protected PreparedStatement fillStatementUpdate(Access entity, PreparedStatement ps) throws SQLException, DAOException {
        if (ps != null) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getValue());
            ps.setInt(3, entity.getId());
        } else {
            throw new DAOException(NULL_PREPARED_STATEMENT_TO_FILL);
        }
        return ps;
    }

    @Override
    protected PreparedStatement fillStatementFullData(Access entity, PreparedStatement ps) throws SQLException, DAOException {
        if (ps != null) {
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getValue());
        } else {
            throw new DAOException(NULL_PREPARED_STATEMENT_TO_FILL);
        }
        return ps;
    }

    @Override
    protected void beforeCreate(Access entity) {

    }
}
