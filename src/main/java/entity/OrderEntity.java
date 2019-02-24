/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 *
 * @author Simon Bojesen
 */
@Entity
public class OrderEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int orderID;
    @JoinColumn(name="FK_order")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<OrderLine> orderlines = new ArrayList();

    public OrderEntity() {
    }

    public OrderEntity(int orderID) {
        this.orderID = orderID;
    }
    

    public List<OrderLine> getOrderlines() {
        return orderlines;
    }

    public void addOrderlines(OrderLine ... orderline) {
        this.orderlines.addAll(Arrays.asList(orderline));
    }
    
    public void addOrderline(OrderLine orderline) {
        this.orderlines.add(orderline);
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
