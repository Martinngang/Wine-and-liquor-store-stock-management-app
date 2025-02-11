/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package liquorstoremanagement.ui;

/**
 *
 * @author MATIAN PC
 */

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import liquorstoremanagement.dao.ProductDAO;
import liquorstoremanagement.dao.OrderDAO;
import liquorstoremanagement.model.Product;
import liquorstoremanagement.model.Order;

public class ClientProductViewPanel extends JPanel {
    private String clientUsername;
    private ProductDAO productDAO;
    private OrderDAO orderDAO;
    private JPanel productsPanel;
    
    public ClientProductViewPanel(String clientUsername) {
        this.clientUsername = clientUsername;
        productDAO = new ProductDAO();
        orderDAO = new OrderDAO();
        initComponents();
        loadProducts();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        productsPanel = new JPanel(new WrapLayout(FlowLayout.LEFT, 20, 20));
        JScrollPane scrollPane = new JScrollPane(productsPanel);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private void loadProducts() {
        productsPanel.removeAll();
        List<Product> products = productDAO.getAllProducts();
        for (Product p : products) {
            JPanel productCard = createProductCard(p);
            productsPanel.add(productCard);
        }
        productsPanel.revalidate();
        productsPanel.repaint();
    }
    
    private JPanel createProductCard(Product p) {
        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(200, 300));
        card.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        
        // Load product image using each product's image path
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        ImageIcon icon;
        try {
            String imagePath = p.getImagePath(); // Use product's image path
            if (imagePath != null && new File(imagePath).exists()) {
                Image img = ImageIO.read(new File(imagePath));
                Image scaledImg = img.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                icon = new ImageIcon(scaledImg);
            } else {
                // If product image not found, use a default image
                String defaultImagePath = "src/main/java/logo.png";
                if(new File(defaultImagePath).exists()){
                    Image img = ImageIO.read(new File(defaultImagePath));
                    Image scaledImg = img.getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(scaledImg);
                } else {
                    // Create a placeholder if even default image is missing
                    BufferedImage placeholder = new BufferedImage(200, 150, BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2d = placeholder.createGraphics();
                    g2d.setPaint(Color.LIGHT_GRAY);
                    g2d.fillRect(0, 0, 200, 150);
                    g2d.setPaint(Color.DARK_GRAY);
                    g2d.setFont(new Font("SansSerif", Font.BOLD, 16));
                    g2d.drawString("No Image", 50, 75);
                    g2d.dispose();
                    icon = new ImageIcon(placeholder);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            icon = new ImageIcon();
        }
        imageLabel.setIcon(icon);
        card.add(imageLabel, BorderLayout.NORTH);
        
        // Details panel: Name, Price, Stock
        JPanel detailsPanel = new JPanel(new GridLayout(3, 1));
        detailsPanel.setBackground(Color.WHITE);
        JLabel nameLabel = new JLabel(p.getName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        JLabel priceLabel = new JLabel("FCFA " + p.getPrice(), SwingConstants.CENTER);
        JLabel stockLabel = new JLabel("Stock: " + p.getQuantity(), SwingConstants.CENTER);
        detailsPanel.add(nameLabel);
        detailsPanel.add(priceLabel);
        detailsPanel.add(stockLabel);
        card.add(detailsPanel, BorderLayout.CENTER);
        
        // Order button at the bottom
        JButton orderButton = new JButton("Order");
        orderButton.setBackground(new Color(60, 179, 113));
        orderButton.setForeground(Color.WHITE);
        orderButton.setFocusPainted(false);
        orderButton.addActionListener(e -> placeOrder(p));
        card.add(orderButton, BorderLayout.SOUTH);
        
        return card;
    }
    
    private void placeOrder(Product p) {
        String qtyStr = JOptionPane.showInputDialog(this, "Enter quantity for " + p.getName() + ":");
        int quantity;
        try {
            quantity = Integer.parseInt(qtyStr);
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Order order = new Order(clientUsername, p.getId(), quantity, p.getPrice() * quantity);
        boolean success = orderDAO.saveOrder(order);
        if (success) {
            JOptionPane.showMessageDialog(this, "Order placed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to place order.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}