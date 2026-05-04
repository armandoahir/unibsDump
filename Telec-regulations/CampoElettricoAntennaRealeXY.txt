close all
clear all
clc

x=-50:1:50;
y=-50:1:50;
z=0;
[X, Y]=meshgrid(x, y);
Pt=10;
n=377;
Rxy=sqrt((X).^2+(Y).^2);
G=10;
Exy=abs(sqrt((n.*Pt.*G.^2)./(2.*pi.*Rxy.^2)));

figure
c = contour(X, Y, Exy, [1 6 20]);
xlabel('x');ylabel('y');clabel(c);


