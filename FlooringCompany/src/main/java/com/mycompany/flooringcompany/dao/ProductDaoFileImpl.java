/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringcompany.dao;

import com.mycompany.flooringcompany.dto.Product;
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
public class ProductDaoFileImpl implements ProductDao {

    private final String PRODUCT_FILE;

    public static final String DELIMITER = ",";

    List<Product> allProducts = new ArrayList<>();

    public ProductDaoFileImpl(String productFile) {
        this.PRODUCT_FILE = productFile;
    }

    @Override
    public Product getProduct(String productType) throws FlooringCompanyPersistenceException {
        for (Product product : allProducts) {
            if (product.getProductType().equals(productType)) {
                return product;
            }
        }
        return null;
    }

    @Override
    public List<Product> getProducts() throws FlooringCompanyPersistenceException {
        loadProductFile();
        return allProducts;
    }

    private void loadProductFile() throws FlooringCompanyPersistenceException {
        allProducts.clear();
        Scanner sc = null;
        try {
            sc = new Scanner(new FileReader(PRODUCT_FILE));
        } catch (FileNotFoundException ex) {
            throw new FlooringCompanyPersistenceException("File not found");
        }
        sc.nextLine();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split(DELIMITER);

            String productType = parts[0];
            BigDecimal costPerSquareFoot = new BigDecimal(parts[1]);
            BigDecimal laborCostPerSquareFoot = new BigDecimal(parts[2]);

            Product thisProduct = new Product(productType, costPerSquareFoot, laborCostPerSquareFoot);
            allProducts.add(thisProduct);
        }

        sc.close();
    }

}
