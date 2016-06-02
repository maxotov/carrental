package kz.project.carrental.dao.impl.mysql;

import kz.project.carrental.entity.Order;
import kz.project.carrental.dao.exception.DAOException;
import kz.project.carrental.entity.Damage;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DamageDAO extends MySqlDAO<Damage> {

    private static final Logger LOGGER = Logger.getLogger(DamageDAO.class);

    //Column names
    private static final String ID = "id";
    private static final String ORDER_ID = "order_id";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";
    private static final String REPAIRED = "repaired";

    //Init general SQL Query
    {
        SQL_FIND_ALL = "SELECT * FROM damage";
        SQL_FIND_BY_ID = "SELECT * FROM damage WHERE id=?";
        SQL_DELETE_BY_ID = "DELETE FROM damage WHERE id=? LIMIT 1";
        SQL_FIND_BY_DATA = "SELECT * FROM damage WHERE order_id=? AND description=? AND price=? AND repaired=?";
        SQL_CREATE = "INSERT INTO damage (order_id, description, price, repaired) VALUES(?,?,?,?)";
        SQL_UPDATE = "UPDATE damage SET order_id=?, description=?, price=?, repaired=? WHERE id=?";
    }

    private static final String SQL_FIND_BY_ORDER_ID = "SELECT * FROM damage WHERE order_id=?";
    private Order currentOrder;

    /**
     * Creates DAO with null data base connection.
     */
    public DamageDAO() {
    }

    /**
     * Creates DAO which will be used passed connection to work with data base.
     *
     * @param connection to use.
     */
    public DamageDAO(Connection connection) {
        super(connection);
    }


    public List<Damage> findByOrder(Order order) throws DAOException {
        List<Damage> damages;
        if (order != null) {
            currentOrder = order;
            damages = findByOrderId(order.getId());
            currentOrder = null;//TODO need?
        } else {
            throw new DAOException("order can't be null");
        }
        return damages;
    }

    public List<Damage> findByOrderId(int order_id) throws DAOException {
        List<Damage> damages = new ArrayList<Damage>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        if (connectionIsAlive()) {
            try {
                ps = connection.prepareStatement(SQL_FIND_BY_ORDER_ID);
                ps.setInt(1, order_id);
                rs = ps.executeQuery();
                while (rs.next()) {
                    damages.add(build(rs));
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            } finally {
                close(rs);
                close(ps);
            }
        }
        return damages;
    }

    @Override
    protected PreparedStatement fillStatementFullData(Damage entity, PreparedStatement ps) throws SQLException, DAOException {
        if (ps != null) {
            ps.setInt(1, (entity.getOrder() != null ? entity.getOrder().getId() : 0));
            ps.setString(2, entity.getDescription());
            ps.setDouble(3, entity.getPrice());
            ps.setBoolean(4, entity.isRepaired());
        } else {
            throw new DAOException(NULL_PREPARED_STATEMENT_TO_FILL);
        }
        return ps;
    }

    @Override
    protected PreparedStatement fillStatementUpdate(Damage entity, PreparedStatement ps) throws SQLException, DAOException {
        if (ps != null) {
            ps.setInt(1, (entity.getOrder() != null ? entity.getOrder().getId() : 0));
            ps.setString(2, entity.getDescription());
            ps.setDouble(3, entity.getPrice());
            ps.setBoolean(4, entity.isRepaired());
            ps.setInt(5, entity.getId());
        } else {
            throw new DAOException(NULL_PREPARED_STATEMENT_TO_FILL);
        }
        return ps;
    }

    @Override
    protected Damage build(ResultSet resultSet) throws SQLException, DAOException {
        OrderDAO orderDAO = new OrderDAO(connection);
        Damage damage = new Damage();
        damage.setId(resultSet.getInt(ID));

        if (currentOrder != null) {
            damage.setOrder(currentOrder);
        } else {
            damage.setOrder(orderDAO.findById(resultSet.getInt(ORDER_ID)));
        }

        damage.setDescription(resultSet.getString(DESCRIPTION));
        damage.setPrice(resultSet.getDouble(PRICE));
        damage.setRepaired(resultSet.getBoolean(REPAIRED));
        return damage;
    }

    @Override
    protected void beforeCreate(Damage entity) throws DAOException {
        if (entity.getOrder() != null) {
            //TODO instead setMethod need use assigning values
            entity.setOrder(checkRelatedEntity(entity.getOrder(), new OrderDAO()));
        }
    }
}
