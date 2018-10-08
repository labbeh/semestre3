#include <stdio.h>
#include <string.h>
#include <stdlib.h>

/*
	Hugo Labbé, D2
*/

#define TAILLE 30

void clearBuffer();
void afficher();

int main()
{
	int nbrLogiciels;
	printf("Saisir le nombre de logiciels installés: ");
	scanf("%d", &nbrLogiciels);

	clearBuffer();

	char *logiciels[nbrLogiciels];
	

	int cpt;
	for(cpt = 0; cpt<nbrLogiciels; cpt++)
	{
		char saisie[TAILLE];
		printf("Entrez logiciel %d: ", cpt+1);

		fgets(saisie, TAILLE, stdin);

		logiciels[cpt] = (char*)malloc( sizeof(char) * strlen(saisie) +1 );
		strcpy(logiciels[cpt], saisie);
	}

	logiciels[cpt] = NULL;

	afficher(logiciels);

	return 0;
}

void afficher(char ** logiciels)
{
	printf("Liste des logiciels: \n");

	for (int cpt = 0; logiciels[cpt] != NULL; cpt++)
	{
		printf("%s", logiciels[cpt]);
	}
}

void clearBuffer()
{
	while(getchar() != '\n');
}