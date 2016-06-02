package kz.project.carrental.dao.impl.mysql;

import kz.project.carrental.entity.Model;
import kz.project.carrental.dao.exception.DAOException;
import kz.project.carrental.entity.Car;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarDAO extends MySqlDAO<Car> {

    private static final Logger LOGGER = Logger.getLogger(CarDAO.class);

    //Column names
    private static final String ID = "id";
    private static final String REG_NUMBER = "regNumber";
    private static final String MODEL_ID = "model_id";
    private static final String NAME = "name";
    private static final String PRICE = "price";
    private static final String PHOTO = "photo";

    // Init general SQL query
    {
        SQL_FIND_ALL = "SELECT * FROM car";
        SQL_FIND_BY_ID = "SELECT * FROM car WHERE id=?";
        SQL_FIND_BY_DATA = "SELECT * FROM car WHERE regNumber=? AND model_id =? AND name=? AND price=? and photo=?";
        SQL_CREATE = "INSERT INTO car (regNumber, model_id, name, price, photo) VALUES(?,?,?,?,?)";
        SQL_DELETE_BY_ID = "DELETE FROM car WHERE id=? LIMIT 1";
        SQL_UPDATE = "UPDATE car SET regNumber=?, model_id=?, name=?, price=?, photo=? WHERE id=?";
    }

    /**
     * Creates DAO with null data base connection.
     */
    public CarDAO() {
    }

    /**
     * Creates DAO which will be used passed connection to work with data base.
     *
     * @param connection to use.
     */
    public CarDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Car build(ResultSet resultSet) throws SQLException, DAOException {
        ModelDAO modelDAO = new ModelDAO(connection);
        Car car = new Car();
        car.setId(resultSet.getInt(ID));
        car.setName(resultSet.getString(NAME));
        car.setRegNumber(resultSet.getString(REG_NUMBER));
        Model model = modelDAO.findById(resultSet.getInt(MODEL_ID));
        car.setModel(model);
        car.setPrice(resultSet.getDouble(PRICE));
        car.setPhoto(resultSet.getString(PHOTO));
        return car;
    }

    @Override
    protected PreparedStatement fillStatementFullData(Car entity, PreparedStatement ps) throws SQLException, DAOException {
        if (ps != null) {
            ps.setString(1, entity.getRegNumber());
            ps.setInt(2, (entity.getModel() != null ? entity.getModel().getId() : 0));
            ps.setString(3, entity.getName());
            ps.setDouble(4, entity.getPrice());
            ps.setString(5, entity.getPhoto());
        } else {
            throw new DAOException(NULL_PREPARED_STATEMENT_TO_FILL);
        }
        return ps;
    }

    @Override
    protected PreparedStatement fillStatementUpdate(Car entity, PreparedStatement ps) throws SQLException, DAOException {
        if (ps != null) {
            ps.setString(1, entity.getRegNumber());
            ps.setInt(2, (entity.getModel() != null ? entity.getModel().getId() : 0));
            ps.setString(3, entity.getName());
            ps.setDouble(4, entity.getPrice());
            ps.setString(5, entity.getPhoto());
            ps.setInt(6, entity.getId());
        } else {
            throw new DAOException(NULL_PREPARED_STATEMENT_TO_FILL);
        }
        return ps;
    }


    @Override
    protected void beforeCreate(Car entity) throws DAOException {
        if (entity.getModel() != null) {
            //TODO instead setMethod need use assigning values
            entity.setModel(checkRelatedEntity(entity.getModel(), new ModelDAO()));
        }
    }


}
