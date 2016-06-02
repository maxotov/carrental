package kz.project.carrental.logic;

import kz.project.carrental.base.exception.ConnectionException;
import kz.project.carrental.dao.exception.DAOException;
import kz.project.carrental.dao.impl.mysql.AccessDAO;
import kz.project.carrental.dao.impl.mysql.UserAccessDAO;
import kz.project.carrental.entity.Access;
import kz.project.carrental.entity.User;
import kz.project.carrental.entity.UserAccess;
import kz.project.carrental.logic.exception.LogicException;
import kz.project.carrental.logic.wrapper.LogicResult;
import kz.project.carrental.util.AccessUtil;
import kz.project.carrental.base.impl.ConnectionPool;
import kz.project.carrental.dao.impl.mysql.UserDAO;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserLogic implements LOGIC_CONST {

    private static final String ACCESS_TYPE = "users.view";

    public LogicResult<List<User>> getAllUsers(User user) throws LogicException {
        LogicResult<List<User>> result = new LogicResult<List<User>>();

        if (!AccessUtil.hasAccess(user, ACCESS_TYPE)) {
            result.setErrorMessage(NO_ACCESS);
            return result;
        }

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            UserDAO userDAO = new UserDAO(connection);
            List<User> users = new ArrayList<User>();
            users = userDAO.findAll();
            result.setResult(users);
        } catch (DAOException e) {
            throw new LogicException(e);
        } catch (ConnectionException e) {
            throw new LogicException(e);
        } finally {
            ConnectionPool.getInstance().closeConnection(connection);
        }
        return result;
    }

    public boolean createUser(User user) throws LogicException {
        boolean flag = false;

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            UserDAO userDAO = new UserDAO(connection);
            userDAO.create(user);
            flag = true;
        } catch (DAOException e) {
            throw new LogicException(e);
        } catch (ConnectionException e) {
            throw new LogicException(e);
        } finally {
            ConnectionPool.getInstance().closeConnection(connection);
        }
        return flag;
    }

    public User findUserById(int userId) throws LogicException {
        User user = null;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            UserDAO userDAO = new UserDAO(connection);
            user = userDAO.findById(userId);
        } catch (DAOException e) {
            throw new LogicException(e);
        } catch (ConnectionException e) {
            throw new LogicException(e);
        } finally {
            ConnectionPool.getInstance().closeConnection(connection);
        }
        return user;
    }

    public boolean createUserAccess(UserAccess userAccess) throws LogicException {
        boolean flag = false;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
           UserAccessDAO userAccessDAO = new UserAccessDAO(connection);
            userAccessDAO.create(userAccess);
            flag = true;
        } catch (DAOException e) {
            throw new LogicException(e);
        } catch (ConnectionException e) {
            throw new LogicException(e);
        } finally {
            ConnectionPool.getInstance().closeConnection(connection);
        }
        return flag;
    }

    public boolean checkLoginRegister(String log) throws LogicException {
        boolean flag = true;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            UserDAO userDAO = new UserDAO(connection);
            User user = userDAO.findByLogin(log);
            if((user!=null) && (log.equals(user.getLogin()))){
                flag = false;
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        } catch (ConnectionException e) {
            throw new LogicException(e);
        } finally {
            ConnectionPool.getInstance().closeConnection(connection);
        }
        return flag;
    }

    public boolean validateParams(String phone, String email, String iin){
        Pattern patternNumber = Pattern.compile("7\\d{9}");
        Matcher matchArr = patternNumber.matcher(phone);
        Pattern patternIIN = Pattern.compile("\\d{12}");
        Matcher matchIIN = patternIIN.matcher(iin);
        Pattern patternEmail = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matchEmail = patternEmail.matcher(email);
        if(phone.isEmpty() || email.isEmpty() || iin.isEmpty()){
            return false;
        } else if(!matchArr.matches()){
            return false;
        } else if(!matchEmail.matches()){
            return false;
        } else if(!matchIIN.matches()){
            return false;
        }
        return true;
    }

    public Access getAccessById(int id) throws LogicException {
        Access access;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            AccessDAO accessDAO = new AccessDAO(connection);
            access = accessDAO.findById(id);
        } catch (DAOException e) {
            throw new LogicException(e);
        } catch (ConnectionException e) {
            throw new LogicException(e);
        } finally {
            ConnectionPool.getInstance().closeConnection(connection);
        }
        return access;
    }

    public List<Access> getAllAccess() throws LogicException {
        List<Access> accesses = new ArrayList<>();
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            AccessDAO accessDAO = new AccessDAO(connection);
            accesses = accessDAO.findAll();
        } catch (DAOException e) {
            throw new LogicException(e);
        } catch (ConnectionException e) {
            throw new LogicException(e);
        } finally {
            ConnectionPool.getInstance().closeConnection(connection);
        }
        return accesses;
    }

    public boolean deleteAccessUser(int user_id, int access_id) {
        boolean flag = false;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            UserAccessDAO userAccessDAO = new UserAccessDAO(connection);
            flag = userAccessDAO.deleteAccessUser(user_id,access_id);
        } catch (DAOException e) {
            e.printStackTrace();
        } catch (ConnectionException e) {
            e.printStackTrace();
        } finally {
            ConnectionPool.getInstance().closeConnection(connection);
        }
        return flag;
    }
}
