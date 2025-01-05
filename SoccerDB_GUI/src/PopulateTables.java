import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class PopulateTables extends JPanel {
    private final DatabaseConnection dbConnection;

    public PopulateTables(MainGUI mainGUI) {
        this.dbConnection = DatabaseConnection.getInstance(); // Use the singleton instance
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Populate Tables", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JButton executeButton = new JButton("Execute Populate Tables (Default)");
        executeButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        executeButton.addActionListener(e -> {
            boolean allSuccessful = true;
            StringBuilder errors = new StringBuilder();

            try {
                // Get the current connection object from dbConnection
                Connection conn = dbConnection.getConnection();

                try {
                    // Populating Stadium Table
                    dbConnection.executeUpdate("INSERT INTO STADIUM (STADIUMID, STADIUMNAME, AREA, SEATS) VALUES ('00', 'Free-Agency', 'N/A', 0)"); 
                    dbConnection.executeUpdate("INSERT INTO STADIUM (STADIUMID, STADIUMNAME, AREA, SEATS) VALUES ('01', 'BMO Field', 'Toronto, ON', 40000)"); 
                    dbConnection.executeUpdate("INSERT INTO STADIUM (STADIUMID, STADIUMNAME, AREA, SEATS) VALUES ('02', 'Stade Olympique', 'Montreal, QC', 61004)"); 
                    dbConnection.executeUpdate("INSERT INTO STADIUM (STADIUMID, STADIUMNAME, AREA, SEATS) VALUES ('03', 'Commonwealth Stadium', 'Edmonton, AB', 56302)"); 
                    dbConnection.executeUpdate("INSERT INTO STADIUM (STADIUMID, STADIUMNAME, AREA, SEATS) VALUES ('04', 'BC Place', 'Vancouver, BC', 54320)"); 
                    dbConnection.executeUpdate("INSERT INTO STADIUM (STADIUMID, STADIUMNAME, AREA, SEATS) VALUES ('05', 'McMahon Stadium', 'Calgary, AB', 37317)");  
                    System.out.println("Successfully inserted into STADDIUM");       
                    
                    // Populating Team Table
                    dbConnection.executeUpdate("INSERT INTO TEAM (TEAMID, TEAMNAME, STADIUMID) VALUES ('FA', 'Free-Agents', '00')");
                    dbConnection.executeUpdate("INSERT INTO TEAM (TEAMID, TEAMNAME, STADIUMID) VALUES ('01', 'Toronto FC', '01')");
                    dbConnection.executeUpdate("INSERT INTO TEAM (TEAMID, TEAMNAME, STADIUMID) VALUES ('02', 'Strikers United', '02')");
                    dbConnection.executeUpdate("INSERT INTO TEAM (TEAMID, TEAMNAME, STADIUMID) VALUES ('03', 'Phoenix FC', '03')");
                    dbConnection.executeUpdate("INSERT INTO TEAM (TEAMID, TEAMNAME, STADIUMID) VALUES ('04', 'Red Raptors FC', '04')");
                    System.out.println("Successfully inserted into TEAM");
                    
                    // Populating Player Table
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('01', 'Lionel Messi', '01')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('02', 'Cristiano Ronaldo', '01')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('03', 'Kylian Mbappe', '01')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('04', 'Kevin De Bruyne', '01')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('05', 'Virgil van Dijk', '01')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('06', 'Mohamed Salah', '01')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('07', 'Sergio Ramos', '01')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('08', 'Robert Lewandowski', '01')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('09', 'Neymar Jr', '01')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('10', 'Luka Modric', '01')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('11', 'Erling Haaland', '01')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('12', 'Karim Benzema', '02')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('13', 'Harry Kane', '02')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('14', 'Paul Pogba', '02')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('15', 'Toni Kroos', '02')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('16', 'Romelu Lukaku', '02')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('17', 'Jadon Sancho', '02')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('18', 'Gareth Bale', '02')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('19', 'Eden Hazard', '02')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('20', 'Alisson Becker', '02')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('21', 'Gerard Pique', '02')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('22', 'Thiago Silva', '02')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('23', 'Luis Suarez', '03')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('24', 'Raheem Sterling', '03')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('25', 'Phil Foden', '03')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('26', 'Joshua Kimmich', '03')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('27', 'Manuel Neuer', '03')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('28', 'Leroy Sane', '03')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('29', 'Riyad Mahrez', '03')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('30', 'Pierre-Emerick Aubameyang', '03')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('31', 'Aymeric Laporte', '03')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('32', 'Andrew Robertson', '03')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('33', 'Pele', '03')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('34', 'Bruno Fernandes', '04')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('35', 'Joao Felix', '04')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('36', 'Sadio Mane', '04')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('37', 'Trent Alexander-Arnold', '04')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('38', 'Marcus Rashford', '04')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('39', 'Mason Mount', '04')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('40', 'Declan Rice', '04')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('41', 'Diego Maradona', '04')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('42', 'Kalidou Koulibaly', '04')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('43', 'Paulo Dybala', '04')");
                    dbConnection.executeUpdate("INSERT INTO PLAYER (PLAYERID, PLAYERNAME, TEAMID) VALUES ('44', 'Zlatan Ibrahimovic', '04')");
                    System.out.println("Successfully inserted into PLAYER");
  
                    // Populating Coach Table
                    dbConnection.executeUpdate("INSERT INTO COACH (COACHNAME,TEAMID) VALUES ('J.Mourinho', '01')");
                    dbConnection.executeUpdate("INSERT INTO COACH (COACHNAME,TEAMID) VALUES ('E.Kamalachandran', '04')");
                    dbConnection.executeUpdate("INSERT INTO COACH (COACHNAME,TEAMID) VALUES ('H.BOB', '02')");
                    dbConnection.executeUpdate("INSERT INTO COACH (COACHNAME,TEAMID) VALUES ('J.JOE', '03')");
                    System.out.println("Successfully inserted into COACH");

                    // Populating Game Table
                    dbConnection.executeUpdate("INSERT INTO GAME (GAMEID, HOMETEAM, AWAYTEAM, HOMEGOALS, AWAYGOALS, STADIUMID, ATTENDANCE, GAMEDATE) VALUES ('01', '01', '02', 3, 0, '01', 36232, TO_DATE('2024-10-12', 'YYYY-MM-DD'))");
                    dbConnection.executeUpdate("INSERT INTO GAME (GAMEID, HOMETEAM, AWAYTEAM, HOMEGOALS, AWAYGOALS, STADIUMID, ATTENDANCE, GAMEDATE) VALUES ('02', '03', '04', 1, 2, '03', 46732, TO_DATE('2024-10-13', 'YYYY-MM-DD'))");
                    dbConnection.executeUpdate("INSERT INTO GAME (GAMEID, HOMETEAM, AWAYTEAM, HOMEGOALS, AWAYGOALS, STADIUMID, ATTENDANCE, GAMEDATE) VALUES ('03', '02', '03', 2, 2, '02', 54337, TO_DATE('2024-10-19', 'YYYY-MM-DD'))");
                    dbConnection.executeUpdate("INSERT INTO GAME (GAMEID, HOMETEAM, AWAYTEAM, HOMEGOALS, AWAYGOALS, STADIUMID, ATTENDANCE, GAMEDATE) VALUES ('04', '04', '01', 0, 1, '04', 37234, TO_DATE('2024-10-20', 'YYYY-MM-DD'))");
                    dbConnection.executeUpdate("INSERT INTO GAME (GAMEID, HOMETEAM, AWAYTEAM, HOMEGOALS, AWAYGOALS, STADIUMID, ATTENDANCE, GAMEDATE) VALUES ('05', '01', '03', 0, 0, '01', 38765, TO_DATE('2024-10-26', 'YYYY-MM-DD'))");
                    dbConnection.executeUpdate("INSERT INTO GAME (GAMEID, HOMETEAM, AWAYTEAM, HOMEGOALS, AWAYGOALS, STADIUMID, ATTENDANCE, GAMEDATE) VALUES ('06', '02', '04', 1, 0, '02', 57283, TO_DATE('2024-10-27', 'YYYY-MM-DD'))");
                    dbConnection.executeUpdate("INSERT INTO GAME (GAMEID, HOMETEAM, AWAYTEAM, HOMEGOALS, AWAYGOALS, STADIUMID, ATTENDANCE, GAMEDATE) VALUES ('07', '03', '01', 2, 3, '03', 52352, TO_DATE('2024-11-02', 'YYYY-MM-DD'))");
                    dbConnection.executeUpdate("INSERT INTO GAME (GAMEID, HOMETEAM, AWAYTEAM, HOMEGOALS, AWAYGOALS, STADIUMID, ATTENDANCE, GAMEDATE) VALUES ('08', '04', '02', 1, 0, '04', 35627, TO_DATE('2024-11-03', 'YYYY-MM-DD'))");
                    dbConnection.executeUpdate("INSERT INTO GAME (GAMEID, HOMETEAM, AWAYTEAM, HOMEGOALS, AWAYGOALS, STADIUMID, ATTENDANCE, GAMEDATE) VALUES ('09', '03', '02', 1, 2, '03', 41738, TO_DATE('2024-11-09', 'YYYY-MM-DD'))");
                    dbConnection.executeUpdate("INSERT INTO GAME (GAMEID, HOMETEAM, AWAYTEAM, HOMEGOALS, AWAYGOALS, STADIUMID, ATTENDANCE, GAMEDATE) VALUES ('10', '01', '04', 0, 2, '01', 39875, TO_DATE('2024-11-10', 'YYYY-MM-DD'))");
                    dbConnection.executeUpdate("INSERT INTO GAME (GAMEID, HOMETEAM, AWAYTEAM, HOMEGOALS, AWAYGOALS, STADIUMID, ATTENDANCE, GAMEDATE) VALUES ('11', '02', '01', 1, 2, '02', 60125, TO_DATE('2024-11-16', 'YYYY-MM-DD'))");
                    dbConnection.executeUpdate("INSERT INTO GAME (GAMEID, HOMETEAM, AWAYTEAM, HOMEGOALS, AWAYGOALS, STADIUMID, ATTENDANCE, GAMEDATE) VALUES ('12', '04', '03', 1, 0, '04', 36783, TO_DATE('2024-11-17', 'YYYY-MM-DD'))");
                    System.out.println("Successfully inserted into GAME");

                    // Populating GOAL table
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('01', '01', '01', '04', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('02', '01', '01', '03', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('03', '01', '02', '01', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('04', '02', '23', '24', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('05', '02', '35', '34', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('06', '02', '36', '37', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('07', '03', '12', '14', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('08', '03', '13', '15', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('09', '03', '23', '25', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('10', '03', '28', '26', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('11', '04', '01', '04', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('12', '06', '19', '14', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('13', '07', '30', '29', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('14', '07', '30', '25', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('15', '07', '01', '06', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('16', '07', '01', '09', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('17', '07', '02', '01', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('18', '08', '44', '40', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('19', '09', '13', '17', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('20', '09', '18', '15', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('21', '09', '23', '32', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('22', '10', '43', '41', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('23', '10', '35', '34', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('24', '11', '12', '14', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('25', '11', '11', '01', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('26', '11', '02', '01', 'G')");
                    dbConnection.executeUpdate("INSERT INTO GOAL (GOALID, GAMEID, SCORER, ASSISTER, GOALTYPE) VALUES ('27', '12', '34', '35', 'G')");
                    System.out.println("Successfully inserted into GAME");
                                  
                    // Populating Score table
                    dbConnection.executeUpdate("INSERT INTO SCORE (GAMEID, SCORE, WINNER) VALUES ('01', '3-0', '01')");
                    dbConnection.executeUpdate("INSERT INTO SCORE (GAMEID, SCORE, WINNER) VALUES ('02', '1-2', '04')");
                    dbConnection.executeUpdate("INSERT INTO SCORE (GAMEID, SCORE, WINNER) VALUES ('03', '2-2', NULL)");
                    dbConnection.executeUpdate("INSERT INTO SCORE (GAMEID, SCORE, WINNER) VALUES ('04', '0-1', '01')");
                    dbConnection.executeUpdate("INSERT INTO SCORE (GAMEID, SCORE, WINNER) VALUES ('05', '0-0', NULL)");
                    dbConnection.executeUpdate("INSERT INTO SCORE (GAMEID, SCORE, WINNER) VALUES ('06', '1-0', '02')");
                    dbConnection.executeUpdate("INSERT INTO SCORE (GAMEID, SCORE, WINNER) VALUES ('07', '2-3', '01')");
                    dbConnection.executeUpdate("INSERT INTO SCORE (GAMEID, SCORE, WINNER) VALUES ('08', '1-0', '04')");
                    dbConnection.executeUpdate("INSERT INTO SCORE (GAMEID, SCORE, WINNER) VALUES ('09', '1-2', '02')");
                    dbConnection.executeUpdate("INSERT INTO SCORE (GAMEID, SCORE, WINNER) VALUES ('10', '0-2', '04')");
                    dbConnection.executeUpdate("INSERT INTO SCORE (GAMEID, SCORE, WINNER) VALUES ('11', '1-2', '01')");
                    dbConnection.executeUpdate("INSERT INTO SCORE (GAMEID, SCORE, WINNER) VALUES ('12', '1-0', '04')");
                    System.out.println("Successfully inserted into SCORE");

                    // Updating player appearances
                    dbConnection.executeUpdate("UPDATE PLAYER SET GAMES = 6 WHERE teamid != 'FA'");

                    // Updating player goals
                    dbConnection.executeUpdate("UPDATE PLAYER SET GOALS = (SELECT COUNT(*) FROM GOAL WHERE playerid = scorer)");

                    // Updating player assists
                    dbConnection.executeUpdate("UPDATE PLAYER SET ASSISTS = (SELECT COUNT(*) FROM GOAL WHERE playerid = assister)");

                    // Updating average attendance for each stadium
                    dbConnection.executeUpdate("UPDATE STADIUM s SET AVGATTENDANCE = (SELECT ROUND(AVG(m.attendance), 2) FROM GAME m WHERE m.stadiumid = s.stadiumid GROUP BY m.stadiumid)");

                    conn.commit();
                    JOptionPane.showMessageDialog(this, "Tables populated successfully!");
                } catch (SQLException ex) {
                    allSuccessful = false;
                    conn.rollback(); // Rollback the transaction in case of error
                    errors.append("Failed to populate table: ").append(ex.getMessage()).append("\n");
                    JOptionPane.showMessageDialog(this, "Some tables could not be populated:\n" + errors, "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
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

