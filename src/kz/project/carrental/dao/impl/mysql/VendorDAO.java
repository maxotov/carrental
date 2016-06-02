package kz.project.carrental.dao.impl.mysql;

import kz.project.carrental.entity.Vendor;
import kz.project.carrental.dao.exception.DAOException;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VendorDAO extends MySqlDAO<Vendor> {

    private static final Logger LOGGER = Logger.getLogger(CarDAO.class);

    //Column names
    private static final String ID = "id";
    private static final String NAME = "name";

    //Init general SQL Query
    {
        SQL_FIND_ALL = "SELECT * FROM vendor";
        SQL_FIND_BY_ID = "SELECT * FROM vendor WHERE id=?";
        SQL_DELETE_BY_ID = "DELETE FROM vendor WHERE id=? LIMIT 1";
        SQL_FIND_BY_DATA = "SELECT * FROM vendor WHERE name=?";
        SQL_CREATE = "INSERT INTO vendor (name) VALUES(?)";
        SQL_UPDATE = "UPDATE vendor SET name=? WHERE id=?";
    }

    /**
     * Creates DAO with null data base connection.
     */
    public VendorDAO() {
    }

    /**
     * Creates DAO which will be used passed connection to work with data base.
     *
     * @param connection to use.
     */
    public VendorDAO(Connection connection) {
        super(connection);
    }

    @Override
    protected Vendor build(ResultSet resultSet) throws SQLException {
        Vendor vendor = new Vendor();
        vendor.setId(resultSet.getInt(ID));
        vendor.setName(resultSet.getString(NAME));
        return vendor;
    }

    @Override
    protected PreparedStatement fillStatementFullData(Vendor entity, PreparedStatement ps) throws SQLException, DAOException {
        if (ps != null) {
            ps.setString(1, entity.getName());
        } else {
            throw new DAOException(NULL_PREPARED_STATEMENT_TO_FILL);
        }
        return ps;
    }

    @Override
    public PreparedStatement fillStatementUpdate(Vendor entity, PreparedStatement ps) throws SQLException, DAOException {
        if (ps != null) {
            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getId());
        } else {
            throw new DAOException(NULL_PREPARED_STATEMENT_TO_FILL);
        }
        return ps;
    }

}
