package kz.project.carrental.base;

import kz.project.carrental.base.exception.ConnectionException;

import java.sql.Connection;

public interface ConnectionSource {

    /**
     * Returns connection to data base or throws ConnectionException.
     *
     * @return connection
     * @throws kz.project.carrental.base.exception.ConnectionException - if can not get a connection.
     */
    public Connection getConnection() throws ConnectionException;

    /**
     * Close passed connection.
     *
     * @param connection to close.
     * @return true - if connection was closed, else false.
     */
    public boolean closeConnection(Connection connection);
}
