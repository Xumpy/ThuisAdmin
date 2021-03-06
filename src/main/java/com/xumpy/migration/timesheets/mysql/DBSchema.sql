CREATE TABLE TA_JOBS(
    PK_ID BIGINT NOT NULL,
    FK_JOB_GROUP_ID BIGINT NOT NULL,
    JOB_DATE DATE NOT NULL,
    WORKED_HOURS DECIMAL(9,2) NOT NULL,
    REMARKS VARCHAR(4000),
    PRIMARY KEY (PK_ID));

CREATE TABLE TA_JOB_GROUPS(
    PK_ID BIGINT NOT NULL,
    JOB_NAME VARCHAR(255) NOT NULL,
    DESCRIPTION VARCHAR(4000),
    PRIMARY KEY (PK_ID));

ALTER TABLE TA_JOBS ADD COLUMN PERCENTAGE BIGINT;

ALTER TABLE TA_JOB_GROUPS ADD COLUMN FK_COMPANY_ID BIGINT;

CREATE TABLE TA_COMPANY(
    PK_ID BIGINT NOT NULL,
    NAME VARCHAR(4000),
    DAILY_PAYED_HOURS DECIMAL(9,2),
    PRIMARY KEY (PK_ID));

CREATE TABLE TA_JOBS_GROUP_PRICES(
    PK_ID BIGINT NOT NULL,
    FK_JOB_GROUP_ID BIGINT NOT NULL,
    START_DATE DATE NOT NULL,
    END_DATE DATE NOT NULL,
    PRICE_PER_HOUR DECIMAL(9,2),
    PRIMARY KEY (PK_ID));

CREATE TABLE TA_TICKED_JOBS(
    PK_ID BIGINT NOT NULL,
    FK_JOB_ID BIGINT NOT NULL,
    SQLITE_ID BIGINT NOT NULL,
    TICKED DATETIME NOT NULL,
    STARTED BOOLEAN,
    PRIMARY KEY (PK_ID));

CREATE TABLE TA_TIMESHEETS(
    PK_ID BIGINT NOT NULL,
    FK_JOB_GROUP_ID BIGINT NOT NULL,
    BEGIN_DATE DATE,
    END_DATE DATE,
    DOCUMENT MEDIUMBLOB,
    DOCUMENT_MIME VARCHAR(255) NOT NULL,
    DOCUMENT_NAME VARCHAR(255) NOT NULL,
    PRIMARY KEY (PK_ID));