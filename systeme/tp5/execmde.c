#include <stdio.h>
#include <unistd.h>
#include <string.h>

int main()
{
	char cmd[BUFSIZ]; // cosntante externe

	printf("Saisir commande et ses éventuelles paramètres ");

	if(fgets(cmd, BUFSIZ, stdin) != NULL)
	{
		cmd[strlen(cmd) -1] = '\0';
		execlp("bash", "bash", "-c", cmd, NULL);
	}

	//execv("/bin/ls", argv);

	return 0;
}