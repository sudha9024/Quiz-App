package quiz.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton nextButton, backButton;
    String firstName,lastName;

    public Login() {
        this.firstName=firstName;
        this.lastName=lastName;
        setTitle("Quiz App");
        setSize(800, 500);
        setLocation(250, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.white);

        // Title Icon
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/ticon.png"));
        Image image = icon.getImage();
        setIconImage(image);

        // Title Label
        JLabel titleLabel = new JLabel("Quiz Test");
        titleLabel.setForeground(new Color(135, 206, 235));
        titleLabel.setBounds(150, 125, 100, 50);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel);

        // Username Label and Field
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(100, 170, 200, 50);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(100, 205, 200, 30);
        usernameField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(usernameField);

        // Password Label and Field
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(100, 220, 200, 50);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 260, 200, 30);
        passwordField.setCursor(new Cursor(Cursor.HAND_CURSOR));
        passwordField.setEchoChar('*');
        add(passwordField);

        // Next Button
        ImageIcon nextIcon = new ImageIcon(ClassLoader.getSystemResource("icons/next.png"));
        Image nextImage = nextIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon nextIconResized = new ImageIcon(nextImage);
        nextButton = new JButton(nextIconResized);
        nextButton.setBounds(250, 310, nextIconResized.getIconWidth(), nextIconResized.getIconHeight());
        nextButton.setBorderPainted(false);
        nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nextButton.addActionListener(this);
        add(nextButton);

        // Back Button
        ImageIcon backIcon = new ImageIcon(ClassLoader.getSystemResource("icons/back.png"));
        Image backImage = backIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon backIconResized = new ImageIcon(backImage);
        backButton = new JButton(backIconResized);
        backButton.setBounds(90, 310, backIconResized.getIconWidth(), backIconResized.getIconHeight());
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(this);
        add(backButton);

        // Side Image
        ImageIcon sideImageIcon = new ImageIcon(ClassLoader.getSystemResource("icons/login.jpg"));
        Image sideImage = sideImageIcon.getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT);
        ImageIcon sideImageResized = new ImageIcon(sideImage);
        JLabel sideImageLabel = new JLabel(sideImageResized);
        sideImageLabel.setBounds(300, 0, 500, 500);
        add(sideImageLabel);

        // Logo Image
        ImageIcon logoIcon = new ImageIcon(ClassLoader.getSystemResource("icons/licon.jpg"));
        Image logoImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon logoIconResized = new ImageIcon(logoImage);
        JLabel logoLabel = new JLabel(logoIconResized);
        logoLabel.setBounds(150, 30, logoIconResized.getIconWidth(), logoIconResized.getIconHeight());
        add(logoLabel);

        setVisible(true);
    }

    public static void main(String[] args, String firstName,String lastName) {
        SwingUtilities.invokeLater(() -> new Login());
    }

    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (e.getSource() == backButton) {
            navigateToRegister();
        } else if (e.getSource() == nextButton) {
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and Password cannot be empty.");
                return;
            }

            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizApp", "root", "");
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM registeration WHERE email = ? AND password = ?")) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    if (username.equals("admin") && password.equals("admin#123")) {
                            setVisible(false);
                            new QuizAdmin();

                    } else {
                        SwingUtilities.invokeLater(() -> {
                            setVisible(false);
                            new Rules(username);
                        });
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid username or password.");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error during login.");
            }
        }
    }

    private void navigateToRegister() {
        System.out.println("Navigating to Register");
        setVisible(false);
        new Register();
    }
}
