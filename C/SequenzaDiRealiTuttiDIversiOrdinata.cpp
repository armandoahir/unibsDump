#include <stdio.h>
#include <stdlib.h>
#define DIM 10

int cerca(float v[], int n, float trova){
	int i;
	
	for( i = 0; i < n; i++)
		if (v[i] == trova) 
			return i;
		
	return -1;
}

int inserisci(float v[], int n, float nuovo){
	int i;
	
	if(cerca(v, n, nuovo) >= 0) 
		return n;
	
	for(i = n-1; i >= 0 && v[i] > nuovo; i--)
	
		v[i+1] = v[i];
		
	v[i+1] = nuovo;
	
	return n+1;
}


void visualizza(float v[], int n){
	int i = 0;
	
	for (i = 0; i < n; i++)
		printf("%.2f ", v[i]);
}

int main(void){
	float vet[DIM];
	float RealeCasuale;
	int n = 0;
	
	
	do{
		RealeCasuale = (rand() % 101) / 100.0; //mettendo .0 a 100 lo rendiamo float
		n = inserisci(vet, n, RealeCasuale);
		
	} while( n < DIM);
	
	visualizza(vet, n);
		
	return 0;
}
