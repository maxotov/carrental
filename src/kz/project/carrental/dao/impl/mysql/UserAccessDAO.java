package kz.project.carrental.dao.impl.mysql;

import kz.project.carrental.dao.exception.DAOException;
import kz.project.carrental.entity.UserAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserAccessDAO extends MySqlDAO<UserAccess> {

    //Column names
    private static final String ID = "id";
    private static final String USER_ID = "user_id";
    private static final String ACCESS_ID = "access_id";

    //Init general SQL Query
    {
        SQL_FIND_ALL = "SELECT * FROM user_access";
        SQL_FIND_BY_ID = "SELECT * FROM user_access WHERE id=?";
        SQL_DELETE_BY_ID = "DELETE FROM user_access WHERE id=? LIMIT 1";
        SQL_FIND_BY_DATA = "SELECT * FROM user_access WHERE user_id=? AND access_id=?";
        SQL_CREATE = "INSERT INTO user_access (user_id, access_id) VALUES(?,?)";
        SQL_UPDATE = "UPDATE user_access SET user_id=?, access_id=? WHERE id=?";
    }
    private static final String SQL_DELETE_USER_ACCESS = "DELETE FROM user_access WHERE user_id=? AND access_id=? LIMIT 1";
    /**
     * Creates DAO with null data base connection.
     */
    public UserAccessDAO() {
    }

    /**
     * Creates DAO which will be used passed connection to work with data base.
     *
     * @param connection to use.
     */
    public UserAccessDAO(Connection connection) {
        super(connection);
    }

    public boolean deleteAccessUser(int user_id, int access_id) throws DAOException {
        boolean flag = false;
        PreparedStatement ps = null;
        ResultSet rs = null;
        if (connectionIsAlive()) {
            try {
                ps = connection.prepareStatement(SQL_DELETE_USER_ACCESS);
                ps.setInt(1, user_id);
                ps.setInt(2, access_id);
                ps.executeUpdate();
                flag = true;
            } catch (SQLException e) {
                DAOException ex = new DAOException(SQL_DELETE_USER_ACCESS, e);
                throw ex;
            } finally {
                close(rs);
                close(ps);
            }
        } else {
            throw new DAOException(ERROR_NO_CONNECTION);
        }
        return flag;
    }

    @Override
    protected PreparedStatement fillStatementFullData(UserAccess entity, PreparedStatement ps) throws SQLException, DAOException {
        if (ps != null) {
            ps.setInt(1, (entity.getUser() != null ? entity.getUser().getId() : 0));
            ps.setInt(2, (entity.getAccess() != null ? entity.getAccess().getId() : 0));
        } else {
            throw new DAOException(NULL_PREPARED_STATEMENT_TO_FILL);
        }
        return ps;
    }

    @Override
    protected PreparedStatement fillStatementUpdate(UserAccess entity, PreparedStatement ps) throws SQLException, DAOException {
        if (ps != null) {
            ps.setInt(1, (entity.getUser() != null ? entity.getUser().getId() : 0));
            ps.setInt(2, (entity.getAccess() != null ? entity.getAccess().getId() : 0));
            ps.setInt(3, entity.getId());
        } else {
            throw new DAOException(NULL_PREPARED_STATEMENT_TO_FILL);
        }
        return ps;
    }

    @Override
    protected UserAccess build(ResultSet resultSet) throws SQLException, DAOException {
        UserDAO userDAO = new UserDAO(connection);
        AccessDAO accessDAO = new AccessDAO(connection);
        UserAccess userAccess = new UserAccess();
        userAccess.setId(resultSet.getInt(ID));
        userAccess.setUser(userDAO.findById(resultSet.getInt(USER_ID)));
        userAccess.setAccess(accessDAO.findById(resultSet.getInt(ACCESS_ID)));
        return userAccess;
    }
}
