/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringcompany.dao;

import com.mycompany.flooringcompany.dto.Order;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 *
 * @author alinc
 */
public interface OrderDao {
    
    void createAnOrder(Order currentOrder) throws FlooringCompanyPersistenceException;
    
    Order getOrder(LocalDate date,int orderNumber )throws FlooringCompanyPersistenceException;
    
    List<Order> displayOrders(LocalDate orderDate)throws FlooringCompanyPersistenceException;
    
    void editAnOrder(Order updatedOrder)throws FlooringCompanyPersistenceException;
    
    void removeOrder(Order removedOrder)throws FlooringCompanyPersistenceException;
    
    int getLatestOrderNumber()throws FlooringCompanyPersistenceException;
}
