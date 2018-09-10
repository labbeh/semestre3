/*--------------------------------------------------------*
*                    Allocation mémoire                   *
*  Disposition des différentes variables d'un programme   *
*---------------------------------------------------------*/
#include <stdio.h>
#include <stdlib.h>

 int a,b ; // varaibles globales utilisables dans toutes les fonctions

 void fonc(int c , int *d){
        int e,f;
        int *p,*q;
        p=&e ;
        q =(int*) malloc(sizeof(int));
        printf("L'adresse de a :\t%x\n",&a);
        printf("L'adresse de b :\t%x\n",&b);
        printf("L'adresse de c :\t%x\n",&c);
        printf("L'adresse de d :\t%x\n",&d);
        printf("L'adresse de e :\t%x\n",&e);
        printf("L'adresse de p :\t%x\n",&p);
        printf("L'adresse de q :\t%x\n",&q);
        printf("La valeur de p :\t%x\n", p);
        printf("La valeur de q :\t%x\n", q);
        printf("L'adresse de fonc :\t%x\n",&fonc);

}
int main(){
        fonc(a,&b);
        return 0 ;

}
