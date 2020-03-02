/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringcompany.service;

/**
 *
 * @author alinc
 */
public class NameInvalidException extends Exception {
    public NameInvalidException(String message) {
        super(message);
    }

    public NameInvalidException(String message,
            Throwable cause) {
        super(message, cause);
    }
}
