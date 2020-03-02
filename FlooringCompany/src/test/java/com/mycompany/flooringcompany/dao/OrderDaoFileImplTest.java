/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringcompany.dao;

import com.mycompany.flooringcompany.dto.Order;
import com.mycompany.flooringcompany.service.FlooringCompanyServiceTest;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author alinc
 */
public class OrderDaoFileImplTest {
    
    OrderDao testDao;
    
    public OrderDaoFileImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws IOException {
        // testService= new FlooringCompanyServiceTest();
        String testFile = "TestOrders";
        testDao = new OrderDaoFileImpl(testFile);
        
    }
    
    @AfterEach
    public void tearDown() {
    }
    
    @Test
    public void testSomeMethod() {
    }
    
    @Test
    public void testAddAndGetOrder() throws FlooringCompanyPersistenceException {
        Order newOrder = new Order(1, "Ada Lovelace", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("249.00"),
                new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("871.50"), new BigDecimal("1033.35"),
                new BigDecimal("476.21"), new BigDecimal("2381.06"), LocalDate.of(2020, 03, 01));
        testDao.createAnOrder(newOrder);
        Order myOrder = testDao.getOrder(LocalDate.of(2020, 03, 01), 1);
        assertEquals(1, myOrder.getOderNumber());
        assertEquals(newOrder, myOrder);        
    }
    
    @Test
    public void testEditAnOrder() throws FlooringCompanyPersistenceException {
        Order newOrder = new Order(2, "Ada Lovelace", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("249.00"),
                new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("871.50"), new BigDecimal("1033.35"),
                new BigDecimal("476.21"), new BigDecimal("2381.06"), java.time.LocalDate.now());
        testDao.createAnOrder(newOrder);
        testDao.editAnOrder(new Order(2, "Charles Gopher", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("249.00"),
                new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("871.50"), new BigDecimal("1033.35"),
                new BigDecimal("476.21"), new BigDecimal("2381.06"), java.time.LocalDate.now()));
        Order editedOrder =testDao.getOrder(LocalDate.of(2020, 03, 01), 2);
        assertNotEquals(newOrder,editedOrder);       
    }
    
}
