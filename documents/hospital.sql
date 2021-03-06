--  HOSPITALDB.SQL

USE HOSPITALDB;

--  these are all lookup tables

-- table is used to store the login info
-- USER_TYPE possible value  P: patient, D: doctor, A: admin
CREATE TABLE IF NOT EXISTS LOGIN_INFO(
    LOGIN_ID VARCHAR(255) NOT NULL,
    PWD VARCHAR(255) NOT NULL,
    USER_TYPE CHAR(1) NOT NULL
);

CREATE TABLE IF NOT EXISTS DISEASE_INFO(
    DISEASE_ID TINYINT NOT NULL auto_increment,
    NAME VARCHAR(255) NOT NULL,
    PRIMARY KEY (DISEASE_ID)
);


CREATE TABLE IF NOT EXISTS TEST_INFO(
    TEST_ID TINYINT NOT NULL auto_increment,
    NAME VARCHAR(255) NOT NULL,
    PRIMARY KEY (TEST_ID)
);

--  entity tables
--  date is in YYYY-MM-DD format

CREATE TABLE IF NOT EXISTS PATIENT(
    PATIENT_ID SMALLINT NOT NULL auto_increment,
    NAME VARCHAR(255) NOT NULL,
    DOB DATE NOT null default '0000-00-00',
    PRIMARY KEY (PATIENT_ID)
);

-- one patient can have many diseases

CREATE TABLE IF NOT EXISTS PATIENT_DISEASES(
    PATIENT_ID SMALLINT,
    DISEASE_ID TINYINT,
    FOREIGN KEY (PATIENT_ID) REFERENCES PATIENT(PATIENT_ID) ON DELETE CASCADE,
    FOREIGN KEY (DISEASE_ID) REFERENCES DISEASE_INFO(DISEASE_ID) ON DELETE CASCADE
);

-- assume, one doctor specializes in only one disease

CREATE TABLE IF NOT EXISTS DOCTOR(
    DOC_ID SMALLINT NOT NULL auto_increment,
    NAME VARCHAR(255) NOT NULL,
    DEGREE VARCHAR(255) NOT NULL,
    DISEASE_ID tinyint NOT NULL,
    PRIMARY KEY (DOC_ID),
    FOREIGN KEY (DISEASE_ID) REFERENCES DISEASE_INFO(DISEASE_ID) ON DELETE CASCADE
);

-- //
-- there can be many rows for 1 patient for 1 doctor prescribing him many tests on a single date

CREATE TABLE IF NOT EXISTS TREATMENT(
    DOC_ID smallint NOT NULL,
    PATIENT_ID smallint NOT NULL,
    TREAT_DT DATE DEFAULT (CURRENT_DATE),
    PRESCRIBED_TEST_ID tinyint NOT NULL,
    FOREIGN KEY (DOC_ID) REFERENCES DOCTOR(DOC_ID) ON DELETE CASCADE,
    FOREIGN KEY (PATIENT_ID) REFERENCES PATIENT(PATIENT_ID) ON DELETE CASCADE,
    FOREIGN KEY (PRESCRIBED_TEST_ID) REFERENCES TEST_INFO(TEST_ID) ON DELETE CASCADE

);




-- one patient can go under many diagnostic tests on different dates
--  and TEST_RESULT will have positive / negative vlaues
-- DROP TABLE IF EXISTS PATIENT_TEST_RESULTS;
CREATE TABLE IF NOT EXISTS PATIENT_TEST_RESULTS(
    PATIENT_ID smallint NOT NULL,
    TEST_ID tinyint NOT NULL,
    TEST_DT DATE DEFAULT (CURRENT_DATE),
    TEST_RESULT CHAR(255),
    FOREIGN KEY (PATIENT_ID) REFERENCES PATIENT(PATIENT_ID) ON DELETE CASCADE,
    FOREIGN KEY (TEST_ID) REFERENCES TEST_INFO(TEST_ID) ON DELETE CASCADE
);

-- SPECIALIZATION: save it as comma seperated list

CREATE TABLE IF NOT EXISTS HOSPITAL(
    HOSPITAL_ID TINYINT NOT NULL auto_increment,
    NAME VARCHAR(255) NOT NULL,
    ADDRESS VARCHAR(255),
    SPECIALIZATION VARCHAR(255),
    PRIMARY KEY(HOSPITAL_ID)
);

-- 1 doctor can be affiliated(associated or works in) many hospitals

CREATE TABLE IF NOT EXISTS HOSPITAL_DOCTOR_AFFILIATION(
    HOSPITAL_ID tinyint NOT NULL,
    DOC_ID smallint NOT NULL,
    FOREIGN KEY (HOSPITAL_ID) REFERENCES HOSPITAL(HOSPITAL_ID) ON DELETE CASCADE,
    FOREIGN KEY (DOC_ID) REFERENCES DOCTOR(DOC_ID) ON DELETE CASCADE
);

