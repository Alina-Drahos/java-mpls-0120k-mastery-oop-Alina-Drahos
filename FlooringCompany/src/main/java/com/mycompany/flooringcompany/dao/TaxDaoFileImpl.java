/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringcompany.dao;

import com.mycompany.flooringcompany.dto.TaxRate;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author alinc
 */
public class TaxDaoFileImpl implements TaxDao {

    private final String TAX_FILE;

    public static final String DELIMITER = ",";

    public TaxDaoFileImpl(String taxFile) {
        this.TAX_FILE = taxFile;
    }

    List<TaxRate> myTaxes = new ArrayList<>();

    @Override
    public BigDecimal getTaxRate(String stateAbbreviation) throws FlooringCompanyPersistenceException {
        loadTaxFile();
        for (TaxRate tax : myTaxes) {
            if (tax.getStateAbbreviation().equals(stateAbbreviation)) {
                return tax.getTaxRate();
            }
        }

        return null;
    }

    @Override
    public List<TaxRate> getAllTaxRates() throws FlooringCompanyPersistenceException {
        loadTaxFile();
        return myTaxes;
    }

    private void loadTaxFile() throws FlooringCompanyPersistenceException {
        myTaxes.clear();
        Scanner sc = null;
        try {
            sc = new Scanner(new FileReader(TAX_FILE));
        } catch (FileNotFoundException ex) {
            throw new FlooringCompanyPersistenceException("File not found");
        }
        sc.nextLine();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(DELIMITER);

            String stateAbbreviation = parts[0];
            String state = parts[1];
            BigDecimal taxRate = new BigDecimal(parts[2]);

            TaxRate taxes = new TaxRate(stateAbbreviation, state, taxRate);
            myTaxes.add(taxes);

        }
        sc.close();
    }

}
