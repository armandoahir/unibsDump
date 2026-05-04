#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define DIM 10

int primo(int numero){
	int i, n_div = 0;
	for(i = 2; i <= numero; i++){
		if(numero % i == 0) n_div++;
	}
	
	if(n_div == 1) return 1;
	return 0;
}

int main(void){
	int vett[DIM], i;
	srand(time(NULL));
	for( i = 0; i < DIM; i++){
		vett[i] = rand() % 99 + 2;
		printf("%d ", vett[i]);
	}
	printf("\n");
	for( i = DIM -1; i >= 0; i--){
		if(primo(vett[i]) > 0) printf("%d ", vett[i]);
	}
}
