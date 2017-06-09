CREATE TABLE Workers(ID NUMBER NOT NULL PRIMARY KEY, -- no explicit constraint name
Name VARCHAR2(100) NOT NULL,
CONSTRAINT UNQ_Workers UNIQUE(Name),
Created_At DATE DEFAULT SYSDATE NOT NULL,
Created_At_Str AS (to_char(Created_At, 'yyyy-mm-dd hh24:mi:ss')) -- for easy display in SQL Developer
);

CREATE INDEX IDX_Workers ON Workers(Created_At);

CREATE TABLE Tasks(ID NUMBER NOT NULL,
CONSTRAINT PK_Tasks PRIMARY KEY(ID),
Worker_ID NUMBER NOT NULL,
CONSTRAINT FK_Tasks_Workers FOREIGN KEY(Worker_ID) REFERENCES Workers(ID),
Created_At DATE DEFAULT SYSDATE NOT NULL,
Created_At_Str AS (to_char(Created_At, 'yyyy-mm-dd hh24:mi:ss')),-- for easy display in SQL Developer
Completed_At DATE NULL,
Completed_At_Str AS (to_char(Completed_At, 'yyyy-mm-dd hh24:mi:ss')),-- for easy display in SQL Developer
CONSTRAINT CHK_Tasks_Valid_Range CHECK(Created_At < Completed_At),
Is_Active CHAR(1) NOT NULL,
CONSTRAINT CHK_Is_Active CHECK (Is_Active IN ('Y', 'N') AND ((Is_Active='Y' AND Completed_At IS NULL) OR Is_Active='N'))
);

CREATE INDEX IDX_Tasks_Worker_ID ON Tasks(Worker_ID);

CREATE UNIQUE INDEX One_Active_Task_Per_Worker ON Tasks(CASE WHEN Is_Active='Y' THEN Worker_ID END);
