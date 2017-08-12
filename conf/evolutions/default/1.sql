# Users schema

# --- !Ups

CREATE TABLE if  NOT EXISTS "client"(
 id  serial NOT NULL,
 firstName VARCHAR(254) NOT NULL,
 lastName  VARCHAR(254) NOT NULL,
 age  INT NOT NULL,
 userEmail VARCHAR(254) NOT NULL,
 password VARCHAR(254) NOT NULL,
 company VARCHAR(254) NOT NULL,
PRIMARY KEY(id));

# --- !Downs

DROP TABLE client;