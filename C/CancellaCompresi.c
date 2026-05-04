#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define DIM 10
#define TRUE 1
#define FALSE 0

int inferiore(int s1, int s2){
	if( s1 < s2 ) return TRUE;
	return FALSE;
}

int cancella(int v[], int s1, int s2,int n){
	int max, min;
	int i, j = 0;
	if(inferiore(s1, s2) == TRUE){
		max = s2;
		min = s1;
	} 
	else{
		max = s1;
		min = s2;
	}
	/*for(i = 0; i < n; n++){
		if(inferiore(max, v[i]) == FALSE && inferiore(v[i], min) == FALSE){
			v[j] = v[i];
			j++;
		}
	}
	return j;
	*/
	for(i = 0; i <n ; i++){
		if(inferiore(max, v[i]) || inferiore(v[i], min)){

			for( j = i+1; j < n; j++)
				v[j-1] = v[j];
		v[n--] = 0;
		i--;
	}
	}
	
	return n;
}

void visualizza(int v[], int n){
	int i;
	for(i = 0; i < n; i++){
		printf(" %d ", v[i]);
	}
}

int main(void){
	int vett[DIM] = {0}, orario1, orario2;
	int i, j;
	
	for(i = 0; i < DIM; i++){
		vett[i] = rand() % 60;
	}
	
	visualizza(vett, DIM);
	
	printf("\nInserisci il primo orario: ");
	scanf("%d", &orario1);
	printf("Inserisci il secondo orario: ");
	scanf("%d", &orario2);
	printf("\n %d ", orario1);
	printf("\n %d\n ", orario2);
	
	j = cancella(vett, orario1, orario2, DIM);
	
	printf("Orari compresi tra %d:%d e %d\n", orario1, orario2, j);
	
	visualizza(vett, j);
	
	return 0;
}
