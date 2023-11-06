package infobeans.banking.system;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JFrame;
import javax.swing.*;
public class WithDrawAmount extends JFrame implements ActionListener{
    JLabel withdraw,withdrawLabel;
    JButton withdrawButton,backButton;
    private String LoggedInAccountNumber;
    JTextField withdrawTextField;
    WithDrawAmount(String accountNumber){
        this.LoggedInAccountNumber = accountNumber;
        setSize(800, 400);
        setLocation(250, 150);
        setLayout(null);
        setVisible(true);
        
        withdrawLabel = new JLabel("WithDraw Your Amount");
        withdrawLabel.setBounds(270, 30, 250, 30);
        withdrawLabel.setFont(new Font("Osward", Font.BOLD, 20));
        add(withdrawLabel);
        
        withdraw = new JLabel("Enter the amount:");
        withdraw.setBounds(150, 130, 170, 20);
        withdraw.setFont(new Font("Osward", Font.BOLD, 20));
        add(withdraw);
        
        withdrawTextField = new JTextField();
        withdrawTextField.setBounds(350,130,170,25);
        add(withdrawTextField);
        
        withdrawButton = new JButton("WithDraw");
        withdrawButton.setBounds(200, 250, 150, 25);
        withdrawButton.addActionListener(this);
        add(withdrawButton);

        backButton = new JButton("Back");
        backButton.setBounds(400, 250, 150, 25);
        backButton.addActionListener(this);
        add(backButton);
        
    }
   public void actionPerformed(ActionEvent ae) {
       if (ae.getSource() == withdrawButton) {
            String amountStr = withdrawTextField.getText();
            try {
                double wpamount = Double.parseDouble(amountStr);
                if (wpamount <= 0) {
                    JOptionPane.showMessageDialog(this, "Please Enter valid depositamount");
                } else {
                    if (performedDeposit(wpamount)) {
                        JOptionPane.showMessageDialog(this, "Withdraw Successful!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "withdraw failed. Please try again later.");
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid withdraw amount.");
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
        String query = "Update Customer set depositamount = depositamount-? where accountnum = ?";
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
        new WithDrawAmount("1332");
    }

}
