package kz.project.carrental.dao.impl.mysql;

import kz.project.carrental.entity.Access;
import kz.project.carrental.entity.User;

import java.util.ArrayList;
import java.util.List;

public class UserDaoTest extends MySqlDaoTest<User> {

    @Override
    public void setUp() throws Exception {
        abstractDAO = new UserDAO();

        List<Access> accesses = new ArrayList<Access>();
        Access access;

        access = new Access();
        access.setName("testAccessName1");
        access.setValue("testAccessValue1");
        accesses.add(access);

        access = new Access();
        access.setName("testAccessName2");
        access.setValue("testAccessValue2");
        accesses.add(access);

        User user = new User();
        user.setLogin("testLogin");
        user.setPassword("testPassword");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setMiddleName("testMiddleName");
        user.setTelephone("+7(777)777-77-77");
        user.setEmail("test@test.test");
        user.setAccesses(accesses);

        testEntity = user;

        super.setUp();
    }

    public void testFindByLogin() throws Exception {
        UserDAO userDAO = new UserDAO(connection);
        User createUser = userDAO.create(testEntity);
        User findUser = userDAO.findByLogin(testEntity.getLogin());
        assertEquals(createUser, findUser);
        userDAO.delete(createUser);
    }

    @Override
    public void tearDown() throws Exception {
        AccessDAO accessDAO = new AccessDAO(connection);
        for (Access access : testEntity.getAccesses()) {
            accessDAO.delete(access);
        }
        super.tearDown();
    }
}
