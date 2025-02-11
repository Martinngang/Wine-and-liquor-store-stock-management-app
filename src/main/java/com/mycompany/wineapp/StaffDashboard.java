/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.wineapp;

/**
 *
 * @author MATIAN PC
 */

import javax.swing.*;
import liquorstoremanagement.ui.ProductManagementForm;
import liquorstoremanagement.ui.SupplierManagementForm;
import liquorstoremanagement.ui.SalesManagementForm;

public class StaffDashboard extends JFrame {
    public StaffDashboard() {
        setTitle("Staff Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Products", new ProductManagementForm());
        tabbedPane.addTab("Suppliers", new SupplierManagementForm());
        tabbedPane.addTab("Sales", new SalesManagementForm());
        
        add(tabbedPane);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StaffDashboard().setVisible(true));
    }
}