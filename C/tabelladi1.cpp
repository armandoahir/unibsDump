#include <stdio.h>
#include <stdlib.h>

int main(void)
{
	int n;
	int c = 1;
	int a = 1;
	
	printf("Inserire un numero naturale ");
	scanf("%d", &n);
	while( c <= n ){
		while( a <= n){
			if( a <= c ) printf(" 1");
			else printf(" 0");
			a++;
		}
		c++;
		a=1;
		printf("\n");
	}
	system("pause");
	return 0;
}
