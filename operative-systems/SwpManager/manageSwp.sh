#!/bin/bash
#Author: A. Ahir

manageSwp () 
{
	if [ -w "$FILE" ]
	then
		echo "Delete "$FILE" [Y/N]:"
		read "ANSWER"

	case $ANSWER in 

		"Y" | "y" )
			rm "$FILE";
			echo "Deleted: $FILE"
			;;

		"N" | "n" )
			echo  "NOT deleted: $FILE."
			;;

		* )
			echo "INPUT ERROR"
			;;
	esac

	else
		echo "PERMISSION ERROR. Cannot delete $FILE."
	fi
}

if [ $# -eq 0 ]
	then
		echo "No PARAM MODE"
		NAME=$(find . -name ".*.swp" 2>/dev/null)
		echo "$NAME"

		for FILE in $NAME
		do
			manageSwp
		done
fi
