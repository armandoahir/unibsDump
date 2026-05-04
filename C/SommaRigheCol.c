#include <stdio.h>
#include <time.h>
#define DIMR 4
#define DIMC 3

int main(void){
	srand(time(NULL));
	int vet[DIMR][DIMC], i, j, sommar, sommac;
	
	for( i = 0; i < DIMR; i++){
		for( j = 0; j < DIMC; j++){
			vet[i][j]= rand() % 9 + 1;
			printf("%d ", vet[i][j]);
		}
		printf("\n");
	}
	
	for( i = 0; i < DIMR; i++){
		sommar = 0;	
		for( j = 0; j < DIMC ; j++){
			sommar += vet[i][j];
		}
		printf("somma della riga %d: %d\n", i+1, sommar);
	}
	
	for( i = 0; i < DIMC; i++){
		sommac = 0;	
		for( j = 0; j < DIMR ; j++){
			sommac += vet[j][i];
		}
		printf("somma della colonna %d: %d\n", i+1, sommac);
	}
	
	return 0;
}
