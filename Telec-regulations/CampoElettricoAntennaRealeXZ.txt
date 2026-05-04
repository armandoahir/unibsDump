close all
clear all
clc

x=-50:1:50;
z=-50:1:50;

[X, Z]=meshgrid(x, z);
Pt=10;
n=377;
Rxz=sqrt((X).^2+(Z).^2);
G=10.*(X)./(Rxz);
Exz=abs(sqrt((n.*Pt.*G.^2)./(2.*pi.*Rxz.^2)));

figure
c = contour(X, Z, Exz, [1 6 20]);
xlabel('x');ylabel('z');clabel(c);