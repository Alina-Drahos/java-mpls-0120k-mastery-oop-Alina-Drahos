/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringcompany.ui;

import com.mycompany.flooringcompany.dto.Order;
import com.mycompany.flooringcompany.dto.Product;
import com.mycompany.flooringcompany.dto.TaxRate;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author alinc
 */
public class FlooringCompanyView {

    UserIO io;

    public FlooringCompanyView(UserIO io) {
        this.io = io;
    }

    public void displayWelcome() {
        io.print("Welcome to TSG Corperate!"
                + "We are looking forward to your order!\n"
                + "Please select any of the following options!");
    }

    public int displayMenuAndGetSelection() {
        io.print("************************");
        io.print("<<FlooringProgram>>");
        io.print("1.Display Orders");
        io.print("2.Add an Order");
        io.print("3.Edit an Order");
        io.print("4.Remove an Order");
        io.print("5.Export All Data");
        io.print("6.Quit");
        io.print("************************");

        return io.readInt("Please select from the above choices.", 1, 6);
    }

    public LocalDate getDate() {
        LocalDate orderDate = io.readDate("Please enter the orderDate in the following format: YYYY-MM-DD!");
        return orderDate;
    }

    public void displayOrdersForDate(LocalDate date, List<Order> allOrders) {
        for (Order o : allOrders) {
            String oneOrder = o.getOderNumber() + " " + o.getCustomerName() + " "
                    + o.getState() + " " + o.getTaxRate() + " " + o.getProductType() + " "
                    + o.getArea() + " " + o.getCostPerSquareFoot()+" "
                    + o.getLaborCostPerSquareFoot() + " " + o.getMaterialCost() + " "
                    + o.getLaborCost() + " "+o.getTax()+" " + o.getTotal();
            io.print(oneOrder);
        }
    }

    public Order addOneOrder(List<Product> allProducts, List<TaxRate> allStates) {
        String customerName = io.readString("Please enter your first and last name!");
        io.print("Please choose from the states below!");
        for (TaxRate rate : allStates) {
            String myState = String.format("%s: %s",
                    rate.getStateName(),
                    rate.getStateAbbreviation());
            io.print(myState);
        }
        String stateAbbreviation = io.readString("Please enter the abbrevation for the state you are ordering from!");
        int i = 1;
        io.print("Please choose any of the products listed below.Please type in the name of the Product!");
        io.print("ProductType--CostPerSquareFoot--LaborCostPerSquareFoot");
        for (Product choice : allProducts) {
            String productInfo = String.format("%s : %.2f %.2f ",
                    choice.getProductType(),
                    choice.getCostPerSquareFoot(),
                    choice.getLaborCostPerSquareFoot());
            io.print(i + " " + productInfo + "\n");
            i++;
        }
        String product = io.readString("Please choose!");
        BigDecimal area = io.readBigDecimal("Please enter the square footage that is needed", "100", "1000000");

        return new Order(-1, customerName, stateAbbreviation, product, area);

    }

    public String newCustomerName() {
        String customerName = io.readString("Please reenter your first and last name!");
        return customerName;
    }

    public String newProduct(List<Product> allProducts) {
        int i = 0;
        io.print("ProductType--CostPerSquareFoot--LaborCostPerSquareFoot");
        for (Product choice : allProducts) {
            String productInfo = String.format("%s : %.2f %.2f ",
                    choice.getProductType(),
                    choice.getCostPerSquareFoot(),
                    choice.getLaborCostPerSquareFoot());
            io.print(i + " " + productInfo + "\n");
            i++;
        }
        String productName = io.readString("Please reenter the Product!");
        return productName;
    }

    public String newState(List<TaxRate> allRates) {
        io.print("Please choose from the states below and only type in the Abbreviation!");
        for (TaxRate rate : allRates) {
            String myState = String.format("%s: %s",
                    rate.getStateName(),
                    rate.getStateAbbreviation());
            io.print(myState);
        }
        String stateAbbreviation = io.readString("Please enter the abbrevation for the state you are ordering from!");
        return stateAbbreviation;
    }

    public Order findOrderToEdit() {
        LocalDate orderDate = io.readDate("Please enter the orderDate in the following format: YYYY-MM-DD!");
        int orderNumber = io.readInt("Please enter the orderNumber you are searching for");

        return new Order(orderNumber, orderDate);
    }

    public Order orderToEdit(Order orderToEdit) {
        String newName = io.readString("Enter customer name (" + orderToEdit.getCustomerName() + "):");
        String newState = io.readString("Please enter the abbreviation for the state (" + orderToEdit.getState() + "):");
        String newProductType = io.readString("Please enter the ProductType (" + orderToEdit.getProductType() + "):");
        BigDecimal newArea = io.readBigDecimal("Please enter the new area for the product (" + orderToEdit.getArea() + "):", "100", "100000");
        io.print("Here are the changes that you want to make, if the field is empty or shows 0.00, you choose not to make any changes:"
                + "\nCustomerName: "+newName+"\nState: "+ newState+"\nProductType: "+newProductType+ "\nArea: "+newArea);
        
        String continueOperation=io.readString("Do you want to continue? Type Yes to save the changes or any other key to return to the Main Menu!");
        if(continueOperation.equalsIgnoreCase("Yes")){
            return new Order(newName, newState, newProductType, newArea);
        }
        return null;
    }

    public Order removeOrder() {
        LocalDate orderDate = io.readDate("Please enter the date of the order that you want to remove!");
        int orderNumber = io.readInt("Please enter the orderNumber!");
        return new Order(orderNumber, orderDate);
    }

    public boolean removeOneOrder(Order o) {
        String oneOrder = o.getOderNumber() + " " + o.getCustomerName() + " "
                + o.getState() + " " + o.getTaxRate() + " " + o.getProductType() + " "
                + o.getProductType() + " " + o.getArea() + " " + o.getCostPerSquareFoot()
                + o.getLaborCostPerSquareFoot() + " " + o.getMaterialCost() + " "
                + o.getLaborCost() + " " + o.getTotal();
        io.print(oneOrder);
        String trueOrFalse = io.readString("Are you sure you want to remove this order?\n Type Yes to remove or any other key to abort");
        if (trueOrFalse.equalsIgnoreCase("yes")) {
            return true;
        }
        return false;
    }

    public boolean exportAllData() {
        return true;
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("===There is an issue!===");
        io.print(errorMsg);
    }

    public void displayExitBanner() {
        io.print("Thank you for your order!\nGood Bye!!!");
    }

}
