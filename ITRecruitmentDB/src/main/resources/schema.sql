-- schema.sql
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS file_model;
DROP TABLE IF EXISTS skill_candidate;
DROP TABLE IF EXISTS skill;
DROP TABLE IF EXISTS candidate;
DROP TABLE IF EXISTS category;


CREATE TABLE category (
    category_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    category_name varchar(50) DEFAULT NULL,
    PRIMARY KEY(category_id)
    );

CREATE TABLE candidate (
    candidate_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    first_name varchar(50) DEFAULT NULL,
    last_name varchar(50) DEFAULT NULL,
    streetaddress varchar(50) DEFAULT NULL,
    postcode char(5) DEFAULT NULL,
    postoffice varchar(50) DEFAULT NULL,
    country varchar(50) DEFAULT NULL,    
    phone char(20) DEFAULT NULL,
    email varchar(50) DEFAULT NULL,
    category_id BIGINT(20) DEFAULT NULL,
    PRIMARY KEY(candidate_id),
    CONSTRAINT FK_candidateCategory FOREIGN KEY (category_id)
    REFERENCES category(category_id)
    );

CREATE TABLE skill (
    skill_id BIGINT(20) NOT NULL AUTO_INCREMENT,
    skill_name varchar(50) NOT NULL,
    PRIMARY KEY(skill_id)
    );
    
CREATE TABLE skill_candidate (
    skill_id BIGINT(20) NOT NULL,
    candidate_id BIGINT(20) NOT NULL,   
    skill_level varchar(50) DEFAULT NULL,
    PRIMARY KEY(skill_id, candidate_id),
    CONSTRAINT FK_SC_skillId FOREIGN KEY (skill_id)
    REFERENCES skill(skill_id),
    CONSTRAINT FK_SC_candidateId FOREIGN KEY (candidate_id)
    REFERENCES candidate(candidate_id),
    CONSTRAINT CHK_SC_level CHECK ( skill_level IN ('excellent', 'good', 'satisfactory', 'some experience', 'no experience'))
    );

CREATE TABLE file_model(
   id BIGINT(20) NOT NULL AUTO_INCREMENT,
   file_name VARCHAR(255) DEFAULT NULL,
   mime_type VARCHAR(255) DEFAULT NULL,
   base64str VARCHAR(255) DEFAULT NULL,
   file LONGBLOB DEFAULT NULL,
   candidate_id BIGINT(20) DEFAULT NULL,
   PRIMARY KEY (id),
   CONSTRAINT FK_fileModelCandidate FOREIGN KEY (candidate_id)
   REFERENCES candidate(candidate_id)
);

CREATE TABLE user (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    role varchar(255) NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(username)
    );

