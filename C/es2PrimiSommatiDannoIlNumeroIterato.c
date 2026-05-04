#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define DIM 10

int primo( int numero){
	int i;
	for(i = 2; i < numero; i++){
		if(numero % i == 0) return 0;
	}
	return 1;
}

void soluzione(int n, int *pj, int *pk){
	int i;
	for( i = 2; i < n; i++){
		if(primo(i) == 1 && primo(n-i) == 1){
			*pj = i;
			*pk = n -i;
			break;
		}
	}
	
}

int main(void){
	int vett[DIM], i, j, k = 2;
	srand(time(NULL));
	
	 for( i = 0; i < DIM - 1; i++){
	 	vett[i] = rand() % 101 + 3;
	 	printf("%d ", vett[i]);
	 }
	 
	 printf("\n-->");
	 scanf("%d", &vett[DIM-1]);
	 
	 for( i = 0; i < DIM; i++){
	 	if(vett[i]%2 == 0){
	 		j = vett[i];
			soluzione(vett[i], &j, &k);
			printf("\n%d = %d + %d", vett[i], j, k);
		}
		k = 2;
	 }
	 
}
