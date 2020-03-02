/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringcompany.dao;

import com.mycompany.flooringcompany.dto.Order;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alinc
 */
public class OrderDaoFileImpl implements OrderDao {

    private final String ORDER_FILE_PATH;

    public static final String DELIMITER = ",";

    List<Order> allOrders = new ArrayList<>();

    public OrderDaoFileImpl(String orderFile) {
        this.ORDER_FILE_PATH = orderFile;
    }

    @Override
    public void createAnOrder(Order currentOrder) throws FlooringCompanyPersistenceException {
        writeToOrderFile(currentOrder.getOrderDate(), currentOrder);
        allOrders.add(currentOrder);
        loadOrderFile(currentOrder.getOrderDate());

    }

    @Override
    public Order getOrder(LocalDate date, int orderNumber) throws FlooringCompanyPersistenceException {
        loadOrderFile(date);
        for (Order o : allOrders) {
            if (o.getOderNumber() == orderNumber) {
                return o;
            }

        }
        throw new FlooringCompanyPersistenceException("Order not found!");
    }

    @Override
    public List<Order> displayOrders(LocalDate orderDate) throws FlooringCompanyPersistenceException {
        loadOrderFile(orderDate);
        return allOrders;
    }

    @Override
    public void editAnOrder(Order updatedOrder) throws FlooringCompanyPersistenceException {
        loadOrderFile(updatedOrder.getOrderDate());
        Order orderToRemove = null;
        for (Order o : allOrders) {
            if (o.getOderNumber() == updatedOrder.getOderNumber()) {
                orderToRemove = o;
            }
        }
        allOrders.remove(orderToRemove);
        allOrders.add(updatedOrder);
        writeOrdersToFile(allOrders);
    }

    @Override
    public void removeOrder(Order removedOrder) throws FlooringCompanyPersistenceException {
        loadOrderFile(removedOrder.getOrderDate());
        allOrders.remove(removedOrder);

        writeOrdersToFile(allOrders);

    }

    private void writeOrdersToFile(List<Order> orders) throws FlooringCompanyPersistenceException {
        PrintWriter pw;
        LocalDate currentDate = orders.get(0).getOrderDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String fileName = ORDER_FILE_PATH + "\\Orders_" + currentDate.format(formatter) + ".txt";

        try {
            pw = new PrintWriter(fileName);
             pw.write("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,"
                    + "LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total\n");
            for (Order eo : allOrders) {
                pw.write(eo.getOderNumber() + DELIMITER
                        + "\"" + eo.getCustomerName() + "\"" + DELIMITER
                        + eo.getState() + DELIMITER
                        + eo.getTaxRate() + DELIMITER
                        + eo.getProductType() + DELIMITER
                        + eo.getArea() + DELIMITER
                        + eo.getCostPerSquareFoot() + DELIMITER
                        + eo.getLaborCostPerSquareFoot() + DELIMITER
                        + eo.getMaterialCost() + DELIMITER
                        + eo.getLaborCost() + DELIMITER
                        + eo.getTax() + DELIMITER
                        + eo.getTotal());
                pw.write("\r\n");
                pw.flush();
            }
            pw.close();
        } catch (IOException ex) {
            throw new FlooringCompanyPersistenceException("Error writing to file");
        }
    }

    @Override
    public int getLatestOrderNumber() throws FlooringCompanyPersistenceException {
        int mostRecentOrderNumber = 0;

        File path = new File(ORDER_FILE_PATH);
        File[] allFiles = path.listFiles();
        for (int i = 0; i < allFiles.length; i++) {
            try {
                int orderNumber = findFileWithLatestOrderNumber(allFiles[i]);
                if (mostRecentOrderNumber < orderNumber) {
                    mostRecentOrderNumber = orderNumber;
                }

            } catch (FileNotFoundException ex) {
                throw new FlooringCompanyPersistenceException("File not found");
            }
        }

        return mostRecentOrderNumber;
    }

