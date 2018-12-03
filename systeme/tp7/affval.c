#include <stdio.h>
#include <sys/types.h>
#include <sys/signal.h>
#include <unistd.h>
#include <stdlib.h>


int val;

void augmenter (int sig)
{
	val++;
}

void diminuer (int sig)
{
	val--;
}

int main()
{
	val = 1;

	  struct sigaction action; /* structure permettant de construire un handler*/
	  sigset_t masque; /* masque contenant les éventuels signaux masqués*/
	                   /*pendant l'execution du handler*/
	 
	 /* on initialise le masque à vide : on en souhaite masquer aucun signal*/
	 sigemptyset(&masque);

	 /* on remplit la structure du handler*/
	 action.sa_handler = augmenter; /*la fonction associée au signal*/
	 action.sa_mask = masque; /* le masque des signaux bloqués*/
	 action.sa_flags = 0; /* d'eventuelles options*/
	 

	 sigaction(SIGUSR1 ,&action,NULL);

	 action.sa_handler = diminuer; /*la fonction associée au signal*/
	 sigaction(SIGUSR2,&action,NULL);
	 
	 printf("Mon pid est %d\n",getpid());


	 while(1){
	 	printf("%d\n", val);
	 	sleep(1);
	 }

	 return 0;
}