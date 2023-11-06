package infobeans.banking.system;

import javax.swing.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateAccountDetails extends JFrame implements ActionListener {

    private String loggedInAccountNumber;  // Store the account number of the logged-in user
    JTextField nameField, fathersNameField, mobileField, emailField,cityField,addressField;
    JButton updateButton, backButton;

    public UpdateAccountDetails(String accountNumber) {
        this.loggedInAccountNumber = accountNumber;

        setTitle("Update Account Details");
        setSize(800, 400);
        setLocation(250, 150);
        setLayout(null);
        setVisible(true);

        // For the logged-in user, you don't need to input the account number again.
        JLabel nameLabel = new JLabel("Name: ");
        nameLabel.setBounds(30, 80, 150, 30);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(100, 80, 150, 30);
        add(nameField);

        JLabel fathersNameLabel = new JLabel("Father's Name: ");
        fathersNameLabel.setBounds(300, 80, 150, 30);
        add(fathersNameLabel);

        fathersNameField = new JTextField();
        fathersNameField.setBounds(400, 80, 150, 30);
        add(fathersNameField);

        JLabel mobileLabel = new JLabel("Mobile Number: ");
        mobileLabel.setBounds(20, 140, 150, 30);
        add(mobileLabel);

        mobileField = new JTextField();
        mobileField.setBounds(120, 140, 150, 30);
        add(mobileField);

        JLabel emailLabel = new JLabel("Email ID: ");
        emailLabel.setBounds(300, 140, 150, 30);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(400, 140, 150, 30);
        add(emailField);
        
        JLabel cityLabel = new JLabel("City:");
        cityLabel.setBounds(50,200,150,30);
        add(cityLabel);
        
        cityField = new JTextField();
        cityField.setBounds(100,200,150,30);
        add(cityField);
        
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(300,200,150,30);
        add(addressLabel);
        
        addressField = new JTextField();
        addressField.setBounds(400,200,150,30);
        add(addressField);

        updateButton = new JButton("Update");
        updateButton.setBounds(180, 280, 100, 30);
        add(updateButton);
        updateButton.addActionListener(this);

        backButton = new JButton("Back");
        backButton.setBounds(300, 280, 100, 30);
        backButton.addActionListener(this);
        add(backButton);
    }
     
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == backButton) {
            this.dispose(); 
        } else if (ae.getSource() == updateButton) {
            String name = nameField.getText();
            String fathersName = fathersNameField.getText();
            String mobile = mobileField.getText();
            String email = emailField.getText();
            String city = cityField.getText();
            String address = addressField.getText();

            try {
                Conn c = new Conn();
                Connection connection = c.getConnection();
                String query = "UPDATE customer SET name=?, fathersname=?, mobileno=?, email_id=?,city = ?,address = ? WHERE accountnum=?";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setString(1, name);
                pstmt.setString(2, fathersName);
                pstmt.setString(3, mobile);
                pstmt.setString(4, email);
                pstmt.setString(5, city);
                pstmt.setString(6,address);
                pstmt.setString(7, loggedInAccountNumber);

                int rowCount = pstmt.executeUpdate();

                if (rowCount > 0) {
                    JOptionPane.showMessageDialog(this, "Account details updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Account details update failed.");
                }

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error updating account details.");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(UpdateAccountDetails.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        new UpdateAccountDetails("12345"); // Replace "12345" with the actual account number.
    }
}
