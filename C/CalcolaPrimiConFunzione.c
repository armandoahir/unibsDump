#include <stdio.h>
#define DIM 10

int primo(int x){
	int i;
	
	for(i = 2; i < x; i++){
		if( x % i == 0 ) return 0;
	}
	return 1;
}

void calcolaPrimi( int *sup, int *inf, int x){
	int i = x-1;
	
	while(primo(i) == 0) i--;
	*inf = i;
	
	i = x+1;
	while(primo(i) == 0) i++;
	*sup = i;
}

int main(void){
	int vet[DIM], i, primoInf, primoSup;
	
	for( i = 0; i < DIM; i++){
		vet[i] = rand() % 90 + 10;
		printf("%d ", vet[i]);
	}
	
	printf("\n");
	
	for( i = 0; i < DIM; i++){
		calcolaPrimi(&primoSup, &primoInf, vet[i]);
		printf("Primi sup e inf a %2d: %3d , %3d\n", vet[i], primoSup, primoInf);
	}
	
	return 0;
}
