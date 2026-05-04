#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#define DIMR 10
#define DIMC 2

int MCD(int num1, int num2){
	while(num1 != num2){
		if(num1 > num2)
			num1 -= num2;
		else
			num2 -= num1;
	}
	return num1;
}

int main(void){
	int mat[DIMR][DIMC], i, j, mcd;
	
	srand(time(NULL));
	for(i = 0; i < DIMR; i++)
		mat[i][0] = rand() % 91 + 10;
	
	for(i = 0; i < DIMR; i++)
		mat[i][1] = rand() % (100 - mat[i][0] + 1) + mat[i][0];	
	
	for(i = 0; i < DIMR; i++){
		for(j = 0; j < DIMC; j++)
			printf("%d ", mat[i][j] );
		printf("\n");
	}
	printf("\n");
	printf("Frazioni ai minimi termini\n");
	printf("\n");	
	for(i = 0; i < DIMR; i++){
		mcd = MCD(mat[i][0], mat[i][1]);
		printf("%d %d", mat[i][0] /mcd, mat[i][1] / mcd);
		printf("\n");
	}
		
	return 0;
}
