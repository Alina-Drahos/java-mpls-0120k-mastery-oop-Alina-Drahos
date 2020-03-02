/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringcompany.dao;

import com.mycompany.flooringcompany.dto.Product;
import java.io.File;
import java.math.BigDecimal;
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
public class ProductDaoFileImplTest {
    ProductDao myProduct;
    public ProductDaoFileImplTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
       myProduct= new ProductDaoFileImpl("Data\\Products.txt");
        
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetProduct() throws Exception {
    }

    @Test
    public void testGetProducts() throws Exception {
        List<Product> myProducts=myProduct.getProducts();
        assertEquals(4,myProducts.size());
        String containsCarpet= myProducts.get(0).getProductType();
        BigDecimal costs=myProducts.get(0).getCostPerSquareFoot();
        assertEquals(new BigDecimal("2.25"),costs);
        assertEquals("Carpet",containsCarpet);
    }
  
    
}
