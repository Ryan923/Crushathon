
package friendzone;

import java.util.Arrays;
import java.util.Scanner;
import java.util.*;

public class AI {
    
    public static void jaunt(Classes.Hud p){
        // Determines whom to invite on a jaunt  and what activities to choose
        /* Priorities:
         * If this is the first turn
         *  1) Invite crush
         *  2) Invite all others of that level
         *  3) Choose activities based on # of interests
         *  4) Record interest hits for each invitee
         */
        
        System.out.println(" Player " + p.name + " arranges a Jaunt: \n");
        
        Queue[] jauntQueue = new Queue[5];                                          // array to hold inputs for the AI's jaunt decisions
        Queue<Classes.RelCard> inputInvite = new LinkedList<Classes.RelCard>();     // Queue to hold integer inputs
        jauntQueue[0] = inputInvite;                            // Placing invitation queue into inputs queue

        if (p.turnCount == 0){                              // First turn
            // Inviting every relation
            for (int i=0; i<p.relation_slots.length; i++){  // for each relation slot...
                if (p.relation_slots[i] != null){           // ...if there IS a relation in that slot...
                    inputInvite.add(p.relation_slots[i]);   // ...add them to the queue
                }//end if
            }//end for
        }//end if (first turn)
        

        getJauntAI(p, jauntQueue);

    }//end jaunt
    
    public static void getJauntAI(Classes.Hud p, Queue[] jauntQueue ){ // coming from player_invitation(); receives player's Hud, array of jaunt decisions
        /* Simpatico - each invitee must be of the same relCard level  */

        int fullCounter = 0;
        boolean done = false;
        Queue<Classes.RelCard> invitesQueue;
        Classes.RelCard selections;
        
        Classes.RelCard[] jauntInvites_relCard = new Classes.RelCard[RunGame.MAXPLAYERS-1];      // holds the rel. cards of all the relations invited on the jaunt
        Classes.Hud[] jauntInvites_hud = new Classes.Hud[RunGame.MAXPLAYERS-1];                  // holds the Huds of all the players invited on the jaunt 
        int action;                                 // input variable

        while (done == false){

            invitesQueue = jauntQueue[0];                                                                                  // Placing 
            selections = invitesQueue.remove();

            // putting selection in the relCard invitees array
            if (Arrays.asList(jauntInvites_relCard).contains(invitesQueue) == false){                                   // If selected relation is not already invited...
                    jauntInvites_relCard[fullCounter] = selections;                                                     // ...add selected relation to relation invite array, and...
                        for (int k=0; k < p.playersList.length; k++){                                                   // ...for each Hud slot in the game...
                            if (p.playersList[k] != null){                                                              // ...if there *is* a Hud in that slot...
                                if ( p.playersList[k].name == selections.color &&                                       // ...if the Hud's name matches the color of the selected relation, and...
                                    Arrays.asList(jauntInvites_hud).contains(p.playersList[k]) == false ){              // ...selected relation is not already invited...
                                    jauntInvites_hud[fullCounter] = p.playersList[k];                                   // ...put that Hud in the Hud invitees array
                                    System.out.println("\t Invited Player " + jauntInvites_hud[fullCounter].name);
                                }//end if
                            }//end if
                        }//end for  
                        fullCounter++;                                                                                  // incrementing the full invite list counter
            }//end if

            if (fullCounter == p.relation_slots.length)                                                                 // if the full invite list counter is maxed, end the done loop!
                done = true;

        }//end while: done

        selectActivitiesAI(p, jauntInvites_relCard, jauntInvites_hud, true);                   // CALLING the activity select function 

    }//end getJauntAI    
    
