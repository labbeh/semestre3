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
	int nbProcess; // nombre de processus dans la file

	/*pid_t pid;
    pid;
    fprintf(stdout,"Avant le fork, pid = %d\n",getpid());*/

    printf("Combien de processus à crééer ?");
    scanf("%d", &nbProcess);

    creerProc(nbProcess, 1);
    
	return 0;
}

void creerProc(int nb, int num)
{
    if(nb != 0)
    {
        pid_t pid = fork();
        printf("%d\n", pid);

        creerProc(--nb, ++num);

        //if(pid == -1) {perror("fork a échoué"); exit(1);}
        //if(pid ==  0) {printf("P%d\t de pid=%d\t de ppid=%d\n", num, getpid(), getppid());}
        
        wait(NULL);
    }
}