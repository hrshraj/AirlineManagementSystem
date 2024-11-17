
package airlinemanagementsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class FlightInfo extends JFrame {

    public FlightInfo() {
        getContentPane().setBackground(Color.GRAY);
        setLayout(null);

        JTable table = new JTable();

        try {
            Conn conn = new Conn();
            ResultSet rs = conn.s.executeQuery("select * from flight");

            // Get metadata to fetch column names
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // Fetch column names
            Vector<String> columnNames = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(rsmd.getColumnName(i));
            }

            // Fetch row data
            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getObject(i));
                }
                data.add(row);
            }

            // Set data to the table model
            DefaultTableModel model = new DefaultTableModel(data, columnNames);
            table.setModel(model);

        } catch (Exception e) {
            e.printStackTrace();
        }

        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 0, 800, 500);
        add(jsp);

        setSize(800, 500);
        setLocation(350, 150);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FlightInfo();
    }
}
