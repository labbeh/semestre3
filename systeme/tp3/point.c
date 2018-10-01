#include <stdio.h> 

struct pointPondere{
	char nom;
	float x, y, poids;
};

void affichePointPondere(struct pointPondere P){
	printf("Le point %c de coordonnées (%.2f,%.2f) et de poids %.2f.\n",
		P.nom,P.x,P.y,P.poids);	
}

void lirePointPondere(struct pointPondere *P){
	printf("Donnez le nom, les 2 coordonnées et le poids du point : ");
	scanf("%c%f%f%f",&P->nom,&P->x,&P->y,&P->poids);
}

struct pointPondere barycentre2(struct pointPondere A, struct pointPondere B){
	struct pointPondere G;
	G.nom='G';
	G.poids=A.poids+B.poids;
	G.x=(A.x*A.poids+B.x*B.poids)/G.poids;
	G.y=(A.y*A.poids+B.y*B.poids)/G.poids;
	return G;
}

int main(void) { 
	struct pointPondere A={'A',1,2,3}, B, G;

	affichePointPondere(A);
	lirePointPondere(&B);
	affichePointPondere(B);
	G=barycentre2(A,B);
	affichePointPondere(G);

	return 0;
}