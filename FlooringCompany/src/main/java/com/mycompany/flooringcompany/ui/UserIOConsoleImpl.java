/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringcompany.ui;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 *
 * @author alinc
 */
public class UserIOConsoleImpl implements UserIO {

    Scanner sc = new Scanner(System.in);

    public void print(String message) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println(message);
    }

    public String readString(String prompt) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        print(prompt);
        return sc.nextLine();
    }

    public BigDecimal readBigDecimal(String prompt) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        print(prompt);
        return new BigDecimal(sc.nextLine());
    }

    public BigDecimal readBigDecimal(String prompt, String min, String max) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        BigDecimal value = new BigDecimal("-1");

        BigDecimal minD = new BigDecimal(min);
        BigDecimal maxD = new BigDecimal(max);

        do {
            try {
                print(prompt);
                String input = sc.nextLine();

                if (input.isEmpty()) {
                    return new BigDecimal("0.00");
                }
                value = new BigDecimal(input);

                if (minD.compareTo(value) > 0 || maxD.compareTo(value) < 0) {
                    print("Error value outside of range");
                }

            } catch (NumberFormatException ex) {
                print("Not a valid number!");
            }
        } while (minD.compareTo(value) > 0 || maxD.compareTo(value) < 0);

        return value;
    }

    public int readInt(String prompt) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        while(true) {
            try {
                print(prompt);
                return Integer.parseInt(sc.nextLine());
            } catch(NumberFormatException e) {
                print("Not a valid number. Try again.");
            }
        }
    }

    public int readInt(String prompt, int min, int max) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        int value = -1;
        do {
            print(prompt);
            value = Integer.parseInt(sc.nextLine());

            if (min > value || max < value) {
                print("Error value outside of range");
            }

        } while (min > value || max < value);
        return value;
    }

    @Override
    public LocalDate readDate(String prompt) {
        boolean format = false;
        LocalDate orderDate = null;
        do {
            try {
                print(prompt);
                orderDate = LocalDate.parse(sc.nextLine());
                format = true;
            } catch (DateTimeParseException ex) {
                System.out.println("Not a valid date");
            }

        } while (!format);
        return orderDate;
    }

    public double readDouble(String prompt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public double readDouble(String prompt, double min, double max) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public float readFloat(String prompt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public float readFloat(String prompt, float min, float max) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public long readLong(String prompt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public long readLong(String prompt, long min, long max) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
