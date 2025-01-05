ALTER TABLE stadium MODIFY (
    seats INTEGER
);

ALTER TABLE stadium MODIFY (
    avgattendance INTEGER
);

ALTER TABLE game MODIFY (
    attendance INTEGER
);

ALTER TABLE goal ADD (
    scorer 
        REFERENCES player (playername)
);


