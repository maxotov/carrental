package kz.project.carrental.logic;

import kz.project.carrental.base.exception.ConnectionException;
import kz.project.carrental.base.impl.ConnectionPool;
import kz.project.carrental.dao.exception.DAOException;
import kz.project.carrental.dao.impl.mysql.CarDAO;
import kz.project.carrental.dao.impl.mysql.OrderDAO;
import kz.project.carrental.entity.Car;
import kz.project.carrental.entity.Damage;
import kz.project.carrental.entity.Order;
import kz.project.carrental.entity.User;
import kz.project.carrental.entity.enums.OrderStatus;
import kz.project.carrental.logic.exception.LogicException;
import kz.project.carrental.logic.wrapper.LogicResult;
import kz.project.carrental.util.AccessUtil;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class OrderLogic implements LOGIC_CONST {

    public LogicResult<Order> prepare(int carId, User user) throws LogicException {
        LogicResult<Order> result = new LogicResult<Order>();

        Connection connection = null;
        try {
            if (AccessUtil.hasAccess(user, "order.make")) {
                connection = ConnectionPool.getInstance().getConnection();
                CarDAO carDAO = new CarDAO(connection);
                Car car = carDAO.findById(carId);
                if (car != null && user != null) {
                    Order order = new Order();
                    order.setCar(car);
                    order.setClient(user);
                    result.setResult(order);
                }
            } else {
                result.setErrorMessage(NO_ACCESS);
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        } catch (ConnectionException e) {
            throw new LogicException(e);
        } finally {
            ConnectionPool.getInstance().closeConnection(connection);
        }

        return result;
    }


    public LogicResult<Order> send(Order order, User user) throws LogicException {
        LogicResult<Order> result = new LogicResult<Order>();
        //check for null
        Connection connection = null;
        try {
            if (AccessUtil.hasAccess(user, "order.make")) {
                connection = ConnectionPool.getInstance().getConnection();
                OrderDAO orderDAO = new OrderDAO(connection);

                //TODO checkOrder();
                LogicResult checkResult = checkOrder(order, connection);
                if (checkResult.noError()) {
                    order.setStatus(OrderStatus.NEW);
                    order.setClient(user);
                    order.setCreateTime(new Timestamp(System.currentTimeMillis()));
                    Order createdOrder = orderDAO.create(order);
                    result.setResult(createdOrder);
                } else {
                    result.setErrorMessage(checkResult.getErrorMessage());
                }

            } else {
                result.setErrorMessage(NO_ACCESS);
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        } catch (ConnectionException e) {
            throw new LogicException(e);
        } finally {
            ConnectionPool.getInstance().closeConnection(connection);
        }

        return result;
    }

    public LogicResult<Order> show(User user, int order_id) throws LogicException {
        LogicResult result = new LogicResult();

        if (AccessUtil.hasAccess(user, "order.make")) {
            Connection connection = null;
            try {
                connection = ConnectionPool.getInstance().getConnection();
                OrderDAO orderDAO = new OrderDAO(connection);
                Order order = orderDAO.findById(order_id);
                if (order == null){
                    result.setErrorMessage("message.error.order.show.no.order");
                }else if (user.equals(order.getClient()) || AccessUtil.hasAccess(user, "order.admin")) {
                    result.setResult(order);
                } else {
                    result.setErrorMessage("message.error.user.no.have.this.order");
                }
            } catch (ConnectionException e) {
                throw new LogicException(e);
            } catch (DAOException e) {
                throw new LogicException(e);
            } finally {
                ConnectionPool.getInstance().closeConnection(connection);
            }

        } else {
            result.setErrorMessage(NO_ACCESS);
        }
        return result;
    }

    public LogicResult<List<Order>> showAll(User user) throws LogicException {
        LogicResult result = new LogicResult();

        if (AccessUtil.hasAccess(user, "order.make")) {
            Connection connection = null;
            try {
                connection = ConnectionPool.getInstance().getConnection();
                OrderDAO orderDAO = new OrderDAO(connection);
                List<Order> orders;
                if (AccessUtil.hasAccess(user, "order.admin")) {
                    orders = orderDAO.findAll();
                } else {
                    orders = orderDAO.findByUser(user);
                }
                result.setResult(orders);
            } catch (ConnectionException e) {
                throw new LogicException(e);
            } catch (DAOException e) {
                throw new LogicException(e);
            } finally {
                ConnectionPool.getInstance().closeConnection(connection);
            }

        } else {
            result.setErrorMessage(NO_ACCESS);
        }
        return result;
    }


    //TODO
    private LogicResult<Order> checkOrder(Order order, Connection connection) throws LogicException {
        LogicResult<Order> result = new LogicResult<Order>();
        OrderDAO orderDAO = new OrderDAO(connection);
        List<Order> crossOrders = null;

        try {
            crossOrders = orderDAO.findСrossingRent(order.getBeginRent(), order.getEndRent(), order.getCar());
        } catch (DAOException e) {
            throw new LogicException(e);
        }

        if (order.getBeginRent().compareTo(new Date(System.currentTimeMillis())) < 0) {
            result.setErrorMessage("message.error.order.begin.less.now");
            return result;
        }
        if (order.getBeginRent().compareTo(order.getEndRent()) >= 0) {
            result.setErrorMessage("message.error.order.begin.more.end");
            return result;
        }
        if (!crossOrders.isEmpty()) {
            result.setErrorMessage("message.error.order.cross.period");
            return result;
        }


        return result;
    }

    public LogicResult<Order> changeOrderStatus(User user, Integer order_id, String status) throws LogicException {
        LogicResult<Order> logicResult = new LogicResult<Order>();

        if (AccessUtil.hasAccess(user, "order.admin")) {
            if (order_id == null) {
                logicResult.setErrorMessage("message.error.order.change.status.incorrect.id.parameter");
                return logicResult;
            }

            OrderStatus newStatus;
            try {
                newStatus = OrderStatus.valueOf(status);
            } catch (IllegalArgumentException e) {
                logicResult.setErrorMessage("message.error.order.change.status.incorrect.status.parameter");
                return logicResult;
            }

            Connection connection = null;
            try {
                connection = ConnectionPool.getInstance().getConnection();
                OrderDAO orderDAO = new OrderDAO(connection);
                Order order = orderDAO.findById(order_id);

                if (order == null) {
                    logicResult.setErrorMessage("message.error.order.change.status.no.order");
                } else if (!allDamageRepaired(order) && OrderStatus.CLOSE.equals(newStatus)) {
                    logicResult.setErrorMessage("message.error.order.change.status.available.damage");
                    logicResult.setResult(order);
                //TODO can't set status accept if was crossing period
                } else if(OrderStatus.ACCEPT.equals(newStatus) &&
                        !orderDAO.findСrossingRent(order.getBeginRent(), order.getEndRent(), order.getCar()).isEmpty()){
                    logicResult.setErrorMessage("message.error.order.change.status.to.accept");
                    logicResult.setResult(order);
                }else{
                    order.setStatus(newStatus);
                    order = orderDAO.update(order);
                    logicResult.setResult(order);
                }
            } catch (ConnectionException | DAOException e) {
                throw new LogicException(e);
            } finally {
                ConnectionPool.getInstance().closeConnection(connection);
            }
        } else {
            logicResult.setErrorMessage(NO_ACCESS);
        }

        return logicResult;
    }

    public LogicResult deleteOrder(User user, Integer order_id) throws LogicException{
        LogicResult logicResult = new LogicResult();
        if (AccessUtil.hasAccess(user, "order.admin")) {
            if (order_id == null) {
                logicResult.setErrorMessage("message.error.order.delete.incorrect.id.parameter");
                return logicResult;
            }

            Connection connection = null;
            try {
                connection = ConnectionPool.getInstance().getConnection();
                OrderDAO orderDAO = new OrderDAO(connection);
                Order order = orderDAO.findById(order_id);

                if (order == null) {
                    logicResult.setErrorMessage("message.error.order.delete.no.order");
                } else if (!allDamageRepaired(order)) {
                    logicResult.setErrorMessage("message.error.order.delete.available.damage");
                    logicResult.setResult(order);
                } else if (!OrderStatus.CLOSE.equals(order.getStatus())) {
                    logicResult.setErrorMessage("message.error.order.delete.order.not.close");
                    logicResult.setResult(order);
                } else if(!orderDAO.delete(order)){
                    logicResult.setErrorMessage("message.error.order.delete.not.successful");
                }
            } catch (ConnectionException | DAOException e) {
                throw new LogicException(e);
            } finally {
                ConnectionPool.getInstance().closeConnection(connection);
            }
        } else {
            logicResult.setErrorMessage(NO_ACCESS);
        }
        return logicResult;
    }

    private boolean allDamageRepaired(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order can't be null.");
        }
        for (Damage damage : order.getDamages()) {
            if (!damage.isRepaired()) {
                return false;
            }
        }
        return true;
    }

    private Double countTotalPrice(Order order) {
        double totalPrice = 0;
        long deltaTime = order.getEndRent().getTime() - order.getBeginRent().getTime();
        double hours = (double) deltaTime / (60 * 60 * 1000);
        totalPrice = order.getCar().getPrice() * hours;
        return totalPrice;
    }
}
