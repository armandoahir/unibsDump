close all
clear all
clc

x=-70:1:70;
y=-70:1:70;
[X, Y]=meshgrid(x, y);
mu=4*10^(-7);
I=500;
X1=X-5;Y1=Y-0;
B= mu.*(I)./(2.*sqrt(((X1).^2+(Y1).^2)));
Bx=B.*((Y1)./(sqrt((X1).^2+(Y1).^2)));
By=B.*((X1)./(sqrt((X1).^2+(Y1).^2)));

I2=-500;
X2=X+5;Y2=Y-0;
B2= mu.*(I2)./(2.*sqrt(((X2).^2+(Y2).^2)));
Bx2=B2.*((Y2)./(sqrt((X2).^2+(Y2).^2)));
By2=B2.*((X2)./(sqrt((X2).^2+(Y2).^2)));

Bxtot=Bx+Bx2;
Bytot=By+By2;
Btot=sqrt(Bxtot.^2+Bytot.^2);

figure
c = contour(X, Y, 1e6*(Bxtot), [-100 -10 -5 -3 3 5 10 100]);
xlabel('x');ylabel('y');clabel(c)

figure
c1 = contour(X, Y, 1e6*(Bytot), [-100 -10 -5 -3 3 5 10 100]);
xlabel('x');ylabel('y'); clabel(c1)

figure
c = contour(X, Y, 1e6*(Btot), [-100 -10 -5 -3 3 5 10 100]);
xlabel('x');ylabel('y');clabel(c)

