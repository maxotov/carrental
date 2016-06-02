package kz.project.carrental.dao.impl.mysql;

import kz.project.carrental.entity.Vendor;

public class VendorDaoTest extends MySqlDaoTest<Vendor> {

    @Override
    public void setUp() throws Exception {
        abstractDAO = new VendorDAO();

        Vendor testVendor = new Vendor();
        testVendor.setName("testVendor");
        testEntity = testVendor;

        super.setUp();
    }

}
