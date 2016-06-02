package kz.project.carrental.dao;

import kz.project.carrental.dao.exception.DAOException;
import kz.project.carrental.entity.Entity;

import java.util.List;

public interface AbstractDAO<T extends Entity> {

    /**
     * Finds all entities.
     *
     * @return list of all entities.
     * @throws kz.project.carrental.dao.exception.DAOException - if while execute method occurs problem.
     */
    public List<T> findAll() throws DAOException;

    /**
     * Find entity by ID.
     *
     * @param id of required entities.
     * @return entity - if it was found, else null.
     * @throws DAOException - if while execute method occurs problem.
     */
    public T findById(int id) throws DAOException;

    /**
     * Find entity by its data. This method use all available entity's fields besides ID field.
     *
     * @param entity entity to find.
     * @return true - if entity was found, else false.
     * @throws DAOException - if while execute method occurs problem.
     */
    public T findByData(T entity) throws DAOException;

    /**
     * Delete entity by ID.
     *
     * @param id of entity to delete.
     * @return true - if entity was deleted, else false.
     * @throws DAOException - if while execute method occurs problem.
     */
    public boolean delete(int id) throws DAOException;

    /**
     * Delete entity by object of entity.
     *
     * @param entity to delete.
     * @return true - if entity was deleted, else false.
     * @throws DAOException - if while execute method occurs problem.
     */
    public boolean delete(T entity) throws DAOException;

    /**
     * Create new entity.
     *
     * @param entity entity to create.
     * @return created entity - if entity was created, else null.
     * @throws DAOException - if while execute method occurs problem.
     */
    public T create(T entity) throws DAOException;

    /**
     * Update an entity.
     *
     * @param entity to update.
     * @return updated entity - if entity was updated, else null.
     * @throws DAOException - if while execute method occurs problem.
     */
    public T update(T entity) throws DAOException;

    /**
     * Save an entity. If entity was created updates it, or else creates entity.
     *
     * @param entity to save.
     * @return saved entity - if entity was update, else null.
     * @throws DAOException - if while execute method occurs problem.
     */
    public T save(T entity) throws DAOException;
}
