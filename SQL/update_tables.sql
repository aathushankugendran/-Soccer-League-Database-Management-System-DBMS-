-- Setting player appearances
UPDATE PLAYER
SET GAMES = 6
WHERE teamid != 'FA'; -- free agents don't play

UPDATE PLAYER
SET GOALS = (
    SELECT COUNT(*) -- all occurrences 
    FROM GOAL
    WHERE playerid = scorer
);

UPDATE PLAYER
SET ASSISTS = (
    SELECT COUNT(*)
    FROM GOAL
    WHERE playerid = assister
);

UPDATE STADIUM s
SET AVGATTENDANCE = (
    SELECT ROUND(AVG(m.attendance), 2)
    FROM GAME m
    WHERE m.stadiumid = s.stadiumid
    GROUP BY m.stadiumid
);