#include <stdio.h>
#include <string.h>
#include <stdlib.h>

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
	char * valeurs; //= (char*) malloc (strlen(getenv(from))+1);
	valeurs = getenv(from);
	char * ligne;

	ligne = strtok(valeurs, ":");
	dest[0] = ligne;

	int cpt;
	for(cpt=1; ligne != NULL; cpt++)
	{
		ligne = strtok(NULL, ":");
		dest[cpt] = ligne;
	}
	
	dest[cpt] = NULL;
}

int main(int argc, char * argv[])
{
	if(argc < 2)
	{
		printf("%s", "Usage: ./a.out -a /-v nom varialbe ");
		return -1;
	}

	if(argc == 2 && strcmp(argv[1], "-a") == 0)
	{
		aff_env();
		return 0;
	}

	if(argc > 2 && strcmp(argv[1], "-v") == 0)
	{
		char *dest[MAX_Valeur_Par_Variable];
		extractvaleur(dest, argv[2]);

		int cpt=0;
		while(dest[cpt] != NULL)
		{
			printf("%s\n", dest[cpt]);
			cpt++;
		}
	}

	return 0;
}