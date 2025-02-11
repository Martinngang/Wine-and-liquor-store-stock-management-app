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
import liquorstoremanagement.ui.UserManagementForm;

public class AdminDashboard extends JFrame {
    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Products", new ProductManagementForm());
        tabbedPane.addTab("Suppliers", new SupplierManagementForm());
        tabbedPane.addTab("Sales", new SalesManagementForm());
        tabbedPane.addTab("Users", new UserManagementForm());
        
        add(tabbedPane);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard().setVisible(true));
    }
}