#include <stdio.h>
#include <errno.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/wait.h>

int main()
{
	int etat;
	pid_t pid = fork();

	if(pid == -1)
	{
		printf("Erreur de fork");
		exit(1);
	}

	if(pid == 0)
	{
		printf("Je suis le fils de pid = %d\t et le pid de mon pere = %d\n", getpid(), getppid());
		sleep(20);

		exit(0);
	}

	else
	{
		printf("Je suis le père de pid = %d\t et le pid de mon fils = %d\n", getpid(), pid);
		wait(&etat);

		if(WIFEXITED(etat))
		{
			printf("fils terminé normalement avec le code de terminaison = %d\n", WIFEXITED(etat));
		}
		else printf("fils termine anormalement\n");
	}

	return 0;
}