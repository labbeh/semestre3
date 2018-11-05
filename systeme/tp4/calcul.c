#include <stdio.h>
#include <errno.h>
#include <sys/types.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main()
{
	int a, b, etat;

	printf("Sasir un premier entier: ");
	scanf("%d", &a);


	do
	{
		printf("Saisir un deuxieme entier: ");
		scanf("%d", &b);

		pid_t pid = fork();

		if(pid == -1)
		{
			printf("Erreur lors de la création du processus fils");
			exit(1);
		}

		// dans le fils
		if(pid == 0)
		{
			if(b != 0)
			{
				printf("%d / %d = %f\n", a, b, (float)a/b);
				exit(0);
			}

			else exit(1);
		}

		else wait(&etat);
	}
	while(WEXITSTATUS(etat)); // while(WEXITSTATUS(etat) == 1)

	printf("Au revoir !\n");

	return 0;
}