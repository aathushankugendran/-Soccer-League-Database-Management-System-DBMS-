#!/bin/sh
# Optional: Uncomment the following line if you need to set the library path
# export LD_LIBRARY_PATH=/usr/lib/oracle/12.1/client64/lib

# Ensure the connection string is correctly formatted
sqlplus64 "akuganen/02152297@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.ryerson.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))" <<EOF

CREATE VIEW team_view AS
SELECT * FROM TEAM;

CREATE VIEW player_view AS
SELECT * FROM PLAYER;

CREATE VIEW coach_view AS
SELECT * FROM COACH;

CREATE VIEW stadium_view AS
SELECT * FROM STADIUM;

CREATE VIEW game_view AS
SELECT * FROM GAME;

CREATE VIEW goal_view AS
SELECT * FROM GOAL;

CREATE VIEW score_view AS
SELECT * FROM SCORE;

SELECT * FROM  team_view;
SELECT * FROM  player_view;
SELECT * FROM coach_view;
SELECT * FROM stadium_view;
SELECT * FROM game_view;
SELECT * FROM goal_view;
SELECT * FROM score_view;

exit;
EOF