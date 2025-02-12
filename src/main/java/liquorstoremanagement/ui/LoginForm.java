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
import javax.swing.border.MatteBorder;
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
    private GradientButton loginButton;
    private JLabel forgotPasswordLabel, orSignUpLabel;
    private BufferedImage backgroundImage, logoImage;
    private UserDAO userDAO;

    public LoginForm() {
        userDAO = new UserDAO();
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
        setSize(500, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(500, 700));

        // Background image
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setBounds(0, 0, 500, 700);
        if (backgroundImage != null) {
            Image scaledBg = backgroundImage.getScaledInstance(500, 700, Image.SCALE_SMOOTH);
            backgroundLabel.setIcon(new ImageIcon(scaledBg));
        }
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        RoundedPanel formPanel = new RoundedPanel(30);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(255, 255, 255, 230));
        formPanel.setBounds(50, 150, 400, 400);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 5, 7, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: Logo
        if (logoImage != null) {
            JLabel logoLabel = new JLabel(new ImageIcon(logoImage.getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            logoLabel.setHorizontalAlignment(JLabel.CENTER);
            formPanel.add(logoLabel, gbc);
        }
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        // Row 1: Username label
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel usernameLabel = new JLabel("Username:");
        formPanel.add(usernameLabel, gbc);

        // Row 2: Username field
        gbc.gridy = 2;
        usernameField = new JTextField();
        usernameField.setBorder(new MatteBorder(10, 10, 10, 10, Color.WHITE));
        addPlaceholder(usernameField, "Type your username");
        formPanel.add(usernameField, gbc);

        // Row 3: Password label
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel passwordLabel = new JLabel("Password:");
        formPanel.add(passwordLabel, gbc);

        // Row 4: Password field
        gbc.gridy = 4;
        passwordField = new JPasswordField();
        passwordField.setBorder(new MatteBorder(10, 10, 10, 10, Color.WHITE));
        addPlaceholder(passwordField, "Type your password");
        formPanel.add(passwordField, gbc);

        // Row 5: Forgot Password link (centered right)
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        forgotPasswordLabel = new JLabel("Forgot Password?");
        forgotPasswordLabel.setForeground(Color.RED);
        forgotPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotPasswordLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                forgotPasswordAction();
            }
        });
        formPanel.add(forgotPasswordLabel, gbc);

        // Row 6: Login button (centered)
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        loginButton = new GradientButton("Login");
        loginButton.setPreferredSize(new Dimension(200, 40));
        loginButton.setForeground(Color.WHITE);
        formPanel.add(loginButton, gbc);

        // Row 7: OR Sign Up link (hyperlink style)
        gbc.gridy = 7;
        orSignUpLabel = new JLabel("OR Sign Up");
        orSignUpLabel.setForeground(Color.BLUE);
        orSignUpLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        orSignUpLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                new RegistrationForm().setVisible(true);
                dispose();
            }
        });
        formPanel.add(orSignUpLabel, gbc);

        layeredPane.add(formPanel, Integer.valueOf(1));
        add(layeredPane);

        loginButton.addActionListener(e -> loginAction());
    }

    private void loginAction() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.equals("Type your username") || password.equals("Type your password")) {
            JOptionPane.showMessageDialog(this, "Please enter username and password!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = new UserDAO().authenticateUser(username, password);
        if (user != null) {
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
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void forgotPasswordAction() {
        String userForReset = JOptionPane.showInputDialog(this, "Enter your username to reset your password:");
        if (userForReset != null && !userForReset.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "A password reset link has been sent to your registered email.", "Reset Password", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void addPlaceholder(JTextField field, String placeholder) {
        field.setForeground(Color.GRAY);
        field.setText(placeholder);
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });
    }

    private void addPlaceholder(JPasswordField field, String placeholder) {
        field.setForeground(Color.GRAY);
        field.setEchoChar((char)0);
        field.setText(placeholder);
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                String text = new String(field.getPassword());
                if (text.equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                    field.setEchoChar('*');
                }
            }
            public void focusLost(FocusEvent e) {
                if (field.getPassword().length == 0) {
                    field.setForeground(Color.GRAY);
                    field.setEchoChar((char)0);
                    field.setText(placeholder);
                }
            }
        });
    }

    class GradientButton extends JButton {
        public GradientButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int width = getWidth();
            int height = getHeight();
            GradientPaint gp = new GradientPaint(0, 0, new Color(173, 216, 230), width, height, new Color(135, 206, 250));
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, width, height, 20, 20);
            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        public void paintBorder(Graphics g) {
        }
    }

    class RoundedPanel extends JPanel {
        private int arc;
        
        public RoundedPanel(int arc) {
            this.arc = arc;
            setOpaque(false);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}