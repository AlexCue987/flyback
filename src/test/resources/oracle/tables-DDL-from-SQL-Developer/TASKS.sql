--------------------------------------------------------
--  DDL for Table TASKS
--------------------------------------------------------

  CREATE TABLE "MY_TEST"."TASKS" 
   (	"ID" NUMBER, 
	"WORKER_ID" NUMBER, 
	"CREATED_AT" DATE DEFAULT SYSDATE, 
	"CREATED_AT_STR" VARCHAR2(75 BYTE) GENERATED ALWAYS AS (TO_CHAR("CREATED_AT",'yyyy-mm-dd hh24:mi:ss')) VIRTUAL VISIBLE , 
	"COMPLETED_AT" DATE, 
	"COMPLETED_AT_STR" VARCHAR2(75 BYTE) GENERATED ALWAYS AS (TO_CHAR("COMPLETED_AT",'yyyy-mm-dd hh24:mi:ss')) VIRTUAL VISIBLE , 
	"IS_ACTIVE" CHAR(1 BYTE), 
	"DESCRIPTION" VARCHAR2(100 BYTE)
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "MY_DATA" ;
--------------------------------------------------------
--  DDL for Index PK_TASKS
--------------------------------------------------------

  CREATE UNIQUE INDEX "MY_TEST"."PK_TASKS" ON "MY_TEST"."TASKS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "MY_DATA" ;
--------------------------------------------------------
--  DDL for Index IDX_TASKS_WORKER_ID
--------------------------------------------------------

  CREATE INDEX "MY_TEST"."IDX_TASKS_WORKER_ID" ON "MY_TEST"."TASKS" ("WORKER_ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "MY_DATA" ;
--------------------------------------------------------
--  DDL for Index ONE_ACTIVE_TASK_PER_WORKER
--------------------------------------------------------

  CREATE UNIQUE INDEX "MY_TEST"."ONE_ACTIVE_TASK_PER_WORKER" ON "MY_TEST"."TASKS" (CASE  WHEN "IS_ACTIVE"='Y' THEN "WORKER_ID" END ) 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "MY_DATA" ;
--------------------------------------------------------
--  DDL for Index IDX_TASKS_DESCRIPTION
--------------------------------------------------------

  CREATE INDEX "MY_TEST"."IDX_TASKS_DESCRIPTION" ON "MY_TEST"."TASKS" ("DESCRIPTION") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "MY_DATA" ;
--------------------------------------------------------
--  Constraints for Table TASKS
--------------------------------------------------------

  ALTER TABLE "MY_TEST"."TASKS" ADD CONSTRAINT "CHK_IS_ACTIVE" CHECK (Is_Active IN ('Y', 'N') AND ((Is_Active='Y' AND Completed_At IS NULL) OR Is_Active='N')) ENABLE;
  ALTER TABLE "MY_TEST"."TASKS" ADD CONSTRAINT "CHK_TASKS_VALID_RANGE" CHECK (Created_At < Completed_At) ENABLE;
  ALTER TABLE "MY_TEST"."TASKS" ADD CONSTRAINT "PK_TASKS" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "MY_DATA"  ENABLE;
  ALTER TABLE "MY_TEST"."TASKS" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "MY_TEST"."TASKS" MODIFY ("WORKER_ID" NOT NULL ENABLE);
  ALTER TABLE "MY_TEST"."TASKS" MODIFY ("CREATED_AT" NOT NULL ENABLE);
  ALTER TABLE "MY_TEST"."TASKS" MODIFY ("IS_ACTIVE" NOT NULL ENABLE);
