/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringcompany.controller;

import com.mycompany.flooringcompany.dao.FlooringCompanyPersistenceException;
import com.mycompany.flooringcompany.dto.Order;
import com.mycompany.flooringcompany.dto.Product;
import com.mycompany.flooringcompany.dto.TaxRate;
import com.mycompany.flooringcompany.service.FlooringCompanyService;
import com.mycompany.flooringcompany.service.NameInvalidException;
import com.mycompany.flooringcompany.service.ProductNotFoundException;
import com.mycompany.flooringcompany.service.StateNotFoundException;
import com.mycompany.flooringcompany.ui.FlooringCompanyView;
import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author alinc
 */
public class FlooringCompanyController {

    private FlooringCompanyService service;
    private FlooringCompanyView view;

    public FlooringCompanyController(FlooringCompanyService service, FlooringCompanyView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        welcomeMessage();
        boolean keepGoing = true;
        while (keepGoing) {
            try {
                int menuSelection = 0;
                menuSelection = getMenuSelection();
                switch (menuSelection) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        exportAllOrders();
                        break;
                    case 6:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

            } catch (FlooringCompanyPersistenceException ex) {
                view.displayErrorMessage(ex.getMessage());
            }
        }
    }

    private void welcomeMessage() {
        view.displayWelcome();
    }

    private int getMenuSelection() {
        return view.displayMenuAndGetSelection();
    }

    private void displayOrders() throws FlooringCompanyPersistenceException {
        LocalDate date = view.getDate();
        List<Order> allOrders = service.getAllOrders(date);
        view.displayOrdersForDate(date, allOrders);
    }

    private void addOrder() throws FlooringCompanyPersistenceException {
        List<Product> allProducts = service.getAllProducts();
        List<TaxRate> allTaxRates = service.getAllTaxRate();
        Order newOrder = view.addOneOrder(allProducts, allTaxRates);
        while (true) {
            try {
                service.addOneOrder(newOrder);
                break;
            } catch (NameInvalidException ex) {
                view.displayErrorMessage(ex.getMessage());
                String name = view.newCustomerName();
                newOrder.setCustomerName(name);
            } catch (ProductNotFoundException ex) {
                view.displayErrorMessage(ex.getMessage());
                String product = view.newProduct(allProducts);
                newOrder.setProductType(product);
            } catch (StateNotFoundException ex) {
                view.displayErrorMessage(ex.getMessage());
                String stateAbbreviation = view.newState(allTaxRates);
                newOrder.setState(stateAbbreviation);
            }

        }
    }

    private void editOrder() throws FlooringCompanyPersistenceException {
        List<Product> allProducts = service.getAllProducts();
        List<TaxRate> allTaxRates = service.getAllTaxRate();
        int orderNumber;
        Order order;
        Order orderToEdit;
        while (true) {
            try {
                order = view.findOrderToEdit();
                orderToEdit = service.getOrder(order.getOrderDate(), order.getOderNumber());
                break;
            } catch (Exception ex) {
                view.displayErrorMessage("Order number not found. Please try again.");
            }
        }

        Order newOrder = view.orderToEdit(orderToEdit);
        if (newOrder == null) {
            return;
        }
        while (true) {
            try {
                service.editOneOrder(orderToEdit, newOrder.getCustomerName(), newOrder.getState(), newOrder.getProductType(), newOrder.getArea());
                break;
            } catch (NameInvalidException ex) {
                view.displayErrorMessage(ex.getMessage());
                String name = view.newCustomerName();
                newOrder.setCustomerName(name);
            } catch (ProductNotFoundException ex) {
                view.displayErrorMessage(ex.getMessage());
                String product = view.newProduct(allProducts);
                newOrder.setProductType(product);
            } catch (StateNotFoundException ex) {
                view.displayErrorMessage(ex.getMessage());
                String stateAbbreviation = view.newState(allTaxRates);
                newOrder.setState(stateAbbreviation);
            }

        }

    }

    private void removeOrder() throws FlooringCompanyPersistenceException {

        while (true) {
            try {
                Order order = view.removeOrder();
                Order orderToRemove = service.getOrder(order.getOrderDate(), order.getOderNumber());
                Boolean shouldRemove = view.removeOneOrder(orderToRemove);
                if (shouldRemove) {
                    service.removeOneOrder(orderToRemove);
                }
                break;
            } catch (Exception e) {
                view.displayErrorMessage("Order does not exist. Try again.");
            }
        }

    }

    private void exportAllOrders() throws FlooringCompanyPersistenceException {
        boolean shouldExport = view.exportAllData();
        if (shouldExport) {
            service.exportAllData();
        }
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }
}
