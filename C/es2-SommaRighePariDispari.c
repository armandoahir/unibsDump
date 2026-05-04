#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define R 10
#define C 10

void somma(int v[], int *pari, int *dispari){
	int i, sommap = 0, sommadisp = 0;
	
	for(i = 0; i < C; i++){
		if (v[i] % 2 == 0)
			sommap = sommap + v[i];
		else
			sommadisp = sommadisp + v[i];
	}
	
	*pari = sommap;
	*dispari = sommadisp;

}


int main(void){
	int mat[R][C], pari[R] = {0}, dispari[R] = {0}, i, j;
	
	
	srand(time(NULL));
	
	for(i = 0; i < R; i++){
		for(j = 0; j < C; j++){
			mat[i][j] = (rand() % 90 ) + 10;
			printf("%2d ", mat[i][j]);
		}
		somma(mat[i], &pari[i], &dispari[i]);
		printf("\n");
	}
	printf("\n");
	
	for(i = 0; i < R; i++)
		printf("%4d", pari[i]);
		
	printf("\n");
	
	for(i = 0; i < R; i++)
		printf("%4d", dispari[i]);
		
	return 0;	
}
