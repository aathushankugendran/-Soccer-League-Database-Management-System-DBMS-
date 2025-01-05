-- Simple Queries for Tables

-- STADIUM: Show all stadiums with 50000+ Capacity
SELECT stadiumname AS "Stadium Name", seats AS "Capacity" 
    -- retrieves a table with the 2 columns specified above
    -- AS gives a name to each column in the queried table
    FROM STADIUM
    WHERE seats > 50000 -- condtion: must have more than 50000 seats
    ORDER BY seats DESC; -- order: by descending seats number (most seats is at top)

-- TEAM: Select all teams and show their stadiums   
SELECT t.teamname AS "Team", s.stadiumname AS "Home Stadium"
    FROM TEAM t -- alias 't' used to clarify where attributes belong
    JOIN STADIUM s ON t.stadiumid = s.stadiumid;
    -- join two columns from different tables based on shared foreign key
    
-- PLAYER: Show the top goal-scorers
SELECT playername AS "Player", goals AS "Goals"
    FROM PLAYER
    WHERE goals > 0 -- players with at least 1 goal
    ORDER BY goals DESC;
    
-- COACH: Show coach and the team they manage
SELECT co.coachname AS "Coach", t.teamname AS "Team"
    FROM COACH co
    JOIN TEAM t ON co.teamid = t.teamid;
    
-- GAME: Show the teams with the most goals at home
SELECT t.teamname AS "Team", SUM(homegoals) AS "Total Home Goals"
    FROM GAME g
    JOIN TEAM t ON g.hometeam = t.teamid
    GROUP BY t.teamname 
    -- makes sure homegoals are grouped by teamname
    -- otherwise SUM(homegoals) returns the total homegoals for every team
    ORDER BY "Total Home Goals" DESC;
    
-- GOAL: Show the total number of goals scored in the league    
SELECT COUNT(*) AS "Totol League Goals" FROM GOAL;
    
-- SCORE: Show all the winning results of Toronto FC
SELECT s.gameid AS "Matchday", s.score AS "Result", t.teamname AS "Team"
    FROM SCORE s
    JOIN TEAM t ON s.winner = t.teamid
    WHERE t.teamname = 'Toronto FC';
    
SELECT global_name FROM global_name;

SELECT playerid, playername FROM PLAYER WHERE playerid = 1;
    