    public static void selectActivitiesAI(Classes.Hud p, Classes.RelCard[] relList, Classes.Hud[] hudList, boolean type ) {    /* receives player Hud, list of invited relations, 
     *                                                                                                          list of invited player Huds, list of ALL players, invitation type (true = jaunt; false = date) */
    //try {
        int i;  // for iteration needs
        int selectionCount = 0;                                         // global counter: total activity cards available 
        Classes.ActCard[] optionArray =     new Classes.ActCard[50];    // each available activity card is put into this array
        Classes.ActCard[] selectionArray =  new Classes.ActCard[3];     // *This length of this array is to be determined by passed variable
        int activityAdd = 0;                                            // The incremented index for adding activites to selectionArray
        Scanner input  = new Scanner(System.in);                        // input scanner   
        int action;                                                     // input variable
        boolean done = false;                                           // condition for ending the function        
        int c = 0;                                                      // quick counter
        
    // Putting the Player's activities in optionArray
        for ( i=0; i< p.actCard_slots.length; i++){                         // For each activity slot in Player Hud...
            if (p.actCard_slots[i] != null){                                // ...if there *is* an activity in that slot...
                optionArray[c] = p.actCard_slots[i];                        // ...put that activity into optionArray at index c
                c++;                                                        // increment c                
            }//end if
        }//end for
    // Putting invitees' activities in optionArray
        for ( i=0; i < hudList.length; i++ ) {                              // for each invitee slot...
            if (hudList[i] != null)                                         // ...if there *is* an invitee in that slot...
                for (int j=0; j < hudList[i].actCard_slots.length; j++){    // ...for each of her activity slots...
                    if (hudList[i].actCard_slots[j] != null)                // ...if there *is* an activity in that slot...
                        optionArray[c] = hudList[i].actCard_slots[j];       // ...put that activity into optionArray at index c.
                    c++;                                                    // increment c
                }//end for
        }//end for
        
    
        while (done == false){
            selectionCount = 0;
            
            if (selectionArray[0] == null){ 
                System.out.println( "\n\t_________________________________________");
                System.out.print("\n Select Activities for your");
                if (type == true) System.out.println(" Jaunt... ");
                else System.out.println(" Date... ");
            }//end if
            else {
                System.out.println(" Anything else?");
                //System.out.println("\n\t\t 0 - No, that's all");  // (*not currently useful - player should always pick maximum activities)
            }//end else
        // Printing Player's selection
            System.out.println("\t My Activities: ");
            for (i=0; i< p.actCard_slots.length; i++){                                                                          // For each of the Player's activity slots...
                if (optionArray[selectionCount] != null){                                                                                // ...if there *is* an activity in that slot...
                    System.out.println( "\t\t " + (selectionCount+1) + " - " + optionArray[selectionCount].title);              // ...print the selectionCount counter and the action card's title!
                    System.out.print(   "\t\t\t involves: ");
                    for (int k=0; k < optionArray[selectionCount].interests.length; k++){                           // For each interest slot (up to 6 slots) on the current activity card...
                        if (optionArray[selectionCount].interests[k] != null){                                      // ...if there *is* an interest in this slot...
                            System.out.print( optionArray[selectionCount].interests[k]);                            // ...print the interest, and...
                            if (k < optionArray[selectionCount].interests.length-1)                                 // ...if the current interest slot is not the last, and...
                                if(optionArray[selectionCount].interests[k+1] != null )                             // ...the slot after it is not null...
                                    System.out.print(", ");                                                         // ...print a comma!
                        }//end if
                    }//end for
                    System.out.println();   // carriage return!
                }//end if
                selectionCount++;                 
            }//end for
        // Printing Invitees' selection
            for ( i=0; i < relList.length; i++ ) {                                                                              // For each invitee slot...
                if (relList[i] != null){                                                                                        // ...if there *is* an invitee in that slot...
                    System.out.println( "\n\t " + relList[i].color + ": " + relList[i].level + "" );                            // ...print her color & relation level, and...
                        for (int j=0; j < hudList[i].actCard_slots.length; j++){                                                // ...for each of this player's activity card slots (3)...
                            if (optionArray[selectionCount] != null){                                                           // ...if there *is* an activity in that slot...
                            System.out.println( "\t\t " + (selectionCount+1) + " - " + optionArray[selectionCount].title);      // ...print the selectionCount counter and the action card's title!
                            System.out.print(   "\t\t\t involves: ");
                                for (int k=0; k < optionArray[selectionCount].interests.length; k++){                           // For each interest slot (up to 6 slots) on the current activity card...
                                    if (optionArray[selectionCount].interests[k] != null){                                      // ...if there *is* an interest in this slot...
                                        System.out.print( optionArray[selectionCount].interests[k]);                            // ...print the interest, and...
                                        if (k < optionArray[selectionCount].interests.length-1)                                 // ...if the current interest slot is not the last, and...
                                            if(optionArray[selectionCount].interests[k+1] != null )                             // ...the slot after it is not null...
                                                System.out.print(", ");                                                         // ...print a comma!
                                    }//end if
                                }//end for
                                System.out.println();   // carriage return!
                            }//end if 
                        selectionCount++;                                                                                       // ...increment the selectionCount counter, and...                
                        }//end for
                }//end if
            }//end for
            action = input.nextInt();                                                                                   // scannin' fer input!    
            if (action == 0){
                done = true;
            }//end if
            if ( Arrays.asList(selectionArray).contains(optionArray[action-1]) == false ){                             // If the selected activity has not previously been selected...
                selectionArray[activityAdd] = optionArray[action-1];                                                   // ...put the selected activity into the selection array
                activityAdd++;                                                                                         // increment selection array's index pointer
            } else System.out.println("That activity has already been selected!");
            
            if (selectionArray[selectionArray.length-1] != null){               // if selectionArray is full...
                System.out.println("Activities chosen!");
                done = true;                                                    // ...end activity choosing!
            }//end if            
            /*
            if (action == 0){                                                   // if player chooses 0...
                System.out.println("Activities chosen!");   
                done = true;                                                    // ...end activity choosing! (*not currently useful, but possibly in the future)
            }//end if
            */            
            System.out.println("\t Activities:");                                               // After each selection, print the activities chosen so far.
            
            for (i=0; i < selectionArray.length; i++){                                                        // For each selected activity slot...
                if (selectionArray[i] != null) {                                                              // ...if there *is* an activity in that slot...
                System.out.println("\t\t " + selectionArray[i].title);                                        // ...print the activity title!
                System.out.print(   "\t\t\t involves: ");
                    for (int k=0; k < selectionArray[i].interests.length; k++){                    // For each interest slot (up to 6 slots) on the current activity card...
                        if (selectionArray[i].interests[k] != null){                               // ...if there *is* an interest in this slot...
                            System.out.print( selectionArray[i].interests[k]);                     // ...print the interest, and...
                            if (k < selectionArray[i].interests.length-1)                          // ...if the current interest slot is not the last, and...
                                if(selectionArray[i].interests[k+1] != null )                      // ...the slot after it is not null...
                                    System.out.print(", ");                                        // ...print a comma!
                        }//end if
                    }//end for
                System.out.println("\n");   // double carriage return!
                }//end if
            }//end for
            
            if ( Arrays.asList(optionArray).contains(selectionArray[activityAdd-1]) )                         // If the chosen activity is indeed in the optionArray...
                optionArray[action-1] = null;                                                                 // ...delete it from the array so it won't print in the next loop
        }//end while
        System.out.print("\n Would you like to launch this ");                                                // end output
        if (type == true)
            System.out.println("Jaunt?");
        if (type == false)
            System.out.println("Date?");
        System.out.println("\t 0 - No, let me re-choose the activities...");
        System.out.println("\t 1 - Yes, launch it!");

        action = input.nextInt();                                                                                       // Scanning for input

    } //end selectActivities    
    
    
}//end file
