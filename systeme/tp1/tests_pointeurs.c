#include <stdio.h>
int main(){
    int  a = 12;
    int *p = &a;
    
    printf("%d\n",   a); // affiche le contenu de a
    printf("%d\n",  &a); // affiche l'adresse mémoire de a
    printf("%d\n",  *p); // affiche le contenu de a
    printf("%d\n",   p); // affiche l'adresse mémoire de a
    
    a = a+2;
    printf("%d\n", a);// affiche le contenu de a
    
    *p = *p+2;
    printf("%d\n", a);
}
