#include<stdio.h>

void main()
{
	int a, b;
	int result;
	
	printf("saisir un 1er entier: ");
	scanf("%d", &a);
	
	printf("saisir un 2ème entier: ");
	scanf("%d", &b);
	
	result = a+b;
	printf("résultat de la somme: %d\n" ,result);
}
