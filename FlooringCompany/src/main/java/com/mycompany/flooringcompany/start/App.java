/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.flooringcompany.start;

import com.mycompany.flooringcompany.controller.FlooringCompanyController;
import com.mycompany.flooringcompany.dao.ExportDao;
import com.mycompany.flooringcompany.dao.ExportDaoFileImpl;
import com.mycompany.flooringcompany.dao.OrderDao;
import com.mycompany.flooringcompany.dao.OrderDaoFileImpl;
import com.mycompany.flooringcompany.dao.ProductDao;
import com.mycompany.flooringcompany.dao.ProductDaoFileImpl;
import com.mycompany.flooringcompany.dao.TaxDao;
import com.mycompany.flooringcompany.dao.TaxDaoFileImpl;
import com.mycompany.flooringcompany.service.FlooringCompanyService;
import com.mycompany.flooringcompany.ui.FlooringCompanyView;
import com.mycompany.flooringcompany.ui.UserIO;
import com.mycompany.flooringcompany.ui.UserIOConsoleImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author alinc
 */
public class App {

    public static void main(String[] args) {
//    UserIO io = new UserIOConsoleImpl();
//    FlooringCompanyView view = new FlooringCompanyView(io);
//
//    TaxDao dao1 = new TaxDaoFileImpl("Data\\Taxes.txt");
//    ProductDao dao2 = new ProductDaoFileImpl("Data\\Products.txt");
//    OrderDao dao3 = new OrderDaoFileImpl("Orders");
//    ExportDao dao4 = new ExportDaoFileImpl("Orders", "ExportOrders\\ExportOrdes.txt");
//
//    FlooringCompanyService service = new FlooringCompanyService(dao1, dao2, dao3, dao4);
//
//    FlooringCompanyController controller = new FlooringCompanyController(service, view);
//
//    controller.run ();

        ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

        FlooringCompanyController controller = appContext.getBean("controller", FlooringCompanyController.class);
        controller.run();

    }

}
