--------------------------------------------------------
--  DDL for Table WORKERS
--------------------------------------------------------

  CREATE TABLE "MY_TEST"."WORKERS" 
   (	"ID" NUMBER, 
	"NAME" VARCHAR2(100 BYTE), 
	"CREATED_AT" DATE DEFAULT SYSDATE, 
	"CREATED_AT_STR" VARCHAR2(75 BYTE) GENERATED ALWAYS AS (TO_CHAR("CREATED_AT",'yyyy-mm-dd hh24:mi:ss')) VIRTUAL VISIBLE 
   ) SEGMENT CREATION DEFERRED 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  TABLESPACE "MY_DATA" ;
--------------------------------------------------------
--  DDL for Index SYS_C0019277686
--------------------------------------------------------

  CREATE UNIQUE INDEX "MY_TEST"."SYS_C0019277686" ON "MY_TEST"."WORKERS" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "MY_DATA" ;
--------------------------------------------------------
--  DDL for Index UNQ_WORKERS
--------------------------------------------------------

  CREATE UNIQUE INDEX "MY_TEST"."UNQ_WORKERS" ON "MY_TEST"."WORKERS" ("NAME") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "MY_DATA" ;
--------------------------------------------------------
--  DDL for Index IDX_WORKERS
--------------------------------------------------------

  CREATE INDEX "MY_TEST"."IDX_WORKERS" ON "MY_TEST"."WORKERS" ("CREATED_AT") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "MY_DATA" ;
--------------------------------------------------------
--  Constraints for Table WORKERS
--------------------------------------------------------

  ALTER TABLE "MY_TEST"."WORKERS" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "MY_TEST"."WORKERS" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "MY_TEST"."WORKERS" MODIFY ("CREATED_AT" NOT NULL ENABLE);
  ALTER TABLE "MY_TEST"."WORKERS" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "MY_DATA"  ENABLE;
  ALTER TABLE "MY_TEST"."WORKERS" ADD CONSTRAINT "UNQ_WORKERS" UNIQUE ("NAME")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  TABLESPACE "MY_DATA"  ENABLE;
