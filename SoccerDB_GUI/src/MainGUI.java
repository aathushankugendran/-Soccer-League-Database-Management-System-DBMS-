import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

public class MainGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainGUI() {
        setTitle("Soccer League Database");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the window on the screen

        

        // Initialize CardLayout and mainPanel
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add the main menu panel
        JPanel mainMenuPanel = createMainMenuPanel();
        mainPanel.add(mainMenuPanel, "Main Menu");
        mainPanel.setBackground(Color.BLUE);

        // Add other panels
        mainPanel.add(new CreateTables(this), "Create Tables");
        mainPanel.add(new DropTables(this), "Drop Tables");
        mainPanel.add(new PopulateTables(this), "Populate Tables");
        mainPanel.add(new ViewTables(this), "View Tables");
        mainPanel.add(new QueryTables(this), "Query Tables");
        mainPanel.add(new CustomSQL(this), "Custom SQL");

        add(mainPanel);
    }

    private JPanel createMainMenuPanel() {
        JPanel menuPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("SOCCER LEAGUE DATABASE", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        menuPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        Color LightBlue = new Color(173, 216, 230);

        JButton createButton = new JButton("Create Tables");
        JButton dropButton = new JButton("Drop Tables");
        JButton populateButton = new JButton("Populate Tables");
        JButton viewButton = new JButton("View Tables");
        JButton queryButton = new JButton("Query Tables");
        JButton SQLButton = new JButton("Custom SQL");
        JButton exitButton = new JButton("Exit Program");

        JButton[] buttons = {createButton, dropButton, populateButton, viewButton, queryButton, SQLButton, exitButton};

        for (JButton button : buttons) {
            button.setFont(new Font("Times New Roman", Font.BOLD, 20));
            button.setBackground(LightBlue);
            buttonPanel.add(button);
        }

        // Add action listeners for navigation
        createButton.addActionListener(e -> cardLayout.show(mainPanel, "Create Tables"));
        dropButton.addActionListener(e -> cardLayout.show(mainPanel, "Drop Tables"));
        populateButton.addActionListener(e -> cardLayout.show(mainPanel, "Populate Tables"));
        viewButton.addActionListener(e -> cardLayout.show(mainPanel, "View Tables"));
        queryButton.addActionListener(e -> cardLayout.show(mainPanel, "Query Tables"));
        SQLButton.addActionListener(e -> cardLayout.show(mainPanel, "Custom SQL"));
        exitButton.addActionListener(e -> System.exit(0));

        menuPanel.add(buttonPanel, BorderLayout.CENTER);
        return menuPanel;
    }

    public static void executeButtonActionEvent(JButton tableButton, DatabaseConnection databaseConnection, String query) {
        tableButton.addActionListener(actionEvent -> {
            try {
                ResultSet queryResult = databaseConnection.executeQuery(query);
                JTable queryResultTable = new JTable(buildTableModel(queryResult));
                JOptionPane.showMessageDialog(null, new JScrollPane(queryResultTable));
            }
            catch (SQLException e) {
                //JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }

    public static DefaultTableModel buildTableModel(ResultSet queryResult) throws SQLException {

        ResultSetMetaData queryMetaData = queryResult.getMetaData();
        int columnCount = queryMetaData.getColumnCount();
        Vector<String> columnNames = new Vector<>();
        Vector<Vector<Object>> queryDataVector = new Vector<>();

        for (int columnNumber = 1; columnNumber <= columnCount; columnNumber++) {
            columnNames.add(queryMetaData.getColumnName(columnNumber));
        }

        while (queryResult.next()) {
            Vector<Object> tempDataVector = new Vector<>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                tempDataVector.add(queryResult.getObject(columnIndex));
            }
            queryDataVector.add(tempDataVector);
        }
        return new DefaultTableModel(queryDataVector, columnNames);
    }


    // Method to switch back to the main menu
    public void showMainMenu() {
        cardLayout.show(mainPanel, "Main Menu");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainGUI mainGUI = new MainGUI();
            mainGUI.setVisible(true);
        });
    }
}
