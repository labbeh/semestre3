#include <stdio.h>

#include <sys/signal.h>
#include <unistd.h>

#include <sys/wait.h>

// Labbé Hugo D2

int nbr;
sigset_t masque;

void clearbuffer()
{
	while(getchar() != '\n');
}


void stop(){

	printf("Valeur acutelle du nombre: %d\n", nbr);
	printf("Mise en pause...\n\n");

	pause();
}

void reprise(){
	printf("Reprise...\n\n");
}

void affichermenu(){
	printf("Choix:\n");
	printf("------\n");

	printf("\ts: stopper le fils\n");
	printf("\tr: reprendre l'exécution du fils\n");
	printf("\tq: tuer le fils et terminer le programme\n");
}

int main(){
	 // création du fils
	pid_t pid = fork();
	int enpause = 0;

	struct sigaction action;

	if(pid == -1) perror("fork");

	if(pid == 0){
		nbr = 1;

		sigemptyset(&masque);

		 action.sa_handler = stop;
		 action.sa_mask    = masque;
		 action.sa_flags   = 0;
		 

		 sigaction(SIGUSR1 ,&action,NULL);

		 action.sa_handler = reprise;
		 sigaction(SIGUSR2,&action,NULL);

		while (1){
			//somme de nombres impairs
			nbr += 2;

			//temporisation pour ne pas sucharger le processeur
			sleep(1);
	 	}
	}

	else{
		affichermenu();
		char saisie;
		
		// il y a des problèmes avec le buffer
		while(saisie != 'q'){
			scanf("%c", &saisie);
			clearbuffer();

			switch(saisie){
				case 's': kill(pid, SIGUSR1); break;
				case 'r': if(enpause == 0) {kill(pid, SIGUSR2); enpause = 1;} 
						  else printf("deja en pause"); break;
				case 'q': kill(pid, SIGKILL); break;

				default: printf("ERREUR: choix impossible\n");
			}
		}

	}

	wait(NULL);

	printf("\n\033[41mfin d'exécution\033[0m\n");
	return 0;
}