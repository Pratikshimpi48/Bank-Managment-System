package infobeans.banking.system;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class UserLogin extends JFrame implements ActionListener {
     JLabel accountNumberLabel, passwordLabel, userLoginLabel;
     JTextField accountNumberTextField;
     JPasswordField passwordField;
     JButton loginButton, backButton;

    public UserLogin() {
        setTitle("User Login");
        setSize(800, 400);
        setLocation(250, 150);
        setLayout(null);
        setVisible(true);

        userLoginLabel = new JLabel("User Login");
        userLoginLabel.setBounds(350, 10, 170, 30);
        userLoginLabel.setFont(new Font("Osward", Font.BOLD, 20));
        add(userLoginLabel);

        accountNumberLabel = new JLabel("Account Number:");
        accountNumberLabel.setBounds(200, 100, 170, 20);
        accountNumberLabel.setFont(new Font("Osward", Font.BOLD, 20));
        add(accountNumberLabel);

        accountNumberTextField = new JTextField();
        accountNumberTextField.setBounds(400, 100, 170, 30);
        add(accountNumberTextField);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(200, 150, 170, 20);
        passwordLabel.setFont(new Font("Osward", Font.BOLD, 20));
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(400, 150, 170, 30);
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(200, 250, 170, 30);
        loginButton.addActionListener(this);
        add(loginButton);

        backButton = new JButton("Back");
        backButton.setBounds(400, 250, 170, 30);
        backButton.addActionListener(this);
        add(backButton);
    }

    private boolean checkLogin(String accountNumber, String password) {
        try {
            Conn c = new Conn();
            Connection connection = c.getConnection();
            String query = "SELECT * FROM Customer WHERE accountnum = ? AND password = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, accountNumber);
//            String accountNumber1 = accountNumber;
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == loginButton) {
            String accnum = accountNumberTextField.getText();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);

            boolean loginSuccessful = checkLogin(accnum, password);

            if (loginSuccessful) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                new UserFunctionality(accnum);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Login failed. Please check your credentials.");
            }
        }

        if (ae.getSource() == backButton) {
            new Choice();
            dispose();
        }
    }

    public static void main(String[] args) {
        new UserLogin();
    }
}
