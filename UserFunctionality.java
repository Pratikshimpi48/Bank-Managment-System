package infobeans.banking.system;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class UserFunctionality extends JFrame implements ActionListener{
    JButton updateButton,withdrawButton,depositButton,transactionhistoryButton,transferButton,checkBalanceButton,applyforLoanButton,loanaccountButton,backButton;
    private String LoggedInAccountNumber;
   UserFunctionality(String accountNumber){
        this.LoggedInAccountNumber = accountNumber;
        setTitle("User Choice");
        setSize(800,400);
        setLocation(250,150);
        setLayout(null);
        setVisible(true);
        
        updateButton = new JButton("Update Account Details");
        updateButton.setBounds(200,30,170,25);
        updateButton.addActionListener(this);
        add(updateButton);
        
        withdrawButton = new JButton("Wthdraw Amount");
        withdrawButton.setBounds(450,30,170,25);
        withdrawButton.addActionListener(this);
        add(withdrawButton);
        
        depositButton = new JButton("Deposit Amount");
        depositButton.setBounds(200,90,170,25);
        depositButton.addActionListener(this);
        add(depositButton);
        
        transferButton = new JButton("Money Transfer");
        transferButton.setBounds(200,150,170,25);
        transferButton.addActionListener(this);
        add(transferButton);
        
        checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.setBounds(450,90,170,25);
        checkBalanceButton.addActionListener(this);
        add(checkBalanceButton);
        
        applyforLoanButton = new JButton("Apply For Loan");
        applyforLoanButton.setBounds(200,210,170,25);
        applyforLoanButton.addActionListener(this);
        add(applyforLoanButton);
        
        loanaccountButton = new JButton("Loan Account Details");
        loanaccountButton.setBounds(450,150,170,25);
        loanaccountButton.addActionListener(this);
        add(loanaccountButton);
        
        backButton = new JButton("Move to Previous Page");
        backButton.setBounds(450,210,170,25);
        backButton.addActionListener(this);
        add(backButton);
    }
    public void actionPerformed(ActionEvent ae) {
        CheckBalance ch = new CheckBalance(LoggedInAccountNumber);
    if (ae.getSource() == updateButton) {
        new UpdateAccountDetails(LoggedInAccountNumber);
        dispose();
       
    }
    if (ae.getSource() == checkBalanceButton) {
        double balance = ch.getBalance();
            JOptionPane.showMessageDialog(this, "Your Account Balance: $" + balance, "Account Balance", JOptionPane.INFORMATION_MESSAGE);
    }
    if (ae.getSource() == backButton) {
        new Choice();
        dispose();
    }
    if(ae.getSource() == depositButton){
        new DepositAmount(LoggedInAccountNumber);
        dispose();
    }
    if(ae.getSource() == withdrawButton){
        new WithDrawAmount(LoggedInAccountNumber);
        dispose();
    }
    if(ae.getSource() == transferButton){
        new TransferMoney(LoggedInAccountNumber);
        dispose();
    }
    if(ae.getSource() == applyforLoanButton){
        new ApplyForLoan(LoggedInAccountNumber);
        dispose();
    }
    if(ae.getSource() == loanaccountButton){
            try {        
                new ViewLoanDetails(LoggedInAccountNumber);
                dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        
    }
}

    public static void main(String[] args) {
        new UserFunctionality("12345");
    }
}
