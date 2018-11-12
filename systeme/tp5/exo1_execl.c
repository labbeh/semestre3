#include <stdio.h>
#include <unistd.h>

int main()
{
	//execl("/bin/ls", "ls", "-l", "/", NULL);
	//execlp("ls", "ls", "-l", "/", NULL);

	char * argv[] = {"ls", "-l", "/", NULL};
	execv("/bin/ls", argv);

	return 0;
}