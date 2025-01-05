#!/bin/bash



MainMenu()
{
    while [ "$CHOICE" != "START" ]
    do
    clear
        echo "================================================================="
        echo "|                     SOCCER LEAGUE DATABASE                    |"
        echo "|             Main Menu - Select Desired Operation(s):          |"
        echo "|         <CTRL-Z Anytime to Enter Interactive CMD Prompt>      |"
        echo "-----------------------------------------------------------------"
        echo " $IS_SELECTEDM M) View Manual"
        echo " "
        echo " $IS_SELECTED1 1) Drop Tables"
        echo " $IS_SELECTED2 2) Create Tables"
        echo " $IS_SELECTED3 3) Populate Tables"
        echo " $IS_SELECTED4 4) Query Tables"
	echo " $IS_SELECTED5 5) View Tables"
        echo " "
        echo " $IS_SELECTEDX X) Force/Stop/Kill Oracle DB"
        echo " "
        echo " $IS_SELECTEDE E) End/Exit"
        echo "Choose: "
        read CHOICE
        if [ "$CHOICE" == "0" ]
        then
            echo "Nothing Here"
	    sleep 5
        elif [ "$CHOICE" == "1" ]
        then
            bash drop_tables.sh
	   echo "Tables dropped. Press any key to continue"
            while true; do
    		read -rsn1 key  # Read a single character silently
    		if [[ -n "$key" ]]; then
        		echo "Key pressed. Continuing..."
        		break  # Exit the loop if a key is pressed
    		fi
	    done
        elif [ "$CHOICE" == "2" ]
        then
         bash create_tables.sh
           echo "Tables created. Press any key to continue"
            while true; do
                read -rsn1 key  # Read a single character silently
                if [[ -n "$key" ]]; then
                        echo "Key pressed. Continuing..."
                        break  # Exit the loop if a key is pressed
                fi
            done
        elif [ "$CHOICE" == "3" ]
        then
            bash populate_tables.sh
           echo "Tables populated. Press any key to continue"
            while true; do
                read -rsn1 key  # Read a single character silently
                if [[ -n "$key" ]]; then
                        echo "Key pressed. Continuing..."
                        break  # Exit the loop if a key is pressed
                fi
            done
        elif [ "$CHOICE" == "4" ]
        then
           bash queries.sh
           echo "Tables dropped. Press any key to continue"
            while true; do
                read -rsn1 key  # Read a single character silently
                if [[ -n "$key" ]]; then
                        echo "Key pressed. Continuing..."
                        break  # Exit the loop if a key is pressed
                fi
            done
	elif [ "$CHOICE" == "5" ]
        then
            bash views.sh
           echo "Viewing all tables. Press any key to continue"
            while true; do
                read -rsn1 key  # Read a single character silently
                if [[ -n "$key" ]]; then
                        echo "Key pressed. Continuing..."
                        break  # Exit the loop if a key is pressed
                fi
            done
        elif [ "$CHOICE" == "E" ]
        then
            exit
        fi
    done
}

#--COMMENTS BLOCK--
# Main Program
#--COMMENTS BLOCK--
ProgramStart()
{
    StartMessage
    while [ 1 ]
    do
        MainMenu
    done
}

ProgramStart
