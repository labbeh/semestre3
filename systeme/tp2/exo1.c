#include<string.h>
#include <stdlib.h>

#define TAILLECHAINE 12

char s0[10]="numero 1";
char* s1="numero 2";

char* cree_copie_chaine(char*src)
{
    char* copie; //copie est de quel type ?
    copie = (char*) malloc (strlen(src)+1); //expliquer le +1: caractère \0 à la fin pas pris en compte dans le strlen
    strcpy (copie , src); /* copie=destination, src=source*/
    return copie; //retourne-t-on la chaîne copiée ou un pointeur sur celle-ci ?
}
int main()
{
    char s2[TAILLECHAINE];
    char s3[]="numero 3";
    char *pa, *pb, *pc, *pe;
    pa = s1;
    pb = s0;
    strcpy (s2, "A + tard");
    pc = cree_copie_chaine(s0);
    pe = "numero 4";
    free(pc);
    return 0;
}
