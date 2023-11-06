package infobeans.banking.system;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Choice extends JFrame implements ActionListener{
    
    JButton Admin,User,Clear;
    Choice(){
        setTitle("Infobeans Banking System");
        setLayout(null);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("Images/Logo.png"));
        Image i2 = i1.getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel label = new JLabel(i3);
        label.setBounds(70,10,100,100);
        add(label);
        
        JLabel text = new JLabel("Welcome To The Bank");
        text.setFont(new Font("Osward",Font.BOLD,30));
        text.setBounds(200,40,400,40);
        add(text);
        
        Admin = new JButton("Admin Login");
        Admin.setBounds(200,150,150,30);
        Admin.setBackground(Color.BLACK);
        Admin.setForeground(Color.WHITE);
        Admin.addActionListener(this);
        add(Admin);
        
        User = new JButton("User Login");
        User.setBounds(420,150,150,30);
        User.setBackground(Color.BLACK);
        User.setForeground(Color.WHITE);
        User.addActionListener(this);

        add(User);
        
        Clear = new JButton("Exit");
        Clear.setBounds(300,230,150,30);
        Clear.setBackground(Color.BLACK);
        Clear.setForeground(Color.WHITE);
        Clear.addActionListener(this);
        add(Clear);

        getContentPane().setBackground(Color.GRAY);
        
        setSize(800,400);
        setVisible(true);
        setLocation(280,200);
    }
    
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == Clear){
            System.exit(0);
        }
        if(ae.getSource() == Admin){
            new AdminLogin();
            dispose();
        }
        if(ae.getSource() == User){
            new UserLogin();
            dispose();
        }
    }
   public static void main(String args[]){
       new Choice();
   } 
}
