/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringcompany.dao;

import com.mycompany.flooringcompany.dto.TaxRate;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author alinc
 */
public interface TaxDao {
    
    BigDecimal getTaxRate(String stateAbbreviation) throws FlooringCompanyPersistenceException;
    
    List<TaxRate> getAllTaxRates()throws FlooringCompanyPersistenceException;
}
