-- Interesting Advanced Queries

-- Show League Table
SELECT 
    t.teamname AS "Team",
    -- Points calculation based on wins, draws, and losses
    SUM(CASE 
            WHEN t.teamid = g.hometeam AND g.homegoals > g.awaygoals THEN 3  
            WHEN t.teamid = g.awayteam AND g.awaygoals > g.homegoals THEN 3  
            WHEN (t.teamid = g.hometeam OR t.teamid = g.awayteam) 
                 AND g.homegoals = g.awaygoals THEN 1  
            ELSE 0
        END) AS "Points",
    
    -- Games played by each team
    COUNT(CASE 
            WHEN t.teamid = g.hometeam OR t.teamid = g.awayteam THEN 1
        END) AS "Games Played",
    
    -- Wins calculation (Home and Away Wins)
    COUNT(CASE 
            WHEN t.teamid = g.hometeam AND g.homegoals > g.awaygoals THEN 1
            WHEN t.teamid = g.awayteam AND g.awaygoals > g.homegoals THEN 1
        END) AS "Wins",
    
    -- Draws calculation
    COUNT(CASE 
            WHEN (t.teamid = g.hometeam OR t.teamid = g.awayteam) 
                 AND g.homegoals = g.awaygoals THEN 1
        END) AS "Draws",
    
    -- Losses calculation
    COUNT(CASE 
            WHEN t.teamid = g.hometeam AND g.homegoals < g.awaygoals THEN 1
            WHEN t.teamid = g.awayteam AND g.awaygoals < g.homegoals THEN 1
        END) AS "Losses",
    
    -- Total goals scored by the team
    SUM(CASE 
          WHEN t.teamid = g.hometeam THEN g.homegoals
          WHEN t.teamid = g.awayteam THEN g.awaygoals
    END) AS "Goals Scored",
    
    -- Total goals conceded by the team
    SUM(CASE 
          WHEN t.teamid = g.hometeam THEN g.awaygoals
          WHEN t.teamid = g.awayteam THEN g.homegoals
    END) AS "Goals Conceded",
    
    -- Goal difference (goals scored - goals conceded)
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
WHERE EXISTS (
    SELECT 1
    FROM TEAM t2
    WHERE t2.teamid = t.teamid
    AND t2.teamid > '0' -- Ensure the teamid is greater than 0 (exclude free agents)
)
GROUP BY t.teamname
ORDER BY "Points" DESC, "Wins" DESC, "Goal Difference" DESC;

-- Show players with most goals and assists
SELECT p.playername AS "Player", p.goals AS "Goals" , p.assists AS "Assists"
FROM player p
WHERE EXISTS ( -- Checks if row exists: returns true
  SELECT 1 -- value 1 is returned if row exists (subquery)
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

-- Show all participants in a league (Players and Coaches)
SELECT p.playername AS "Name",
       t1.teamname AS Team, 
       'player' AS Role  -- Static string for players
FROM PLAYER p
JOIN TEAM t1 ON p.teamid = t1.teamid

UNION ALL  -- UNION ALL to includes all results without removing duplicates

SELECT  c.coachname,
        t2.teamname,
       'coach' AS Role  -- Static string for coaches
FROM COACH c
JOIN TEAM t2 ON c.teamid = t2.teamid
ORDER BY Team, Role;

-- Show games where attendance was above the average
SELECT m.gameid AS game_id,
       t1.teamname AS "Home Team",
       t2.teamname AS "Away Team",
       s.stadiumname AS "Stadium",
       m.attendance
       FROM GAME m
       JOIN TEAM t1 ON m.hometeam = t1.teamid
       JOIN TEAM t2 ON m.awayteam = t2.teamid
       JOIN STADIUM s ON m.stadiumid = s.stadiumid
       
MINUS

SELECT m.gameid AS game_id,
       t1.teamname AS "Home Team",
       t2.teamname AS "Away Team",
       s.stadiumname AS "Stadium",
       m.attendance
       FROM GAME m
       JOIN TEAM t1 ON m.hometeam = t1.teamid
       JOIN TEAM t2 ON m.awayteam = t2.teamid
       JOIN STADIUM s ON m.stadiumid = s.stadiumid
       WHERE (m.attendance - s.avgattendance <= 0); 

-- Show most crucial players: Goal + Assists / Team Goals
SELECT pc.playername AS "Player", 
       t.teamname AS "Team", 
       pc.goals + pc.assists AS "Goal contributions",
       tg.total_team_goals, 
       ROUND((pc.goals + pc.assists) / tg.total_team_goals * 100, 2) AS contribution_percentage
FROM (
    -- Calculating each player's contributions (goals + assists)
    SELECT p.playerid, p.playername, p.teamid,
           SUM(CASE WHEN g.scorer = p.playerid THEN 1 ELSE 0 END) AS goals,
           SUM(CASE WHEN g.assister = p.playerid THEN 1 ELSE 0 END) AS assists
    FROM player p
    LEFT JOIN goal g ON p.playerid = g.scorer OR p.playerid = g.assister
    GROUP BY p.playerid, p.playername, p.teamid
    HAVING p.teamid > '0' -- Do not include free-agents
) pc -- Subquery alias: "pc" = player contribution
-- Calculating total number of goals scored by each team
JOIN (
    SELECT p.teamid, SUM(CASE WHEN g.scorer IS NOT NULL THEN 1 ELSE 0 END) AS total_team_goals
    FROM goal g
    JOIN player p ON g.scorer = p.playerid  -- Join to get the player's team for each goal
    GROUP BY p.teamid
    HAVING p.teamid > '0' -- Do not include free-agents
) tg ON pc.teamid = tg.teamid -- Subquery alias: "tg" = team goals
-- Join with the team table to get team names
JOIN team t ON pc.teamid = t.teamid
WHERE tg.total_team_goals > 0  -- Exclude teams with no goals
ORDER BY contribution_percentage DESC;

-- Find all games with more than 3 goals
SELECT t1.teamname AS "Home Team",
       t2.teamname AS "Away Team",
       s.score
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
-- HAVING clause checks if score is more than 3 goals
-- INSTR returns the instance of a char in a sting: like charAT()