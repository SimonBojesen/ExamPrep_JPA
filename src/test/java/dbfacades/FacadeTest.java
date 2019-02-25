package dbfacades;

import entity.Customer;
import entity.ItemType;
import entity.OrderEntity;
import entity.OrderLine;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * UNIT TEST example that mocks away the database with an in-memory database See
 * Persistence unit in persistence.xml in the test packages
 *
 * Use this in your own project by: - Delete everything inside the setUp method,
 * but first, observe how test data is created - Delete the single test method,
 * and replace with your own tests
 *
 */
public class FacadeTest {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu-test", null);

    DemoFacade facade = new DemoFacade(emf);

    
    /**
     * Setup test data in the database to a known state BEFORE Each test
     */
    @Before
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //First we add 3 ItemTypes
            ItemType item1 = new ItemType("Spider-Man Comicbook", "A great comicbook for kids", 100.00);
            ItemType item2 = new ItemType("Pliers", "A tool for bending things back in place", 50.00);
            ItemType item3 = new ItemType("Asus Laptop", "Do you want to work and play games on the same laptop? Then this is the laptop for you", 10000.00);
            //First customer will have one order with one orderline keeping it a little simpler
            Customer c1 = new Customer("Simon", "simon@hotmail.com");
            Customer c2 = new Customer("Benjamin", "benjamin123@live.dk");
            //order and orderline setup for customer 1
            OrderLine ol1 = new OrderLine(10);
            ol1.setItem(item1);
            OrderEntity o1 = new OrderEntity(1);
            o1.addOrderline(ol1);
            c1.addOrder(o1);

            //Order and orderline setup for customer 2 this time made with arrays just for fun
            OrderLine ol2 = new OrderLine(3);
            OrderLine ol3 = new OrderLine(5);
            ol2.setItem(item2);
            ol3.setItem(item3);
            OrderEntity o2 = new OrderEntity(2);
            o2.addOrderlines(ol2, ol3);

            OrderLine ol4 = new OrderLine(12);
            ol4.setItem(item3);
            OrderEntity o3 = new OrderEntity(3);
            o2.addOrderline(ol4);
            c2.addOrders(o2, o3);
            //Lastly before persisting well add all the orderlines to their linked item for both customer1 and 2
            item1.addOrderline(ol1);
            item2.addOrderline(ol2);
            item3.addOrderlines(ol3, ol4);
            em.persist(item1);
            em.persist(item2);
            em.persist(item3);
            em.persist(c1);
            em.persist(c2);
            em.persist(ol1);
            em.persist(o1);
            em.persist(ol2);
            em.persist(ol3);
            em.persist(o2);
            em.persist(ol4);
            em.persist(o3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // Test the single method in the Facade
    @Test
    public void createCustomer() {
        EntityManager em = emf.createEntityManager();
        try {
            Customer expected = new Customer("hans", "hans@hansen.dk");
            facade.createCustomer(expected);
            Customer result = em.find(Customer.class, expected.getId());
            Assert.assertEquals(expected.getName(), result.getName());
        } finally {
            em.close();
        }
    }

    @Test
    public void findCustomerById() {
        Customer c = facade.findCustomerById(1);
        Assert.assertEquals(1, (int) c.getId());
    }
    
//    @Test
//    public void getAllCustomers() {
//        EntityManager em = emf.createEntityManager();
//        try {
//            List<Customer> expected = facade.getAllCustomer();
//            Assert.assertEquals(2, expected.size());
//        } finally {
//            em.close();
//        }
//    }

    @Test
    public void createOrder() {
        EntityManager em = emf.createEntityManager();
        try {
            OrderEntity order = new OrderEntity();
            OrderEntity expected = facade.createOrder(order);
            OrderEntity result = em.find(OrderEntity.class, expected.getId());
            Assert.assertEquals(expected.getId(), result.getId());
        } finally {
            em.close();
        }
    }
    
    @Test
    public void addOrderToCustomer() {
        EntityManager em = emf.createEntityManager();
        try {
            boolean b = false;
            OrderEntity order = em.find(OrderEntity.class, 1);
            Customer cust = em.find(Customer.class, 2);
            facade.addOrderToCustomer(order, cust);
            List<OrderEntity> orders = cust.getOrders();
            for (OrderEntity o : orders) {
                if (o.getId().intValue() == order.getId().intValue()) {
                    b = true;
                }
            }
            Assert.assertTrue(b);
        } finally {
            em.close();
        }
    }
    
    @Test
    public void findOrderById() {
        OrderEntity o = facade.findOrderById(1);
        Assert.assertEquals(1, (int) o.getId());
    }
    
    @Test
    public void getAllOrderFromCustomer() {
        EntityManager em = emf.createEntityManager();
        try {
            Customer c = em.find(Customer.class, 1);
            List<OrderEntity> expected = facade.getAllOrderFromCustomer(c);
            Assert.assertEquals(1, expected.size());
        } finally {
            em.close();
        }
    }
    
    @Test
    public void createOrderLineAndAddToOrder() {
        EntityManager em = emf.createEntityManager();
        try {
            boolean b = false; 
            OrderLine ol = new OrderLine(10);
            OrderEntity order = em.find(OrderEntity.class, 1);
            facade.createOrderLineAndAddToOrder(ol, order);
            List<OrderLine> olListed = order.getOrderlines();
            for (OrderLine orderline : olListed) {
                if (orderline.getId().intValue() == ol.getId().intValue()) {
                    b = true;
                }
            }
            Assert.assertTrue(b);
        } finally {
            em.close();
        }
    }
    
    
}
