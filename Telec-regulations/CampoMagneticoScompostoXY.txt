close all
clear all

x=-70:1:70;
y=-70:1:70;
[X, Y]=meshgrid(x, y);
mu=4*10^(-7);
I=1000;
B= mu.*(I)./(2.*sqrt(((X-10).^2+(Y-10).^2)));
Bx=B.*((Y-10)./(sqrt((X-10).^2+(Y-10).^2)));
By=B.*((X-10)./(sqrt((X-10).^2+(Y-10).^2)));
Btot=sqrt(Bx.^2+By.^2);

figure
c = contour(X, Y, 1e6*abs(Bx), [3 5 10 100]);
xlabel('x');ylabel('y');clabel(c)

figure
c1 = contour(X, Y, 1e6*abs(By), [3 5 10 100]);
xlabel('x');ylabel('y'); clabel(c1)

figure
c2 = contour(X, Y, 1e6*abs(Btot), [3 5 10 100]);
xlabel('x');ylabel('y'); clabel(c2)