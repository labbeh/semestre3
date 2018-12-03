#include <stdio.h>
#include <sys/types.h>
#include <sys/signal.h>
#include <unistd.h>
#include <stdlib.h>


int val;

int min;
int max;

void augmenter(int);
void diminuer(int);

void augmenter (int sig)
{
	struct sigaction action;
	sigset_t masque;

	if(val == min){
		sigemptyset(&masque);

		action.sa_handler = diminuer;
		action.sa_mask 	  = masque;
		action.sa_flags   = 0;

		sigaction(SIGUSR2, &action, NULL);
	}
	val++;

	if(val == max){
		sigemptyset(&masque);

		action.sa_handler = SIG_IGN;
		action.sa_mask 	  = masque;
		action.sa_flags   = 0;

		sigaction(SIGUSR1, &action, NULL);
	}
}

void diminuer (int sig)
{
	struct sigaction action;
	sigset_t masque;

	if(val == max){
		sigemptyset(&masque);

		action.sa_handler = augmenter;
		action.sa_mask 	  = masque;
		action.sa_flags   = 0;

		sigaction(SIGUSR1, &action, NULL);
	}
	val--;

	if(val == min){
		sigemptyset(&masque);

		action.sa_handler = SIG_IGN;
		action.sa_mask 	  = masque;
		action.sa_flags   = 0;

		sigaction(SIGUSR2, &action, NULL);
	}
}

int main( int argc, char *argv[] )
{
	if(argc < 3){
		printf("\033[41m");
		printf("ERREUR: saisir borne min et borne max en paramètre\n");
		printf("\033[0m");

		exit(1);
	}

	min = atoi(argv[1]);
	max = atoi(argv[2]);

	val = min;

	if(min >= max){
		printf("\033[41m");
		printf("ERREUR: min doit être strictement inférieur à max\n");
		printf("\033[0m");

		exit(1);
	}

	  struct sigaction action1; /* structure permettant de construire un handler*/
	  struct sigaction action2;

	  sigset_t masque; /* masque contenant les éventuels signaux masqués*/
	                   /*pendant l'execution du handler*/
	 
	 /* on initialise le masque à vide : on en souhaite masquer aucun signal*/
	 sigemptyset(&masque);

	 /* on remplit la structure du handler*/
	 action1.sa_handler = augmenter; /*la fonction associée au signal*/
	 action1.sa_mask = masque; /* le masque des signaux bloqués*/
	 action1.sa_flags = 0; /* d'eventuelles options*/
	 

	 sigaction(SIGUSR1 ,&action,NULL);

	 action2.sa_handler = diminuer; /*la fonction associée au signal*/
	 action2.sa_mask = masque; /* le masque des signaux bloqués*/
	 action2.sa_flags = 0; /* d'eventuelles options*/

	 sigaction(SIGUSR2,&action,NULL);
	 
	 printf("Mon pid est %d\n",getpid());


	 while(1){
	 	printf("%d\n", val);
	 	sleep(1);
	 }

	 return 0;
}