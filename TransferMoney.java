package infobeans.banking.system;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class TransferMoney extends JFrame implements ActionListener {

    JTextField userAccountTextField, amountTextField;
    JButton tarnsferButton, backButton, transactionHistoryButton;
    JLabel Transfer, eneteracc, enteramt;
    private String loggedInAccountNumber;

    TransferMoney(String accountNumber) {
        this.loggedInAccountNumber = accountNumber;
        setSize(800, 400);
        setLocation(250, 150);
        setLayout(null);
        setVisible(true);

        Transfer = new JLabel("Transfer Money");
        Transfer.setBounds(300, 30, 250, 30);
        Transfer.setFont(new Font("Osward", Font.BOLD, 20));
        add(Transfer);

        eneteracc = new JLabel("Enter the accountnumber:");
        eneteracc.setBounds(130, 130, 250, 20);
        eneteracc.setFont(new Font("Osward", Font.BOLD, 20));
        add(eneteracc);

        userAccountTextField = new JTextField();
        userAccountTextField.setBounds(400, 130, 170, 30);
        userAccountTextField.setFont(new Font("Osward", Font.BOLD, 20));
        add(userAccountTextField);

        enteramt = new JLabel("Enter the amount:");
        enteramt.setBounds(130, 200, 170, 25);
        enteramt.setFont(new Font("Osward", Font.BOLD, 20));
        add(enteramt);

        amountTextField = new JTextField();
        amountTextField.setBounds(400, 200, 170, 30);
        amountTextField.setFont(new Font("Osward", Font.BOLD, 20));
        add(amountTextField);

        tarnsferButton = new JButton("Transfer");
        tarnsferButton.setBounds(100, 300, 150, 25);
        tarnsferButton.addActionListener(this);
        add(tarnsferButton);

        backButton = new JButton("Back");
        backButton.setBounds(280, 300, 150, 25);
        backButton.addActionListener(this);
        add(backButton);

        transactionHistoryButton = new JButton("Transaction History");
        transactionHistoryButton.setBounds(450, 300, 200, 25);
        transactionHistoryButton.addActionListener(this);
        add(transactionHistoryButton);

    }
    double amount = 0;

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == tarnsferButton) {
            String targetAccount = userAccountTextField.getText();
            String amountStr = amountTextField.getText();
            if (isValidInput(targetAccount, amountStr)) {
                double amount = Double.parseDouble(amountTextField.getText());

                try {
                    if (transferMoney(loggedInAccountNumber, targetAccount, amount)) {
                        JOptionPane.showMessageDialog(this, "Money transferred successfully!");
                        history(targetAccount);
                    } else {
                        JOptionPane.showMessageDialog(this, "Transfer failed. Please check the account number and amount.");
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(TransferMoney.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else if (ae.getSource() == backButton) {
            new UserFunctionality(loggedInAccountNumber);
            dispose();
        } else if (ae.getSource() == transactionHistoryButton) {
            displayTransactionHistory(loggedInAccountNumber);
        }
    }

    private boolean isValidInput(String targetAccount, String amountStr) {
        if (targetAccount.isEmpty() || amountStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both fields.");
            return false;
        }
        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid, positive amount.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric amount.");
            return false;
        }
        return true;
    }

    private boolean transferMoney(String sourceAccount, String targetAccount, double amount) throws ClassNotFoundException {
        Connection connection = null;
        PreparedStatement updateSourceAccount = null;
        PreparedStatement updateTargetAccount = null;
        PreparedStatement checkTargetAccount = null;

        try {
            Conn c = new Conn();
            connection = c.getConnection();
            connection.setAutoCommit(false);

            String checkTargetAccountQuery = "SELECT depositamount FROM Customer WHERE accountnum = ?";
            PreparedStatement checkTargetAccountStatement = connection.prepareStatement(checkTargetAccountQuery);
            checkTargetAccountStatement.setString(1, targetAccount);
            ResultSet targetAccountResult = checkTargetAccountStatement.executeQuery();

            if (!targetAccountResult.next()) {
                JOptionPane.showMessageDialog(this, "Target account does not exist.");
                return false;
            }
            String account = targetAccount;

            String checkBalanceQuery = "Select depositamount from Customer Where accountnum = ?";
            PreparedStatement checkBalanceStatement = connection.prepareStatement(checkBalanceQuery);
            checkBalanceStatement.setString(1, sourceAccount);
            ResultSet sourceAccountResult = checkBalanceStatement.executeQuery();

            if (!sourceAccountResult.next()) {
                return false;
            }
            double sourceAccountBalance = sourceAccountResult.getDouble("depositamount");
            if (sourceAccountBalance < amount) {
                return false;
            }
            String updateSourceQuery = "Update Customer set depositamount = depositamount-? where accountnum = ?";
            updateSourceAccount = connection.prepareStatement(updateSourceQuery);
            updateSourceAccount.setDouble(1, amount);
            updateSourceAccount.setString(2, sourceAccount);
            updateSourceAccount.executeUpdate();

            String updateTargetQuery = "Update Customer set depositamount = depositamount + ? where accountnum = ?";
            updateTargetAccount = connection.prepareStatement(updateTargetQuery);
            updateTargetAccount.setDouble(1, amount);
            updateTargetAccount.setString(2, targetAccount);
            updateTargetAccount.executeUpdate();

            connection.commit();
            return true;
        } catch (SQLException ex) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
            return false;
        } finally {
            try {
                if (updateSourceAccount != null) {
                    updateSourceAccount.close();
                }
                if (updateTargetAccount != null) {
                    updateTargetAccount.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void displayTransactionHistory(String accountNumber) {
        Connection connection = null;
        PreparedStatement fetchTransactionHistory = null;
        ResultSet resultSet = null;

        try {
            Conn c = new Conn();
            connection = c.getConnection();
            String fetchTransactionQuery = "SELECT accountno, Transaction_date, credit, debit, detail "
                    + "FROM Transaction INNER JOIN Customer ON Transaction.accountno = Customer.accountnum "
                    + "WHERE accountno = ?";
            fetchTransactionHistory = connection.prepareStatement(fetchTransactionQuery);
            fetchTransactionHistory.setString(1, accountNumber);
            resultSet = fetchTransactionHistory.executeQuery();

            JTextArea transactionHistoryTextArea = new JTextArea();
            while (resultSet.next()) {
                String transactionDate = resultSet.getString("Transaction_date");
                double credit = resultSet.getDouble("credit");
                double debit = resultSet.getDouble("debit");
                String details = resultSet.getString("detail");

                String transactionInfo = "Date: " + transactionDate + ", Credit: " + credit + ", Debit: " + debit + ", Detail: " + details;
                transactionHistoryTextArea.append(transactionInfo + "\n");
            }
            JFrame historyFrame = new JFrame("Transaction History");
            historyFrame.setSize(400, 400);
            historyFrame.setLocationRelativeTo(this);
            historyFrame.add(new JScrollPane(transactionHistoryTextArea));
            historyFrame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void history(String targetAccount) {
        Connection connection = null;
        PreparedStatement insertTransaction = null;

        try {
            Conn c = new Conn();
            connection = c.getConnection();
            Date date = null;

            String insertTransactionQuery = "INSERT INTO Transaction (accountno, transaction_date,credit,debit,detail ) VALUES (?, ?, ?, ?,?)";
            insertTransaction = connection.prepareStatement(insertTransactionQuery);
            insertTransaction.setString(1, loggedInAccountNumber);
            insertTransaction.setDate(2, (java.sql.Date) date);
            insertTransaction.setDouble(3, amount);
            insertTransaction.setDouble(4, 0.0);
            insertTransaction.setString(5, "Money Sent to " + targetAccount);

            int count = insertTransaction.executeUpdate();

            String insertTransactionQuery1 = "INSERT INTO Transaction (accountno, transaction_date,credit,debit,detail ) VALUES (?, ?, ?, ?,?)";
            insertTransaction = connection.prepareStatement(insertTransactionQuery1);
            insertTransaction.setString(1, targetAccount);
            insertTransaction.setDate(2, (java.sql.Date) date);
            insertTransaction.setDouble(3, 0.0);
            insertTransaction.setDouble(4, amount);
            insertTransaction.setString(5, "Money Recieve from " + loggedInAccountNumber);

            if (count > 0) {
                count = insertTransaction.executeUpdate();
            } else {
                System.out.println("Failed");
            }
            if (count > 0) {
                System.out.println("success");
            } else {
                System.out.println("Failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TransferMoney("12345");
    }

}
