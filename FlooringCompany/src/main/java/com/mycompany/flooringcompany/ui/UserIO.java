/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringcompany.ui;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author alinc
 */
public interface UserIO {
    void print(String message);

    String readString(String prompt);
    
    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    BigDecimal readBigDecimal(String prompt);

    BigDecimal readBigDecimal(String prompt, String min, String max);

    double readDouble(String prompt);

    double readDouble(String prompt, double min, double max);

    float readFloat(String prompt);

    float readFloat(String prompt, float min, float max);

    long readLong(String prompt);

    long readLong(String prompt, long min, long max);
    
    LocalDate readDate(String prompt);
}
