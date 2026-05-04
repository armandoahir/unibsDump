#include <stdio.h>
#include <string.h>
#define LUN_COGNOMI 30
#define NUM_COGNOMI 50

int inserimento(char stringhe[][LUN_COGNOMI], int n, char proibita[]){
	char parola[LUN_COGNOMI];
	printf("Insersci il cognome --> ");
	scanf("%s", parola);
	
	if( strcmp(parola, "fine") != 0 && n < NUM_COGNOMI){
		strcpy(stringhe[n], parola);
		n++;
	}
	return n;
}

int cancella(char stringhe[][LUN_COGNOMI], char parola[], int n){
	int i;
	for( i = 0; i < n; n++){
		if(strcmp(stringhe[i], parola) == 0) break;
		
	}
	strcpy(stringhe[i], stringhe[n-1]);
	n = n-1;
return n;	
}

void visualizza(char stringhe[][LUN_COGNOMI], int n){
	int i;
	
	for( i = 0; i < n; i++) printf("%s\n", stringhe[i]);
	printf("%d\n", n);
}

int main(void){
	char cognomi[NUM_COGNOMI][LUN_COGNOMI], CognomeCanc[LUN_COGNOMI];
	int n = 0, n_old;
	
	do{
		n_old = n;
		n = inserimento(cognomi, n, "fine");
	}while(n != n_old );
	
	visualizza(cognomi, n);	
	
	printf("Inserisci il cognome da cancellare ");
	scanf("%s", CognomeCanc);
	
	n = cancella(cognomi, CognomeCanc, n);
	
	visualizza(cognomi, n);
	
	return 0;
}
