package kz.project.carrental.logic;

import kz.project.carrental.base.exception.ConnectionException;
import kz.project.carrental.base.impl.ConnectionPool;
import kz.project.carrental.dao.exception.DAOException;
import kz.project.carrental.dao.impl.mysql.CarDAO;
import kz.project.carrental.entity.Car;
import kz.project.carrental.logic.exception.LogicException;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CarLogic {

    public List<Car> getAllCars() throws LogicException {
        List<Car> cars = new ArrayList<Car>();

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            CarDAO carDAO = new CarDAO(connection);
            cars = carDAO.findAll();
        } catch (DAOException e) {
            throw new LogicException(e);
        } catch (ConnectionException e) {
            throw new LogicException(e);
        } finally {
            ConnectionPool.getInstance().closeConnection(connection);
        }
        return cars;
    }

    public boolean createCar(Car car) throws LogicException {
        boolean flag = false;

        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            CarDAO userDAO = new CarDAO(connection);
            userDAO.create(car);
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


}
