#!/bin/bash
#Author: Ahir A.

DAY=$1
if [ $# -eq 0 ]
then DAY=$(date +%u)
fi

if ["$DAY" -lt 7 ]
then
let X=7-$DAY
echo "$X days to Sunday"

elif [ "$DAY" -eq 7 ]
then
echo "Have a nice Sunday!"

else
let Z=$DAY-7
echo "with this number you're $Z days out of bound"
fi

