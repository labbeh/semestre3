#include <stdio.h>
#include <unistd.h>
#include <string.h>

#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

#include <pwd.h>

#include <stdlib.h>

int main( int argc,  char * argv[] ) // le nbr de paramètre compte le nom de l'exécutable
{
	if(argc == 1){
		printf("Erreur: saisir des commandes à lancer\n");
		exit(1);
	}

	pid_t pid;

	for(int i=1; i<argc; i++)
	{
		pid = fork();

		if(pid == -1){
			perror("fork");
			exit(1);
		}
		
		if(pid ==  0){
			execlp("bash", "bash", "-c", argv[i], NULL);
		}

		while(wait(NULL) > 0);

		printf("\n\033[41mTERMINE\n");
		printf("\033[0m");
	}

	return 0;
}