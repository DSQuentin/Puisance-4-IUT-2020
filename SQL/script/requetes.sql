select *
from PARTIE
where idpa in (select idpa
               from PARTIE, UTILISATEUR
	       where pseudout='iuto' and
	             numetape!=-1 and 
	             (idut = idut_1 or idut=idut_2)
	      );

select *
from MESSAGE
where idmsg in (select idmsg
                from RECEVOIR natural join UTILISATEUR
		where pseudout='iuto' and lu='N'
		);

select count(idpa)
from PARTIE, UTILISATEUR u1, UTILISATEUR u2
where numetape=-1 and u1.pseudout='iuto' and u2.pseudout='iutc' and
      ((u1.idut=idut_1 and u2.idut=idut_2 and score_1>score_2) or
       (u1.idut=idut_2 and u2.idut=idut_1 and score_2>score_1));
