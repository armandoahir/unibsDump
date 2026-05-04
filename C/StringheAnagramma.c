#include <stdio.h>
#define DIM 20
#include <time.h>
#include <string.h>

int main(void){
	char parola[DIM], anagramma[DIM] = "";//inizializzo anagramma con il carattere di fine stringa \0
	int i, j, pos;
	
	scanf("%19s", parola);
	//printf("%s", parola);
	srand(time(NULL));
	for ( j = 0; j < 5; j++) {
	
		for( i = 0; i < strlen(parola); i++){ 
		
		do{
			pos = rand() %strlen(parola);
		} while (anagramma[pos] != '\0');
		
		anagramma[pos] = parola[i];
	}
	anagramma[strlen(parola)] = '\0';
	printf("%s", anagramma);	
	
	}
	return 0;
}
