/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringcompany.dao;

import com.mycompany.flooringcompany.dto.Order;
import java.util.List;

/**
 *
 * @author alinc
 */
public interface ExportDao {
    
    void exportAllData()throws FlooringCompanyPersistenceException;
}
