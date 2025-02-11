/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package liquorstoremanagement.ui;

/**
 *
 * @author MATIAN PC
 */
import liquorstoremanagement.model.Product;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ProductDialog extends JDialog {
    private JTextField nameField, categoryField, brandField, quantityField, priceField, alcoholField;
    private JButton okButton, cancelButton;
    private boolean succeeded;
    private Product product;
    
    public ProductDialog(Frame parent, String title, Product product) {
        super(parent, title, true);
        this.product = product;
        initComponents();
        if (product != null) {
            populateFields(product);
        }
    }
    
    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);
        
        panel.add(new JLabel("Category:"));
        categoryField = new JTextField();
        panel.add(categoryField);
        
        panel.add(new JLabel("Brand:"));
        brandField = new JTextField();
        panel.add(brandField);
        
        panel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        panel.add(quantityField);
        
        panel.add(new JLabel("Price:"));
        priceField = new JTextField();
        panel.add(priceField);
        
        panel.add(new JLabel("Alcohol %:"));
        alcoholField = new JTextField();
        panel.add(alcoholField);
        
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.PAGE_END);
        pack();
        setLocationRelativeTo(getParent());
        
        // Action for OK button
        okButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (validateInput()) {
                    succeeded = true;
                    ProductDialog.this.product = createProductFromFields();
                    dispose();
                }
            }
        });
        
        // Action for Cancel button
        cancelButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                succeeded = false;
                dispose();
            }
        });
    }
    
    // Populate fields if updating an existing product
    private void populateFields(Product product) {
        nameField.setText(product.getName());
        categoryField.setText(product.getCategory());
        brandField.setText(product.getBrand());
        quantityField.setText(String.valueOf(product.getQuantity()));
        priceField.setText(String.valueOf(product.getPrice()));
        alcoholField.setText(String.valueOf(product.getAlcoholPercentage()));
    }
    
    // Validate input fields are not empty (additional validation can be added)
    private boolean validateInput() {
        if(nameField.getText().trim().isEmpty() || categoryField.getText().trim().isEmpty() ||
           brandField.getText().trim().isEmpty() || quantityField.getText().trim().isEmpty() ||
           priceField.getText().trim().isEmpty() || alcoholField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    // Create or update a Product object based on the form input
    private Product createProductFromFields() {
        String name = nameField.getText().trim();
        String category = categoryField.getText().trim();
        String brand = brandField.getText().trim();
        int quantity = Integer.parseInt(quantityField.getText().trim());
        double price = Double.parseDouble(priceField.getText().trim());
        double alcohol = Double.parseDouble(alcoholField.getText().trim());
        
        if (product != null) {
            // Update the existing product
            product.setName(name);
            product.setCategory(category);
            product.setBrand(brand);
            product.setQuantity(quantity);
            product.setPrice(price);
            product.setAlcoholPercentage(alcohol);
            return product;
        } else {
            // Create a new product (ID will be auto-generated in the database)
            return new Product(name, category, brand, quantity, price, alcohol);
        }
    }
    
    public boolean isSucceeded() {
        return succeeded;
    }
    
    public Product getProduct() {
        return product;
    }
}