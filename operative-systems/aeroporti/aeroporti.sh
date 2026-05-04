#!/bin/bash
#Author Ahir A.

SIGLA=$1
FILE=AirportList.txt

if [ $# -eq 0 ]
then
while [ "$SIGLA" != "exit" ]
do
echo "Inserire una sigla (exit per uscire):"
read SIGLA
echo "$(grep -i $SIGLA $FILE)"
done
fi

for SIGLA 
do
echo "$(grep -i $SIGLA $FILE)"
done
