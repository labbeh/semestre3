#include <stdio.h>
#include <string.h>
#include <stdlib.h>

/*
	Hugo Labbé, D2
*/

struct entree * creer();

struct entree{
	char mot[41];
	char definition[201];
};

int main()
{
	struct entree * glossaire[50000];

	int nbEffectif, i;

	nbEffectif = 2;

	// saisie des entrées
	for (int i = 0; i < nbEffectif; i++)
	{
		printf("Entrée %d: ", i+1);
		glossaire[i] = creer();
	}

	// affichage des entrées
	for (int i = 0; i < nbEffectif; i++)
	{
		printf("%s : %s", &glossaire[i]->mot, &glossaire[i]->definition);
	}
	return 0;
}

struct entree * creer()
{
	struct entree e;

	printf("mot: ");
	fgets(e.mot, 41, stdin);

	printf("définition du mot: ");
	fgets(e.definition, 201, stdin);

	struct entree * pe;
	pe = (struct entree *)malloc(sizeof(struct entree));

	pe = &e;
	return pe;
}