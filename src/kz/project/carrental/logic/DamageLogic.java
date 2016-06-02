package kz.project.carrental.logic;

import kz.project.carrental.base.exception.ConnectionException;
import kz.project.carrental.dao.exception.DAOException;
import kz.project.carrental.dao.impl.mysql.DamageDAO;
import kz.project.carrental.dao.impl.mysql.OrderDAO;
import kz.project.carrental.entity.Damage;
import kz.project.carrental.entity.Order;
import kz.project.carrental.entity.User;
import kz.project.carrental.logic.exception.LogicException;
import kz.project.carrental.logic.wrapper.LogicResult;
import kz.project.carrental.util.AccessUtil;
import kz.project.carrental.base.impl.ConnectionPool;

import java.sql.Connection;

public class DamageLogic implements LOGIC_CONST {

    public LogicResult<Order> changeDamageStatus(User user, Integer damage_id, Boolean status) throws LogicException {
        LogicResult<Order> logicResult = new LogicResult();

        if (AccessUtil.hasAccess(user, "order.admin")) {
            if (damage_id == null) {
                logicResult.setErrorMessage("message.error.damage.change.status.incorrect.id.parameter");
                return logicResult;
            }

            if (status == null) {
                logicResult.setErrorMessage("message.error.damage.change.status.incorrect.status.parameter");
                return logicResult;
            }

            Connection connection = null;
            try {
                connection = ConnectionPool.getInstance().getConnection();
                DamageDAO damageDAO = new DamageDAO(connection);
                OrderDAO orderDAO = new OrderDAO(connection);
                Damage damage = damageDAO.findById(damage_id);


                if (damage == null) {
                    logicResult.setErrorMessage("message.error.damage.change.status.no.damage");
                } else {
                    damage.setRepaired(status);
                    damage = damageDAO.update(damage);
                    Order order = orderDAO.findById(damage.getOrder().getId());
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

    public LogicResult<Order> create(User user, Integer order_id, String description, Double price) throws LogicException {
        LogicResult<Order> logicResult = new LogicResult();

        if (AccessUtil.hasAccess(user, "order.admin")) {
            Connection connection = null;
            try {
                connection = ConnectionPool.getInstance().getConnection();
                OrderDAO orderDAO = new OrderDAO(connection);
                Order order = orderDAO.findById(order_id);

                if (order != null) {

                    DamageDAO damageDAO = new DamageDAO(connection);
                    Damage damage = new Damage();
                    damage.setDescription(description);
                    damage.setPrice(price);
                    damage.setOrder(order);
                    damage = damageDAO.create(damage);

                    if (damage == null) {
                        logicResult.setErrorMessage("message.error.damage.change.status.no.damage");
                    } else {
                        order = orderDAO.findById(order_id);
                        logicResult.setResult(order);
                    }
                } else {
                    System.out.println("order to add damage not found");
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

    public LogicResult<Order> delete(User user, Integer damage_id) throws LogicException{
        LogicResult<Order> logicResult = new LogicResult();

        if (AccessUtil.hasAccess(user, "order.admin")) {
            Connection connection = null;
            try {
                connection = ConnectionPool.getInstance().getConnection();
                OrderDAO orderDAO = new OrderDAO(connection);
                DamageDAO damageDAO = new DamageDAO(connection);
                Damage damage = damageDAO.findById(damage_id);

                if (damage != null) {

                    if (damageDAO.delete(damage)) {
                        Order order = orderDAO.findById(damage.getOrder().getId());
                        logicResult.setResult(order);
                    } else {
                        logicResult.setErrorMessage("message.error.damage.change.status.no.damage");
                    }
                } else {
                    System.out.println("damage to delete not found");
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
}
