package kz.project.carrental.dao.impl.mysql;

import kz.project.carrental.entity.User;
import kz.project.carrental.entity.enums.OrderStatus;
import kz.project.carrental.dao.exception.DAOException;
import kz.project.carrental.entity.Car;
import kz.project.carrental.entity.Damage;
import kz.project.carrental.entity.Order;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends MySqlDAO<Order> {

    private static final Logger LOGGER = Logger.getLogger(OrderDAO.class);

    //Column names
    private static final String ID = "id";
    private static final String CAR_ID = "car_id";
    private static final String CLIENT_ID = "client_id";
    private static final String BEGIN_RENT = "beginRent";
    private static final String END_RENT = "endRent";
    private static final String STATUS = "status";
    private static final String CREATE_TIME = "createTime";

    // Init general SQL Query
    {
        SQL_FIND_ALL = "SELECT * FROM `order`";
        SQL_FIND_BY_ID = "SELECT * FROM `order` WHERE id=?";
        SQL_FIND_BY_DATA = "SELECT * FROM `order` WHERE car_id=? AND client_id=? AND beginRent=? AND endRent=? " +
                "AND status=? AND  createTime=?";
        SQL_CREATE = "INSERT INTO `order` (car_id, client_id, beginRent, endRent, status, createTime) VALUES(?,?,?,?,?,?)";
        SQL_DELETE_BY_ID = "DELETE FROM `order` WHERE id=? LIMIT 1";
        SQL_UPDATE = "UPDATE `order` SET car_id=?, client_id=?, beginRent=?, endRent=?, `status`=?, `createTime`=? WHERE id=?";
    }

    private String SQL_FIND_STATUS_TIME = "SELECT * FROM `order` WHERE  ((? <= `endRent` ) AND ( `beginRent` <= ? )) " +
            "AND `car_id`=? AND (`status`=? OR `status`=?)";
    private String SQL_FIND_BY_USER = "SELECT * FROM `order` WHERE client_id=?";

    /**
     * Creates DAO with null data base connection.
     */
    public OrderDAO() {
    }

    /**
     * Creates DAO which will be used passed connection to work with data base.
     *
     * @param connection to use.
     */
    public OrderDAO(Connection connection) {
        super(connection);
    }

    /**
     * Checks crossing periods of the lease. Crossing periods are when their periods crossing and have a status
     * of OPEN or ACCEPT
     *
     * @param beginRent time of begin rent.
     * @param endRent   time of end rent.
     * @param car       for rent.
     * @return empty list of orders if no crossing periods, else all periods which are crossing with passed period for car.
     * @throws DAOException - if while execute method occurs problem.
     */
    public List<Order> findСrossingRent(Timestamp beginRent, Timestamp endRent, Car car) throws DAOException {
        List<Order> orders = new ArrayList<Order>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        if (connectionIsAlive()) {
            try {
                ps = connection.prepareStatement(SQL_FIND_STATUS_TIME);
                ps.setTimestamp(1, beginRent);
                ps.setTimestamp(2, endRent);
                ps.setInt(3, car.getId());
                ps.setInt(4, OrderStatus.ACCEPT.ordinal());
                ps.setInt(5, OrderStatus.OPEN.ordinal());
                rs = ps.executeQuery();
                while (rs.next()) {
                    orders.add(build(rs));
                }
            } catch (SQLException e) {
                DAOException ex = new DAOException(SQL_FIND_STATUS_TIME, e);
                throw ex;
            } finally {
                close(rs);
                close(ps);
            }
        }
        return orders;
    }

    public List<Order> findByUser(User user) throws DAOException {
        List<Order> orders = new ArrayList<Order>();
        if (user != null) {
            PreparedStatement ps = null;
            ResultSet rs = null;
            if (connectionIsAlive()) {
                try {
                    ps = connection.prepareStatement(SQL_FIND_BY_USER);
                    ps.setInt(1, user.getId());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        orders.add(build(rs));
                    }
                } catch (SQLException e) {
                    throw new DAOException(e);
                } finally {
                    close(rs);
                    close(ps);
                }
            }
        }
        return orders;
    }

    @Override
    protected PreparedStatement fillStatementFullData(Order entity, PreparedStatement ps) throws SQLException, DAOException {
        if (ps != null) {
            ps.setInt(1, (entity.getCar() != null ? entity.getCar().getId() : 0));
            ps.setInt(2, (entity.getClient() != null ? entity.getClient().getId() : 0));
            ps.setTimestamp(3, entity.getBeginRent());
            ps.setTimestamp(4, entity.getEndRent());
            ps.setInt(5, entity.getStatus().ordinal());
            ps.setTimestamp(6, entity.getCreateTime());
        } else {
            throw new DAOException(NULL_PREPARED_STATEMENT_TO_FILL);
        }
        return ps;
    }

    @Override
    protected PreparedStatement fillStatementUpdate(Order entity, PreparedStatement ps) throws SQLException, DAOException {
        if (ps != null) {
            ps.setInt(1, (entity.getCar() != null ? entity.getCar().getId() : 0));
            ps.setInt(2, (entity.getClient() != null ? entity.getClient().getId() : 0));
            ps.setTimestamp(3, entity.getBeginRent());
            ps.setTimestamp(4, entity.getEndRent());
            ps.setInt(5, entity.getStatus().ordinal());
            ps.setTimestamp(6, entity.getCreateTime());
            ps.setInt(7, entity.getId());
        } else {
            throw new DAOException(NULL_PREPARED_STATEMENT_TO_FILL);
        }
        return ps;
    }

    @Override
    public Order build(ResultSet resultSet) throws SQLException, DAOException {
        CarDAO carDAO = new CarDAO(connection);
        DamageDAO damageDAO = new DamageDAO(connection);
        UserDAO userDAO = new UserDAO(connection);
        Order order = new Order();
        order.setId(resultSet.getInt(ID));
        order.setCar(carDAO.findById(resultSet.getInt(CAR_ID)));
        order.setClient(userDAO.findById(resultSet.getInt(CLIENT_ID)));
        order.setBeginRent(resultSet.getTimestamp(BEGIN_RENT));
        order.setEndRent(resultSet.getTimestamp(END_RENT));
        order.setStatus(OrderStatus.values()[resultSet.getInt(STATUS)]);
        order.setCreateTime(resultSet.getTimestamp(CREATE_TIME));
        order.setDamages(damageDAO.findByOrder(order));
        return order;
    }

    @Override
    protected void beforeCreate(Order entity) throws DAOException {
        if (entity.getClient() != null) {
            //TODO instead setMethod need use assigning values
            entity.setClient(checkRelatedEntity(entity.getClient(), new UserDAO()));
        }
        if (entity.getCar() != null) {
            //TODO instead setMethod need use assigning values
            entity.setCar(checkRelatedEntity(entity.getCar(), new CarDAO()));
        }
    }

    @Override
    protected void beforeDelete(Order entity) throws DAOException {
        if (entity.getDamages() != null) {
            DamageDAO damageDAO = new DamageDAO(connection);
            for (Damage damage : entity.getDamages()) {
                damageDAO.delete(damage);
            }
        }
    }
}
