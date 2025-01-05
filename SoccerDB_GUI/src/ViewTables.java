import javax.swing.*;
import java.awt.*;

public class ViewTables extends JPanel {
    private DatabaseConnection dbConnection;

    public ViewTables(MainGUI mainGUI) {
        this.dbConnection = DatabaseConnection.getInstance(); // Use the singleton instance        
        setLayout(new BorderLayout());
        JLabel title = new JLabel("View Tables", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        //Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 10, 10)); // 1 column, multiple rows with spacing


        // Button to show PLAYER table
        JButton viewPlayerButton = new JButton("View Players");
        viewPlayerButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        String queryPlayers = "SELECT * FROM PLAYER";
        MainGUI.executeButtonActionEvent(viewPlayerButton, dbConnection, queryPlayers);
        buttonPanel.add(viewPlayerButton);

        // Button to show COACH table
        JButton viewCoachButton = new JButton("View Coaches");
        viewCoachButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        String queryCoaches = "SELECT * FROM COACH";
        MainGUI.executeButtonActionEvent(viewCoachButton, dbConnection, queryCoaches);
        buttonPanel.add(viewCoachButton);

        // Button to show TEAM table
        JButton viewTeamButton = new JButton("View Teams");
        viewTeamButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        String queryTeams = "SELECT * FROM TEAM";
        MainGUI.executeButtonActionEvent(viewTeamButton, dbConnection, queryTeams);
        buttonPanel.add(viewTeamButton);

        // Button to show STADIUM table
        JButton viewStadiumButton = new JButton("View Stadiums");
        viewStadiumButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        String queryStadiums = "SELECT * FROM STADIUM";
        MainGUI.executeButtonActionEvent(viewStadiumButton, dbConnection, queryStadiums);
        buttonPanel.add(viewStadiumButton);

        // Button to show GAME table
        JButton viewGameButton = new JButton("View Games");
        viewGameButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        String queryGames = "SELECT * FROM GAME";
        MainGUI.executeButtonActionEvent(viewGameButton, dbConnection, queryGames);
        buttonPanel.add(viewGameButton);

        // Button to show SCORE table
        JButton viewScoreButton = new JButton("View Scores");
        viewScoreButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        String queryScores = "SELECT * FROM SCORE";
        MainGUI.executeButtonActionEvent(viewScoreButton, dbConnection, queryScores);
        buttonPanel.add(viewScoreButton);

        // Button to show GOAL table
        JButton viewGoalButton = new JButton("View Goals");
        viewGoalButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        String queryGoals = "SELECT * FROM GOAL";
        MainGUI.executeButtonActionEvent(viewGoalButton, dbConnection, queryGoals);
        buttonPanel.add(viewGoalButton);

        //Adding all buttons to the center
        add(buttonPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        backButton.addActionListener(e -> mainGUI.showMainMenu());
        add(backButton, BorderLayout.SOUTH);
    }
}
