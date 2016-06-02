package kz.project.carrental.dao.impl.mysql;

import kz.project.carrental.entity.Model;
import kz.project.carrental.entity.Vendor;

public class ModelDaoTest extends MySqlDaoTest<Model> {

    @Override
    public void setUp() throws Exception {
        abstractDAO = new ModelDAO();

        Vendor testVendor = new Vendor();
        testVendor.setName("testVendorForTestModel");

        Model testModel = new Model();
        testModel.setName("testModel");
        testModel.setVendor(testVendor);

        testEntity = testModel;

        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        VendorDAO vendorDAO = new VendorDAO(connection);
        vendorDAO.delete(testEntity.getVendor());
        super.tearDown();
    }
}
