package kz.project.carrental.dao.impl.mysql;

import kz.project.carrental.entity.Access;

public class AccessMysqlTest extends MySqlDaoTest<Access> {

    @Override
    public void setUp() throws Exception {
        abstractDAO = new AccessDAO();
        Access testAccess = new Access();
        testAccess.setName("testAccess");
        testAccess.setValue("test.Value");
        testEntity = testAccess;

        super.setUp();
    }

}
