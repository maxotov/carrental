package kz.project.carrental.dao.impl.mysql;

import kz.project.carrental.dao.exception.DAOException;
import kz.project.carrental.entity.Model;
import kz.project.carrental.entity.Vendor;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModelDAO extends MySqlDAO<Model> {

    private static final Logger LOGGER = Logger.getLogger(ModelDAO.class);

    //Column names
    private static final String ID = "id";
    private static final String VENDOR_ID = "vendor_id";
    private static final String NAME = "name";

    //Init general SQL Query
    {
        SQL_FIND_ALL = "SELECT * FROM model";
        SQL_FIND_BY_ID = "SELECT * FROM model WHERE id=?";
        SQL_FIND_BY_DATA = "SELECT * FROM model WHERE vendor_id=? AND name=?";
        SQL_CREATE = "INSERT INTO model (vendor_id, name) VALUES(?,?)";
        SQL_DELETE_BY_ID = "DELETE FROM model WHERE id=? LIMIT 1";
        SQL_UPDATE = "UPDATE model SET vendor_id=?, name=? WHERE id=?";
    }

    private static final String SQL_FIND_BY_VENDOR = "SELECT model.* FROM model JOIN vendor ON model.vendor_id=vendor.id where vendor.id=?";
    /**
     * Creates DAO with null data base connection.
     */
    public ModelDAO() {
    }

    /**
     * Creates DAO which will be used passed connection to work with data base.
     *
     * @param connection to use.
     */
    public ModelDAO(Connection connection) {
        super(connection);
    }

    public List<Model> findByVendorId(int id) throws DAOException {
        List<Model> accesses = new ArrayList<Model>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        if (connectionIsAlive()) {
            try {
                ps = connection.prepareStatement(SQL_FIND_BY_VENDOR);
                ps.setInt(1, id);
                rs = ps.executeQuery();
                while (rs.next()) {
                    accesses.add(build(rs));
                }
            } catch (SQLException e) {
                DAOException ex = new DAOException(SQL_FIND_BY_VENDOR, e);
                throw ex;
            } finally {
                close(rs);
                close(ps);
            }
        } else {
            throw new DAOException(ERROR_NO_CONNECTION);
        }

        return accesses;
    }

    @Override
    protected Model build(ResultSet resultSet) throws SQLException, DAOException {
        VendorDAO vendorDAO = new VendorDAO(connection);
        Model model = new Model();
        model.setId(resultSet.getInt(ID));
        model.setName(resultSet.getString(NAME));
        Vendor vendor = vendorDAO.findById(resultSet.getInt(VENDOR_ID));
        model.setVendor(vendor);
        return model;
    }

    @Override
    protected PreparedStatement fillStatementUpdate(Model entity, PreparedStatement ps) throws SQLException, DAOException {
        if (ps != null) {
            ps.setInt(1, (entity.getVendor() != null ? entity.getVendor().getId() : 0));
            ps.setString(2, entity.getName());
            ps.setInt(3, entity.getId());
        } else {
            throw new DAOException(NULL_PREPARED_STATEMENT_TO_FILL);
        }
        return ps;
    }

    @Override
    protected PreparedStatement fillStatementFullData(Model entity, PreparedStatement ps) throws SQLException, DAOException {
        if (ps != null) {
            ps.setInt(1, (entity.getVendor() != null ? entity.getVendor().getId() : 0));
            ps.setString(2, entity.getName());
        } else {
            throw new DAOException(NULL_PREPARED_STATEMENT_TO_FILL);
        }
        return ps;
    }

    @Override
    protected void beforeCreate(Model entity) throws DAOException {
        if (entity.getVendor() != null) {
            //TODO instead setMethod need use assigning values
            entity.setVendor(checkRelatedEntity(entity.getVendor(), new VendorDAO()));
        }
    }


}
