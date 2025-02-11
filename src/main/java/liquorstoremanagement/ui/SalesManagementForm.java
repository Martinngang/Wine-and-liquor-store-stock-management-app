/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package liquorstoremanagement.ui;

/**
 *
 * @author MATIAN PC
 */

import liquorstoremanagement.dao.SaleDAO;
import liquorstoremanagement.model.Sale;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SalesManagementForm extends JPanel {
    private JTable salesTable;
    private DefaultTableModel tableModel;
    private JButton addButton, updateButton, deleteButton, refreshButton;
    private SaleDAO saleDAO;

    public SalesManagementForm() {
        saleDAO = new SaleDAO();
        initComponents();
        loadSalesData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"ID", "Product ID", "Quantity Sold", "Total Price", "Sale Date"}, 0);
        salesTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(salesTable);

        addButton = new JButton("Add Sale");
        updateButton = new JButton("Update Sale");
        deleteButton = new JButton("Delete Sale");
        refreshButton = new JButton("Refresh");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> openAddSaleDialog());
        updateButton.addActionListener(e -> openUpdateSaleDialog());
        deleteButton.addActionListener(e -> deleteSelectedSale());
        refreshButton.addActionListener(e -> loadSalesData());
    }

    private void loadSalesData() {
        List<Sale> sales = saleDAO.getAllSales();
        tableModel.setRowCount(0); // Clear existing data
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Sale sale : sales) {
            tableModel.addRow(new Object[]{
                sale.getId(),
                sale.getProductId(),
                sale.getQuantitySold(),
                sale.getTotalPrice(),
                sdf.format(sale.getSaleDate())
            });
        }
    }

    private void openAddSaleDialog() {
        try {
            String productIdStr = JOptionPane.showInputDialog(this, "Enter Product ID:");
            if (productIdStr == null) return;
            int productId = Integer.parseInt(productIdStr);

            String quantityStr = JOptionPane.showInputDialog(this, "Enter Quantity Sold:");
            int quantitySold = Integer.parseInt(quantityStr);

            String totalPriceStr = JOptionPane.showInputDialog(this, "Enter Total Price:");
            double totalPrice = Double.parseDouble(totalPriceStr);

            String saleDateStr = JOptionPane.showInputDialog(this, "Enter Sale Date (yyyy-MM-dd):");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date saleDate = sdf.parse(saleDateStr);

            Sale sale = new Sale(productId, quantitySold, totalPrice, saleDate);
            saleDAO.addSale(sale);
            loadSalesData();
        } catch (ParseException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage());
        }
    }

    private void openUpdateSaleDialog() {
        int selectedRow = salesTable.getSelectedRow();
        if (selectedRow >= 0) {
            try {
                int id = (int) tableModel.getValueAt(selectedRow, 0);
                String productIdStr = JOptionPane.showInputDialog(this, "Update Product ID:", tableModel.getValueAt(selectedRow, 1));
                int productId = Integer.parseInt(productIdStr);

                String quantityStr = JOptionPane.showInputDialog(this, "Update Quantity Sold:", tableModel.getValueAt(selectedRow, 2));
                int quantitySold = Integer.parseInt(quantityStr);

                String totalPriceStr = JOptionPane.showInputDialog(this, "Update Total Price:", tableModel.getValueAt(selectedRow, 3));
                double totalPrice = Double.parseDouble(totalPriceStr);

                String saleDateStr = JOptionPane.showInputDialog(this, "Update Sale Date (yyyy-MM-dd):", tableModel.getValueAt(selectedRow, 4));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date saleDate = sdf.parse(saleDateStr);

                Sale sale = new Sale(id, productId, quantitySold, totalPrice, saleDate);
                saleDAO.updateSale(sale);
                loadSalesData();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error updating sale: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a sale to update.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteSelectedSale() {
        int selectedRow = salesTable.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the selected sale?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                saleDAO.deleteSale(id);
                loadSalesData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a sale to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}