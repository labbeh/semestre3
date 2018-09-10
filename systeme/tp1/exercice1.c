int *p ;  			// déclaration d'un pointeur vers un int
int *a, b ;  		// déclaration d'un pointeur vers un int et d'un int
int *p[10] ;  		// déclaration d'un tableau de 10 pointeurs vers des int
int (*p)[10] ; 		// pointeur qui pointe sur un tableau de 10 entiers (bien voir la différence avec celui du dessus)
char *p() ;  		// c'est une fonction qui n'a pas de paramètre et qui retourne un pointeur vers un char
char (*p)() ; 		// p est un pointeur sur une fonction qui retourne un char
int (*p)(char *a) ; // p est un pointeur sur une fonction qui prend en paramètre un pointeur vers un char et retourne un int
