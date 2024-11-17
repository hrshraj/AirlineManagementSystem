
package airlinemanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Vector;

public class JourneyDetails extends JFrame implements ActionListener {
    JTable table;
    JTextField pnr;
    JButton show;

    public JourneyDetails() {
        // Frame settings
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setLayout(null);

        // PNR label
        JLabel lblpnr = new JLabel("PNR Details");
        lblpnr.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblpnr.setBounds(50, 50, 100, 25);
        add(lblpnr);

        // PNR input field
        pnr = new JTextField();
        pnr.setBounds(160, 50, 120, 25);
        add(pnr);

        // Show Details button
        show = new JButton("Show Details");
        show.setBackground(Color.BLACK);
        show.setForeground(Color.WHITE);
        show.setBounds(290, 50, 120, 25);
        show.addActionListener(this);
        add(show);

        // Table to display data
        table = new JTable();
        JScrollPane jsp = new JScrollPane(table);
        jsp.setBounds(0, 100, 800, 150);
        jsp.setBackground(Color.LIGHT_GRAY);
        add(jsp);

        // Frame size and visibility
//        setSize(800, 600);
//        setLocation(400, 150);
        setSize(800, 500);
        setLocation(350, 150);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            Conn conn = new Conn();
            String query = "SELECT * FROM reservation WHERE PNR = '" + pnr.getText() + "'";
            ResultSet rs = conn.s.executeQuery(query);

            // Get metadata to dynamically build table columns
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // Extract column names
            Vector<String> columnNames = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(rsmd.getColumnName(i));
            }

            // Extract data rows
            Vector<Vector<String>> data = new Vector<>();
            while (rs.next()) {
                Vector<String> row = new Vector<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }

            if (data.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No Information Found");
                return;
            }

            // Set the data and column names to the JTable
            table.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new JourneyDetails();
    }
}
