#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <sys/wait.h>
#include <stdlib.h>

// Labbé Hugo D2

int main( int argc, char *argv[]){

	if(argc < 3){
		printf("\033[41mERREUR usage: ./multi_test.x -r|-w|-x fich1 fich2 ... fichn\033[0m\n");
		exit(1);
	}

	pid_t pid;
	int status;
	char * typedroit;

	if 	   (strcmp(argv[1], "-r") == 0) typedroit = strdup("lecture"  );
	else if(strcmp(argv[1], "-w") == 0) typedroit = strdup("écriture" );
	else if(strcmp(argv[1], "-x") == 0) typedroit = strdup("exécution");
	else{
		printf("\033[41mERREUR le paramètre doit être -r | -w | -x\033[0m\n");
		exit(1);
	}

	for(int i=2; i<argc; i++){
		pid = fork();

		if(pid == -1){
			perror("fork");
			exit(1);
		}

		if(pid == 0){
			execlp("test", "test", argv[1], argv[i], NULL);
		}

		wait(&status);

		if(status == 0) printf("\033[42mfichier %s est accessible en %s\n", argv[i], typedroit);
		else 			printf("\033[41mfichier %s non accessible en %s\n", argv[i], typedroit);
	}

	// remettre la couleur de base sur le terminal
	printf("\033[0m");

	return 0;
}