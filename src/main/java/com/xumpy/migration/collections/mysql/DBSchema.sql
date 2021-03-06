DROP TABLE TA_PERSON_COLLECTIONS;
DROP TABLE TA_PERSON_COL_STATUS;
DROP TABLE TA_COLLECTION_DETAILS;
DROP TABLE TA_COLLECTIONS;

CREATE TABLE TA_COLLECTIONS(
    PK_ID BIGINT NOT NULL AUTO_INCREMENT,
    FK_MAIN_COLLECTION_ID BIGINT,
    NAME VARCHAR(255),
    DESCRIPTION VARCHAR(4000),
    PRIMARY KEY (PK_ID));

CREATE TABLE TA_COLLECTION_DETAILS(
    PK_ID BIGINT NOT NULL AUTO_INCREMENT,
    FK_COLLECTION_ID BIGINT NOT NULL,
    CODE VARCHAR(255),
    NAME VARCHAR(255),
    DESCRIPTION VARCHAR(4000),
    IMAGE BLOB,
    IMAGE_NAME VARCHAR(50),
    IMAGE_MIME VARCHAR(50),
    PRIMARY KEY (PK_ID));

CREATE TABLE TA_PERSON_COL_STATUS(
    PK_ID BIGINT NOT NULL AUTO_INCREMENT,
    NAME VARCHAR(255),
    DESCRIPTION VARCHAR(4000),
    PRIMARY KEY (PK_ID));

CREATE TABLE TA_PERSON_COLLECTIONS(
    PK_ID BIGINT NOT NULL AUTO_INCREMENT,
    FK_PERSON_ID BIGINT NOT NULL,
    FK_COLLECTION_DETAIL_ID BIGINT NOT NULL,
    FK_PERSON_COL_STATUS_ID BIGINT NOT NULL,
    PRIMARY KEY (PK_ID));
    

INSERT INTO TA_PERSON_COL_STATUS VALUES(1, "Not Owned", "I'm searching for this product");
INSERT INTO TA_PERSON_COL_STATUS VALUES(2, "Owned", "This product is in my possession");
INSERT INTO TA_PERSON_COL_STATUS VALUES(3, "Bad State", "I own this product but it is in a bad state");