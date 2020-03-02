/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringcompany.dao;

import com.mycompany.flooringcompany.dto.Order;
import com.mycompany.flooringcompany.service.FlooringCompanyServiceTest;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
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
    public static void setUpClass() throws IOException {
        if (new File("TestOrders\\Orders_01012020.txt").isFile()) {
            Path filePath = Paths.get("TestOrders\\Orders_01012020.txt");
            Files.delete(filePath);
        }
        if (new File("TestOrders\\Orders_02012020.txt").isFile()) {
            Path filePath = Paths.get("TestOrders\\Orders_02012020.txt");
            Files.delete(filePath);
        }
        if (new File("TestOrders\\Orders_03012020.txt").isFile()) {
            Path filePath = Paths.get("TestOrders\\Orders_03012020.txt");
            Files.delete(filePath);
        }
        if (new File("TestOrders\\Orders_03012020.txt").isFile()) {
            Path filePath = Paths.get("TestOrders\\Orders_02102020.txt");
            Files.delete(filePath);
        }

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
                new BigDecimal("476.21"), new BigDecimal("2381.06"), LocalDate.of(2020, 02, 01));
        testDao.createAnOrder(newOrder);
        Order myOrder = testDao.getOrder(LocalDate.of(2020, 02, 01), 1);
        assertEquals(1, myOrder.getOderNumber());
        assertEquals(newOrder, myOrder);
    }

    @Test
    public void testEditAnOrder() throws FlooringCompanyPersistenceException {
        Order newOrder = new Order(2, "Ada Lovelace", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("249.00"),
                new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("871.50"), new BigDecimal("1033.35"),
                new BigDecimal("476.21"), new BigDecimal("2381.06"), LocalDate.of(2020, 03, 01));
        testDao.createAnOrder(newOrder);
        testDao.editAnOrder(new Order(2, "Charles Gopher", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("249.00"),
                new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("871.50"), new BigDecimal("1033.35"),
                new BigDecimal("476.21"), new BigDecimal("2381.06"), LocalDate.of(2020, 03, 01)));
        Order editedOrder = testDao.getOrder(LocalDate.of(2020, 03, 01), 2);
        assertNotEquals(newOrder, editedOrder);
    }

    @Test
    public void displayOrders() throws FlooringCompanyPersistenceException {
        Order newOrder1 = new Order(2, "Ada Lovelace", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("249.00"),
                new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("871.50"), new BigDecimal("1033.35"),
                new BigDecimal("476.21"), new BigDecimal("2381.06"), LocalDate.of(2020, 01, 01));

        Order newOrder2 = new Order(3, "Gopher Flugger", "CA", new BigDecimal("25.00"), "Carpet", new BigDecimal("959.00"),
                new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("871.50"), new BigDecimal("1033.35"),
                new BigDecimal("476.21"), new BigDecimal("2381.06"), LocalDate.of(2020, 01, 01));

        testDao.createAnOrder(newOrder1);
        testDao.createAnOrder(newOrder2);

        List<Order> allOrders = testDao.displayOrders(LocalDate.of(2020, 01, 01));

        assertEquals(newOrder1, allOrders.get(0));
        assertEquals(newOrder2, allOrders.get(1));

    }

    @Test
    public void testRemoveAnOrder() throws FlooringCompanyPersistenceException {
        Order newOrder1 = new Order(6, "Ada Lovelace", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("249.00"),
                new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("871.50"), new BigDecimal("1033.35"),
                new BigDecimal("476.21"), new BigDecimal("2381.06"), LocalDate.of(2020, 02, 10));
        Order newOrder2= new Order(9, "Ada Lovelace", "CA", new BigDecimal("25.00"), "Carpet", new BigDecimal("249.00"),
                new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("871.50"), new BigDecimal("1033.35"),
                new BigDecimal("476.21"), new BigDecimal("2381.06"), LocalDate.of(2020, 02, 10));
        testDao.createAnOrder(newOrder1);
        testDao.createAnOrder(newOrder2);
        testDao.removeOrder(newOrder2);
        
        List<Order> allOrders=testDao.displayOrders(LocalDate.of(2020,02,10));
        assertEquals(1,allOrders.size());
        
    }

}
