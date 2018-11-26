#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <stdlib.h>

int main(){
	if(fork()==0){
	//execlp("xterm","xterm","-e","gedit","winTest.c",NULL); 
	/*	execlp("xterm","xterm","-e","cat","winTest.c",NULL); */
	//execlp("xterm","xterm","-e","sh","-c","cat winTest.c ;nedit winTest.c",NULL); 
		execlp("xterm","xterm","-hold","-e","sh","-c","cat winTest.c",NULL); 
		exit(1);
	}
	wait(NULL);
		
}
