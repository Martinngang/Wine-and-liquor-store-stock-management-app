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
import java.awt.*;
import liquorstoremanagement.ui.ClientProductViewPanel;
public class ClientDashboard extends JFrame {
    private String clientUsername;
public ClientDashboard(String clientUsername) {
        this.clientUsername = clientUsername;
        initComponents();
    }
private void initComponents() {
        setTitle("Client Dashboard - " + clientUsername);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(900, 600));
JLabel backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, 900, 600);
        try {
            ImageIcon bgIcon = new ImageIcon("src/main/java/bg.jpg");
            Image scaledImage = bgIcon.getImage().getScaledInstance(900, 600, Image.SCALE_SMOOTH);
            backgroundLabel.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            e.printStackTrace();
        }
        layeredPane.add(backgroundLabel, new Integer(0));
JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Products", new ClientProductViewPanel(clientUsername));
        tabbedPane.setBounds(50, 50, 800, 500);
        layeredPane.add(tabbedPane, new Integer(1));
add(layeredPane);
    }
public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientDashboard("TestClient").setVisible(true));
    }
}