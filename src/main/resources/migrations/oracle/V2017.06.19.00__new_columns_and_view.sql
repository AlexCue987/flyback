ALTER TABLE Tasks ADD Description VARCHAR2(100) NULL;

CREATE INDEX IDX_Tasks_Description ON Tasks(Description);

CREATE VIEW Workers_Tasks
AS
SELECT
Workers.Name AS Worker_Name,
Tasks.Description AS Task_Description,
Tasks.Created_At_Str,
Tasks.Completed_At_Str,
Tasks.Is_Active
FROM Workers JOIN Tasks ON Workers.ID = Tasks.Worker_ID;
