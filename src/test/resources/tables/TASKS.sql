CREATE TABLE TASKS(
    ID NUMBER NOT NULL ,
    CONSTRAINT PK_TASKS PRIMARY KEY(ID),
    WORKER_ID NUMBER NOT NULL ,
    CONSTRAINT FK_TASKS_WORKERS FOREIGN KEY(WORKER_ID) REFERENCES WORKERS(ID),
    CREATED_AT DATE NOT NULL DEFAULT (SYSDATE ),
    CREATED_AT_STR VARCHAR2(75) NULL AS(TO_CHAR("CREATED_AT",'yyyy-mm-dd hh24:mi:ss')),
    COMPLETED_AT DATE NULL ,
    CONSTRAINT CHK_TASKS_VALID_RANGE CHECK(Created_At < Completed_At),
    COMPLETED_AT_STR VARCHAR2(75) NULL AS(TO_CHAR("COMPLETED_AT",'yyyy-mm-dd hh24:mi:ss')),
    IS_ACTIVE CHAR NOT NULL ,
    CONSTRAINT CHK_IS_ACTIVE CHECK(Is_Active IN ('Y', 'N') AND ((Is_Active='Y' AND Completed_At IS NULL) OR Is_Active='N'))
);

CREATE INDEX IDX_TASKS_WORKER_ID ON TASKS(WORKER_ID);

CREATE UNIQUE INDEX ONE_ACTIVE_TASK_PER_WORKER ON TASKS(CASE  WHEN "IS_ACTIVE"='Y' THEN "WORKER_ID" END );