#include <stdio.h>
#include <stdlib.h>             /* à compléter pour malloc() et free */
#include <assert.h>             /* à compléter pour assert() */
int main(){
         int x=1;
         int * y;
         int ** z;
         y = &x ;   /* initialisation du pointeur y */
         z = (int**) malloc(2*sizeof(int*));
         assert(z != NULL);
         z[0] = y;
         x = 2;
         z[1] = y;
         printf("*z[0]=%d\t\t*z[1]=%d\n", *z[0], *z[1]);// même valeur car adresse mémoire similaire malgré évolution du contenu
         free (z); // mémoire devient dispo
         return 0;
}
