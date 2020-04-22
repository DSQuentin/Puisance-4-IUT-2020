CREATE DATABASE IF NOT EXISTS P4 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE P4;

CREATE TABLE ROLE (
  nomrole varchar(10),
  PRIMARY KEY (nomrole)
);


CREATE TABLE MESSAGE (
  idmsg decimal(6,0),
  datemsg datetime,
  contenumsg text,
  idut decimal(6,0),
  PRIMARY KEY (idmsg)
);

CREATE TABLE RECEVOIR (
   idmsg decimal(6,0),
   idut decimal(6,0),
   lu char(1),
   PRIMARY KEY (idmsg,idut)
);

CREATE TABLE UTILISATEUR (
  idut decimal(6,0),
  pseudout varchar(10) unique,
  emailut varchar(100),
  mdput varchar(100),
  activeut char(1),
  nomrole varchar(10),
  PRIMARY KEY (idut)
);

CREATE TABLE ETREAMI (
  idut_1 decimal(6,0),
  idut_2 decimal(6,0),
  PRIMARY KEY (idut_1, idut_2)
);


CREATE TABLE PARTIE (
  idpa decimal(6,0),
  debutpa datetime,
  numetape int,
  etatpartie text,
  idut_1 decimal(6,0),
  idut_2  decimal(6,0),
  score_1 int,
  score_2 int,
  PRIMARY KEY (idpa)
);

CREATE TABLE INVITATION (
  idinv decimal(6,0),
  dateinv datetime,
  etatinv char(1),
  idut_exp decimal(6,0),
  idut_dest decimal(6,0),
  msginv text,
  PRIMARY KEY (idinv)
);

ALTER TABLE MESSAGE ADD FOREIGN KEY (idut) REFERENCES UTILISATEUR (idut);
ALTER TABLE RECEVOIR ADD FOREIGN KEY (idut) REFERENCES UTILISATEUR (idut);
ALTER TABLE RECEVOIR ADD FOREIGN KEY (idmsg) REFERENCES MESSAGE (idmsg);
ALTER TABLE UTILISATEUR ADD FOREIGN KEY (nomrole) REFERENCES ROLE (nomrole);
ALTER TABLE ETREAMI ADD FOREIGN KEY (idut_1) REFERENCES UTILISATEUR (idut);
ALTER TABLE ETREAMI ADD FOREIGN KEY (idut_2) REFERENCES UTILISATEUR (idut);
ALTER TABLE PARTIE ADD FOREIGN KEY (idut_1) REFERENCES UTILISATEUR (idut);
ALTER TABLE PARTIE ADD FOREIGN KEY (idut_2) REFERENCES UTILISATEUR (idut);
ALTER TABLE INVITATION ADD FOREIGN KEY (idut_exp) REFERENCES UTILISATEUR (idut);
ALTER TABLE INVITATION ADD FOREIGN KEY (idut_dest) REFERENCES UTILISATEUR (idut);
