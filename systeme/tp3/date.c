#include <stdio.h> 
#include <stdlib.h>
#include <string.h>

char * tabMois[] ={"janvier","fevrier","mars","avril","mai","juin","juillet","aout","septembre","octobre","novembre","decembre"} ;


struct date
{
	unsigned short  num	   ;
	char  		   mois[10];
	unsigned short annee   ;
};

void afficherDate( struct date date )
{
	printf("%d %s %d\n", date.num, date.mois, date.annee);
}

void demanderDate( struct date * pdate )
{
	scanf("%2hu %s %4d", &pdate->num, &pdate->mois, &pdate->annee);
}

int numeroMois( char *nomMois )
{
	int length = 12;
	int cpt;

	for(cpt=0; cpt<length; cpt++)
		if(strcmp(tabMois[cpt], nomMois) == 0)
			return cpt+1;

	return -1;
}

/*int comparerDate (struct date date1, struct date date2)
{
	if(date1.num == date2.num && date1.mois == date2.mois && date1.annee == date2.annee)
		return 0;
	return -1;
}*/

void main()
{
	struct date d={23, "mai", 2012};

	struct date * d2;
	d2 = (struct date *)malloc(sizeof(struct date));
	/*afficherDate(d);

	struct date d2;

	printf("%s", "saisir date: ");
	demanderDate(&d2);
	afficherDate(d2);*/
	printf("%d\n", numeroMois("juillet"));
}