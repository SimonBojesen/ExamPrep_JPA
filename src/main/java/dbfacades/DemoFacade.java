/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dbfacades;

import entity.Customer;
import entity.OrderEntity;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Simon Bojesen
 */
public class DemoFacade {
    EntityManagerFactory emf;

    public DemoFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }
    
    public Customer createCustomer(Customer cust) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(cust);
            em.getTransaction().commit();
            return cust;
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

    OrderEntity createOrder(OrderEntity order) {
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

}
