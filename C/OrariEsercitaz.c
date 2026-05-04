#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define DIM 10

typedef struct{
	int ore, min;
} Orario;

int inferiore(Orario s1, Orario s2){
	
	if(((s1.ore)*60 + s1.min) < ((s2.ore)*60 + s2.min)) return 1;
	
	return 0;
}

int rimuovi(Orario v[], int n, Orario s1, Orario s2){
	Orario max, min;
	int i, k;
	
	if(inferiore(s1, s2) == 1){
		max = s2;
		min = s1;
	}
	else{
		min = s2;
		max = s1;
	}
	
	for(i = 0; i < n; i++){
		if(inferiore(max, v[i]) || inferiore(v[i], min))
			for(k = i; k < n; k++)
				v[k]=v[k+1];
	i--;
	}
	
	return n;
}

void visualizza(Orario v[], int n){
	int i;
	
	for(i = 0; i < n; i++){
		printf("%d:%02d\n", v[i].ore, v[i].min);
	}
}


int main(void){
	Orario vett[DIM] = {0, 0}, orario1, orario2;
	int i, n;
	
	for(i = 0; i < DIM; i++){
		vett[i].ore = rand() % 24;
		vett[i].min = rand() % 60;
	}
	
	visualizza(vett, DIM);
	
	printf("Inserisci il primo orario --> ");
	scanf("%d:%2d", &orario1.ore, &orario1.min );
	printf("Inserisci il secondo orario --> ");
	scanf("%d:%2d", &orario2.ore, &orario2.min );
	
	printf("%d:%02d\n", orario1.ore, orario1.min);
	
	n = rimuovi(vett, DIM, orario1, orario2);
	
	visualizza(vett, n);
	
	return 0;
}
