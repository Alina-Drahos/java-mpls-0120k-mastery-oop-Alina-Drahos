/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringcompany.dao;

import com.mycompany.flooringcompany.dto.TaxRate;
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
public class TaxDaoFileImplTest {
    TaxDao myTaxes;
    
    public TaxDaoFileImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
        myTaxes= new TaxDaoFileImpl("Data\\Taxes.txt");
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetTaxRate() throws Exception {
    }

    @Test
    public void testGetAllTaxRates() throws Exception {
       List<TaxRate> allRates= myTaxes.getAllTaxRates();
       assertEquals(4,allRates.size());
       String state= allRates.get(1).getStateAbbreviation();
       assertEquals("WA",state);
    }
    
}
