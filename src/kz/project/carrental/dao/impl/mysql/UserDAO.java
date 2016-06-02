package kz.project.carrental.dao.impl.mysql;

import kz.project.carrental.dao.exception.DAOException;
import kz.project.carrental.entity.Access;
import kz.project.carrental.entity.User;
import kz.project.carrental.entity.UserAccess;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAO extends MySqlDAO<User> {

    private static final Logger LOGGER = Logger.getLogger(UserDAO.class);

    //Column names
    private static final String ID = "id";
    private static final String LOGIN = "login";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String MIDDLE_NAME = "middleName";
    private static final String EMAIL = "email";
    private static final String TELEPHONE = "telephone";
    private static final String IIN = "iin";
    private static final String CATEGORY = "category";

    //Init general SQL Query
    {
        SQL_FIND_ALL = "SELECT * FROM user";
        SQL_FIND_BY_ID = "SELECT * FROM user WHERE id=?";
        SQL_FIND_BY_DATA = "SELECT * FROM user WHERE login=? AND password=? AND firstName=? " +
                "AND lastName=? AND middleName=? AND email=? AND telephone=? AND iin=? AND category=?";
        SQL_DELETE_BY_ID = "DELETE FROM user WHERE id=? LIMIT 1";
        SQL_UPDATE = "UPDATE user SET login=?,password=?, firstName=?, lastName=?, " +
                "middleName=?, email=?, telephone=?, iin=?, category=? WHERE id=?";
        SQL_CREATE = "INSERT INTO user (login, password, firstName, lastName, middleName, email, telephone, iin, category)" +
                " VALUES(?,?,?,?,?,?,?,?,?)";
    }

    private static final String SQL_FIND_BY_LOGIN = "SELECT * FROM user WHERE login=?";
    private static final String SQL_USER_ACCESS = "INSERT IGNORE INTO user_access (user_id, access_id) VALUES (?,?)";

    private List<Access> accesses;

    /**
     * Creates DAO with null data base connection.
     */
    public UserDAO() {
    }

    /**
     * Creates DAO which will be used passed connection to work with data base.
     *
     * @param connection to use.
     */
    public UserDAO(Connection connection) {
        super(connection);
    }

    /**
     * Find user by login.
     *
     * @param login of user.
     * @return user - if it exists, or else null.
     * @throws kz.project.carrental.dao.exception.DAOException - if while execute method occurs problem.
     */
    public User findByLogin(String login) throws DAOException {
        User user = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        if (connectionIsAlive()) {
            try {
                ps = connection.prepareStatement(SQL_FIND_BY_LOGIN);
                ps.setString(1, login);
                rs = ps.executeQuery();
                if (rs.next()) {
                    user = build(rs);
                }
            } catch (SQLException e) {
                DAOException ex = new DAOException(SQL_FIND_BY_LOGIN, e);
                throw ex;
            } finally {
                close(rs);
                close(ps);
            }
        } else {
            throw new DAOException(ERROR_NO_CONNECTION);
        }
        return user;
    }

    @Override
    public User build(ResultSet resultSet) throws SQLException, DAOException {
        AccessDAO accessDAO = new AccessDAO(connection);
        User user = new User();
        user.setId(resultSet.getInt(ID));
        user.setFirstName(resultSet.getString(FIRST_NAME));
        user.setLastName(resultSet.getString(LAST_NAME));
        user.setMiddleName(resultSet.getString(MIDDLE_NAME));
        user.setLogin(resultSet.getString(LOGIN));
        user.setPassword(resultSet.getString(PASSWORD));
        user.setEmail(resultSet.getString(EMAIL));
        user.setTelephone(resultSet.getString(TELEPHONE));
        user.setIin(resultSet.getString(IIN));
        user.setCategory(resultSet.getString(CATEGORY));
        user.setAccesses(accessDAO.findByUser(user));
        return user;
    }

    @Override
    protected PreparedStatement fillStatementFullData(User entity, PreparedStatement ps) throws SQLException, DAOException {
        if (ps != null) {
            ps.setString(1, entity.getLogin());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getFirstName());
            ps.setString(4, entity.getLastName());
            ps.setString(5, entity.getMiddleName());
            ps.setString(6, entity.getEmail());
            ps.setString(7, entity.getTelephone());
            ps.setString(8, entity.getIin());
            ps.setString(9, entity.getCategory());
        } else {
            throw new DAOException(NULL_PREPARED_STATEMENT_TO_FILL);
        }
        return ps;
    }

    @Override
    protected PreparedStatement fillStatementUpdate(User entity, PreparedStatement ps) throws SQLException, DAOException {
        if (ps != null) {
            ps.setString(1, entity.getLogin());
            ps.setString(2, entity.getPassword());
            ps.setString(3, entity.getFirstName());
            ps.setString(4, entity.getLastName());
            ps.setString(5, entity.getMiddleName());
            ps.setString(6, entity.getEmail());
            ps.setString(7, entity.getTelephone());
            ps.setString(8, entity.getIin());
            ps.setString(9, entity.getCategory());
            ps.setInt(10, entity.getId());
        } else {
            throw new DAOException(NULL_PREPARED_STATEMENT_TO_FILL);
        }
        return ps;
    }

    @Override
    protected void beforeCreate(User entity) throws DAOException {
        if (entity.getAccesses() != null) {
            accesses = checkRelatedEntity(entity.getAccesses(), new AccessDAO());
            entity.setAccesses(accesses);
        }
    }

    @Override
    protected void afterCreate(User entity) throws DAOException {
        if (accesses != null && entity != null) {
            entity.setAccesses(accesses);
            UserAccessDAO userAccessDAO = new UserAccessDAO(connection);
            for (Access access : accesses) {
                UserAccess userAccess = new UserAccess();
                userAccess.setUser(entity);
                userAccess.setAccess(access);
                userAccessDAO.save(userAccess);
            }
            accesses = null;
        }
    }
}
