import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DropTables extends JPanel {
    private final DatabaseConnection dbConnection;

    public DropTables(MainGUI mainGUI) {
        this.dbConnection = DatabaseConnection.getInstance(); // Use the singleton instance
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Drop Tables", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JButton executeButton = new JButton("Execute Drop Tables (Default)");
        executeButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        executeButton.addActionListener(e -> {
            boolean allSuccessful = true;
            StringBuilder errors = new StringBuilder();

            try {
                String[] tables = { "score", "goal", "game", "coach", "player", "team", "stadium" };

                for (String table : tables) {
                    try {
                        dbConnection.executeUpdate("DROP TABLE " + table);
                    } catch (SQLException ex) {
                        allSuccessful = false;
                        errors.append("Failed to drop table ").append(table).append(": ").append(ex.getMessage()).append("\n");
                    }
                }

                if (allSuccessful) {
                    dbConnection.commit();
                    JOptionPane.showMessageDialog(this, "Tables dropped successfully!");
                } else {
                    dbConnection.rollback();
                    JOptionPane.showMessageDialog(this, "Some tables could not be dropped:\n" + errors, "Partial Success", JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Unexpected error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(executeButton, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        backButton.addActionListener(e -> mainGUI.showMainMenu());
        add(backButton, BorderLayout.SOUTH);
    }
}

