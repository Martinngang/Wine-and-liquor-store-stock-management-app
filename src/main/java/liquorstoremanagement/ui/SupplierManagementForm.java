/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package liquorstoremanagement.ui;

/**
 *
 * @author MATIAN PC
 */

import liquorstoremanagement.dao.SupplierDAO;
import liquorstoremanagement.model.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class SupplierManagementForm extends JPanel {
    private JTable supplierTable;
    private DefaultTableModel tableModel;
    private JButton addButton, updateButton, deleteButton, refreshButton;
    private SupplierDAO supplierDAO;

    public SupplierManagementForm() {
        supplierDAO = new SupplierDAO();
        initComponents();
        loadSupplierData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Contact", "Email"}, 0);
        supplierTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(supplierTable);

        addButton = new JButton("Add Supplier");
        updateButton = new JButton("Update Supplier");
        deleteButton = new JButton("Delete Supplier");
        refreshButton = new JButton("Refresh");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add Button - Open Dialog
        addButton.addActionListener(e -> openAddSupplierDialog());

        // Update Button - Open Dialog with Selected Data
        updateButton.addActionListener(e -> openUpdateSupplierDialog());

        // Delete Button - Remove Selected Supplier
        deleteButton.addActionListener(e -> deleteSelectedSupplier());

        // Refresh Button - Reload Data
        refreshButton.addActionListener(e -> loadSupplierData());
    }

    private void loadSupplierData() {
        List<Supplier> suppliers = supplierDAO.getAllSuppliers();
        tableModel.setRowCount(0);
        for (Supplier supplier : suppliers) {
            tableModel.addRow(new Object[]{
                    supplier.getId(),
                    supplier.getName(),
                    supplier.getContact(),
                    supplier.getEmail()
            });
        }
    }

    private void openAddSupplierDialog() {
        String name = JOptionPane.showInputDialog(this, "Enter Supplier Name:");
        if (name != null && !name.trim().isEmpty()) {
            String contact = JOptionPane.showInputDialog(this, "Enter Contact:");
            String email = JOptionPane.showInputDialog(this, "Enter Email:");

            Supplier newSupplier = new Supplier(0, name, contact, email);
            supplierDAO.addSupplier(newSupplier);
            loadSupplierData();
        }
    }

    private void openUpdateSupplierDialog() {
        int selectedRow = supplierTable.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String name = (String) tableModel.getValueAt(selectedRow, 1);
            String contact = (String) tableModel.getValueAt(selectedRow, 2);
            String email = (String) tableModel.getValueAt(selectedRow, 3);

            String newName = JOptionPane.showInputDialog(this, "Update Name:", name);
            String newContact = JOptionPane.showInputDialog(this, "Update Contact:", contact);
            String newEmail = JOptionPane.showInputDialog(this, "Update Email:", email);

            if (newName != null && newContact != null && newEmail != null) {
                Supplier updatedSupplier = new Supplier(id, newName, newContact, newEmail);
                supplierDAO.updateSupplier(updatedSupplier);
                loadSupplierData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a supplier to update.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteSelectedSupplier() {
        int selectedRow = supplierTable.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                supplierDAO.deleteSupplier(id);
                loadSupplierData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a supplier to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}