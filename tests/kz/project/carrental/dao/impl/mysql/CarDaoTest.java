package kz.project.carrental.dao.impl.mysql;

import kz.project.carrental.entity.Car;
import kz.project.carrental.entity.Model;
import kz.project.carrental.entity.Vendor;

public class CarDaoTest extends MySqlDaoTest<Car> {

    @Override
    public void setUp() throws Exception {
        abstractDAO = new CarDAO();

        Vendor vendor = new Vendor();
        vendor.setName("testVendorName");

        Model model = new Model();
        model.setVendor(vendor);
        model.setName("testModelName");

        Car car = new Car();
        car.setRegNumber("testRegNum");
        car.setName("testName");
        car.setPhoto("testPhoto.png");
        car.setPrice(111);
        car.setModel(model);

        testEntity = car;

        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        ModelDAO modelDAO = new ModelDAO(connection);
        VendorDAO vendorDAO = new VendorDAO(connection);
        modelDAO.delete(testEntity.getModel());
        vendorDAO.delete(testEntity.getModel().getVendor());
        super.tearDown();
    }
}
