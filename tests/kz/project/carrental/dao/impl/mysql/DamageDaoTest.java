package kz.project.carrental.dao.impl.mysql;

import kz.project.carrental.entity.*;
import kz.project.carrental.entity.enums.OrderStatus;

import java.sql.Timestamp;

public class DamageDaoTest extends MySqlDaoTest<Damage> {
    @Override
    public void setUp() throws Exception {
        abstractDAO = new DamageDAO();

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
        order.setBeginRent(new Timestamp(System.currentTimeMillis()));
        order.setEndRent(new Timestamp(System.currentTimeMillis()));

        Damage damage = new Damage();
        damage.setRepaired(false);
        damage.setPrice(222.2);
        damage.setDescription("testDescription");
        damage.setOrder(order);

        testEntity = damage;

        super.setUp();
    }

    @Override
    public void tearDown() throws Exception {
        OrderDAO orderDAO = new OrderDAO(connection);
        CarDAO carDAO = new CarDAO(connection);
        ModelDAO modelDAO = new ModelDAO(connection);
        VendorDAO vendorDAO = new VendorDAO(connection);
        UserDAO userDAO = new UserDAO(connection);

        orderDAO.delete(testEntity.getOrder());
        userDAO.delete(testEntity.getOrder().getClient());
        carDAO.delete(testEntity.getOrder().getCar());
        modelDAO.delete(testEntity.getOrder().getCar().getModel());
        vendorDAO.delete(testEntity.getOrder().getCar().getModel().getVendor());

        super.tearDown();
    }
}
