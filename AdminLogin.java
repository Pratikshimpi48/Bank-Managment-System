package infobeans.banking.system;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
public class AdminLogin extends JFrame implements ActionListener{
    
    JButton loginButton,backButton;
    JPasswordField passwordField;
    JTextField usernameField;
    private JFrame prev;
    
    AdminLogin(){
        setTitle("Admin Login");
        setSize(800,400);
        setLocation(250,150);
        setVisible(true);
        setLayout(null);
        
        JLabel text = new JLabel("Welocme Admin");
        text.setFont(new Font("Osward",Font.BOLD,30));
        text.setBounds(230,20,400,40);
        add(text);
        
        JLabel username = new JLabel("Account NO: ");
        username.setBounds(200,100,170,30);
        username.setFont(new Font("Osward",Font.BOLD,20));
        add(username);
        
        usernameField = new JTextField();
        usernameField.setBounds(350,100,170,30);
        add(usernameField);
        
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setBounds(200,150,150,30);
        passwordLabel.setFont(new Font("Osward",Font.BOLD,20));
        add(passwordLabel);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(350,150,170,30);
        add(passwordField);
        
        loginButton = new JButton("Login");
        loginButton.setBounds(280,230,100,30);
        loginButton.addActionListener(this);
        add(loginButton);
        
        backButton = new JButton("Back");
        backButton.setBounds(400, 230, 100, 30);
        backButton.addActionListener(this);
        add(backButton);
    }
    AdminLogin(JFrame prev){
        this.prev = prev;
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == loginButton){
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            
            if(username.equals("bank") && password.equals("123")){
                new AdminFunctionality();
                dispose();
            }
            else{
            JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
            usernameField.setText("");
            passwordField.setText("");
            }
        }
        else if(ae.getSource() == backButton){
              if(prev != null){
                    prev.setVisible(true);
               }  
              dispose();
              new Choice();
            }
        }
    public static void main(String args[]){
        JFrame choicewin = new Choice();
        choicewin.setSize(800,400);
        choicewin.setLocation(250,150);
        new AdminLogin();
    }
}

