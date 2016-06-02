package kz.project.carrental.logic;

import kz.project.carrental.logic.exception.LogicException;
import kz.project.carrental.base.exception.ConnectionException;
import kz.project.carrental.base.impl.ConnectionPool;
import kz.project.carrental.dao.exception.DAOException;
import kz.project.carrental.dao.impl.mysql.VendorDAO;
import kz.project.carrental.entity.Vendor;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class VendorLogic implements LOGIC_CONST {

    public List<Vendor> getAllVendors() throws LogicException {
        List<Vendor> result = new ArrayList<Vendor>();

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            VendorDAO userDAO = new VendorDAO(connection);
            result = userDAO.findAll();
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
