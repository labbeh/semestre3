#include <stdio.h>
#include <unistd.h>
#include <string.h>

#include <unistd.h>
#include <sys/types.h>

#include <pwd.h>

#include <errno.h>


int main()
{
	printf("Pid du processus: %d\n", getpid());
	printf("Uid de l'utilisateur: %d\n", getuid());
	printf("Nom de l'utilisateur: %s\n", getpwuid(getuid())->pw_name);

	char cmd[BUFSIZ];
	char * args[100];
	char * s;
	int i;

	while(1) // boucle infini  car pas de type booléen en C (comme while(true) en java)
	{
		printf("Donnez executable avec ou sans params ");

		if(fgets(cmd, BUFSIZ, stdin) != NULL)
		{
			for(i=0, s=cmd; (s = strtok(s, " \n")) != NULL; i++, s=NULL)
			{
				args[i] = s;
			}
			args[i] = NULL;
			execvp(args[0], args);

			printf("Erreur numéro: %d\n", errno);
			printf("Erreur message: %s\n", strerror(errno));
		}
	}

	return 0;
}
