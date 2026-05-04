#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define DIM 10
#define TRUE 1
#define FALSE 0


typedef struct{
	int min;
	int h;
}Orario;

Orario estraiOrario(void){
	
	Orario o;
	o.h	= rand() % 24;
	o.min = rand() % 60;
	return o;
	
}

int precede(Orario o1, Orario o2){
	
	if(o1.h < o2.h || (o1.h == o2.h && o1.min < o2.min)) return TRUE;
	return FALSE;
	
}

int minTrascorsi(Orario o1, Orario o2){
	int min;
	
	if(precede(o1, o2) == TRUE)
		min = (o2.h - o1.h) * 60 + o2.min - o1.min;
	else
		min = (o1.h - o2.h) * 60 + o1.min - o2.min;
	
	return min;	
}

void orarioPrecSucc(Orario v[], int n, Orario rif, Orario *prec, Orario *succ){
	int i;
	
	prec->h = -1;
	succ->h = -1;
	
	for( i = 0; i < n; i++){
		if(precede(rif, v[i]) == TRUE){
		if( succ->h < 0 || minTrascorsi(v[i], rif) < minTrascorsi(*succ, rif) ) 		
			*succ = v[i];
		}
		if(precede(v[i], rif) == TRUE){
			if( prec->h < 0 || minTrascorsi(v[i], rif) < minTrascorsi(*prec, rif)) 
				*prec = v[i];
		}
	}
}


int main(void){
	Orario vett[DIM], OrarioRif, OrarioPrec, OrarioSucc;
	int i;
	
	for(i = 0; i < DIM; i++){
		vett[i]= estraiOrario();   //estriOrario è una funzione senza parametri.
		printf("%d:%02d\n", vett[i].h, vett[i].min);
	}
	
	printf("Inserisci un oarario: ");
	scanf("%d:%d", &OrarioRif.h, &OrarioRif.min);
	
	orarioPrecSucc(vett, DIM, OrarioRif, &OrarioPrec, &OrarioSucc);
	
	printf("Orario appena precedente: %d:%02d\n",  OrarioPrec.h, OrarioPrec.min);
	printf("Orario appena successivo: %d:%02d",  OrarioSucc.h, OrarioSucc.min);
	return 0;
}
