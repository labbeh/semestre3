/*---------------------------------------------------------------------------*
*                       Occupation mémoire                                   *
* Place occupée par les différentes variables d'un programme selon leur type *
*----------------------------------------------------------------------------*/
#include <stdio.h>
#include <string.h>
int main(){
    char chaine[]="abcd";
    char c='a';
    printf("Taille de \"a\"  :\t%d\n",sizeof("a"));// chaine de caractère affiche 2 car \0 à la fin
    printf("Taille de char  :\t%d\n",sizeof(char));
    printf("Taille de int   :\t%d\n",sizeof(int));
    printf("Taille de short :\t%d\n",sizeof(short));
    printf("Taille de long  :\t%d\n",sizeof(long));
    printf("Taille de float :\t%d\n",sizeof(float));
    printf("Taille de double:\t%d\n",sizeof(double));
    printf("Taille de 1 :\t%d\n",sizeof(1));
    printf("Taille de 'a':\t%d\n",sizeof('a'));// affiche 4 car il est considéré comme un entier, caractère constant considéré comme un int
    printf("Taille de c :\t%d\n",sizeof(c));// affiche 1 car déclaré comme caractère
    printf("Taille de '\\n':\t%d\n",sizeof('\n'));
    printf("Taille de chaine:\t%d\n",sizeof(chaine));	  // affiche 5 car prend en compte le \0 de marque de fin
    printf("Longueur de la chaine:\t%d\n",strlen(chaine));// contrairement à ici ou strlen ne le prend pas en compte
    return 0;
}
