MainMenu()
{
    while [ "$CHOICE" != "START" ]
    do
        clear
        echo "================================================================="
        echo "|                     SOCCER LEAGUE DATABASE                    |"
        echo "|             Query Menu - Select Desired Query(ies):            |"
        echo "|         <CTRL-Z Anytime to Enter Interactive CMD Prompt>       |"
        echo "-----------------------------------------------------------------"
        echo " $IS_SELECTEDM M) View Manual"
        echo " "
        echo " $IS_SELECTED1 1) View League Table"
        echo " $IS_SELECTED2 2) View Top Performing Players"
        echo " $IS_SELECTED3 3) View League Participants"
        echo " $IS_SELECTED4 4) View Matches with Above-average Attendance"
        echo " $IS_SELECTED5 5) View Matches with More Than 3 Goals"
        echo " "
        echo " $IS_SELECTEDX X) Force/Stop/Kill Oracle DB"
        echo " "
        echo " $IS_SELECTEDE E) End/Exit"
        echo "Choose: "
        read CHOICE
        if [ "$CHOICE" == "0" ]; then
            echo "Nothing Here"
            sleep 5
        elif [ "$CHOICE" == "1" ]; then
            echo "Showing League Table"
sqlplus64 "akuganen/02152297@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.torontomu.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))" <<EOF
SELECT 
    t.teamname AS "Team",
    SUM(CASE 
            WHEN t.teamid = g.hometeam AND g.homegoals > g.awaygoals THEN 3  
            WHEN t.teamid = g.awayteam AND g.awaygoals > g.homegoals THEN 3  
            WHEN (t.teamid = g.hometeam OR t.teamid = g.awayteam) 
                AND g.homegoals = g.awaygoals THEN 1  
            ELSE 0
        END) AS "Points",
    COUNT(CASE 
            WHEN t.teamid = g.hometeam OR t.teamid = g.awayteam THEN 1
        END) AS "Games Played",
    COUNT(CASE 
            WHEN t.teamid = g.hometeam AND g.homegoals > g.awaygoals THEN 1
            WHEN t.teamid = g.awayteam AND g.awaygoals > g.homegoals THEN 1
        END) AS "Wins",
    COUNT(CASE 
            WHEN (t.teamid = g.hometeam OR t.teamid = g.awayteam) 
                AND g.homegoals = g.awaygoals THEN 1
        END) AS "Draws",
    COUNT(CASE 
            WHEN t.teamid = g.hometeam AND g.homegoals < g.awaygoals THEN 1
            WHEN t.teamid = g.awayteam AND g.awaygoals < g.homegoals THEN 1
        END) AS "Losses",
    SUM(CASE 
        WHEN t.teamid = g.hometeam THEN g.homegoals
        WHEN t.teamid = g.awayteam THEN g.awaygoals
        END) AS "Goals Scored",
    SUM(CASE 
        WHEN t.teamid = g.hometeam THEN g.awaygoals
        WHEN t.teamid = g.awayteam THEN g.homegoals
    END) AS "Goals Conceded",
    (SUM(CASE 
        WHEN t.teamid = g.hometeam THEN g.homegoals
        WHEN t.teamid = g.awayteam THEN g.awaygoals
    END)
    -
    SUM(CASE
        WHEN t.teamid = g.hometeam THEN g.awaygoals
        WHEN t.teamid = g.awayteam THEN g.homegoals
    END)) AS "Goal Difference"
FROM GAME g
JOIN TEAM t ON (t.teamid = g.hometeam OR t.teamid = g.awayteam)
GROUP BY t.teamname
ORDER BY "Points" DESC, "Wins" DESC, "Goal Difference" DESC;
EOF
            while true; do
                read -rsn1 key  # Read a single character silently
                if [[ -n "$key" ]]; then
                    echo "Key pressed. Continuing..."
                    break  # Exit the loop if a key is pressed
                fi
            done
        elif [ "$CHOICE" == "2" ]; then
            echo "Showing Top Performers"
sqlplus64 "akuganen/02152297@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.torontomu.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))" <<EOF
SELECT p.playername AS "Player", p.goals AS "Goals", p.assists AS "Assists"
FROM player p
WHERE EXISTS (
    SELECT 1
    FROM goal g
    WHERE g.scorer = p.playerid
    GROUP BY p.playerid
    HAVING COUNT(*) > 0
)
OR EXISTS (
    SELECT 1
    FROM goal g
    WHERE g.assister = p.playerid
    GROUP BY p.playerid
    HAVING COUNT(*) > 0
)
ORDER BY p.goals DESC, p.assists DESC;
EOF
            while true; do
                read -rsn1 key  # Read a single character silently
                if [[ -n "$key" ]]; then
                    echo "Key pressed. Continuing..."
                    break  # Exit the loop if a key is pressed
                fi
            done
        elif [ "$CHOICE" == "3" ]; then
            echo "Showing League Participants"
sqlplus64 "akuganen/02152297@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.torontomu.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))" <<EOF
SELECT p.playername AS "Name", t1.teamname AS Team, 'player' AS Role  -- Static string for players
FROM PLAYER p
JOIN TEAM t1 ON p.teamid = t1.teamid
UNION ALL  -- UNION ALL to includes all results without removing duplicates
SELECT c.coachname, t2.teamname, 'coach' AS Role  -- Static string for coaches
FROM COACH c
JOIN TEAM t2 ON c.teamid = t2.teamid
ORDER BY Team, Role;
EOF
            while true; do
                read -rsn1 key  # Read a single character silently
                if [[ -n "$key" ]]; then
                    echo "Key pressed. Continuing..."
                    break  # Exit the loop if a key is pressed
                fi
            done
        elif [ "$CHOICE" == "4" ]; then
            echo "Showing Matches with Above-average Attendance"
sqlplus64 "akuganen/02152297@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.torontomu.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))" <<EOF
SELECT m.gameid AS game_id, t1.teamname AS "Home Team", t2.teamname AS "Away Team", s.stadiumname AS "Stadium", m.attendance
FROM GAME m
JOIN TEAM t1 ON m.hometeam = t1.teamid
JOIN TEAM t2 ON m.awayteam = t2.teamid
JOIN STADIUM s ON m.stadiumid = s.stadiumid
MINUS
SELECT m.gameid AS game_id, t1.teamname AS "Home Team", t2.teamname AS "Away Team", s.stadiumname AS "Stadium", m.attendance
FROM GAME m
JOIN TEAM t1 ON m.hometeam = t1.teamid
JOIN TEAM t2 ON m.awayteam = t2.teamid
JOIN STADIUM s ON m.stadiumid = s.stadiumid
WHERE (m.attendance - s.avgattendance <= 0);
EOF
            while true; do
                read -rsn1 key  # Read a single character silently
                if [[ -n "$key" ]]; then
                    echo "Key pressed. Continuing..."
                    break  # Exit the loop if a key is pressed
                fi
            done
        elif [ "$CHOICE" == "5" ]; then
            echo "Showing all games with more than 3 goals"
sqlplus64 "akuganen/02152297@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.torontomu.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))" <<EOF
SELECT t1.teamname AS "Home Team", t2.teamname AS "Away Team", s.score
FROM score s
JOIN game g ON s.gameid = g.gameid
JOIN team t1 ON g.hometeam = t1.teamid
JOIN team t2 ON g.awayteam = t2.teamid
WHERE EXISTS (
    SELECT 1
    FROM game g2
    WHERE g2.gameid = g.gameid
    AND (g2.homegoals + g2.awaygoals) > 3
)
GROUP BY t1.teamname, t2.teamname, s.score
HAVING (TO_NUMBER(SUBSTR(s.score, 1, INSTR(s.score, '-') - 1)) + 
        TO_NUMBER(SUBSTR(s.score, INSTR(s.score, '-') + 1))) > 3;
EOF
            while true; do
                read -rsn1 key  # Read a single character silently
                if [[ -n "$key" ]]; then
                    echo "Key pressed. Continuing..."
                    break  # Exit the loop if a key is pressed
                fi
            done
        elif [ "$CHOICE" == "X" ]; then
            echo "Stopping Oracle..."
            # Your logic for force/stopping/killing Oracle DB goes here
        elif [ "$CHOICE" == "E" ]; then
            exit
        fi
    done
}
MainMenu
