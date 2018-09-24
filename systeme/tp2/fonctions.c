#include<stdio.h>
#include<string.h>
#include<stdlib.h>

int MAX_Valeur_Par_Variable = 100;
extern char ** environ;

void aff_env()
{
	int cpt;
	for(cpt=0; environ[cpt] != NULL; cpt++)
		printf("%s\n", environ[cpt]);
}

void extractvaleur(char *dest[], char *from)
{
	char * valeurs;
	valeurs = getenv(from);

	dest[0] = strtok(valeurs, ":");
	int cpt;
	for(cpt=1; valeurs != NULL; cpt++)
	{
		dest[cpt] = strtok(NULL, ":");
		printf("%d\n", cpt);
	}
	
	dest[cpt] = NULL;
}

int main(int argc, char * argv[])
{
	if(argc == 1) return 0;
	if(argc == 2 && strcmp(argv[1], "-a") == 0)
	{
		aff_env();
		return 0;
	}
	if(argc > 2 && strcmp(argv[1], "-v") == 0)
	{
		char ** dest =(char**)malloc(sizeof(char*) * MAX_Valeur_Par_Variable);;

		extractvaleur(dest, argv[2]);
	}

	return 0;
}