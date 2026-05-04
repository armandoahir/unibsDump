#include <stdio.h>
#define DIMR 10
#define DIMC 12
#include <time.h>


int iniziazlizza(int mat[DIMR][DIMC]){
	int i, j;
	srand(time(NULL));
	for( i = 0; i < DIMR; i++){
		for(j = 0; j < DIMC; j++){
			mat[i][j] = rand() % 101 - 50;
			return mat[i][j];
		}
	}
}

int main(void){
	int matrice[DIMR][DIMC], i, j;
	
	for(i = 0; i < DIMR; i++){
		for(j = 0; j < DIMC; j++){
			inizializza(matrice[i][j]);
		}
	}
	
		for( i = 0; i < DIMR; i++){
			for(j = 0; j < DIMC; j++){
				printf("%d ", matrice[i][j]);
			}
		printf("\n");
		}
	
	return 0;
}
