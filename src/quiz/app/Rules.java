package quiz.app;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Color.white;

public class Rules extends JFrame implements ActionListener{
   JCheckBox checkBox;
   JButton back,next;
   boolean flag=false;
   String username;
    Rules(String username){
        this.username=username;
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("icons/ticon.png"));
        Image imaget = icon.getImage();
        setIconImage(imaget);
        setSize(800, 500);
        setLayout(new FlowLayout());
        add(new JButton("Sample Button"));
        setTitle("Quiz App");
        getContentPane().setBackground(white);
        setLayout(null);
        setSize(800,500);
        setLocation(250,150);
        setVisible(true);

        JLabel heading=new JLabel("Instruction For Online Test");
        heading.setForeground(new Color(135,206,235));
        heading.setBounds(60,30,500,50);
        heading.setFont(new Font("Arial",Font.BOLD,20));
        add(heading);

        String temp= "<html>"
                + "<body>"
                +"<h2 style='Color:red;'>Read Instruction Carefully</h1>"+"<br>"+"<h4>1. Arrange for stable Internet connectivity."+"<br>"
                + "2. Giving examination on Laptop or Desktop is highly recommended. "+"<br>"
                +"3. Make sure mobile/laptop is fully charged. Power bank for mobile or "+"<br>"+" UPS/Inverter for laptop/desktop should be arranged for uninterrupted power supply."+"<br>"
                +"4. Students should have sufficient data in Fair Usage Policy (FUP) / Internet plan "+"<br>"+"with sufficient data pack of internet service provider."+"<br>"
                +"5. Login to the portal 10 min before the online examination start time."+"<br>"
                +"6. Close all browsers/tabs before starting the online examination."+"<br>"
                +"7. Once the exam starts, do not switch to any other window/tab. On doing so, "+"<br>"+"your attempt may be considered as malpractice and your exam may get terminated."+"<br>"
                +"8. Do Not Pickup/Receive the Call during the exam if you are giving the exam on mobile. "+"<br>"+"This also will be treated as changing the window."+"<br>"
                +"9. To avoid unwanted pop-ups, use of Ad Blocker is recommended."+"<br>"
                +"10. Clear browser cache memory on mobile and laptops. Clear browsing history and also delete"+"<br>"+" temp files."+"<br>"
                + "</h4>"
                + "</body>"
                + "</html>";

        JLabel rules=new JLabel(temp);

        JScrollPane scrollPane = new JScrollPane(rules);
        scrollPane.setBounds(80,80,600,225);
        rules.setHorizontalAlignment(SwingConstants.CENTER);
        scrollPane.setForeground(Color.BLACK);
        scrollPane.setFont(new Font("Arial",Font.PLAIN,18));
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);

        checkBox=new JCheckBox();
        checkBox.setBounds(60,327,25,25);
        checkBox.addActionListener(this);
        add(checkBox);

        JLabel read=new JLabel("I have read all the instruction carefully and understood them.");
        read.setFont(new Font("Arial",Font.PLAIN,15));
        read.setBounds(85,325,400,30);
        add(read);


        next=new JButton("I am ready to begin");
        next.setBounds(300,390,150,25);
        next.setBorderPainted(false);
        next.setBackground(new Color(135,206,235));
        next.addActionListener(this);
        add(next);


        back=new JButton("Previous");
        back.setBounds(30,390,100,25);
        back.setBorderPainted(false);
        back.setBackground(new Color(135,206,235));
        back.addActionListener(this);
        add(back);



    }
    public static void main(String [] args, String username){
        SwingUtilities.invokeLater(() -> {
            Rules ex=new Rules(username);
            ex.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(flag==false  && e.getSource()==checkBox)
            flag=true;
        if(e.getSource()==back){
            setVisible(false);
            new Login();
        }
        else if(e.getSource()==next){
           if(flag==true){
               setVisible(false);
               new Test(username);
           }
        }
    }
}