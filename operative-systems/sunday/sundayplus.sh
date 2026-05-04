#!/bin/bash
#Author: Ahir A.
GIORNO=$1

if [ $# -eq 0 ] 
then
GIORNO=$(date +%u) #acquisizione della data dal sistema
fi

if [ "$GIORNO" -lt 7 ]
then 
let X=7-$GIORNO
echo "Mancano $X giorno/i a Domenica."

elif [ "$GIORNO" -eq 7 ]
then 
echo "Buona domenica!"

else 
let Z=$GIORNO-7
echo "Con il valore inserito, la settimana avrebbe $Z giorno/i di troppo..."
fi

