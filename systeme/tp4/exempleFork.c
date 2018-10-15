#include <stdio.h>
#include <errno.h>
#include <sys/types.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>


extern int errno;
 
int main()
{
        pid_t pid;
        pid = getpid();
        fprintf(stdout,"Avant le fork, pid = %d\n",pid);
       
       pid = fork();
    /* En cas de reussite du fork, le pere et le fils poursuivent leur execution a partir d'ici.
      La valeur de pid permet de distinguer le pere du fils. */
    switch(pid) {
        case -1 :   /* erreur dans fork() */
                    fprintf(stderr,"error %d in fork: %s\n",errno,strerror(errno));
                    exit(errno);

      case 0 :    /* on est dans le fils */
                     sleep(6);
                /* On suspend le processus pendant 6 secondes. Cela permet d'utiliser
                 la commande ps (p.ex. ps -gux) pour visualiser la liste des processus. */
                   
                    fprintf(stdout,"Dans le fils, pid = %d \t pid du pere = %d\n",getpid(),getppid());
                    break;

      default :   /* on est dans le pere */
                 fprintf(stdout,"Dans le pere, pid = %d \t pid du pere = %d \n",getpid(),getppid());
                 wait(NULL);
     }
return 0;
} 