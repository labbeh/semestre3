#include <stdio.h>
#include <errno.h>
#include <sys/types.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

void creerProc(int nb, int num);

int main()
{
	int nbProcess; // nombre de processus dans la file

    do
    {
        printf("Combien de processus à crééer ?");
        scanf("%d", &nbProcess);
    }
    while(nbProcess < 2);

    printf("P%d\t pid = %d\n", 1, getpid());

    creerProc(nbProcess, 2);
    
	return 0;
}

void creerProc(int nb, int num)
{

    pid_t pid;

    if(nb > 0)
    {
        pid = fork();

        if(pid == -1)
        {
            perror("fils a echoue");
            exit(1);
        }

        if(pid == 0) // fils
        {
            printf("P%d\t pid = %d\t ppid = %d\n", num, getpid(), getppid());
            creerProc(nb-1, num+1);
        }

        else wait(NULL);
    }
}