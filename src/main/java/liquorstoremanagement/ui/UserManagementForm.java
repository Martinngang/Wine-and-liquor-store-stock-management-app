/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package liquorstoremanagement.ui;

/**
 *
 * @author MATIAN PC
 */



import liquorstoremanagement.dao.UserDAO;
import liquorstoremanagement.model.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class UserManagementForm extends JPanel {
    private JTable userTable;
    private DefaultTableModel tableModel;
    private JButton addButton, updateButton, deleteButton, refreshButton;
    private UserDAO userDAO;

    public UserManagementForm() {
        userDAO = new UserDAO();
        initComponents();
        loadUserData();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"ID", "Username", "Password", "Role"}, 0);
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);

        addButton = new JButton("Add User");
        updateButton = new JButton("Update User");
        deleteButton = new JButton("Delete User");
        refreshButton = new JButton("Refresh");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> openAddUserDialog());
        updateButton.addActionListener(e -> openUpdateUserDialog());
        deleteButton.addActionListener(e -> deleteSelectedUser());
        refreshButton.addActionListener(e -> loadUserData());
    }

    private void loadUserData() {
        List<User> users = userDAO.getAllUsers();
        tableModel.setRowCount(0); // Clear existing rows
        for (User user : users) {
            tableModel.addRow(new Object[]{
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getRole()
            });
        }
    }

    private void openAddUserDialog() {
        String username = JOptionPane.showInputDialog(this, "Enter Username:");
        if (username == null || username.trim().isEmpty()) return;
        String password = JOptionPane.showInputDialog(this, "Enter Password:");
        String role = JOptionPane.showInputDialog(this, "Enter Role (e.g., admin, staff, client):");

        User newUser = new User(0, username, password, role.toLowerCase());
        boolean success = userDAO.addUser(newUser);
        if (success) {
            JOptionPane.showMessageDialog(this, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadUserData();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add user.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openUpdateUserDialog() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String username = JOptionPane.showInputDialog(this, "Update Username:", tableModel.getValueAt(selectedRow, 1));
            String password = JOptionPane.showInputDialog(this, "Update Password:", tableModel.getValueAt(selectedRow, 2));
            String role = JOptionPane.showInputDialog(this, "Update Role:", tableModel.getValueAt(selectedRow, 3));

            if (username != null && password != null && role != null) {
                User updatedUser = new User(id, username, password, role.toLowerCase());
                userDAO.updateUser(updatedUser);
                loadUserData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a user to update.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteSelectedUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the selected user?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                userDAO.deleteUser(id);
                loadUserData();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a user to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}