-- the belo code will drop all tables at once keeping order in mind
-- DROP TABLE IF EXISTS HOSPITAL_DOCTOR_AFFILIATION;
-- DROP TABLE IF EXISTS HOSPITAL;
-- DROP TABLE IF EXISTS TREATMENT;
-- DROP TABLE IF EXISTS DOCTOR;
-- DROP TABLE IF EXISTS PATIENT_DISEASES;
-- DROP TABLE IF EXISTS PATIENT;
-- DROP TABLE IF EXISTS TEST_INFO;
-- DROP TABLE IF EXISTS DISEASE_INFO;


-- data insert commands
-- DELETE FROM DISEASE_INFO;
INSERT INTO DISEASE_INFO(NAME) VALUES ('dengue');
INSERT INTO DISEASE_INFO(NAME) VALUES ('TB');
INSERT INTO DISEASE_INFO(NAME) VALUES ('viral fever');

-- DELETE FROM TEST_INFO;
INSERT INTO TEST_INFO(NAME) VALUES ('blood test');
INSERT INTO TEST_INFO(NAME) VALUES ('urine test');
INSERT INTO TEST_INFO(NAME) VALUES ('x-ray');
INSERT INTO TEST_INFO(NAME) VALUES ('MRI');
INSERT INTO TEST_INFO(NAME) VALUES ('Heamoglobin');


INSERT INTO PATIENT(NAME, DOB) VALUES ('John Tinder', '1979-11-29');
INSERT INTO PATIENT(NAME, DOB) VALUES ('Dave Smith', '1982-01-01');

INSERT INTO HOSPITAL(NAME, ADDRESS, SPECIALIZATION) VALUES ('City hospital', 'M G Road', 'all diseases');

INSERT INTO DOCTOR(NAME, DEGREE, DISEASE_ID) VALUES ('Rekha Jindal', 'MD', 2);
INSERT INTO DOCTOR(NAME, DEGREE, DISEASE_ID) VALUES ('Vijay Kedia', 'MBBS', 1);
INSERT INTO DOCTOR(NAME, DEGREE, DISEASE_ID) VALUES ('Mohan Kumar', 'BHMS', 3);

INSERT INTO HOSPITAL_DOCTOR_AFFILIATION(HOSPITAL_ID, DOC_ID) VALUES (1, 1);
INSERT INTO HOSPITAL_DOCTOR_AFFILIATION(HOSPITAL_ID, DOC_ID) VALUES (1, 2);
INSERT INTO HOSPITAL_DOCTOR_AFFILIATION(HOSPITAL_ID, DOC_ID) VALUES (1, 3);

INSERT INTO PATIENT_DISEASES(PATIENT_ID, DISEASE_ID) VALUES (1, 3);
INSERT INTO PATIENT_DISEASES(PATIENT_ID, DISEASE_ID) VALUES (2, 2);

INSERT INTO PATIENT_TEST_RESULTS(PATIENT_ID, TEST_ID, TEST_DT, TEST_RESULT) VALUES (1, 1, '1979-11-29', 'POSITIVE');
INSERT INTO PATIENT_TEST_RESULTS(PATIENT_ID, TEST_ID, TEST_DT, TEST_RESULT) VALUES (2, 4, '1979-11-29', 'NEGATIVE');

INSERT INTO TREATMENT(DOC_ID, PATIENT_ID, TREAT_DT, PRESCRIBED_TEST_ID) VALUES (2, 2, '2000-11-25', 5);
INSERT INTO TREATMENT(DOC_ID, PATIENT_ID, TREAT_DT, PRESCRIBED_TEST_ID) VALUES (1, 1, '2001-10-20', 2);

-- this is a special login for admin user
INSERT INTO LOGIN_INFO(LOGIN_ID, PWD, USER_TYPE) VALUES ('0', '0', 'A');
-- patients
INSERT INTO LOGIN_INFO(LOGIN_ID, PWD, USER_TYPE) VALUES ('1', '1', 'P');
INSERT INTO LOGIN_INFO(LOGIN_ID, PWD, USER_TYPE) VALUES ('2', '2', 'P');
-- doctors
INSERT INTO LOGIN_INFO(LOGIN_ID, PWD, USER_TYPE) VALUES ('1', '1', 'D');
INSERT INTO LOGIN_INFO(LOGIN_ID, PWD, USER_TYPE) VALUES ('2', '2', 'D');
INSERT INTO LOGIN_INFO(LOGIN_ID, PWD, USER_TYPE) VALUES ('3', '3', 'D');

