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
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import liquorstoremanagement.dao.UserDAO;
import liquorstoremanagement.model.User;

public class RegistrationForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton registerButton, cancelButton;
    private BufferedImage backgroundImage, logoImage;
    private UserDAO userDAO;

    public RegistrationForm() {
        userDAO = new UserDAO(); // Initialize UserDAO
        // Load images from resources (update the paths if needed)
        try {
            backgroundImage = ImageIO.read(new File("src/main/java/background.jpg"));
            logoImage = ImageIO.read(new File("src/main/java/logo.png"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        initComponents();
    }

    private void initComponents() {
        setTitle("User Registration");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Create a layered pane to hold background and form
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(400, 600));
        
        // Background label (scaled to frame size)
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, 400, 600);
        if (backgroundImage != null) {
            Image scaledBg = backgroundImage.getScaledInstance(400, 600, Image.SCALE_SMOOTH);
            backgroundLabel.setIcon(new ImageIcon(scaledBg));
        }
        layeredPane.add(backgroundLabel, new Integer(0));
        
        // Create a semi-transparent form panel for registration
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(true);
        formPanel.setBackground(new Color(255, 255, 255, 220)); // White with transparency
        // Set the form panel's bounds (adjust as desired)
        formPanel.setBounds(50, 150, 300, 350);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Add a logo at the top of the form panel (optional)
        if (logoImage != null) {
            JLabel logoLabel = new JLabel(new ImageIcon(logoImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            formPanel.add(logoLabel, gbc);
        }
        
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Username label and field
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(usernameLabel, gbc);
        
        usernameField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);
        
        // Password label and field
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(passwordLabel, gbc);
        
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);
        
        // Confirm Password label and field
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(confirmPasswordLabel, gbc);
        
        confirmPasswordField = new JPasswordField(15);
        gbc.gridx = 1;
        formPanel.add(confirmPasswordField, gbc);
        
        // Buttons: Register and Cancel
        registerButton = new JButton("Register");
        cancelButton = new JButton("Cancel");
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(registerButton, gbc);
        gbc.gridx = 1;
        formPanel.add(cancelButton, gbc);
        
        // Add the form panel to the layered pane
        layeredPane.add(formPanel, new Integer(1));
        
        // Add the layered pane to the frame
        add(layeredPane);
        
        // Button action listeners
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the registration form
            }
        });
    }
    
    private void registerUser() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        // Automatically set role to "client"
        String role = "client";

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (userDAO.userExists(username)) {
            JOptionPane.showMessageDialog(this, "Username already taken!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User newUser = new User(0, username, password, role);
        boolean success = userDAO.addUser(newUser);

        if (success) {
            JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration failed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrationForm().setVisible(true));
    }
}