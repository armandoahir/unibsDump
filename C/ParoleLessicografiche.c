#include <stdio.h>
#include <string.h>
#define DIMR 10
#define DIMC 20

int main(void){
	char parole[DIMR][DIMC], ultima[DIMC] ="";
	int i, conta = 0;
	
	for(i = 0; i < DIMR; i++){
		printf("-->");
		scanf("%s", parole[i]);
	}
	
	for(i = 0; i < DIMR; i++){
		//strcmp produce 0 se le due stringhe coincidono, un dato positivo se il primo carattere che non coincide ha un valore maggiore in str1 rispetto a str2
		//un dato negativo se il primo carattere che non coincide ha un valore minore in str1 rispetto a str2
		if(strcmp(parole[i], ultima) > 0) strcpy(ultima, parole[i]);
		
		if(strcmp(parole[i], parole[DIMR -1])< 0) conta++;
	}
	
	printf("Numero di parole che precedono %s: %d\n", parole[DIMR-1], conta);
	printf("L'ultima parola in ordine lessicografico: %s", ultima);
	return 0;
}
