package kz.project.carrental.logic;

import kz.project.carrental.entity.Model;
import kz.project.carrental.logic.exception.LogicException;
import kz.project.carrental.base.exception.ConnectionException;
import kz.project.carrental.base.impl.ConnectionPool;
import kz.project.carrental.dao.exception.DAOException;
import kz.project.carrental.dao.impl.mysql.ModelDAO;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ModelLogic implements LOGIC_CONST  {

    public List<Model> getModelsByVendor(int idVendor) throws LogicException {
        List<Model> result = new ArrayList<Model>();

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            ModelDAO userDAO = new ModelDAO(connection);
            result = userDAO.findByVendorId(idVendor);
        } catch (DAOException e) {
            throw new LogicException(e);
        } catch (ConnectionException e) {
            throw new LogicException(e);
        } finally {
            ConnectionPool.getInstance().closeConnection(connection);
        }
        return result;
    }

    public Model getModelById(int id) throws LogicException {
        Model result = new Model();

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            ModelDAO userDAO = new ModelDAO(connection);
            result = userDAO.findById(id);
        } catch (DAOException e) {
            throw new LogicException(e);
        } catch (ConnectionException e) {
            throw new LogicException(e);
        } finally {
            ConnectionPool.getInstance().closeConnection(connection);
        }
        return result;
    }
}
