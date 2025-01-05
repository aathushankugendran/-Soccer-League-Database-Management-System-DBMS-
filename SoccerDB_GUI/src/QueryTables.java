import javax.swing.*;
import java.awt.*;

public class QueryTables extends JPanel {
    private DatabaseConnection dbConnection;

    public QueryTables(MainGUI mainGUI) {
        this.dbConnection = DatabaseConnection.getInstance(); // Use the singleton instance
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Advanced Queries", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        //Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1, 10, 10)); // 1 column, multiple rows with spacing

        // Button to show League Table
        JButton leagueTableButton = new JButton("Show League Table");
        leagueTableButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        String queryLeagueTable = "SELECT " +
                                    "t.teamname AS \"Team\", " +
                                    "SUM(CASE " +
                                    "WHEN t.teamid = g.hometeam AND g.homegoals > g.awaygoals THEN 3 " +
                                    "WHEN t.teamid = g.awayteam AND g.awaygoals > g.homegoals THEN 3 " +
                                    "WHEN (t.teamid = g.hometeam OR t.teamid = g.awayteam) " +
                                    "AND g.homegoals = g.awaygoals THEN 1 " +
                                    "ELSE 0 " +
                                    "END) AS \"Points\", " +
                                    "COUNT(CASE " +
                                    "WHEN t.teamid = g.hometeam OR t.teamid = g.awayteam THEN 1 " +
                                    "END) AS \"Games Played\", " +
                                    "COUNT(CASE " +
                                    "WHEN t.teamid = g.hometeam AND g.homegoals > g.awaygoals THEN 1 " +
                                    "WHEN t.teamid = g.awayteam AND g.awaygoals > g.homegoals THEN 1 " +
                                    "END) AS \"Wins\", " +
                                    "COUNT(CASE " +
                                    "WHEN (t.teamid = g.hometeam OR t.teamid = g.awayteam) " +
                                    "AND g.homegoals = g.awaygoals THEN 1 " +
                                    "END) AS \"Draws\", " +
                                    "COUNT(CASE " +
                                    "WHEN t.teamid = g.hometeam AND g.homegoals < g.awaygoals THEN 1 " +
                                    "WHEN t.teamid = g.awayteam AND g.awaygoals < g.homegoals THEN 1 " +
                                    "END) AS \"Losses\", " +
                                    "SUM(CASE " +
                                    "WHEN t.teamid = g.hometeam THEN g.homegoals " +
                                    "WHEN t.teamid = g.awayteam THEN g.awaygoals " +
                                    "END) AS \"Goals Scored\", " +
                                    "SUM(CASE " +
                                    "WHEN t.teamid = g.hometeam THEN g.awaygoals " +
                                    "WHEN t.teamid = g.awayteam THEN g.homegoals " +
                                    "END) AS \"Goals Conceded\", " +
                                    "(SUM(CASE " +
                                    "WHEN t.teamid = g.hometeam THEN g.homegoals " +
                                    "WHEN t.teamid = g.awayteam THEN g.awaygoals " +
                                    "END) - " +
                                    "SUM(CASE " +
                                    "WHEN t.teamid = g.hometeam THEN g.awaygoals " +
                                    "WHEN t.teamid = g.awayteam THEN g.homegoals " +
                                    "END)) AS \"Goal Difference\" " +
                                    "FROM GAME g " +
                                    "JOIN TEAM t ON (t.teamid = g.hometeam OR t.teamid = g.awayteam) " +
                                    "WHERE EXISTS ( " +
                                    "SELECT 1 " +
                                    "FROM TEAM t2 " +
                                    "WHERE t2.teamid = t.teamid " +
                                    "AND t2.teamid > '0' " +
                                    ") " +
                                    "GROUP BY t.teamname " +
                                    "ORDER BY \"Points\" DESC, \"Wins\" DESC, \"Goal Difference\" DESC"
                                    ;
        MainGUI.executeButtonActionEvent(leagueTableButton, dbConnection, queryLeagueTable);
        buttonPanel.add(leagueTableButton);

        // Button to show players with most goals and assists
        JButton mostGoalsAndAssistsButton = new JButton("Players with Most Goals and Assists");
        mostGoalsAndAssistsButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        String queryGoalsAssists = "SELECT p.playername AS \"Player\", p.goals AS \"Goals\", p.assists AS \"Assists\" " +
                                    "FROM player p " +
                                    "WHERE EXISTS ( " +
                                    "SELECT 1 " +
                                    "FROM goal g " +
                                    "WHERE g.scorer = p.playerid " +
                                    "GROUP BY p.playerid " +
                                    "HAVING COUNT(*) > 0 " +
                                    ") " +
                                    "OR EXISTS ( " +
                                    "SELECT 1 " +
                                    "FROM goal g " +
                                    "WHERE g.assister = p.playerid " +
                                    "GROUP BY p.playerid " +
                                    "HAVING COUNT(*) > 0 " +
                                    ") " +
                                    "ORDER BY p.goals DESC, p.assists DESC"
                                    ;
        MainGUI.executeButtonActionEvent(mostGoalsAndAssistsButton, dbConnection, queryGoalsAssists);
        buttonPanel.add(mostGoalsAndAssistsButton);

        // Button to show all participants in a league (Players and Coaches)
        JButton participantsButton = new JButton("Show League Participants");
        participantsButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        String queryParticipants = "SELECT p.playername AS \"Name\", " +
                                    "t1.teamname AS Team, " +
                                    "'player' AS Role " +
                                    "FROM PLAYER p " +
                                    "JOIN TEAM t1 ON p.teamid = t1.teamid " +
                                    "UNION ALL " +
                                    "SELECT c.coachname, " +
                                    "t2.teamname, " +
                                    "'coach' AS Role " +
                                    "FROM COACH c " +
                                    "JOIN TEAM t2 ON c.teamid = t2.teamid " +
                                    "ORDER BY Team, Role"
                                    ;
        MainGUI.executeButtonActionEvent(participantsButton, dbConnection, queryParticipants);
        buttonPanel.add(participantsButton);

        // Button to show games with attendance above average
        JButton gamesAboveAverageButton = new JButton("Games with Above Average Attendance");
        gamesAboveAverageButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        String queryGamesAboveAvg = "SELECT m.gameid AS game_id, " +
                                    "t1.teamname AS \"Home Team\", " +
                                    "t2.teamname AS \"Away Team\", " +
                                    "s.stadiumname AS \"Stadium\", " +
                                    "m.attendance " +
                                    "FROM GAME m " +
                                    "JOIN TEAM t1 ON m.hometeam = t1.teamid " +
                                    "JOIN TEAM t2 ON m.awayteam = t2.teamid " +
                                    "JOIN STADIUM s ON m.stadiumid = s.stadiumid " +
                                    "MINUS " +
                                    "SELECT m.gameid AS game_id, " +
                                    "t1.teamname AS \"Home Team\", " +
                                    "t2.teamname AS \"Away Team\", " +
                                    "s.stadiumname AS \"Stadium\", " +
                                    "m.attendance " +
                                    "FROM GAME m " +
                                    "JOIN TEAM t1 ON m.hometeam = t1.teamid " +
                                    "JOIN TEAM t2 ON m.awayteam = t2.teamid " +
                                    "JOIN STADIUM s ON m.stadiumid = s.stadiumid " +
                                    "WHERE (m.attendance - s.avgattendance <= 0)"
                                    ;
        MainGUI.executeButtonActionEvent(gamesAboveAverageButton, dbConnection, queryGamesAboveAvg);
        buttonPanel.add(gamesAboveAverageButton);

        // Button to show most crucial players
        JButton crucialPlayersButton = new JButton("Most Crucial Players");
        crucialPlayersButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        String queryCrucialPlayers = "SELECT pc.playername AS \"Player\", " +
                                        "t.teamname AS \"Team\", " +
                                        "pc.goals + pc.assists AS \"Goal contributions\", " +
                                        "tg.total_team_goals, " +
                                        "ROUND((pc.goals + pc.assists) / tg.total_team_goals * 100, 2) AS contribution_percentage " +
                                        "FROM ( " +
                                        "    SELECT p.playerid, p.playername, p.teamid, " +
                                        "           SUM(CASE WHEN g.scorer = p.playerid THEN 1 ELSE 0 END) AS goals, " +
                                        "           SUM(CASE WHEN g.assister = p.playerid THEN 1 ELSE 0 END) AS assists " +
                                        "    FROM player p " +
                                        "    LEFT JOIN goal g ON p.playerid = g.scorer OR p.playerid = g.assister " +
                                        "    GROUP BY p.playerid, p.playername, p.teamid " +
                                        "    HAVING p.teamid > '0' " +
                                        ") pc " +
                                        "JOIN ( " +
                                        "    SELECT p.teamid, SUM(CASE WHEN g.scorer IS NOT NULL THEN 1 ELSE 0 END) AS total_team_goals " +
                                        "    FROM goal g " +
                                        "    JOIN player p ON g.scorer = p.playerid " +
                                        "    GROUP BY p.teamid " +
                                        "    HAVING p.teamid > '0' " +
                                        ") tg ON pc.teamid = tg.teamid " +
                                        "JOIN team t ON pc.teamid = t.teamid " +
                                        "WHERE tg.total_team_goals > 0 " +
                                        "ORDER BY contribution_percentage DESC"
                                        ;
        MainGUI.executeButtonActionEvent(crucialPlayersButton, dbConnection, queryCrucialPlayers);
        buttonPanel.add(crucialPlayersButton);

        // Button to show games with more than 3 goals
        JButton gamesMoreThan3GoalsButton = new JButton("Games with More Than 3 Goals");
        gamesMoreThan3GoalsButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        String queryGamesMoreThan3Goals = "SELECT t1.teamname AS \"Home Team\", " +
                                            "t2.teamname AS \"Away Team\", " +
                                            "s.score " +
                                            "FROM score s " +
                                            "JOIN game g ON s.gameid = g.gameid " +
                                            "JOIN team t1 ON g.hometeam = t1.teamid " +
                                            "JOIN team t2 ON g.awayteam = t2.teamid " +
                                            "WHERE EXISTS ( " +
                                            "    SELECT 1 " +
                                            "    FROM game g2 " +
                                            "    WHERE g2.gameid = g.gameid " +
                                            "    AND (g2.homegoals + g2.awaygoals) > 3 " +
                                            ") " +
                                            "GROUP BY t1.teamname, t2.teamname, s.score " +
                                            "HAVING (TO_NUMBER(SUBSTR(s.score, 1, INSTR(s.score, '-') - 1)) + " +
                                            "         TO_NUMBER(SUBSTR(s.score, INSTR(s.score, '-') + 1))) > 3"
                                            ;
        MainGUI.executeButtonActionEvent(gamesMoreThan3GoalsButton, dbConnection, queryGamesMoreThan3Goals);
        buttonPanel.add(gamesMoreThan3GoalsButton);

        // Adding all buttons to the center
        add(buttonPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        backButton.addActionListener(e -> mainGUI.showMainMenu());
        add(backButton, BorderLayout.SOUTH);
    }
}
