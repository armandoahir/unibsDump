#include <stdio.h>
#include <string.h>
#define DIM 50

char sposta(char v[], int num, int *p){
	
	if (v[*p] >= 97 && v[*p] <= 122)
		v[*p] = ((v[*p] - 'a' + num + 'z' - 'a' + 1) % ('z'-'a'+1)) + 'a';
	return	v[*p];
}

int main(void){
	char frase[DIM];
	int x, LunghezzaFrase, i;
	
	gets(frase);
	LunghezzaFrase = strlen(frase);
	printf("%s -- Lunghezza frase: %d", frase, LunghezzaFrase);
	
	printf("\nInserisci un intero x tale che |x| <= 10\n");
	
	do{
		printf("-->");
		scanf("%d", &x);
	}while( x < -10 || x > 10);
	
	
	for( i = 0; i < LunghezzaFrase; i++){
		frase[i] = sposta(frase, x, &i);
	}
	printf("%s", frase);
	
	return 0;
}
