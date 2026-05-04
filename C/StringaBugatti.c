#include <stdio.h>
#define DIM 50

int main(void){
	char vet[DIM], vet2[DIM], i, j;
	
	gets(vet);
	printf("%s\n", vet);
	j = 0;
	for( i = 0; i < strnlen(vet); i++){
		if(vet[i] >= 97 && vet[i] <= 122){
			vet2[j] = vet[i];
			j++;
		}
		else if (vet[i] > 64 && vet[i] < 91) {
			vet2[j] = vet[i] + 32;
			j++;
		}
	}
	printf("%s", vet2);
	return 0;
}
