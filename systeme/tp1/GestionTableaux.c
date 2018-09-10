#include <stdio.h>

void generer(int tab[], int nbElt);
void afficher(int tab[], int nbElt);

int main()
{
	int n1;
	int *t;
	
	do{
		printf("Donnez un entier supérieur à 2: ");
		scanf("%d", &n1);
		// vider tampon éventuellment
	}
	while(n1 <= 2)
	
	return 0;
}
