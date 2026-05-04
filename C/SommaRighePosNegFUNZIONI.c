#include <stdio.h>
#define DIMR 5
#define DIMC 3
#include <time.h>

int sommaNeg(int vet[]){
	int tot = 0, i = 0;
	
	for( i = 0; i < DIMC; i++){
		if(vet[i] < 0) tot += vet[i];
	}
	return tot;
}

int sommaPos(int vet[]){
	int tot = 0, i = 0;
	
	for( i = 0; i < DIMC; i++){
		if(vet[i] > 0) tot += vet[i];
	}
	return tot;
}

int main(void){
	int mat[DIMR][DIMC], i, j, sommaN, sommaP;
	srand(time(NULL));
	
		for( i = 0; i < DIMR; i++){
			for(j = 0; j < DIMC; j++){
				mat[i][j] = rand() % 101 - 50;
				printf("%d ", mat[i][j]);
			}
		printf("\n");
		}
	
	for( i = 0; i < DIMR; i++){
		sommaN = sommaNeg(mat[i]);
		sommaP = sommaPos(mat[i]);
		printf("riga %d somma negativi: %d\n", i, sommaN);
		printf("       somma positivi: %d\n", sommaP);
	}
	
	return 0;
}
