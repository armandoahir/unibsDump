close all
clear all
clc

x=-50:1:50;
y=-50:1:50;
[X, Y]=meshgrid(x, y);
Pt=10;
n=377;
R=sqrt((X).^2+(Y).^2);
E=abs(sqrt((n*Pt)./(2.*pi.*R.^2)));

figure
c = contour(X, Y, E, [1 6 20]);
xlabel('x');ylabel('y');clabel(c);