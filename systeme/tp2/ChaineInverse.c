#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<ctype.h>

char * construireChaineInverse(char * str)
{
	int longueur = strlen(str);
	char * inverse = (char*)malloc(sizeof(char) * (strlen(str)+1));

	int cpt;
	for(cpt = 0; cpt < longueur; cpt++)
	{
		inverse[cpt] = str[longueur-cpt-1];
	}

	return inverse;
}

int main()
{
	char * tab = "test 1";
	
	printf("%s", construireChaineInverse(tab));
	return 0;
}