    private int findFileWithLatestOrderNumber(File oneFile) throws FileNotFoundException {
        int mostRecentNumber = 0;
        Scanner sc = new Scanner(new FileReader(oneFile));
        sc.nextLine();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] details = line.split(DELIMITER);
            if (mostRecentNumber < Integer.parseInt(details[0])) {
                mostRecentNumber = Integer.parseInt(details[0]);
            }
        }
        return mostRecentNumber;
    }

    private void loadOrderFile(LocalDate fileDate) throws FlooringCompanyPersistenceException {
        allOrders.clear();
        Scanner sc = null;

        LocalDate currentDate = fileDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String fileName = ORDER_FILE_PATH + "\\Orders_" + currentDate.format(formatter) + ".txt";

        try {
            sc = new Scanner(new FileReader(fileName));
        } catch (FileNotFoundException ex) {
            throw new FlooringCompanyPersistenceException("File not found");
        }
        //Skipping the Header Line
        sc.nextLine();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(DELIMITER);

            int orderNumber = Integer.parseInt(parts[0]);
            if (parts[2].contains("\"")) {
                String customerName = parts[1].substring(1) + "," + parts[2].substring(0, parts[2].length() - 1);
                String state = parts[3];
                BigDecimal taxRate = new BigDecimal(parts[4]);
                String productType = parts[5];
                BigDecimal area = new BigDecimal(parts[6]);
                BigDecimal costPerSquareFoot = new BigDecimal(parts[7]);
                BigDecimal laborCostPerSquareFoot = new BigDecimal(parts[8]);
                BigDecimal materialCost = new BigDecimal(parts[9]);
                BigDecimal laborCost = new BigDecimal(parts[10]);
                BigDecimal tax = new BigDecimal(parts[11]);
                BigDecimal total = new BigDecimal(parts[12]);
                allOrders.add(new Order(orderNumber, customerName, state, taxRate, productType,
                        area, costPerSquareFoot, laborCostPerSquareFoot, materialCost,
                        laborCost, tax, total, fileDate));
            } else {
                String customerName = parts[1].substring(1, parts[1].length() - 1);
                String state = parts[2];
                BigDecimal taxRate = new BigDecimal(parts[3]);
                String productType = parts[4];
                BigDecimal area = new BigDecimal(parts[5]);
                BigDecimal costPerSquareFoot = new BigDecimal(parts[6]);
                BigDecimal laborCostPerSquareFoot = new BigDecimal(parts[7]);
                BigDecimal materialCost = new BigDecimal(parts[8]);
                BigDecimal laborCost = new BigDecimal(parts[9]);
                BigDecimal tax = new BigDecimal(parts[10]);
                BigDecimal total = new BigDecimal(parts[11]);
                allOrders.add(new Order(orderNumber, customerName, state, taxRate, productType,
                        area, costPerSquareFoot, laborCostPerSquareFoot, materialCost,
                        laborCost, tax, total, fileDate));
            }
        }
        sc.close();

    }

    private void writeToOrderFile(LocalDate fileDate, Order o) throws FlooringCompanyPersistenceException {
        if (doesFileExist(fileDate)) {
            loadOrderFile(fileDate);
        }
        PrintWriter pw;
        LocalDate currentDate = fileDate;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String fileName = ORDER_FILE_PATH + "\\Orders_" + currentDate.format(formatter) + ".txt";

        try {
            pw = new PrintWriter(fileName);
            pw.write("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,"
                    + "LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total\n");
            for (Order eo : allOrders) {
                pw.write(eo.getOderNumber() + DELIMITER
                        + "\"" + eo.getCustomerName() + "\"" + DELIMITER
                        + eo.getState() + DELIMITER
                        + eo.getTaxRate() + DELIMITER
                        + eo.getProductType() + DELIMITER
                        + eo.getArea() + DELIMITER
                        + eo.getCostPerSquareFoot() + DELIMITER
                        + eo.getLaborCostPerSquareFoot() + DELIMITER
                        + eo.getMaterialCost() + DELIMITER
                        + eo.getLaborCost() + DELIMITER
                        + eo.getTax() + DELIMITER
                        + eo.getTotal());
                pw.write("\r\n");
            }
            pw.write(o.getOderNumber() + DELIMITER
                    + "\"" + o.getCustomerName() + "\"" + DELIMITER
                    + o.getState() + DELIMITER
                    + o.getTaxRate() + DELIMITER
                    + o.getProductType() + DELIMITER
                    + o.getArea() + DELIMITER
                    + o.getCostPerSquareFoot() + DELIMITER
                    + o.getLaborCostPerSquareFoot() + DELIMITER
                    + o.getMaterialCost() + DELIMITER
                    + o.getLaborCost() + DELIMITER
                    + o.getTax() + DELIMITER
                    + o.getTotal());
            pw.write("\r\n");
            pw.flush();
            pw.close();
        } catch (IOException ex) {
            throw new FlooringCompanyPersistenceException("Error writing to file");
        }

    }

    public boolean doesFileExist(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMddyyyy");
        String fileName = ORDER_FILE_PATH + "\\Orders_" + date.format(formatter) + ".txt";
        File f = new File(fileName);
        if (f.exists() && !f.isDirectory()) {
            return true;
        }
        return false;
    }
}
