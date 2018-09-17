#include <stdio.h>
#include <stdlib.h> 

void generer (int tab[], int nbElt);// ou int *tab car un tableau est un pointeur constant
void afficher(int *tab , int nbElt);// adresse tableau == adresse du 1er élément
void trier	 (int tab[], int nbElt);

int intcompare(const void *p1, const void *p2);


int main()
{
	int n1;
	int *T;
	
	srand((unsigned)time(NULL)); // pour ne pas avoir les mêmes valeur aléatoires au rand
	
	do{
		printf("Donnez un entier supérieur à 2: ");
		scanf("%d", &n1);
		// vider tampon éventuellment
	}
	while(n1 <= 2);
	
	T = (int*)malloc(sizeof(int) * n1);
	generer(T, n1);
	
	printf("Tableau non trié: \n\n");
	afficher(T, n1);
	
	printf("Tableau trié: \n");
	trier(T, n1);
	afficher(T, n1);
	
	return 0;
}

void generer(int tab[], int nbElt)
{
	int i;
	for(i=0; i<nbElt; i++) tab[i] = rand()%51;
}

void afficher(int *tab, int nbElt)
{
	int i;
	for(i=0; i<nbElt; i++) printf("%d  ", *(tab+i));
	
	printf("\n");
}

int intcompare(const void *p1, const void *p2)
{
	const int *i = p1, *j = p2;
	if(*i > *j) return  1;
	if(*i < *j) return -1;
	
	return 0;
}

void trier(int tab[], int nbElt)
{
	qsort(tab, nbElt, sizeof(int), intcompare);
}


