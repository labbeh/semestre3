#include <stdio.h>
#include <unistd.h>
#include <string.h>

#include <unistd.h>
#include <sys/types.h>

#include <pwd.h>

#include <stdlib.h>

void afficher(char * elts[]);

int main()
{
	char * envpath = getenv("PATH");
	char * copie; // car le strtok modifie la contenue de envpath

	char * elts[20];

	copie = strdup(envpath);

	if(envpath == NULL)
	{
		printf("ERREUR: variable PATH introuvable\n");
		exit(1);
	}

	elts[0] = strtok(copie, ":");

	for(int i=1; (elts[i] = strtok(NULL, ":")) != NULL; i++);

	//afficher(elts);
	execvp("ls", elts);

	return 0;
}

void afficher(char * elts[])
{
	for(int i=0; elts[i] != NULL; i++)
	{
		printf(elts[i]);
		printf("\n");
	}
}