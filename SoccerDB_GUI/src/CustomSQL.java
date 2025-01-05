import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomSQL extends JPanel {
    private final DatabaseConnection dbConnection;

    public CustomSQL(MainGUI mainGUI) {
        this.dbConnection = DatabaseConnection.getInstance(); // Use the singleton instance
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Custom SQL", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        // Panel for page elements
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 10, 10)); // 1 column, multiple rows with spacing

        // Create a JTextField for user input
        JTextField sqlText = new JTextField();
        sqlText.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        panel.add(sqlText);

        // Add the button to Update DB
        JButton executeButton = new JButton("Update Database");
        executeButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panel.add(executeButton);

        // Add the button to Update DB
        JButton queryButton = new JButton("Query Database");
        queryButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panel.add(queryButton);

        // Add a "Back" button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        panel.add(backButton);

        add(panel, BorderLayout.CENTER);

        // Action listener for the "Execute" button
        executeButton.addActionListener(e -> {
            String query = sqlText.getText().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "SQL statement cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                    // Execute non-SELECT SQL statements
                    dbConnection.executeUpdate(query);
                    JOptionPane.showMessageDialog(this, "SQL executed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Unexpected error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        queryButton.addActionListener(e -> {
            String query = sqlText.getText().trim();
            if (query.isEmpty()) {
                JOptionPane.showMessageDialog(this, "SQL statement cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!query.toLowerCase().startsWith("select")) {
                JOptionPane.showMessageDialog(this, "Only SELECT queries are allowed for querying.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                ResultSet queryResult = dbConnection.executeQuery(query);
                JTable queryResultTable = new JTable(MainGUI.buildTableModel(queryResult));
                JOptionPane.showMessageDialog(this, new JScrollPane(queryResultTable));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Unexpected error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        // Action listener for the "Back" button
        backButton.addActionListener(e -> mainGUI.showMainMenu());
    }
}
