/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dbfacades;

import entity.Customer;
import entity.OrderEntity;
import entity.OrderLine;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Simon Bojesen
 */
public class DemoFacade {
    EntityManagerFactory emf;

    public DemoFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public void createCustomer(Customer cust) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cust);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
    public Customer findCustomerById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Customer c = em.find(Customer.class, id);
            em.getTransaction().commit();
            return c;
        } finally {
            em.close();
        }
    }

    public List<Customer> getAllCustomer() {
        EntityManager em = emf.createEntityManager();
        try {
            return (List<Customer>) em.createQuery("select c from Customer c").getResultList();
        } finally {
            em.close();
        }
    }

    public OrderEntity createOrder(OrderEntity order) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(order);
            em.getTransaction().commit();
            return order;
        } finally {
            em.close();
        }
    }

    public void addOrderToCustomer(OrderEntity order, Customer cust) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            cust.addOrder(order);
            em.merge(cust);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    OrderEntity findOrderById(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            OrderEntity order = em.find(OrderEntity.class, id);
            em.getTransaction().commit();
            return order;
        } finally {
            em.close();
        }
    }

    List<OrderEntity> getAllOrderFromCustomer(Customer c) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            List<OrderEntity> orders = c.getOrders();
            em.getTransaction().commit();
            return orders;
        } finally {
            em.close();
        }
    }

    void createOrderLineAndAddToOrder(OrderLine ol, OrderEntity order) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(ol);
            order.addOrderline(ol);
            em.merge(order);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

}
