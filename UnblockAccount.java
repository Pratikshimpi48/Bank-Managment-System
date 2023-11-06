package infobeans.banking.system;

import java.awt.Font;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;



public class UnblockAccount extends JFrame implements ActionListener{
    private JTextField accountNumberField;
    private JButton unblockButton;
    UnblockAccount(){
        setTitle("Unblock Account");
        setSize(400, 200);
        setLocation(400, 200);
        setLayout(null);
        setVisible(true);
        
        JLabel label = new JLabel("Enter Account Number:");
        label.setFont(new Font("Osward", Font.BOLD, 16));
        label.setBounds(50, 30, 200, 30);
        add(label);
        
        accountNumberField = new JTextField();
        accountNumberField.setBounds(220, 30, 150, 30);
        add(accountNumberField);
        
        unblockButton = new JButton("Unblock Account");
        unblockButton.setBounds(150, 100, 150, 30);
        unblockButton.addActionListener(this);
        add(unblockButton);
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == unblockButton){
            String accountNumber = accountNumberField.getText();
            if(unBlockAccount(accountNumber)){
              JOptionPane.showMessageDialog(this, "Account unblocked successfully.");
            }
            else {
                JOptionPane.showMessageDialog(this, "Failed to unblock the account.");
            }
        }
    }
    private boolean unBlockAccount(String accountNumber){
        try{
            Conn c = new Conn();
            Connection conn = c.getConnection();
            String query = "UPDATE Customer SET status = 'Unblocked' WHERE accountnum = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, accountNumber);
            
            int rowAffect = pstmt.executeUpdate();
            conn.close();
            
            return rowAffect > 0;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public static void main(String args[]){
        new UnblockAccount();
    }
}
