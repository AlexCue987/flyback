CREATE VIEW Workers_Active_Tasks
AS
SELECT
Workers.Name AS Worker_Name,
Tasks.Description AS Task_Description,
Tasks.Created_At_Str,
Tasks.Completed_At_Str
FROM Workers JOIN Tasks ON Workers.ID = Tasks.Worker_ID
WHERE Tasks.Is_Active='Y';
