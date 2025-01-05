-- Views + Advanced Queries
CREATE VIEW top_performers AS
SELECT t.teamname AS "Team", p.playername AS "Player",
          p.goals AS "Goals", p.assists AS "Assists",
          (p.goals + p.assists) AS "Total Contributions"
    -- "Total Contributions" is a derived column from PLAYER table
    FROM PLAYER p
    JOIN TEAM t ON p.teamid = t.teamid
    WHERE p.goals > 0 OR p.assists > 0
    ORDER BY "Total Contributions" DESC, p.goals DESC, p.assists DESC;
    -- if multiple players has the same amount of contributions, 
    -- they are then ordered by goals, however if their goals are the same,
    -- they are finally ordered by assists

--DROP VIEW top_performers;

CREATE VIEW league_table AS
SELECT 
    t.teamname AS "Team",
    SUM(CASE -- Calculating points (CASE: WHEN: THEN is like 'if statement')
            WHEN t.teamid = g.hometeam AND g.homegoals > g.awaygoals THEN 3  
            -- Home win: More home goals than away goals -> Add 3 points
            WHEN t.teamid = g.awayteam AND g.awaygoals > g.homegoals THEN 3  
            -- Away win: More away goals than home goals -> Add 3 points
            WHEN (t.teamid = g.hometeam OR t.teamid = g.awayteam) 
                 AND g.homegoals = g.awaygoals THEN 1  
                 -- Draw: Same amount of home and away goals -> Add 1 point
            ELSE 0  -- Loss -> Add 0 points
        END) AS "Points",
    COUNT(CASE -- Calculating number of wins, draws, and losses
            WHEN t.teamid = g.hometeam AND g.homegoals > g.awaygoals THEN 1
            -- Home win: More home goals than away goals -> Add 1 win
            WHEN t.teamid = g.awayteam AND g.awaygoals > g.homegoals THEN 1
            -- Away win: More away goals than home goals -> Add 1 win
        END) AS "Wins",
    COUNT(CASE 
            WHEN (t.teamid = g.hometeam OR t.teamid = g.awayteam) 
                 AND g.homegoals = g.awaygoals THEN 1
                 -- Draw: Same amount of home and away goals -> Add 1 draw
        END) AS "Draws",
    COUNT(CASE 
            WHEN t.teamid = g.hometeam AND g.homegoals < g.awaygoals THEN 1
            -- Home loss: More away goals than home goals -> Add 1 loss
            WHEN t.teamid = g.awayteam AND g.awaygoals < g.homegoals THEN 1
            -- Away loss: More home goals than away goals -> Add 1 loss
        END) AS "Losses",
    SUM(CASE -- Total goals scored per team: sum of home and away goals scored
          WHEN t.teamid = g.hometeam THEN g.homegoals
          -- Counting team's home goals scored
          WHEN t.teamid = g.awayteam THEN g.awaygoals
          -- Counting team's away goals scored
    END) AS "Goals Scored",
    SUM(CASE -- Total goals conceded per team: sum of home and away goals conceded
          WHEN t.teamid = g.hometeam THEN g.awaygoals
          -- Counting team's home goals conceded
          WHEN t.teamid = g.awayteam THEN g.homegoals
          -- Counting team's away goals conceded
    END) AS "Goals Conceded",
    (SUM(CASE -- Goal Difference: goals scored - goals conceded
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
    -- Team could either be home or away depending on game
    GROUP BY t.teamname
    ORDER BY "Points" DESC, "Wins" DESC, "Goal Difference" DESC;
    -- Table ordered by points, then wins, then goal difference

-- DROP VIEW league_table;    

CREATE VIEW messi_log AS
SELECT m.gameid, 
    CASE
        WHEN m.hometeam = p.teamid THEN t2.teamname
        WHEN m.awayteam = p.teamid THEN t1.teamname
        END AS "Opposition",
    s.score AS "Score"
        
    FROM GAME m
    JOIN PLAYER p ON (m.hometeam = p.teamid OR m.awayteam = p.teamid)
    JOIN SCORE s ON m.gameid = s.gameid
    JOIN TEAM t1 ON m.hometeam = t1.teamid
    JOIN TEAM t2 ON m.awayteam = t2.teamid
    WHERE p.playerid = 1; -- Select player to log based on ID

-- DROP VIEW messi_log

CREATE VIEW stadium_avgs AS
SELECT t.teamname AS "Team",
        s.stadiumname AS "Stadium",
        ROUND(AVG(m.attendance),2) AS "Attendance Avg."
        -- rounds avg attendance to 2 decimal places
FROM GAME m
JOIN STADIUM s ON m.stadiumid = s.stadiumid
JOIN TEAM t ON s.stadiumid = t.stadiumid
GROUP BY t.teamname, s.stadiumname;

-- DROP VIEW stadium_avgs;

SELECT * FROM top_performers;
SELECT * FROM league_table;
SELECT * FROM messi_log ORDER BY TO_NUMBER(gameid) ASC; 
-- need to explicit cast since gameid is stored as a string
SELECT * FROM stadium_avgs;