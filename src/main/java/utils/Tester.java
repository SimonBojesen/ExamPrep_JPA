package utils;

import dbfacades.DemoFacade;
import entity.Customer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Tester {

    public static void main(String[] args) {
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        
        DemoFacade df = new DemoFacade(emf);
        Customer cust = new Customer("børge", "børge@børgesen.dk");
        Customer c = df.createCustomer(cust);
        System.out.println(c.getId() + " " + c.getName());
    }

}
