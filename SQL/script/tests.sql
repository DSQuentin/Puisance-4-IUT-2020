insert into ROLE(nomrole) values ('admin'),('util');
insert into UTILISATEUR(idut, pseudout, emailut, mdput, activeut, nomrole) values
       (1,'administrateur','admin@p4.fr',password('mdp1'),'O','admin'),
       (2,'iuto','iuto@univ.fr',password('mdpiuto'),'O','util'),
       (3,'iutc','iutc@univ.fr',password('mdpiutc'),'O','util'),
       (4,'iutv','iutv@univ.fr',password('mdpiutv'),'O','util');

insert into ETREAMI(idut_1,idut_2) values (2,3);
insert into INVITATION(idinv, dateinv, etatinv, idut_exp, idut_dest, msginv) values
       (1,STR_TO_DATE('02-04-2020:12:52','%d-%m-%Y:%h:%i'),'A',3,2,'Tu veux faire une partie?'),
       (2,STR_TO_DATE('02-04-2020:12:58','%d-%m-%Y:%h:%i'),'A',2,4,'Tu veux faire une partie?'),
       (3,STR_TO_DATE('02-04-2020:13:24','%d-%m-%Y:%h:%i'),'E',4,NULL,'Qui veux me défier?'),
       (4,STR_TO_DATE('02-04-2020:13:25','%d-%m-%Y:%h:%i'),'A',2,3,'Une revanche?');

insert into PARTIE (idpa, debutpa, numetape, etatpartie,idut_1,idut_2,score_1,score_2)  values
       (1,STR_TO_DATE('02-04-2020:12:55','%d-%m-%Y:%h:%i'),-1,'ici le json de l''état de la partie',3,2,0,5),
       (2,STR_TO_DATE('02-04-2020:13:01','%d-%m-%Y:%h:%i'),7,'ici le json de l''état de la partie',2,4,0,0),
       (3,STR_TO_DATE('02-04-2020:13:35','%d-%m-%Y:%h:%i'),-1,'ici le json de l''état de la partie',2,3,0,7);

insert into MESSAGE (idmsg,datemsg,contenumsg,idut) values
       (1,STR_TO_DATE('02-04-2020:09:17','%d-%m-%Y:%h:%i'),'Bienvenue sur notre nouvelle plateforme de jeu!',1),
       (2,STR_TO_DATE('02-04-2020:10:24','%d-%m-%Y:%h:%i'),'Salut, alors tu es inscrit pour jouer à P4',3),
       (3,STR_TO_DATE('02-04-2020:09:17','%d-%m-%Y:%h:%i'),'Ouais! Je suis prêt à te défier!!!!',2),
       (4,STR_TO_DATE('02-04-2020:13:08','%d-%m-%Y:%h:%i'),'Attention! Votre mot de passe est trop simple',1);
insert into RECEVOIR(idmsg,idut,lu) values
       (1,2,'O'),
       (1,3,'O'),
       (1,4,'N'),
       (2,2,'O'),
       (3,3,'N'),
       (4,2,'N');
       
       

