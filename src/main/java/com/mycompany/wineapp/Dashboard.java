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
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import liquorstoremanagement.ui.ProductManagementForm;
import liquorstoremanagement.ui.SupplierManagementForm;
import liquorstoremanagement.ui.SalesManagementForm;
import liquorstoremanagement.ui.UserManagementForm;
import liquorstoremanagement.ui.LoginForm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Dashboard extends JFrame {
    private String userRole;

    public Dashboard(String userRole) {
        this.userRole = userRole;
        initComponents();
    }

    private void initComponents() {
        setTitle("Dashboard - " + userRole);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));

        JLabel welcomeLabel = new JLabel("Welcome, " + userRole, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(welcomeLabel, BorderLayout.NORTH);

        JButton productsButton = new JButton("View Products");
        JButton ordersButton = new JButton("Manage Orders");
        JButton salesButton = new JButton("View Sales");
        JButton usersButton = new JButton("Manage Users");
        JButton logoutButton = new JButton("Logout");

        panel.add(productsButton);
        
        if (!userRole.equals("Client")) {
            panel.add(salesButton);
        }
        
        if (userRole.equals("Admin")) {
            panel.add(usersButton);
        }

        add(panel, BorderLayout.CENTER);
        add(logoutButton, BorderLayout.SOUTH);

        // Event Listeners
        productsButton.addActionListener(e -> viewProducts());
        salesButton.addActionListener(e -> viewSales());
        usersButton.addActionListener(e -> manageUsers());
        logoutButton.addActionListener(e -> logout());
    }

    private void viewProducts() {
        JOptionPane.showMessageDialog(this, "Showing Products...");
    }

    private void viewSales() {
        JOptionPane.showMessageDialog(this, "Showing Sales...");
    }

    private void manageUsers() {
        JOptionPane.showMessageDialog(this, "Managing Users...");
    }

    private void logout() {
        new LoginForm().setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        new Dashboard("Admin").setVisible(true);
    }
}