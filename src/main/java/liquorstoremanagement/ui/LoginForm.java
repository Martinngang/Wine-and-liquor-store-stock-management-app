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
import com.mycompany.wineapp.AdminDashboard;
import com.mycompany.wineapp.StaffDashboard;
import com.mycompany.wineapp.ClientDashboard;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private BufferedImage backgroundImage, logoImage;
    private UserDAO userDAO;

    public LoginForm() {
        userDAO = new UserDAO();
        // Load images from your resources directory.
        try {
            backgroundImage = ImageIO.read(new File("src/main/java/background.jpg"));
            logoImage = ImageIO.read(new File("src/main/java/logo.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
    }

    private void initComponents() {
        setTitle("Login");
        setSize(400, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a layered pane for a background image and overlay form.
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(400, 600));

        // Background label
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, 400, 600);
        if (backgroundImage != null) {
            Image scaledBg = backgroundImage.getScaledInstance(400, 600, Image.SCALE_SMOOTH);
            backgroundLabel.setIcon(new ImageIcon(scaledBg));
        }
        layeredPane.add(backgroundLabel, new Integer(0));

        // Form panel with a semi-transparent background.
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(true);
        formPanel.setBackground(new Color(255, 255, 255, 220)); // White with alpha
        formPanel.setBounds(50, 200, 300, 250);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        // Add logo at the top if available.
        if (logoImage != null) {
            JLabel logoLabel = new JLabel(new ImageIcon(logoImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
            formPanel.add(logoLabel, gbc);
        }

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        
        // Username Label and Field
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(usernameLabel, gbc);
        usernameField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);
        
        // Password Label and Field
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(passwordLabel, gbc);
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);
        
        // Buttons: Login and Register
        loginButton = new JButton("Login");
        registerButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(loginButton, gbc);
        gbc.gridx = 1;
        formPanel.add(registerButton, gbc);

        layeredPane.add(formPanel, new Integer(1));
        add(layeredPane);

        // Action listeners
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loginAction();
            }
        });
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RegistrationForm().setVisible(true);
                dispose();
            }
        });
    }

    private void loginAction() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        System.out.println("Attempting login for: " + username);

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = userDAO.authenticateUser(username, password);
        if (user != null) {
            System.out.println("User found: " + user.getUsername() + ", Role: " + user.getRole());
            JOptionPane.showMessageDialog(this, "Login successful! You are logged in as " + user.getRole(), "Success", JOptionPane.INFORMATION_MESSAGE);
            String role = user.getRole().toLowerCase();
            if (role.equals("client")) {
                new ClientDashboard(username).setVisible(true);
            } else if (role.equals("staff")) {
                new StaffDashboard().setVisible(true);
            } else if (role.equals("admin")) {
                new AdminDashboard().setVisible(true);
            }
            dispose();
        } else {
            System.out.println("Authentication failed for: " + username);
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}