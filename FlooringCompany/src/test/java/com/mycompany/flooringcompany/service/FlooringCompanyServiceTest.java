/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringcompany.service;

import com.mycompany.flooringcompany.dao.ExportDao;
import com.mycompany.flooringcompany.dao.FlooringCompanyDaoMock;
import com.mycompany.flooringcompany.dao.FlooringCompanyPersistenceException;
import com.mycompany.flooringcompany.dao.OrderDao;
import com.mycompany.flooringcompany.dao.ProductDao;
import com.mycompany.flooringcompany.dao.TaxDao;
import com.mycompany.flooringcompany.dto.Order;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import static java.time.temporal.TemporalQueries.localDate;
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
public class FlooringCompanyServiceTest {

    private FlooringCompanyService service;

    public FlooringCompanyServiceTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        FlooringCompanyDaoMock dao = new FlooringCompanyDaoMock();
        service = new FlooringCompanyService(dao, dao, dao, dao);
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetAllOrders() throws FlooringCompanyPersistenceException {
        LocalDate date = LocalDate.now();
        List<Order> orders = service.getAllOrders(date);
        assertEquals(1, orders.size());
    }

    @Test
    public void testGetOrder() throws FlooringCompanyPersistenceException {
        int orderNumber = service.getOrder(java.time.LocalDate.now(), 1).getOderNumber();

        assertEquals(1, orderNumber);
    }

    @Test
    public void testAddOneOrder() throws FlooringCompanyPersistenceException, NameInvalidException, ProductNotFoundException, StateNotFoundException {
        Order calculatedOrder = service.addOneOrder(new Order(0, "Ada Lovelace", "CA", null, "Tile", new BigDecimal("249.00"), null,
                null, null, null, null, null, null));
        assertEquals(new BigDecimal("2381.06"), calculatedOrder.getTotal().setScale(2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal("476.21"), calculatedOrder.getTax().setScale(2, RoundingMode.HALF_UP));
        assertEquals(calculatedOrder, new Order(2, "Ada Lovelace", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("249.00"),
                new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("871.50"), new BigDecimal("1033.35"),
                new BigDecimal("476.21"), new BigDecimal("2381.06"), java.time.LocalDate.now()));
    }

    @Test
    public void testEditOneOrder() throws FlooringCompanyPersistenceException, NameInvalidException, ProductNotFoundException, StateNotFoundException {
        //2,Doctor Who,WA,9.25,Wood,243.00,5.15,4.75,1251.45,1154.25,216.51,2622.21
        Order order1 = new Order(2, "Ada Lovelace", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("249.00"),
                new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("871.50"), new BigDecimal("1033.35"),
                new BigDecimal("476.21"), new BigDecimal("2381.06"), java.time.LocalDate.now());
        Order editedOrder1 = service.editOneOrder(order1, "Gopher", "", "", new BigDecimal("249.00"));
        assertEquals("Gopher", editedOrder1.getCustomerName());
        Order editedOrder2 = service.editOneOrder(order1, "", "", "Carpet", new BigDecimal("0.00"));
        assertEquals(new BigDecimal("1353.94"), editedOrder2.getTotal());
    }

    @Test
    public void testNameValidaty() throws FlooringCompanyPersistenceException, StateNotFoundException, ProductNotFoundException {
        Order orderInvalidName = new Order(2, "AdaLovelace", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("249.00"),
                new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("871.50"), new BigDecimal("1033.35"),
                new BigDecimal("476.21"), new BigDecimal("2381.06"), java.time.LocalDate.now());
        boolean threwExceptionName = false;
        boolean threwExceptionState = false;
        try {
            service.editOneOrder(orderInvalidName, "$%^&", "CA", "Tile", new BigDecimal("0.00"));

        } catch (NameInvalidException ex) {
            threwExceptionName = true;
        }
        assertTrue(threwExceptionName);
    }

    @Test
    public void productValidaty() throws FlooringCompanyPersistenceException, NameInvalidException, StateNotFoundException {
        Order orderInvalidName = new Order(2, "AdaLovelace", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("249.00"),
                new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("871.50"), new BigDecimal("1033.35"),
                new BigDecimal("476.21"), new BigDecimal("2381.06"), java.time.LocalDate.now());

        boolean threwExceptionProduct = false;
        try {
            service.editOneOrder(orderInvalidName, "Ada Lovelace", "CA", "Diamond", new BigDecimal("0.00"));
        } catch (ProductNotFoundException ex) {
            threwExceptionProduct = true;
        }

        assertTrue(threwExceptionProduct);

    }

    @Test
    public void stateValidaty() throws FlooringCompanyPersistenceException, NameInvalidException, ProductNotFoundException {
        Order orderInvalidName = new Order(2, "AdaLovelace", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("249.00"),
                new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("871.50"), new BigDecimal("1033.35"),
                new BigDecimal("476.21"), new BigDecimal("2381.06"), java.time.LocalDate.now());

        boolean threwExceptionState = false;
        try{
            service.editOneOrder(orderInvalidName, "Ada Lovelace", "CAT", "Tile", new BigDecimal("0.00"));
        }catch(StateNotFoundException ex){
            threwExceptionState=true;
        }
        
        assertTrue(threwExceptionState);
    }

    @Test
    public void testRemoveOneOrder() {
    }

    @Test
    public void testExportAllData() {

    }

}
