/* PROGRAMME DE TEST POUR L'INSTALLATION D'UN HANDLER DE SIGNAL*/

/* ATTENTION : Ce programme boucle à l'infini. Pour l'arrêter, il faut*/
/* récupérer le pid du processus et le tuer dans une autre fenÃªtre */
/* par un kill -9 pid.
/* Pour tester ce programme, il suffit de le lancer et de taper */
/* de temps à autres CTRL+C. Au lieu de s'arrêter, le programme affiche */
/* le message "signal SIGINT reçu !" */

#include <stdio.h>
#include <sys/types.h>
#include <sys/signal.h>
#include <unistd.h>
#include <stdlib.h>


/* handler (fonction) associé au signal SIGINT*/
void traitement (int sig)
{
	if 	   (sig == SIGINT ) printf("signal SIGINT  reçu ! \n");
	else if(sig == SIGUSR1) printf("signal SIGUSR1 reçu ! \n");
}

int main()
{
	  struct sigaction action; /* structure permettant de construire un handler*/
	  sigset_t masque; /* masque contenant les éventuels signaux masqués*/
	                   /*pendant l'execution du handler*/
	 
	  /* on initialise le masque à vide : on en souhaite masquer aucun signal*/
	 sigemptyset(&masque);
	 /* on remplit la structure du handler*/
	 action.sa_handler = traitement; /*la fonction associée au signal*/
	 action.sa_mask = masque; /* le masque des signaux bloqués*/
	 action.sa_flags = 0; /* d'eventuelles options*/
	 
	 /*on installe le handler*/
	 /* cela veut dire qu'on l'associe a un signal paticulier : ici SIGINT*/
	 /* cela veut aussi dire qu'on rend le handler actif*/
	 sigaction(SIGINT ,&action,NULL);
	 sigaction(SIGUSR1,&action,NULL);
	 
	 printf("Mon pid est %d\n",getpid());

	 sigfillset(&masque 	   ); // mettre tout les signaux dans le masque ...
	 sigdelset (&masque, SIGINT); // ... puis on enlève le SIGINT

	 sigsuspend(&masque); 
	 // au final on aura tous les signax, sauf le SIGINT

	 /*while (1)
	 {
		  printf("un tour! \n");
		  sleep(1);
	 }*/
	 return 0;
}











