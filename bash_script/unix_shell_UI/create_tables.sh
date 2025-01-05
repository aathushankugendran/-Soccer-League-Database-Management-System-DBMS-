#!/bin/sh
# Optional: Uncomment the following line if you need to set the library path
# export LD_LIBRARY_PATH=/usr/lib/oracle/12.1/client64/lib

# Ensure the connection string is correctly formatted
sqlplus64 "akuganen/02152297@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.torontomu.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))" <<EOF
-- Creating Tables: tables at the top of dependancy chain created first
CREATE TABLE stadium (
    stadiumid     VARCHAR(2) PRIMARY KEY,
    stadiumname   VARCHAR(30) NOT NULL UNIQUE,
    area          VARCHAR(30) NOT NULL,
    seats         INTEGER DEFAULT 0,
    avgattendance INTEGER DEFAULT 0
);

CREATE TABLE team (
    teamid    VARCHAR(2) PRIMARY KEY,
    teamname  VARCHAR(30) NOT NULL UNIQUE,
    stadiumid VARCHAR(2)
        REFERENCES stadium ( stadiumid )
);

CREATE TABLE player (
    playerid   VARCHAR(3) PRIMARY KEY,
    playername VARCHAR(30) NOT NULL UNIQUE,
    teamid     VARCHAR(2)
        REFERENCES team (teamid),
    games      SMALLINT DEFAULT 0,
    goals      SMALLINT DEFAULT 0,
    assists    SMALLINT DEFAULT 0
);

CREATE TABLE coach (
    coachname VARCHAR(30) NOT NULL UNIQUE,
    teamid    VARCHAR(2)
        REFERENCES team ( teamid )
);

CREATE TABLE game (
    gameid      VARCHAR(2) PRIMARY KEY,
    hometeam    VARCHAR(3)
        REFERENCES team ( teamid ),
    awayteam    VARCHAR(3)
        REFERENCES team ( teamid ),
    homegoals   SMALLINT,
    awaygoals   SMALLINT,
    stadiumid VARCHAR(2)
        REFERENCES stadium ( stadiumid ),
    attendance  INTEGER DEFAULT 0,
    gamedate    DATE NOT NULL
);

CREATE TABLE score (
    gameid  VARCHAR(2)
        REFERENCES game ( gameid ),
    score  VARCHAR(5),
    winner VARCHAR(4) 
        REFERENCES team (teamid)
);

CREATE TABLE goal (
    gameid    VARCHAR(2)
        REFERENCES game ( gameid ),
    scorer   VARCHAR(3)
        REFERENCES player (playerid),
    assister VARCHAR(3)
        REFERENCES player (playerid),
    goaltype     VARCHAR(3) NOT NULL
);
exit;
EOF
