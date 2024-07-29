//package quiz.app;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class FinalResult extends JFrame{
//    FinalResult(){
//        setTitle("Quiz App");
//        getContentPane().setBackground(Color.white);
//        setLayout(null); // Set layout to null for manual positioning
//        setVisible(true);
//        setSize(800, 500);
//        setLocation(250, 150);
//
//        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/login.jpg"));
//        Image i = i1.getImage().getScaledInstance(500, 500, Image.SCALE_DEFAULT);
//        ImageIcon i2 = new ImageIcon(i);
//        JLabel image = new JLabel(i2);
////        image.setBounds(150, 0, 500, 500); // Adjust bounds to fit within the window
//        add(image);
//    }
//    public static void main(String[] args){
//        new FinalResult();
//    }
//}
package quiz.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Color.white;
//import static quiz.app.Test.username;

public class FinalResult extends JFrame implements ActionListener {
    JButton play;
    String username;
//    int result=0;
    FinalResult(int result, int currentQuestionIndex, String username) {
        this.username=username;
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/ticon.png"));
        Image imaget = icon.getImage();
        setIconImage(imaget);
        setSize(700, 500);
        setLayout(new FlowLayout());
        add(new JButton("Sample Button"));

        setTitle("Quiz App");
        getContentPane().setBackground(white);
        setLayout(null);
        setLocation(250,150);
        setVisible(true);

        JLabel title=new JLabel("Quiz Result");
        title.setFont(new Font("Arial", Font.BOLD,20));
        title.setBounds(300,5,300,50);
        add(title);

        // Ensure the path to the image is correct
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/trophy.png"));
        Image i = i1.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT);
        ImageIcon i2 = new ImageIcon(i);
        JLabel image = new JLabel(i2);
        image.setBounds(250, 20, i2.getIconWidth(), i2.getIconHeight()); // Adjust bounds to fit within the window
        add(image);

        JLabel c=new JLabel("Correct:");
        c.setBounds(200,225,200,30);
        c.setFont(new Font("Arial", Font.BOLD,15));
        add(c);

        JLabel r=new JLabel(String.valueOf(result));
        r.setBounds(270,225,100,30);
        r.setFont(new Font("Arial", Font.BOLD,15));
        add(r);

        JLabel w=new JLabel("Wrong:");
        w.setBounds(200,250,200,30);
        w.setFont(new Font("Arial", Font.BOLD,15));
        add(w);

        JLabel w1=new JLabel(String.valueOf(currentQuestionIndex-result-1));
        w1.setBounds(270,250,100,30);
        w1.setFont(new Font("Arial", Font.BOLD,15));
        add(w1);

        JLabel sc=new JLabel("Score:");
        sc.setBounds(400,225,200,30);
        sc.setFont(new Font("Arial", Font.BOLD,15));
        add(sc);

        JLabel s=new JLabel(String.valueOf(result*10-(currentQuestionIndex-result-1)*5));
        s.setBounds(450,225,100,30);
        s.setFont(new Font("Arial", Font.BOLD,15));
        add(s);

        JLabel sk=new JLabel("Skip:");
        sk.setBounds(400,250,200,30);
        sk.setFont(new Font("Arial", Font.BOLD,15));
        add(sk);

        JLabel k=new JLabel(String.valueOf(10-currentQuestionIndex+1));
        k.setBounds(450,250,100,30);
        k.setFont(new Font("Arial", Font.BOLD,15));
        add(k);

        play=new JButton("ReTake");
        play.setBounds(300,325,80,30);
        play.setBackground(new Color(135,206,235));
        play.addActionListener(this);
        play.setBorderPainted(false);
        play.setIgnoreRepaint(false);
        add(play);


    }

    public static void main(String[] args, int result, int currentQuestionIndex, String username) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                new FinalResult(result, currentQuestionIndex, username);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==play){
            setVisible(false);
            new Rules(username);
        }
    }
}
