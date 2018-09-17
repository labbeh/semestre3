#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<ctype.h>

#define TAILLE 130
#define ROUGE(c) printf("\033[31m%c\033[0m", c)

void main()
{
	char ligne[TAILLE];
	
	printf("%s", "saisir une chaine: ");
	fgets(ligne, TAILLE, stdin);
	
	// parcours de la chaine pour détecter les chiffres
	int cpt;
	int longueur = strlen(ligne);
	
	for(cpt=0; cpt<longueur; cpt++)
	{
		if(isdigit(ligne[cpt]))
		{
			//printf("%s", "\033[31m");
			ROUGE(ligne[cpt]);
			//printf("%s", "\033[0m");
		}
		else printf("%c", ligne[cpt]);
	}
	
	printf("%s", "");
}
