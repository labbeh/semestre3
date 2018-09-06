#include<stdio.h>
#include "operation.h"

void clearBuffer();
char menu();

void main()
{
	int a, b;
	char choix;
	
	choix = menu();
	clearBuffer();
	
	
	while(choix != 'q')
	{
		printf("saisir un 1er entier: ");
		scanf("%d", &a);
		
		printf("saisir un 2ème entier: ");
		scanf("%d", &b);
		clearBuffer();
		
		switch(choix)
		{
			case '+': printf("résultat de la somme: %d\n", somme(a, b)        ); break;
			case '-': printf("résultat de la différence: %d\n", diff(a, b)    ); break;
			case '*': printf("résultat de la multiplication: %d\n", mult(a, b)); break;
			case '/': printf("résultat de la division: %d\n", div(a, b)       ); break;
			
		}
		
		choix = menu();
		clearBuffer();
	}
}

void clearBuffer()
{
	while(getchar() != '\n');
}

char menu()
{
	printf("Tapez le symbole qui correspond: \n");
	printf("+-------------------------------+\n");
	
	printf("\tsomme: +\n");
	printf("\tdifférence: -\n");
	printf("\tmultiplication: *\n");
	printf("\tdivision: /\n");
	printf("\tquitter: q\n");
	
	printf("\n");
	
	char cRet;
	scanf("%c", &cRet);
	
	return cRet;
}
