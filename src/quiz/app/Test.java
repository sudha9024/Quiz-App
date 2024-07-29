package quiz.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Test extends JFrame implements ActionListener {

    private JLabel name, qno, qtotal, n, timerLabel;
    private JButton next, submit;
    private int currentQuestionIndex = 1,t=7,result=0; // Variable to keep track of the current question
    private int timeRemaining = 30; // Total time in seconds (e.g., 10 minutes)
    private Timer timer;

    // Declare radio buttons as instance variables to manage them easily
    private JRadioButton A,B,C,D;
    private ButtonGroup optionsGroup;
    JLabel quest,atxt,btxt,ctxt,dtxt;
     String username;

    public Test(String username) {
        this.username=username;
        setTitle("Quiz App");
        setSize(800, 500);
        setLocation(250, 150);
        getContentPane().setBackground(Color.white);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set Icon
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/ticon.png"));
        setIconImage(icon.getImage());

        // Logo
        ImageIcon logi1 = new ImageIcon(ClassLoader.getSystemResource("icons/licon.jpg"));
        Image logi = logi1.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
        JLabel images = new JLabel(new ImageIcon(logi));
        images.setBounds(25, 10, 60, 60);
        add(images);

        // User Information
        name = new JLabel();
        name.setBounds(10, 55, 200, 50);
        name.setFont(new Font("Arial", Font.BOLD, 15));
        add(name);

        JLabel t = new JLabel("Total Question:");
        t.setBounds(10, 75, 200, 50);
        t.setFont(new Font("Arial", Font.PLAIN, 15));
        add(t);

        qtotal = new JLabel("10");
        qtotal.setBounds(110, 75, 200, 50);
        qtotal.setFont(new Font("Arial", Font.PLAIN, 15));
        add(qtotal);

        JLabel q = new JLabel("Question No:");
        q.setBounds(10, 95, 200, 50);
        q.setFont(new Font("Arial", Font.PLAIN, 15));
        add(q);

        qno = new JLabel(String.valueOf(currentQuestionIndex));
        qno.setBounds(110, 95, 200, 50);
        qno.setFont(new Font("Arial", Font.PLAIN, 15));
        add(qno);

        // Date
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);
        JLabel dateLabel = new JLabel("Date: " + formattedDate);
        dateLabel.setBounds(400, 10, 200, 30);
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        add(dateLabel);

        // Timer
        timerLabel = new JLabel("Time remaining: " + formatTime(timeRemaining));
        timerLabel.setBounds(550, 10, 200, 30);
        timerLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        add(timerLabel);

        // Question
        n = new JLabel("1.");
        n.setBounds(160, 60, 20, 50);
        n.setFont(new Font("Arial", Font.PLAIN, 15));
        add(n);

        quest = new JLabel();
        quest.setBounds(184, 60, 600, 50);
        quest.setFont(new Font("Arial", Font.PLAIN, 15));
        add(quest);

        // Options
        A = new JRadioButton();
        A.setBounds(170, 140, 20, 20);
        A.setActionCommand("A");
        add(A);

        atxt = new JLabel("Option A");
        atxt.setBounds(200, 125, 600, 50);
        atxt.setFont(new Font("Arial", Font.PLAIN, 15));
        add(atxt);

        B = new JRadioButton();
        B.setBounds(170, 180, 20, 20);
        B.setActionCommand("B");
        add( B);

        btxt = new JLabel("Option B");
        btxt.setBounds(200, 165, 600, 50);
        btxt.setFont(new Font("Arial", Font.PLAIN, 15));
        add(btxt);

        C = new JRadioButton();
        C.setBounds(170, 220, 20, 20);
        C.setActionCommand("C");
        add(C);

        ctxt = new JLabel("Option C");
        ctxt.setBounds(200, 205, 600, 50);
        ctxt.setFont(new Font("Arial", Font.PLAIN, 15));
        add(ctxt);

        D = new JRadioButton();
        D.setBounds(170, 260, 20, 20);
        D.setBorderPainted(false);
        D.setActionCommand("D");
        add(D);

        dtxt = new JLabel("Option D");
        dtxt.setBounds(200, 245, 600, 50);
        dtxt.setFont(new Font("Arial", Font.PLAIN, 15));
        add(dtxt);

        // Button Group for Options
        optionsGroup = new ButtonGroup();
        optionsGroup.add(A);
        optionsGroup.add(B);
        optionsGroup.add(C);
        optionsGroup.add(D);

        // Buttons
        next = new JButton("Next");
        next.setBounds(200, 350, 100, 30);
        next.setBackground(new Color(135, 206, 235));
        next.setBorderPainted(false);
        next.addActionListener(this);
        add(next);

        submit = new JButton("Submit");
        submit.setBounds(600, 350, 100, 30);
        submit.setBackground(new Color(135, 206, 235));
        submit.setBorderPainted(false);
        submit.addActionListener(this);
        add(submit);

        // Timer setup
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                timerLabel.setText("Time remaining: " + formatTime(timeRemaining));
                if (timeRemaining <= 0) {
                    timer.stop();
                    // Handle time up event
                    JOptionPane.showMessageDialog(null, "Time is up! Submitting the quiz.");
                    updateScore();
                    submitQuiz();
                }
            }
        });
        timer.start();

        updateQuestion();
        fetch();
        setVisible(true);
    }

    public static void main(String[] args,String username) {
        SwingUtilities.invokeLater(() -> new Test(username));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit || currentQuestionIndex >= 10) {
            updateScore();
            submitQuiz();
        } else if (e.getSource() == next) {
            if (optionsGroup.getSelection() == null) {
                JOptionPane.showMessageDialog(this, "Please select an option.");
            } else {
                // Check if the selected option matches the answer for the current question
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizApp", "root", "");
                     PreparedStatement stmt = conn.prepareStatement("SELECT * FROM question WHERE qid = ?")) {
                    stmt.setInt(1, t);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        String ans = rs.getString("Ans");
                        if (ans.equals(optionsGroup.getSelection().getActionCommand())) {
                            result++;
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error checking answer.");
                }

                currentQuestionIndex++;
                t++;
                updateQuestion();
            }
        }
    }

    private void updateScore() {
        String url = "jdbc:mysql://localhost:3306/quizApp";
        String user = "root";
        String password = ""; // Update with your database password
        System.out.println(username);
        String query = "UPDATE registeration SET Score = ? WHERE email = ?";
        int t=currentQuestionIndex-result-1;
        int ans=result*10-t*5;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the parameters
            stmt.setInt(1, ans); // Set the score
            stmt.setString(2, username); // Set the email
            System.out.println(username);

            // Execute the update
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {

                JOptionPane.showMessageDialog(this, "Score updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "No user found with the given email.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating score.");
        }

    }

    private void fetch(){
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizApp", "root", "");
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM registeration WHERE email = ?")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            System.out.println(username);
            if (rs.next()) {
                // Update the form with the new question data
                String firstName = rs.getString("First_Name");
                String lastName = rs.getString("Last_Name");
                name.setText(firstName + " " + lastName);

                quest.setText(rs.getString("Ques"));
                atxt.setText(rs.getString("Opta"));
                btxt.setText(rs.getString("Optb"));
                ctxt.setText(rs.getString("Optc"));
                dtxt.setText(rs.getString("Optd"));

                // Clear the selection for the next question
                optionsGroup.clearSelection();

                qno.setText(String.valueOf(currentQuestionIndex));
                n.setText(currentQuestionIndex + ".");


            } else {
                // Increment the question ID to check the next question
                t++;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error retrieving question data.");
           // Exit the loop in case of an error to prevent infinite looping
        }
    }



    private void updateQuestion() {
        boolean questionFound = false;
        while (!questionFound) {
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizApp", "root", "");
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM question WHERE qid = ?")) {
                stmt.setInt(1, t);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    // Update the form with the new question data
                    quest.setText(rs.getString("Ques"));
                    atxt.setText(rs.getString("Opta"));
                    btxt.setText(rs.getString("Optb"));
                    ctxt.setText(rs.getString("Optc"));
                    dtxt.setText(rs.getString("Optd"));

                    // Clear the selection for the next question
                    optionsGroup.clearSelection();

                    qno.setText(String.valueOf(currentQuestionIndex));
                    n.setText(currentQuestionIndex + ".");

                    questionFound = true;
                } else {
                    // Increment the question ID to check the next question
                    t++;
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error retrieving question data.");
                break;  // Exit the loop in case of an error to prevent infinite looping
            }
        }
    }





    private void submitQuiz() {
        timer.stop();
        System.out.println(result);
        setVisible(false);
        new FinalResult(result,currentQuestionIndex,username);
    }

    private String formatTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, remainingSeconds);
    }
}



