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
    stadiumid VARCHAR(3)
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
    goalid    VARCHAR(2) PRIMARY KEY,
    gameid    VARCHAR(2)
        REFERENCES game ( gameid ),
    scorer   VARCHAR(3)
        REFERENCES player (playerid),
    assister VARCHAR(3)
        REFERENCES player (playerid),
    goaltype     VARCHAR(3) NOT NULL
);