#include <stdio.h>
#include <string.h>
#include <stdlib.h>

/*
	Hugo Labbé, D2
*/

char * construireChaineAutoconcatenee(char * s);

int main()
{
	char chaine[7] = "azerty";
	char * chaineConcat;

	chaineConcat = construireChaineAutoconcatenee(chaine);

	printf("Chaine %s concatenee avec elle-meme = %s\n", chaine, chaineConcat);

	return 0;
}

// sans utiliser strcat()
char * construireChaineAutoconcatenee(char * s)
{
	int tailleChaine = (strlen(s)+1) * 2;
	char * ret = (char*)malloc( sizeof(char) * tailleChaine );

	int cpt;
	int cpt2 = 0;
	for(cpt=0; cpt<strlen(s); cpt++)
	{
		ret[cpt2] = s[cpt];
		cpt2++;
	}

	for(cpt=0; cpt<strlen(s); cpt++)
	{
		ret[cpt2] = s[cpt];
		cpt2++;
	}

	return ret;
}