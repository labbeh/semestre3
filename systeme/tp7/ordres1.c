#include <stdio.h>
#include <sys/types.h>
#include <sys/signal.h>
#include <unistd.h>
#include <stdlib.h>


void clearbuffer()
{
	while(getchar() != '\n');
}

int main( int argc, char *argv[] )
{
	if(argc < 2){
		printf("\033[41m");
		printf("ERREUR: saisir le PID du processus en paramètre\n");
		printf("\033[0m");

		exit(1);
	}

	char saisie;
	int pid;

	// conversion en int
	pid = atoi(argv[1]);

	while(saisie != 'A'){
		printf("Saisir +|-|A: ");
		scanf("%c", &saisie);
		clearbuffer();

		switch(saisie){
			case '+': kill(pid, SIGUSR1); break;
			case '-': kill(pid, SIGUSR2); break;
			case 'A': kill(pid, SIGINT ); break;

			default: printf("ERREUR: choix impossible\n");
		}
	}

	return 0;
}