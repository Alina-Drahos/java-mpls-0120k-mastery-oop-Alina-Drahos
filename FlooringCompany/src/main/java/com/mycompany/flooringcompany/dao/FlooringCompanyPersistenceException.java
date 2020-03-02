/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringcompany.dao;

/**
 *
 * @author alinc
 */
public class FlooringCompanyPersistenceException extends Exception {
    
    public FlooringCompanyPersistenceException(String message) {
        super(message);
    }

    public FlooringCompanyPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
