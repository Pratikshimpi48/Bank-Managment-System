package infobeans.banking.system;

import java.awt.Font;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class ViewAllAccount extends JFrame {
    ViewAllAccount(){
        setSize(800,400);
        setLocation(250,180);
        setLayout(null);
        setVisible(true);
        
        JLabel label = new JLabel("All Accounts");
        label.setFont(new Font("Osward", Font.BOLD, 30));
        label.setBounds(330, 10, 200, 40);
        add(label);
        
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 60, 700, 400);
        add(scrollPane);
        
        try{
             Conn c = new Conn();
            Connection connection = c.getConnection();
            String query = "SELECT * FROM customer";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet resultSet = pstmt.executeQuery();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Add column names to the model
            for (int i = 1; i <= columnCount; i++) {
                model.addColumn(metaData.getColumnName(i));
            }

            // Add data rows to the model
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                model.addRow(row);
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String args[]){
        new ViewAllAccount();
    }
}
