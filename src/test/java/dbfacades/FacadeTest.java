package dbfacades;

import entity.Customer;
import entity.ItemType;
import entity.OrderLine;
import entity.OrderOne;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FacadeTest {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu", null);

    DemoFacade facade = new DemoFacade(emf);

    @Before
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Delete all, since some future test cases might add/change data
            em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();
            em.createNativeQuery("truncate table itemtype").executeUpdate();
            em.createNativeQuery("truncate table orderline").executeUpdate();
            em.createNativeQuery("truncate table orderone").executeUpdate();
            em.createNativeQuery("truncate table customer").executeUpdate();
            em.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1;").executeUpdate();
            //Add our test data
            Customer c1 = new Customer("John Doe", "John@Doe.dk");
            Customer c2 = new Customer("Jane Doe", "Jane@Doe.dk");
            em.persist(c1);
            em.persist(c2);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            em.close();
        }
    }

    @Test
    public void createCustomer() {
        List<Customer> customersBefore = facade.getAllCustomers();
        facade.createCustomer("name", "email");
        List<Customer> customers = facade.getAllCustomers();
        Assert.assertEquals(customersBefore.size() + 1, customers.size());
    }

    @Test
    public void getCustomerByID() {
        List<Customer> customers = facade.getAllCustomers();
        Customer customer = facade.getCustomerByID(customers.get(0).getId());
        Assert.assertEquals(customers.get(0).getName(), customer.getName());
    }

    @Test
    public void getAllCustomers() {
        List<Customer> customers = facade.getAllCustomers();
        Assert.assertFalse(customers.isEmpty());
    }

    @Test
    public void createOrder() {
        List<OrderOne> ordersBefore = facade.getAllOrders();
        OrderOne orderOne = new OrderOne();
        facade.createOrder(orderOne);
        List<OrderOne> orders = facade.getAllOrders();
        Assert.assertEquals(ordersBefore.size() + 1, orders.size());
    }

    @Test
    public void addOrderToCustomer() {
        List<OrderOne> ordersBefore = facade.getAllOrders();
        OrderOne orderOne = new OrderOne();
        List<Customer> customers = facade.getAllCustomers();
        orderOne.setCustomer(customers.get(0));
        customers.get(0).getOrder().add(orderOne);
        customers.get(0).setOrder(customers.get(0).getOrder());
        facade.createOrder(orderOne);
        List<OrderOne> orders = facade.getAllOrders();
        Assert.assertEquals(ordersBefore.size() + 1, orders.size());
    }

    @Test
    public void getOrderById() {
        facade.createOrder(new OrderOne());
        List<OrderOne> orders = facade.getAllOrders();
        OrderOne order = facade.getOrderById(orders.get(0).getId());
        Assert.assertEquals(orders.get(0).getId(), order.getId());
    }

    @Test
    public void getOrdersByCustomerId() {
        Customer customer = facade.getAllCustomers().get(0);
        OrderOne o1 = new OrderOne();
        o1.setCustomer(customer);
        customer.getOrder().add(o1);
        customer.setOrder(customer.getOrder());
        facade.createOrder(o1);
        List<OrderOne> orders = facade.getAllOrdersByCustomerId(customer.getId());
        Assert.assertEquals(1, orders.size());
    }

    @Test
    public void createOrderLine() {
        OrderLine orderLine = new OrderLine(22243);
        OrderOne orderOne = new OrderOne();
        System.out.println(orderLine);
        System.out.println(orderOne);
        orderLine.setOrderOne(orderOne);
        orderOne.getOrderLines().add(orderLine);
        orderOne.setOrderLines(orderOne.getOrderLines());
        facade.createOrder(orderOne);
        facade.createOrderLine(orderLine);
        List<OrderLine> orderLines = facade.getOrderLinesByOrderId(orderOne.getId());
        Assert.assertEquals(orderLine.getQuantity(), orderLines.get(0).getQuantity());

    }

    @Test
    public void createItemType() {
        ItemType itemType = new ItemType("Whopper", "Delikat", 9000.1);
        OrderLine orderLine = new OrderLine(23999999);
        
        itemType.getOrderLines().add(orderLine);
        
        orderLine.setItemType(itemType);
        
        facade.createItemType(itemType);
       // facade.createOrderLine(orderLine);
        List<ItemType> itemTypes = facade.getAllItemType();
        Assert.assertEquals(itemType.getName(), itemTypes.get(0).getName());
    }

    @Test
    public void getTotalPriceOfOrder() {
        OrderOne order = new OrderOne();
        OrderLine line = new OrderLine(2500000);
        ItemType type = new ItemType("Fisk", "Klam, og den kunne svømme, men ikke mere.... *sad face* RIP!", 5.01);

        order.getOrderLines().add(line);

        line.setOrderOne(order);
        line.setItemType(type);

        type.getOrderLines().add(line);
        type.setOrderLines(type.getOrderLines());

        facade.createOrder(order);
        //facade.createOrderLine(line);
        //facade.createItemType(type);

        OrderLine l = facade.getAllOrderLines().get(0);
        ItemType i = facade.getAllItemType().get(0);

        Assert.assertEquals(12525000, l.getQuantity() * i.getPrice(), 0.2);
    }
}
