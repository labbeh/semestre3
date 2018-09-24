#include<stdio.h>
#include<string.h>
#include<stdlib.h>

// atoi	  : convertir chaine en entier int atoi(const char *nptr); ou long
// strtok : char *strtok(char *str, const char *delim); comme le split en java


char * tabMois[] ={"janvier","fevrier","mars","avril","mai","juin","juillet","aout","septembre","octobre","novembre","decembre"} ;

int main()
{
	int TAILLE = 11; // 10 caractère + \0
	char * saisie = (char*)malloc(sizeof(char) * TAILLE);

	printf("%s", "Saisir une date au format jj/mm/aaaa: ");
	fgets(saisie, TAILLE, stdin);

	char * sousChaine[3];
	sousChaine[0] = strtok(saisie, "/");

	int cpt;
	for(cpt=1; saisie != NULL; cpt++)
	{
		sousChaine[cpt] = strtok(NULL, "/");
	}

	int jour  = atoi(sousChaine[0]);
	int mois  = atoi(sousChaine[1]);
	int annee = atoi(sousChaine[2]);

	// vérification de la saisie
	if 	   (jour  < 0 || jour > 32) { printf("%s", "ERREUR: Jour invalide\n" ); return 1; }
	else if(mois  < 0 || mois > 12) { printf("%s", "ERREUR: mois invalide\n" ); return 1; }
	else if(annee < 0			  ) { printf("%s", "ERREUR: année négative\n"); return 1; }

	else printf("%d %s %d\n", jour, tabMois[mois-1], annee);

	return 0;
}