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
	int n = 8; // nombre de processus dans la file

	pid_t pid;
    pid;
    fprintf(stdout,"Avant le fork, pid = %d\n",getpid());

    for(int cpt=2; cpt<=n; cpt++)
    {
    	pid = fork();

    	if(pid == -1) {perror("fork a échoué"); exit(1);}
    	if(pid ==  0) {printf("P%d\t de pid=%d\t de ppid=%d\n", cpt, getpid(), getppid());}
    	else break;
    }
    wait(NULL);
    
	return 0;
}