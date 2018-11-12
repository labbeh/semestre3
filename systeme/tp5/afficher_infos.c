#include <stdio.h>
#include <unistd.h>
#include <string.h>

#include <unistd.h>
#include <sys/types.h>

#include <pwd.h>


int main()
{
	printf("Pid du processus: %d\n", getpid());
	printf("Uid de l'utilisateur: %d\n", getuid());
	printf("Nom de l'utilisateur: %s\n", getpwuid(getuid())->pw_name);

	return 0;
}
