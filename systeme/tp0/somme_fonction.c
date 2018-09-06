#include<stdio.h>

int somme(int, int);
int diff(int, int);
int mult(int, int);
int div(int, int);

void main()
{
	int a, b;
	
	printf("saisir un 1er entier: ");
	scanf("%d", &a);
	
	printf("saisir un 2ème entier: ");
	scanf("%d", &b);
	
	printf("résultat de la somme: %d\n" ,somme(a, b));
}

int somme(int a, int b)
{
	int result = a+b;
	return result;
}

int diff(int a, int b)
{
	int result = a-b;
	return result;
}

int mult(int a, int b)
{
	int result = a*b;
	return result;
}

int div(int a, int b)
{
	if(b == 0) return 0;
	
	int result = a/b;
	return result;
}
