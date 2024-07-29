package quiz.app;

import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class Register extends JFrame implements ActionListener {
    JButton registerButton;
    JPanel personalPanel;
    int qid;
    JTextField firstNameField = new JTextField();
    JTextField middleNameField = new JTextField();
    JTextField lastNameField = new JTextField();
    JTextField mobileTextField = new JTextField();
    JTextField emailTextField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JPasswordField confirmPasswordField = new JPasswordField();
    JDatePickerImpl datePicker;
    JComboBox<String> salutationComboBox;
    JComboBox<String> genderComboBox;

    public Register() {
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/ticon.png"));
        Image imaget = icon.getImage();
        setIconImage(imaget);
        setSize(800, 600);
        setLayout(new FlowLayout());
        add(new JButton("Sample Button"));

        setTitle("Registration");
        getContentPane().setBackground(Color.white);
        setLayout(null);
        setSize(800, 500);
        setLocation(250, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Title Label
        JLabel titleLabel = new JLabel("Quiz Registration");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(300, 10, 300, 30);
        add(titleLabel);

        // Scroll Pane for Personal Details Panel
        personalPanel = new JPanel();
        personalPanel.setLayout(new GridLayout(10, 2, 5, 5));
        personalPanel.setForeground(Color.BLACK);

        createDropdown("Salutation", personalPanel, new String[]{"Select an Option","Mr.", "Mrs."}, true);
        createLabelAndTextField("First Name", personalPanel, firstNameField);
        createLabelAndTextField("Middle Name", personalPanel, middleNameField);
        createLabelAndTextField("Last Name", personalPanel, lastNameField);
        createDropdown("Gender", personalPanel, new String[]{"Select an Option","Male", "Female"}, false);
        createDOB("Date of Birth", personalPanel);
        createEmail("Email", personalPanel);
        createMobile("Mobile No", personalPanel);
        createPassword("Password", personalPanel, passwordField);
        createPassword("Confirm Password", personalPanel, confirmPasswordField);

        JScrollPane personalScrollPane = new JScrollPane(personalPanel);
        personalScrollPane.setBounds(40, 50, 680, 330);
        add(personalScrollPane);

        // Register Button
        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.BOLD, 18));
        registerButton.setBounds(350, 400, 100, 35);
        registerButton.setBackground(new Color(0, 122, 204));
        registerButton.addActionListener(this);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder());
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(registerButton);

        setVisible(true);
    }

    private void createEmail(String labelName, JPanel panel) {
        JLabel label = new JLabel(labelName + ":");
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(Color.BLACK);
        panel.add(label);

        emailTextField.setFont(new Font("Arial", Font.PLAIN, 15));
        addPlaceholder(emailTextField, "Enter " + labelName + " here...");
        panel.add(emailTextField);
    }

    private void createMobile(String labelName, JPanel panel) {
        JLabel label = new JLabel(labelName + ":");
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(Color.BLACK);
        panel.add(label);

        mobileTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();
                }
                if (mobileTextField.getText().length() >= 10) {
                    e.consume();
                }
            }
        });
        addPlaceholder(mobileTextField, "Enter " + labelName + " here...");
        panel.add(mobileTextField);
    }

    private void createDropdown(String labelName, JPanel panel, String[] options, boolean isSalutation) {
        JLabel label = new JLabel(labelName + ":");
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(Color.BLACK);
        panel.add(label);

        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        comboBox.setName(labelName);
        comboBox.setBackground(Color.WHITE);
        panel.add(comboBox);

        if (isSalutation) {
            salutationComboBox = comboBox;
        } else {
            genderComboBox = comboBox;
        }
    }

    private void createDOB(String labelName, JPanel panel) {
        JLabel label = new JLabel(labelName + ":");
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(Color.BLACK);
        panel.add(label);

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setFont(new Font("Arial", Font.PLAIN, 15));
        datePicker.setBackground(Color.WHITE);

        panel.add(datePicker);
    }

    private void createPassword(String labelName, JPanel panel, JPasswordField passwordField) {
        JLabel label = new JLabel(labelName + ":");
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(Color.BLACK);
        panel.add(label);

        passwordField.setEchoChar('*');
        passwordField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JPasswordField pf = (JPasswordField) input;
                String password = new String(pf.getPassword());

                boolean hasUppercase = !password.equals(password.toLowerCase());
                boolean hasLowercase = !password.equals(password.toUpperCase());
                boolean hasDigit = password.matches(".*\\d.*");
                boolean hasSpecialChar = password.matches(".*[!@#$%^&*()].*");
                boolean isAtLeast8 = password.length() >= 8;

                if (hasUppercase && hasLowercase && hasDigit && hasSpecialChar && isAtLeast8) {
                    return true;
                } else {
                    String message = "<html>Password must contain:<br>" +
                            "- At least 8 characters<br>" +
                            "- An uppercase letter<br>" +
                            "- A lowercase letter<br>" +
                            "- A digit<br>" +
                            "- A special character (!@#$%^&*())</html>";
                    JOptionPane.showMessageDialog(null, message);
                    return false;
                }
            }
        });
        passwordField.setFont(new Font("Arial", Font.PLAIN, 15));
        panel.add(passwordField);
    }

    private void createLabelAndTextField(String labelName, JPanel panel, JTextField textField) {
        JLabel label = new JLabel(labelName + ":");
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(Color.BLACK);
        panel.add(label);

        addPlaceholder(textField, "Enter " + labelName + " here...");
        textField.setFont(new Font("Arial", Font.PLAIN, 15));
        textField.setName(labelName);
        panel.setBackground(Color.WHITE);
        panel.add(textField);
    }

    private void addPlaceholder(JTextField txtField, String placeholder) {
        txtField.setForeground(Color.GRAY);
        txtField.setText(placeholder);

        txtField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtField.getText().equals(placeholder)) {
                    txtField.setText("");
                    txtField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtField.getText().isEmpty()) {
                    txtField.setForeground(Color.GRAY);
                    txtField.setText(placeholder);
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

            if (e.getSource() == registerButton) {

                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String gender = (String) genderComboBox.getSelectedItem();
                Date dob = (Date) datePicker.getModel().getValue();
                String email = emailTextField.getText();
                String mobile = mobileTextField.getText();
                String password = new String(passwordField.getPassword());
                String confirmPassword = new String(confirmPasswordField.getPassword());

                // Basic validation
                String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
                Pattern pattern = Pattern.compile(emailPattern);
                Matcher matcher = pattern.matcher(email);

                if (!matcher.matches()) {
                    JOptionPane.showMessageDialog(this, "Invalid Email Address.");
                    return;
                }

                if (mobile.length() != 10) {
                    JOptionPane.showMessageDialog(this, "Invalid Mobile Number.");
                    return;
                }

                if (dob == null) {
                    JOptionPane.showMessageDialog(this, "Please select a valid Date of Birth.");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(this, "Passwords do not match.");
                    return;
                }

                // Hash password (e.g., using BCrypt)
                // String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizApp", "root", "");
                     PreparedStatement stmt = conn.prepareStatement("INSERT INTO registeration (First_name, Last_name, Gender, DOB, Email, Mobile, Password) VALUES (?, ?, ?, ?, ?, ?, ?)",Statement.RETURN_GENERATED_KEYS)) {

                    stmt.setString(1, firstName);
                    stmt.setString(2, lastName);
                    stmt.setString(3, gender);
                    stmt.setDate(4, new java.sql.Date(dob.getTime()));
                    stmt.setString(5, email);
                    stmt.setString(6, mobile);
                    stmt.setString(7, password); // Use hashedPassword instead

                    int rowsInserted = stmt.executeUpdate();
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rowsInserted > 0&&rs.next()) {
                        int userId = rs.getInt(1);
                        JOptionPane.showMessageDialog(this, "User registered successfully");
                        dispose();
                        new Login(); // Open login form after successful registration
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error registering user");
                }
            }




    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Register::new);
    }
}

class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    private final String datePattern = "MM/dd/yyyy";
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);


    @Override
    public Object stringToValue(String text) throws java.text.ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws java.text.ParseException {
        if (value != null) {
            if (value instanceof java.util.Calendar) {
                java.util.Calendar cal = (java.util.Calendar) value;
                return dateFormatter.format(cal.getTime());
            } else if (value instanceof java.util.Date) {
                java.util.Date date = (java.util.Date) value;
                return dateFormatter.format(date);
            }
        }
        return "";
    }
}

