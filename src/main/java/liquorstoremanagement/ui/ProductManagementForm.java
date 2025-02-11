/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package liquorstoremanagement.ui;

/**
 *
 * @author MATIAN PC
 */

import liquorstoremanagement.dao.ProductDAO;
import liquorstoremanagement.model.Product;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ProductManagementForm extends JPanel {
    private ProductDAO productDAO;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JButton addButton, updateButton, deleteButton, refreshButton;

    public ProductManagementForm() {
        productDAO = new ProductDAO();
        initComponents();
        loadProductData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[]{"No.", "ID", "Name", "Category", "Brand", "Quantity", "Price", "Alcohol %"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Product");
        updateButton = new JButton("Update Product");
        deleteButton = new JButton("Delete Product");
        refreshButton = new JButton("Refresh");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> openAddProductDialog());
        updateButton.addActionListener(e -> openUpdateProductDialog());
        deleteButton.addActionListener(e -> deleteSelectedProduct());
        refreshButton.addActionListener(e -> loadProductData());
    }

    private void loadProductData() {
        List<Product> products = productDAO.getAllProducts();
        tableModel.setRowCount(0);
        int seq = 1;
        for (Product p : products) {
            tableModel.addRow(new Object[]{
                seq++,        // Sequential number
                p.getId(),    
                p.getName(),
                p.getCategory(),
                p.getBrand(),
                p.getQuantity(),
                p.getPrice(),
                p.getAlcoholPercentage()
            });
        }
    }

    private void openAddProductDialog() {
        ProductDialog dialog = new ProductDialog(null, "Add Product", null);
        dialog.setVisible(true);
        if (dialog.isSucceeded()) {
            productDAO.addProduct(dialog.getProduct());
            loadProductData();
        }
    }

    private void openUpdateProductDialog() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            int productId = (int) tableModel.getValueAt(selectedRow, 1);
            String name = (String) tableModel.getValueAt(selectedRow, 2);
            String category = (String) tableModel.getValueAt(selectedRow, 3);
            String brand = (String) tableModel.getValueAt(selectedRow, 4);
            int quantity = (int) tableModel.getValueAt(selectedRow, 5);
            double price = (double) tableModel.getValueAt(selectedRow, 6);
            double alcoholPercentage = (double) tableModel.getValueAt(selectedRow, 7);
            Product product = new Product(productId, name, category, brand, quantity, price, alcoholPercentage);
            ProductDialog dialog = new ProductDialog(null, "Update Product", product);
            dialog.setVisible(true);
            if (dialog.isSucceeded()) {
                productDAO.updateProduct(dialog.getProduct());
                loadProductData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to update.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteSelectedProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            int productId = (int) tableModel.getValueAt(selectedRow, 1);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the selected product?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                productDAO.deleteProduct(productId);
                loadProductData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }
}