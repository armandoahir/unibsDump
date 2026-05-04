#include <stdio.h>
#include <time.h>
#define DIMR 5
#define DIMC 12

int main(void){
	int tab[DIMR][DIMC], i, j, somma;
	srand(time(NULL));
	
	for( i = 0; i < DIMR; i++){
		for( j = 0; j < DIMC; j++){
			tab[i][j] = (rand() % 101) - 20;
			printf("%4d  ", tab[i][j]);
		}
		printf("\n");
	}
	
	for( i = 0; i < DIMC; i++){
		somma = 0;
		for(j = 0; j < DIMR; j++){
			somma += tab[j][i];
		}
		printf("\nSomma riga %d: %d", j, somma);
	}
	
	return 0;
}
