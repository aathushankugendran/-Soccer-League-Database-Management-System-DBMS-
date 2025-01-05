import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class CreateTables extends JPanel {
    private final DatabaseConnection dbConnection;

    public CreateTables(MainGUI mainGUI) {
        this.dbConnection = DatabaseConnection.getInstance(); // Use the singleton instance
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Create Tables", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JButton executeButton = new JButton("Execute Create Tables (Default)");
        executeButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        executeButton.addActionListener(e -> {
            boolean allSuccessful = true;
            StringBuilder errors = new StringBuilder();

            try {
                // Get the current connection object from dbConnection
                Connection conn = dbConnection.getConnection();

                try {
                    // Table creation statements
                    dbConnection.executeUpdate("CREATE TABLE stadium (" 
                        + "stadiumid     VARCHAR(2) PRIMARY KEY, "
                        + "stadiumname   VARCHAR(30) NOT NULL UNIQUE, "
                        + "area          VARCHAR(30) NOT NULL, "
                        + "seats         INTEGER DEFAULT 0, "
                        + "avgattendance INTEGER DEFAULT 0 "
                        + ")");
                    dbConnection.executeUpdate("CREATE TABLE team (" 
                        + "teamid    VARCHAR(2) PRIMARY KEY, "
                        + "teamname  VARCHAR(30) NOT NULL UNIQUE, "
                        + "stadiumid VARCHAR(2) REFERENCES stadium (stadiumid) "
                        + ")");
                    dbConnection.executeUpdate("CREATE TABLE player (" 
                        + "playerid   VARCHAR(3) PRIMARY KEY, "
                        + "playername VARCHAR(30) NOT NULL UNIQUE, "
                        + "teamid     VARCHAR(2) REFERENCES team (teamid), "
                        + "games      SMALLINT DEFAULT 0, "
                        + "goals      SMALLINT DEFAULT 0, "
                        + "assists    SMALLINT DEFAULT 0 "
                        + ")");
                    dbConnection.executeUpdate("CREATE TABLE coach (" 
                        + "coachname VARCHAR(30) NOT NULL UNIQUE, "
                        + "teamid    VARCHAR(2) REFERENCES team (teamid) "
                        + ")");
                    dbConnection.executeUpdate("CREATE TABLE game (" 
                        + "gameid      VARCHAR(2) PRIMARY KEY, "
                        + "hometeam    VARCHAR(3) REFERENCES team (teamid), "
                        + "awayteam    VARCHAR(3) REFERENCES team (teamid), "
                        + "homegoals   SMALLINT, "
                        + "awaygoals   SMALLINT, "
                        + "stadiumid   VARCHAR(3) REFERENCES stadium (stadiumid), "
                        + "attendance  INTEGER DEFAULT 0, "
                        + "gamedate    DATE NOT NULL "
                        + ")");
                    dbConnection.executeUpdate("CREATE TABLE score (" 
                        + "gameid  VARCHAR(2) REFERENCES game (gameid), "
                        + "score   VARCHAR(5), "
                        + "winner  VARCHAR(2) REFERENCES team (teamid) "
                        + ")");
                    dbConnection.executeUpdate("CREATE TABLE goal (" 
                        + "goalid    VARCHAR(2) PRIMARY KEY, "
                        + "gameid    VARCHAR(2) REFERENCES game (gameid), "
                        + "scorer    VARCHAR(3) REFERENCES player (playerid), "
                        + "assister  VARCHAR(3) REFERENCES player (playerid), "
                        + "goaltype  VARCHAR(3) NOT NULL "
                        + ")");
                    
                    // Commit the transaction if all tables are created successfully
                    conn.commit();
                    JOptionPane.showMessageDialog(this, "Tables created successfully!");

                } catch (SQLException ex) {
                    allSuccessful = false;
                    conn.rollback(); // Rollback the transaction in case of error
                    errors.append("Failed to create table: ").append(ex.getMessage()).append("\n");
                    JOptionPane.showMessageDialog(this, "Some tables could not be created:\n" + errors, "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error with database connection: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(executeButton, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        backButton.addActionListener(e -> mainGUI.showMainMenu());
        add(backButton, BorderLayout.SOUTH);
    }
}
