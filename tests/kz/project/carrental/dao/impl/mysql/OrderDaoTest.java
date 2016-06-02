package kz.project.carrental.dao.impl.mysql;

import kz.project.carrental.entity.*;
import kz.project.carrental.entity.enums.OrderStatus;

import java.sql.Timestamp;

public class OrderDaoTest extends MySqlDaoTest<Order> {

    @Override
    public void setUp() throws Exception {
        abstractDAO = new OrderDAO();

        User client = new User();
        client.setLogin("testLogin");
        client.setPassword("testPassword");
        client.setFirstName("testFirstName");
        client.setLastName("testLastName");
        client.setMiddleName("testMiddleName");
        client.setTelephone("+7(777)777-77-77");
        client.setEmail("test@test.test");

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

        Order order = new Order();
        order.setStatus(OrderStatus.NEW);
        order.setClient(client);
        order.setCar(car);
        order.setCreateTime(new Timestamp(System.currentTimeMillis()));
        order.setBeginRent(new Timestamp(System.currentTimeMillis()));
        order.setEndRent(new Timestamp(System.currentTimeMillis()));

        testEntity = order;

        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        CarDAO carDAO = new CarDAO(connection);
        ModelDAO modelDAO = new ModelDAO(connection);
        VendorDAO vendorDAO = new VendorDAO(connection);
        UserDAO userDAO = new UserDAO(connection);

        carDAO.delete(testEntity.getCar());
        modelDAO.delete(testEntity.getCar().getModel());
        vendorDAO.delete(testEntity.getCar().getModel().getVendor());
        userDAO.delete(testEntity.getClient());

        super.tearDown();
    }
}
