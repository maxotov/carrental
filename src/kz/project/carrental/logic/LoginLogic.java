package kz.project.carrental.logic;

import kz.project.carrental.base.exception.ConnectionException;
import kz.project.carrental.base.impl.ConnectionPool;
import kz.project.carrental.dao.exception.DAOException;
import kz.project.carrental.dao.impl.mysql.UserDAO;
import kz.project.carrental.entity.User;
import kz.project.carrental.logic.exception.LogicException;
import kz.project.carrental.logic.wrapper.LogicResult;
import kz.project.carrental.util.AccessUtil;
import kz.project.carrental.util.MD5Util;
import org.apache.log4j.Logger;

import java.sql.Connection;

public class LoginLogic implements LOGIC_CONST {

    private static final Logger LOGGER = Logger.getLogger(LoginLogic.class);

    //message
    private static final String INVALID_LOGIN = "Invalid login [login=%s:password=%s]";
    private static final String ACCESS_TYPE = "login";

    /**
     * Verified the user name and password. If a user is in the database returns it.
     *
     * @param login    of verified user.
     * @param password of verified user.
     * @return user - if login and password are correct, else null and error message.
     * @throws LogicException - if while execute method occurs problem.
     */
    public LogicResult<User> verifyUser(String login, String password) throws LogicException {
        LogicResult<User> result = new LogicResult<User>();

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            UserDAO userDAO = new UserDAO(connection);
            User tmpUser = userDAO.findByLogin(login);
            if (equalsPassword(tmpUser, password)) {
                if (AccessUtil.hasAccess(tmpUser, ACCESS_TYPE)) {
                    result.setResult(tmpUser);
                } else {
                    result.setErrorMessage(NO_ACCESS);
                }
            } else {
                LOGGER.warn(String.format(INVALID_LOGIN, login, password));
                result.setErrorMessage(LOGIN_INCORRECT);
            }
        } catch (DAOException | ConnectionException e) {
            throw new LogicException(e);
        } finally {
            ConnectionPool.getInstance().closeConnection(connection);
        }
        return result;
    }


    /**
     * Compares the  password with the password to the user.
     *
     * @param user     user with password.
     * @param password password to compare.
     * @return true - if password equivalents, or else false.
     */
    private boolean equalsPassword(User user, String password) {
        if (user != null && password != null && user.getPassword().equals(MD5Util.getHashMD5(password))) {
            return true;
        }
        return false;
    }
}
