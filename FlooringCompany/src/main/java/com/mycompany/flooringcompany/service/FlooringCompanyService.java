/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringcompany.service;

import com.mycompany.flooringcompany.dao.ExportDao;
import com.mycompany.flooringcompany.dao.FlooringCompanyPersistenceException;
import com.mycompany.flooringcompany.dao.OrderDao;
import com.mycompany.flooringcompany.dao.ProductDao;
import com.mycompany.flooringcompany.dao.TaxDao;
import com.mycompany.flooringcompany.dto.Order;
import com.mycompany.flooringcompany.dto.Product;
import com.mycompany.flooringcompany.dto.TaxRate;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author alinc
 */
public class FlooringCompanyService {

    private TaxDao taxDao;
    private ProductDao productDao;
    private OrderDao orderDao;
    private ExportDao exportDao;

    public FlooringCompanyService(TaxDao taxDao, ProductDao productDao, OrderDao orderDao, ExportDao exportDao) {
        this.taxDao = taxDao;
        this.productDao = productDao;
        this.orderDao = orderDao;
        this.exportDao = exportDao;
    }

    public List<Order> getAllOrders(LocalDate date) throws FlooringCompanyPersistenceException {
        List<Order> allOrders = orderDao.displayOrders(date);
        return allOrders;
    }

    public Order getOrder(LocalDate orderDate, int orderNumber) throws FlooringCompanyPersistenceException {
        return orderDao.getOrder(orderDate, orderNumber);
    }

    public Order addOneOrder(Order newOrder) throws FlooringCompanyPersistenceException, NameInvalidException, ProductNotFoundException, StateNotFoundException {
        validateUserName(newOrder.getCustomerName());
        validateState(newOrder.getState());
        validateProduct(newOrder.getProductType());

        int orderNumber = orderDao.getLatestOrderNumber() + 1;
        newOrder.setOrderNumber(orderNumber);
        newOrder.setOrderDate(java.time.LocalDate.now());
        
        updateCosts(newOrder);

        orderDao.createAnOrder(newOrder);
        return newOrder;

    }

    private void validateUserName(String name) throws NameInvalidException {
        //check name against pattern
        boolean isNameValid = Pattern.matches("[a-zA-Z0-9,. ]+", name);
        if (!isNameValid) {
            throw new NameInvalidException("The name you provided is not valid.Please enter a new Name!");
        }

    }

    private void validateState(String state) throws StateNotFoundException, FlooringCompanyPersistenceException {
        //check if the stateAbbreviation
        boolean isStateAbbValid = isStateValid(state);
        if (!isStateAbbValid) {
            throw new StateNotFoundException("We do not offer our products in this state.Please pick a different state!");
        }

    }

    private void validateProduct(String product) throws ProductNotFoundException, FlooringCompanyPersistenceException {
        //check product against list of all products
        boolean validProduct = isProdcutValid(product);
        if (!validProduct) {
            throw new ProductNotFoundException("We do not offer this kind of product.Please choose an available product!");
        }

    }

    public List<Product> getAllProducts() throws FlooringCompanyPersistenceException {
        return productDao.getProducts();
    }

    public List<TaxRate> getAllTaxRate() throws FlooringCompanyPersistenceException {
        return taxDao.getAllTaxRates();
    }

    public Order editOneOrder(Order orderToUpdate, String name, String state,
            String productType, BigDecimal area) throws FlooringCompanyPersistenceException,
            NameInvalidException, ProductNotFoundException, StateNotFoundException {

        Order oldOrder = orderDao.getOrder(orderToUpdate.getOrderDate(), orderToUpdate.getOderNumber());

        boolean updateCosts = false;

        if (!name.isEmpty()) {
            validateUserName(name);
            oldOrder.setCustomerName(name);
        }

        if (!area.equals(new BigDecimal("0.00"))) {
            orderToUpdate.setArea(area);
            updateCosts = true;
        }

        if (!state.isEmpty()) {
            validateState(state);
            oldOrder.setState(state);
            updateCosts = true;
        }

        if (!productType.isEmpty()) {
            validateProduct(productType);
            oldOrder.setProductType(productType);
            updateCosts = true;
        }

        if (updateCosts == true) {
            updateCosts(oldOrder);

        }
        orderDao.editAnOrder(oldOrder);
        return oldOrder;
    }

    private void updateCosts(Order order) throws FlooringCompanyPersistenceException {
        Product newProduct = productDao.getProduct(order.getProductType());
        BigDecimal applicableTaxRate = taxDao.getTaxRate(order.getState()).setScale(2, RoundingMode.HALF_UP);

        BigDecimal materialCost = order.getArea().multiply(newProduct.getCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP);
        BigDecimal laborCost = order.getArea().multiply(newProduct.getLaborCostPerSquareFoot()).setScale(2, RoundingMode.HALF_UP);

        BigDecimal tax = materialCost.add(laborCost).multiply(applicableTaxRate.divide(new BigDecimal("100"))).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = materialCost.add(laborCost).add(tax).setScale(2, RoundingMode.HALF_UP);

        order.setTaxRate(applicableTaxRate);
        order.setCostPerSquareFoot(newProduct.getCostPerSquareFoot());
        order.setLaborCostPerSquareFoot(newProduct.getLaborCostPerSquareFoot());
        order.setMaterialCost(materialCost);
        order.setLaborCost(laborCost);
        order.setTax(tax);
        order.setTotal(total);

    }

    public void removeOneOrder(Order orderToRemove) throws FlooringCompanyPersistenceException {
        orderDao.removeOrder(orderToRemove);
    }

    public void exportAllData() throws FlooringCompanyPersistenceException {
        exportDao.exportAllData();
    }

    private boolean isProdcutValid(String productType) throws FlooringCompanyPersistenceException {
        for (Product product : getAllProducts()) {
            if (productType.equals(product.getProductType())) {
                return true;
            }
        }
        return false;
    }

    private boolean isStateValid(String stateAbbreviation) throws FlooringCompanyPersistenceException {
        for (TaxRate states : getAllTaxRate()) {
            if (stateAbbreviation.equals(states.getStateAbbreviation())) {
                return true;
            }
        }
        return false;
    }
}
