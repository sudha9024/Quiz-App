package quiz.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class QuizAdmin extends JFrame {
    JButton add, update, delete, all, result, logout, exit, save;
    CardLayout cardLayout;
    JPanel mainPanel;

    JTextField ques, quesa, quesb, quesc, quesd, quesans, quesid;

    QuizAdmin() {
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/ticon.png"));
        Image imaget = icon.getImage();
        setIconImage(imaget);
        setTitle("Quiz App");
        setSize(780, 500);
        setLocation(250, 150);
        setLayout(new BorderLayout());

        // Create the navigation bar
        JPanel navBar = new JPanel();
        navBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        navBar.setBackground(Color.WHITE);

        // Create buttons for the navigation bar
        add = new JButton("Add Question");
        add.setBorderPainted(false);
        add.setBackground(new Color(135, 206, 235));


        update = new JButton("Update Question");
        update.setBorderPainted(false);
        update.setBackground(new Color(135, 206, 235));

        delete = new JButton("Delete Question");
        delete.setBorderPainted(false);
        delete.setBackground(new Color(135, 206, 235));

        all = new JButton("All Question");
        all.setBorderPainted(false);
        all.setBackground(new Color(135, 206, 235));

        result = new JButton("Students Result");
        result.setBorderPainted(false);
        result.setBackground(new Color(135, 206, 235));

        logout = new JButton("Logout");
        logout.setBorderPainted(false);
        logout.setBackground(new Color(135, 206, 235));

        exit = new JButton("Exit");
        exit.setBorderPainted(false);
        exit.setBackground(new Color(135, 206, 235));

        // Add action listeners to buttons
        add.addActionListener(e -> cardLayout.show(mainPanel, "Add"));
        update.addActionListener(e -> cardLayout.show(mainPanel, "Update"));
        delete.addActionListener(e -> cardLayout.show(mainPanel, "Delete"));
        all.addActionListener(e -> cardLayout.show(mainPanel, "AllQuestion"));
        result.addActionListener(e -> cardLayout.show(mainPanel, "Result"));
        logout.addActionListener(e -> {
            setVisible(false);
            new Login();
        });
        exit.addActionListener(e -> System.exit(0));

        // Add buttons to the navigation bar
        navBar.add(add);
        navBar.add(update);
        navBar.add(delete);
        navBar.add(all);
        navBar.add(result);
        navBar.add(logout);
        navBar.add(exit);

        // Create the main panel with CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create different views (panels)
        JPanel addPanel = new AddPanel();
        JPanel updatePanel = new UpdatePanel();
        JPanel deletePanel = new DeletePanel();
        JPanel allPanel = new AllPanel();
        JPanel resultPanel = new ResultPanel();

        // Add views to the main panel
        mainPanel.add(addPanel, "Add");
        mainPanel.add(updatePanel, "Update");
        mainPanel.add(deletePanel, "Delete");
        mainPanel.add(allPanel, "AllQuestion");
        mainPanel.add(resultPanel, "Result");

        // Add the navigation bar and main panel to the frame
        add(navBar, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);


        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(QuizAdmin::new);
    }





    class AddPanel extends JPanel implements ActionListener {
        public AddPanel() {
            setSize(600, 400);
            setBackground(Color.WHITE);
            setLayout(null);

            JLabel question = new JLabel("Add Question");
            question.setBounds(300, 0, 200, 80);
            question.setFont(new Font("Arial", Font.BOLD, 20));
            add(question);

            JLabel qid = new JLabel("QuestionID:");
            qid.setBounds(60, 60, 300, 50);
            qid.setFont(new Font("Arial", Font.PLAIN, 15));
            add(qid);

            quesid = new JTextField();
            quesid.setBounds(180, 70, 30, 25);
            quesid.setFont(new Font("Arial", Font.PLAIN, 15));
            quesid.setCursor(new Cursor(Cursor.HAND_CURSOR));
            add(quesid);

            JLabel q = new JLabel("Question:");
            q.setBounds(60, 65, 200, 100);
            q.setFont(new Font("Arial", Font.PLAIN, 15));
            add(q);

            ques = new JTextField();
            ques.setBounds(180, 100, 300, 25);
            ques.setFont(new Font("Arial", Font.PLAIN, 15));
            ques.setCursor(new Cursor(Cursor.HAND_CURSOR));
            add(ques);

            JLabel qa = new JLabel("Option A:");
            qa.setBounds(60, 95, 200, 100);
            qa.setFont(new Font("Arial", Font.PLAIN, 15));
            add(qa);

            quesa = new JTextField();
            quesa.setBounds(180, 130, 300, 25);
            quesa.setFont(new Font("Arial", Font.PLAIN, 15));
            quesa.setCursor(new Cursor(Cursor.HAND_CURSOR));
            add(quesa);

            JLabel qb = new JLabel("Option B:");
            qb.setBounds(60, 125, 200, 100);
            qb.setFont(new Font("Arial", Font.PLAIN, 15));
            add(qb);

            quesb = new JTextField();
            quesb.setBounds(180, 160, 300, 25);
            quesb.setFont(new Font("Arial", Font.PLAIN, 15));
            quesb.setCursor(new Cursor(Cursor.HAND_CURSOR));
            add(quesb);

            JLabel qc = new JLabel("Option C:");
            qc.setBounds(60, 150, 200, 100);
            qc.setFont(new Font("Arial", Font.PLAIN, 15));
            add(qc);

            quesc = new JTextField();
            quesc.setBounds(180, 190, 300, 25);
            quesc.setCursor(new Cursor(Cursor.HAND_CURSOR));
            quesc.setFont(new Font("Arial", Font.PLAIN, 15));
            add(quesc);

            JLabel qd = new JLabel("Option D:");
            qd.setBounds(60, 180, 200, 100);
            qd.setFont(new Font("Arial", Font.PLAIN, 15));
            add(qd);

            quesd = new JTextField();
            quesd.setBounds(180, 220, 300, 25);
            quesd.setFont(new Font("Arial", Font.PLAIN, 15));
            quesd.setCursor(new Cursor(Cursor.HAND_CURSOR));
            add(quesd);

            JLabel ans = new JLabel("Answer:");
            ans.setBounds(60, 210, 300, 100);
            ans.setFont(new Font("Arial", Font.PLAIN, 15));
            add(ans);

            quesans = new JTextField();
            quesans.setBounds(180, 250, 300, 25);
            quesans.setFont(new Font("Arial", Font.PLAIN, 15));
            quesans.setCursor(new Cursor(Cursor.HAND_CURSOR));
            add(quesans);

            save = new JButton("Save");
            save.setBackground(new Color(135, 206, 235));
            save.setBounds(300, 300, 80, 40);
            save.setBorderPainted(false);
            save.addActionListener( this);
            save.setCursor(new Cursor(Cursor.HAND_CURSOR));
            add(save);

            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

                if(e.getSource()==save){
                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizApp", "root", "");
                         PreparedStatement stmt = conn.prepareStatement("INSERT INTO Question (Ques,Opta,Optb,Optc,Optd,Ans) VALUES (?, ?, ?, ?, ?, ?)")) {

                        stmt.setString(1, ques.getText());
                        stmt.setString(2, quesa.getText());
                        stmt.setString(3, quesb.getText());
                        stmt.setString(4, quesc.getText());
                        stmt.setString(5, quesd.getText());
                        stmt.setString(6, quesans.getText());

                        stmt.executeUpdate();
                        JOptionPane.showMessageDialog(this, "Question is Saved");
                        quesid.setText("");
                        ques.setText("");
                        quesa.setText("");
                        quesb.setText("");
                        quesc.setText("");
                        quesd.setText("");
                        quesans.setText("");


                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error registering user");
                    }

                }

        }
    }

    class UpdatePanel extends JPanel implements ActionListener {
        JTextField ques, quesid, quesa, quesb, quesc, quesd, quesans;
        JButton save, search;

        public UpdatePanel() {
            setSize(600, 400);
            setBackground(Color.WHITE);
            setLayout(null);

            JLabel question = new JLabel("Update Question");
            question.setBounds(300, 0, 200, 80);
            question.setFont(new Font("Arial", Font.BOLD, 20));
            add(question);

            JLabel qid = new JLabel("QuestionID:");
            qid.setBounds(60, 60, 300, 50);
            qid.setFont(new Font("Arial", Font.PLAIN, 15));
            add(qid);

            quesid = new JTextField();
            quesid.setBounds(180, 70, 30, 25);
            quesid.setFont(new Font("Arial", Font.PLAIN, 15));
            quesid.setCursor(new Cursor(Cursor.HAND_CURSOR));
            add(quesid);

            search = new JButton("Search");
            search.setBounds(250, 70, 80, 25);
            search.setBackground(new Color(135, 206, 235));
            search.setBorderPainted(false);
            search.addActionListener(this);
            search.setCursor(new Cursor(Cursor.HAND_CURSOR));
            add(search);

            JLabel q = new JLabel("Question:");
            q.setBounds(60, 65, 200, 100);
            q.setFont(new Font("Arial", Font.PLAIN, 15));
            add(q);

            ques = new JTextField();
            ques.setBounds(180, 100, 300, 25);
            ques.setFont(new Font("Arial", Font.PLAIN, 15));
            ques.setCursor(new Cursor(Cursor.HAND_CURSOR));
            add(ques);

            JLabel qa = new JLabel("Option A:");
            qa.setBounds(60, 95, 200, 100);
            qa.setFont(new Font("Arial", Font.PLAIN, 15));
            add(qa);


            quesa = new JTextField();
            quesa.setBounds(180, 130, 300, 25);
            quesa.setFont(new Font("Arial", Font.PLAIN, 15));
            quesa.setCursor(new Cursor(Cursor.HAND_CURSOR));
            add(quesa);


            JLabel qb = new JLabel("Option B:");
            qb.setBounds(60, 125, 200, 100);
            qb.setFont(new Font("Arial", Font.PLAIN, 15));
            add(qb);

            quesb = new JTextField();
            quesb.setBounds(180, 160, 300, 25);
            quesb.setFont(new Font("Arial", Font.PLAIN, 15));
            quesb.setCursor(new Cursor(Cursor.HAND_CURSOR));
            add(quesb);

            JLabel qc = new JLabel("Option C:");
            qc.setBounds(60, 150, 200, 100);
            qc.setFont(new Font("Arial", Font.PLAIN, 15));
            add(qc);

            quesc = new JTextField();
            quesc.setBounds(180, 190, 300, 25);
            quesc.setFont(new Font("Arial", Font.PLAIN, 15));
            quesc.setCursor(new Cursor(Cursor.HAND_CURSOR));
            add(quesc);

            JLabel qd = new JLabel("Option D:");
            qd.setBounds(60, 180, 200, 100);
            qd.setFont(new Font("Arial", Font.PLAIN, 15));
            add(qd);

            quesd = new JTextField();
            quesd.setBounds(180, 220, 300, 25);
            quesd.setFont(new Font("Arial", Font.PLAIN, 15));
            quesd.setCursor(new Cursor(Cursor.HAND_CURSOR));
            add(quesd);

            JLabel ans = new JLabel("Answer:");
            ans.setBounds(60, 210, 300, 100);
            ans.setFont(new Font("Arial", Font.PLAIN, 15));
            add(ans);

            quesans = new JTextField();
            quesans.setBounds(180, 250, 300, 25);
            quesans.setFont(new Font("Arial", Font.PLAIN, 15));
            quesans.setCursor(new Cursor(Cursor.HAND_CURSOR));
            add(quesans);

            update = new JButton("Update");
            update.setBackground(new Color(135, 206, 235));
            update.setBounds(300, 300, 80, 40);
            update.setBorderPainted(false);
            update.addActionListener(this);
            update.setCursor(new Cursor(Cursor.HAND_CURSOR));
            add(update);

            setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource()==search){
                try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizApp", "root", "");
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM question WHERE qid = ?")) {
                    stmt.setInt(1, Integer.parseInt(quesid.getText()));
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        quesid.setText(rs.getString("Qid"));

                        ques.setText(rs.getString("Ques"));
                        quesa.setText(rs.getString("Opta"));
                        quesb.setText(rs.getString("Optb"));
                        quesc.setText(rs.getString("Optc"));
                        quesd.setText(rs.getString("Optd"));
                        quesans.setText(rs.getString("Ans"));
                    } else {
                        JOptionPane.showMessageDialog(this, "No data found for the given ID.");
                    }
                }catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error registering user");
                }

            }

            if(e.getSource()==update){
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizApp", "root", "");
                     PreparedStatement stmt = conn.prepareStatement("UPDATE question SET Ques = ?, Opta=?, Optb=?,Optc=?,Optd=?,Ans=?")) {
                    stmt.setString(1, ques.getText());
                    stmt.setString(2, quesa.getText());
                    stmt.setString(3, quesb.getText());
                    stmt.setString(4, quesc.getText());
                    stmt.setString(5, quesd.getText());
                    stmt.setString(6, quesans.getText());
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Question is Updated");
                    quesid.setText("");
                    ques.setText("");
                    quesa.setText("");
                    quesb.setText("");
                    quesc.setText("");
                    quesd.setText("");
                    quesans.setText("");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error registering user");
                }

            }
        }
    }

    class DeletePanel extends JPanel implements ActionListener{
        JTextField quesid;
        JButton delete;

        public DeletePanel() {
            setSize(600, 400);
            setBackground(Color.WHITE);
            setLayout(null);

            JLabel question = new JLabel("Delete Question");
            question.setBounds(300, 0, 200, 80);
            question.setFont(new Font("Arial", Font.BOLD, 20));
            add(question);

            JLabel qid = new JLabel("QuestionID:");
            qid.setBounds(60, 60, 300, 50);
            qid.setFont(new Font("Arial", Font.PLAIN, 15));
            add(qid);

            quesid = new JTextField();
            quesid.setBounds(180, 70, 30, 25);
            quesid.setFont(new Font("Arial", Font.PLAIN, 15));
            quesid.setCursor(new Cursor(Cursor.HAND_CURSOR));
            add(quesid);

            delete = new JButton("Delete");
            delete.setBackground(new Color(135, 206, 235));
            delete.setBounds(250, 70, 80, 25);
            delete.setBorderPainted(false);
            delete.addActionListener(this);
            delete.setCursor(new Cursor(Cursor.HAND_CURSOR));
            add(delete);

            setVisible(true);
        }


            @Override
            public void actionPerformed(ActionEvent e) {

                if(e.getSource()==delete){
                    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizApp", "root", "");
                         PreparedStatement stmt = conn.prepareStatement("DELETE FROM question WHERE Qid = ?;")) {
                        stmt.setString(1, quesid.getText());
                        stmt.executeUpdate();
                        JOptionPane.showMessageDialog(this, "Question is Deleted");


                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Error registering user");
                    }

                }

        }
    }

    class AllPanel extends JPanel {
        public AllPanel() {
            setSize(500, 400);
            setBackground(Color.WHITE);
            setLayout(new BorderLayout());

            JLabel title = new JLabel("All Questions", SwingConstants.CENTER);
            title.setFont(new Font("Arial", Font.BOLD, 20));
            add(title, BorderLayout.NORTH);

            // Table to display questions (Dummy data for now)
            String[] columnNames = {"QuestionID", "Question", "Option A", "Option B", "Option C", "Option D", "Answer"};
            Object[][] data = fetchDataFromDatabase();
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);

        }

        private Object[][] fetchDataFromDatabase() {
            List<Object[]> dataList = new ArrayList<>();

            String url = "jdbc:mysql://localhost:3306/quizApp";
            String user = "root";
            String password = "";

            String query = "SELECT * FROM question";

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Object[] row = {
                            rs.getInt("Qid"),
                            rs.getString("Ques"),
                            rs.getString("Opta"),
                            rs.getString("Optb"),
                            rs.getString("Optc"),
                            rs.getString("Optd"),
                            rs.getString("Ans")
                    };
                    dataList.add(row);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error fetching data from the database.");
            }

            // Convert the list to a 2D array
            Object[][] dataArray = new Object[dataList.size()][];
            return dataList.toArray(dataArray);
        }

    }

    class ResultPanel extends JPanel {
        public ResultPanel() {
            setSize(600, 400);
            setBackground(Color.WHITE);
            setLayout(new BorderLayout());

            JLabel title = new JLabel("Students' Results", SwingConstants.CENTER);
            title.setFont(new Font("Arial", Font.BOLD, 20));
            add(title, BorderLayout.NORTH);

            // Table to display results (Dummy data for now)
            String[] columnNames = {"StudentID", "First_Name","Last_Name" ,"Score"};
            Object[][] data = fetchDataFromDatabaseRegistration();
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);
        }
        private Object[][] fetchDataFromDatabaseRegistration() {
            List<Object[]> dataList = new ArrayList<>();

            String url = "jdbc:mysql://localhost:3306/quizApp";
            String user = "root";
            String password = "";

            String query = "SELECT * FROM registeration";

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    Object[] row = {
                            rs.getInt("SID"),
                            rs.getString("First_Name"),
                            rs.getString("Last_Name"),
                            rs.getString("Score"),

                    };
                    dataList.add(row);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error fetching data from the database.");
            }

            // Convert the list to a 2D array
            Object[][] dataArray = new Object[dataList.size()][];
            return dataList.toArray(dataArray);
        }

    }

}

