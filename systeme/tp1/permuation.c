#include <stdio.h>

void permuter(int *a, int *b)
{
	int temp = *a;
	*a = *b;
	*b = temp;
}

int main()
{
	int nbr1 = 12;
	int nbr2 = 23;
	
	printf("Avant permutation nbr1= %d nbr2= %d\n", nbr1, nbr2);
	permuter(&nbr1, &nbr2);
	printf("Après permutation nbr1= %d nbr2= %d\n", nbr1, nbr2);
	
	return 0;
}
