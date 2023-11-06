package infobeans.banking.system;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.*;
import javax.swing.event.AncestorListener;

public class DepositAmount extends JFrame implements ActionListener {

    private String LoggedInAccountNumber;
    JLabel deposit, depositLabel;
    JTextField depositTextField;
    JButton dopositButton, backButton;

    DepositAmount(String accountNumber) {
        this.LoggedInAccountNumber = accountNumber;
        setSize(800, 400);
        setLocation(250, 150);
        setLayout(null);
        setVisible(true);

        depositLabel = new JLabel("Deposit Your Amount");
        depositLabel.setBounds(270, 30, 250, 30);
        depositLabel.setFont(new Font("Osward", Font.BOLD, 20));
        add(depositLabel);

        deposit = new JLabel("Enter the amount:");
        deposit.setBounds(150, 130, 170, 20);
        deposit.setFont(new Font("Osward", Font.BOLD, 20));
        add(deposit);

        depositTextField = new JTextField();
        depositTextField.setBounds(350, 130, 170, 30);
        add(depositTextField);

        dopositButton = new JButton("Deposit");
        dopositButton.setBounds(200, 250, 150, 25);
        dopositButton.addActionListener(this);
        add(dopositButton);

        backButton = new JButton("Back");
        backButton.setBounds(400, 250, 150, 25);
        backButton.addActionListener(this);
        add(backButton);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == dopositButton) {
            String amountStr = depositTextField.getText();
            try {
                double dpamount = Double.parseDouble(amountStr);
                if (dpamount <= 0) {
                    JOptionPane.showMessageDialog(this, "Please Enter valid depositamount");
                } else {
                    if (performedDeposit(dpamount)) {
                        JOptionPane.showMessageDialog(this, "Deposit Successful!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Deposit failed. Please try again later.");
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid deposit amount.");
            }
        }
        if(ae.getSource() == backButton){
            new UserFunctionality(LoggedInAccountNumber);
            dispose();
        }
    }
private boolean performedDeposit(double depositAmount){
    try {
        Conn c = new Conn();
        Connection connection = c.getConnection();
        String query = "Update Customer set depositamount = depositamount+? where accountnum = ?";
        PreparedStatement updateStatement = connection.prepareStatement(query);
        updateStatement.setDouble(1, depositAmount);
        updateStatement.setString(2, LoggedInAccountNumber);
        
        int updatedRows = updateStatement.executeUpdate();
        connection.close();
        return updatedRows > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
    public static void main(String[] args) {
        new DepositAmount("12345");
    }
}
