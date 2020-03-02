/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringcompany.dao;

import static com.mycompany.flooringcompany.dao.OrderDaoFileImpl.DELIMITER;
import com.mycompany.flooringcompany.dto.Order;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alinc
 */
public class ExportDaoFileImpl implements ExportDao {

    private final String ORDER_FILE_PATH;
    private final String EXPORT_FILE_PATH;

    public static final String DELIMITER = ",";

    public ExportDaoFileImpl(String filePath, String destinationPath) {
        this.ORDER_FILE_PATH = filePath;
        this.EXPORT_FILE_PATH = destinationPath;
    }

    @Override
    public void exportAllData() throws FlooringCompanyPersistenceException {
        try {
            writeToExportFile();
        } catch (FlooringCompanyPersistenceException ex) {
            throw new FlooringCompanyPersistenceException("File not found");
        }
    }

    private void writeToExportFile() throws FlooringCompanyPersistenceException {
        try {
            PrintWriter pw = new PrintWriter(EXPORT_FILE_PATH);
            Scanner sc = null;
            File path = new File(ORDER_FILE_PATH);
            File[] allFiles = path.listFiles();
            pw.write("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,"
                    + "LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total,OrderDate\n");
            for (int i = 0; i < allFiles.length; i++) {
                sc = new Scanner(new FileReader(allFiles[i]));
                String fileName=allFiles[i].getName();
                String date=fileName.substring(7);
                String dateFormatted=date.substring(0,2)+"-"+date.substring(2,4)+"-"+date.substring(4,8);
                sc.nextLine();
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String newLine=line+DELIMITER+dateFormatted;
                    pw.println(newLine);                   
                }
                sc.close();
            }
            pw.close();                      
        }        
        catch (FileNotFoundException ex) {
            throw new FlooringCompanyPersistenceException("File not found");
        }

    }

}
