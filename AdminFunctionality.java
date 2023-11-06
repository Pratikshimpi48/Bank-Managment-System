package infobeans.banking.system;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminFunctionality extends JFrame implements ActionListener {
    JButton createaccount,viewparticular,blockacc,unblockacc,viewallacc,logoutacc;
    private JFrame prev;
    
    AdminFunctionality(){
        setTitle("Admin Functinality");
        setSize(800,400);
        setLocation(250,150);
        setVisible(true);
        setLayout(null);
        
        JLabel text = new JLabel("Choose The Option");
        text.setFont(new Font("Osward",Font.BOLD,30));
        text.setBounds(230,10,400,40);
        add(text);
        
        createaccount = new JButton("Create Account: ");
        createaccount.setBounds(200,70,150,30);
        createaccount.addActionListener(this);
        add(createaccount);
        
        viewparticular = new JButton("View Particular Account");
        viewparticular.setBounds(400,70,200,30);
        viewparticular.addActionListener(this);
        add(viewparticular);
        
        blockacc = new JButton("Block Account");
        blockacc.setBounds(200,140,150,30);
        blockacc.addActionListener(this);
        add(blockacc);
        
        unblockacc = new JButton("Unblock Account");
        unblockacc.setBounds(400,140,200,30);
        unblockacc.addActionListener(this);
        add(unblockacc);
        
        viewallacc = new JButton("View All Account");
        viewallacc.setBounds(200,200,150,30);
        viewallacc.addActionListener(this);
        add(viewallacc);
        
        logoutacc = new JButton("Logout Account");
        logoutacc.setBounds(400,200,200,30);
        logoutacc.addActionListener(this);
        add(logoutacc);
    }
    AdminFunctionality(JFrame prev){
        this.prev = prev;
    }
    private boolean validAndFetchedAccount(String accountNumber,String password){
        try{
            Conn c = new Conn();
            Connection connection = c.getConnection();
            String query = "Select * From Customer Where accountnum = ? And password = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, accountNumber);
            pstmt.setString(2, password);
            
            ResultSet resultset = pstmt.executeQuery();
            if(resultset.next()){
                return true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == createaccount){
            new CreateAccount();
        }
        if(ae.getSource() == logoutacc){
           new Choice();  
           dispose();
           }
        if(ae.getSource() == viewparticular){
            String accountNumber = JOptionPane.showInputDialog("Enter Account Number:");
            String password = JOptionPane.showInputDialog("Enter Password:");

            if (validAndFetchedAccount(accountNumber, password)) {
                new ViewParticularAcc(accountNumber);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Account Number or Password");
            }
        }
        if(ae.getSource() == viewallacc){
            new ViewAllAccount();
        }
        if(ae.getSource() == blockacc){
            new BlockAccount();
        }
        if(ae.getSource() == unblockacc){
            new UnblockAccount();
        }
    }
    public static void main(String args[]){
        JFrame choicewin = new Choice();
        choicewin.setSize(800,400);
        choicewin.setLocation(250,150);
        new AdminFunctionality();
    }
}
