package utils;

import dbfacades.DemoFacade;
import entity.Customer;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Tester {

    public static void main(String[] args) {
       EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        
        
    }

}
