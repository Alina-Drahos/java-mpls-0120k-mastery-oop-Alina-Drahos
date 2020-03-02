/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringcompany.dao;

import com.mycompany.flooringcompany.dto.Order;
import com.mycompany.flooringcompany.dto.Product;
import com.mycompany.flooringcompany.dto.TaxRate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author alinc
 */
public class FlooringCompanyDaoMock implements OrderDao, ExportDao, ProductDao, TaxDao {

    @Override
    public void createAnOrder(Order currentOrder) {
        
    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Order thisOrder = new Order(1, "Ada Lovelace", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("249.00"),
                new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("871.50"), new BigDecimal("1033.35"),
                new BigDecimal("476.21"), new BigDecimal("2381.06"), java.time.LocalDate.now());

        return thisOrder;
    }

    public List<Order> displayOrders(Date orderDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editAnOrder(Order updatedOrder) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeOrder(Order removedOrder) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getLatestOrderNumber() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return 1;
    }

    @Override
    public void exportAllData() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Product getProduct(String ProductType) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        switch(ProductType){
            case "Carpet":
                return new Product("Carpet",new BigDecimal("2.25"),
                new BigDecimal("2.10"));
                
            case "Laminat":
                return new Product("Laminate",new BigDecimal("1.75"),
                new BigDecimal("2.10"));
                
            case"Tile":
            return new Product("Tile",new BigDecimal("3.50"),
                new BigDecimal("4.15"));
            
            case "Wood":
                return new Product("Wood",new BigDecimal("5.15"),
                new BigDecimal("4.75"));
                
        }
        
        return null;
    }

    @Override
    public List<Product> getProducts() {
       List<Product>allProducts= new ArrayList<>();
       allProducts.add(new Product("Carpet", new BigDecimal("2.25"), new BigDecimal("2.10")));
       allProducts.add(new Product("Laminate", new BigDecimal("1.75"), new BigDecimal("2.10")));
       allProducts.add(new Product("Tile", new BigDecimal("3.50"), new BigDecimal("4.15")));
       allProducts.add(new Product("Wood", new BigDecimal("5.15"), new BigDecimal("4.75")));
       return allProducts;
       
    }

    @Override
    public BigDecimal getTaxRate(String stateAbbreviation) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        switch(stateAbbreviation){
            case "TX":
                return new BigDecimal("4.45");
            case "WA":
                return new BigDecimal("9.25");
            case "KY":
                return new BigDecimal("6.00");
            case "CA":
                return new BigDecimal("25.00");
                
                
        }
        
        return new BigDecimal("0.00");
    }

    @Override
    public List<Order> displayOrders(LocalDate orderDate) throws FlooringCompanyPersistenceException {
        Order thisOrder = new Order(1, "Ada Lovelace", "CA", new BigDecimal("25.00"), "Tile", new BigDecimal("249.00"),
                new BigDecimal("3.50"), new BigDecimal("4.15"), new BigDecimal("871.50"), new BigDecimal("1033.35"),
                new BigDecimal("476.21"), new BigDecimal("2381.06"), java.time.LocalDate.now());
         List<Order> allOrders= new ArrayList<>();
         allOrders.add(thisOrder);
         return allOrders;
    }

    @Override
    public List<TaxRate> getAllTaxRates() throws FlooringCompanyPersistenceException {
       List<TaxRate>allRates= new ArrayList<>();
       allRates.add(new TaxRate("TX","Texas" ,new BigDecimal("4.45")));
       allRates.add(new TaxRate("WA","Washington" ,new BigDecimal("9.25")));
       allRates.add(new TaxRate("KY","Kentucky" ,new BigDecimal("6.00")));
       allRates.add(new TaxRate("CA","California" ,new BigDecimal("25.00")));
       return allRates;
    }

}
