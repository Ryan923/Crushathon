package friendzone;

import java.util.Random;
import java.util.*;

public class Manipulations {
    
    public static void manipulationTarget(Classes.Hud p){
        boolean done = false;
        int optionCounter = 0;
        int selectionCounter = 0;
        Classes.RelCard[] options_relCard = new Classes.RelCard[RunGame.MAXPLAYERS-1];       // holds all relations the player is currently *able* to date        
        Classes.RelCard[] manipulate_relCard = new Classes.RelCard[RunGame.MAXDATES];            // holds the rel. card of whomever the player invites on the date
        Classes.Hud[] manipulate_hud = new Classes.Hud[RunGame.MAXPLAYERS-1];                    // holds the Hud of whomever the player invites on the date       
        Scanner input  = new Scanner(System.in);                                                    // input scanner   
        int action = -1;                                                                                 // input variable
        
        for (int j=0; j < p.relation_slots.length; j++){                                            // For all player slots...
            if (p.relation_slots[j] != null){                                                       // ...if there *is* a player in that slot...
                if ( Arrays.asList(manipulate_relCard).contains(p.relation_slots[j]) == false  &&   // ...if they aren't invited yet, and...   
                    p.relation_slots[j].value != 0 &&                                               // ...they aren't of level: Stranger, and...
                    p.relation_slots[j].value != RunGame.HATE_VALUE){                            // ...they aren't of level: Hate...            
                    options_relCard[selectionCounter] = p.relation_slots[j];                           // ...put them in the options array
                    selectionCounter++;
                }//end if
            }//end if
        }//end for         
        
        while (done == false){                                                                      // finished with input condition
            if (manipulate_relCard[0] == null && options_relCard[0] != null){                       // If no one is currently invited...
                System.out.println( " Whom would you like to manipulate?"); 
                System.out.println( "\t 0 - No one, that's who! (Go back)"); 

            }//end if
            else if (options_relCard[0] != null) {
                System.out.println( " You've chosen to manipulate " + manipulate_relCard[0].color);
                System.out.println( "\t Are you sure?");
                System.out.println( "\t 0 - No, let me choose again");
                System.out.println( "\t 1 - Yes, let's do this!");
                
            // input error check loop
            action = -1;
            while (action < 0 || action > 1)
                action = input.nextInt();                                                               // scanning for input
                
            if (action == 0)
                manipulationTarget(p);                                                                  // 0 - recursive call
            if (action == 1)
                manipulationType(p, manipulate_relCard[0], manipulate_hud[0]);                          // call manipulationType                                                   
            }//end else
            
            for (int j=0; j < p.relation_slots.length; j++){                                            // For all player slots...
                if (p.relation_slots[j] != null){                                                       // ...if there *is* a player in that slot...
                    if ( Arrays.asList(manipulate_relCard).contains(p.relation_slots[j]) == false  &&   // ...if they aren't invited yet, and...   
                        p.relation_slots[j].value != 0 &&                                               // ...they aren't of level: Stranger, and...
                        p.relation_slots[j].value != RunGame.HATE_VALUE){                            // ...they aren't of level: Hate...            

                        System.out.println( "\t " + (optionCounter+1) + " - " +                         // ...print their option number, and...
                                            options_relCard[optionCounter].color + 
                                            ": " + options_relCard[optionCounter].level );              // ...print their color, and current relationship level to the player
                        optionCounter++;                    
                    }//end if
                }//end if
            }//end for     
            
            if (options_relCard[0] == null){                                                            // If there are no manipulatable relations...
                System.out.println( " None of your relations may be manipulated at "
                                    + "their current level.");
                done = true;
                System.out.println( "\t 0 - Go back to main menu"); 
            }//end if  
        
            // input error check loop
            action = -1;
            while (action < 0 || action > options_relCard.length)
                action = input.nextInt();                                                                   // scanning for input

            if (action == 0){                                                                               // If the user enters "0" end while loop and go to menu
                done = true;
                Functions.mainMenu(p);                                                          
            }//end if

            manipulate_relCard[0] = options_relCard[action-1];                                              // put selected relation into the relCard invitees array

            // putting selection in the hud invitee array
            for (int k=0; k < p.playersList.length; k++){                                                   // for each player slot in the game...
                if (p.playersList[k] != null){                                                              // ...if there *is* a player in that slot...
                    if ( p.playersList[k].name == options_relCard[action-1].color ){                        // ...if the player's name matches the color of the selected relation...
                        manipulate_hud[0] = p.playersList[k];                                               // ...put that player in the Hud invitees array
                    }//end if        
                }//end if 
            }//end for
        }//end while

    }//end manipulationTarget    
    
    public static void manipulationType(Classes.Hud p, Classes.RelCard targR, Classes.Hud targH){ // Player's Hud, target's relationship card, target's Hud
        String[] manipulations = {"Activity Swap", "Snoop", "Cold Shoulder", "Interest Swap"};
        Scanner input  = new Scanner(System.in);                                                            // input scanner   
        int action;                                                                                         // input variable
        
        System.out.println(targR.intro);
        System.out.println(" In which of the available ways would you like to manipulate Player " + targR.color+"?");
        
        if (targR.value == 0 || targR.value == RunGame.HATE_VALUE){                                // If target is a stranger or hater...
            System.out.println("Error! Relation may not be manipulated at current level");                  // ...report error, and...
            manipulationTarget(p);                                                                          // ...go back
        }//end if
        
        if (targR.value == 1){                                                                           // If target is an Acquaintance...
            System.out.println("\t 0 - Never mind, go back");            
            System.out.println("\t 1 - " + manipulations[0] );
            try{
                action = input.nextInt();
                switch (action){
                    case 0:{ 
                        manipulationTarget(p);  
                        break;
                    }
                    case 1:{
                        Manipulations.activity_swap(p, targR, targH);            // CALLING function: player_invitation
                        break;
                    }
                    default:
                        System.out.println("\t Incorrect selection - enter again");
                }//end switch
            } catch (InputMismatchException e) {    // catches if the player enters a ch rather than an int
                System.err.println("Caught Input Mismatch: " + e.getMessage());
                System.out.println( "\t Incorrect selection - enter again");
                }//end try/catch  
        }//end if
        
        if (targR.value == 2){                                                                           // If target is a Friend...
            System.out.println("\t 0 - Never mind, go back");            
            System.out.println("\t 1 - " + manipulations[0] );
            System.out.println("\t 2 - " + manipulations[1] );
            try{
                action = input.nextInt();
                switch (action){
                    case 0:{ 
                        manipulationTarget(p); 
                        break;
                    }
                    case 1:{
                        Manipulations.activity_swap(p, targR, targH);            
                        break;
                    }
                    case 2:{
                        Manipulations.snoop(p, targR, targH);
                        break;
                    }
                    default:
                        System.out.println("\t Incorrect selection - enter again");
                }//end switch
            } catch (InputMismatchException e) {    // catches if the player enters a ch rather than an int
                System.err.println("Caught Input Mismatch: " + e.getMessage());
                System.out.println( "\t Incorrect selection - enter again");
                }//end try/catch  
        }//end if       
        
        if (targR.value == 3){                                                                           // If target is an Admirer...
            System.out.println("\t 0 - Never mind, go back");            
            System.out.println("\t 1 - " + manipulations[0] );
            System.out.println("\t 2 - " + manipulations[1] );
            System.out.println("\t 3 - " + manipulations[2] + " (Power Play)" ); 
            System.out.println("\t 4 - " + manipulations[3] + " (Power Play)" ); 
            try{
                action = input.nextInt();
                switch (action){
                    case 0:{ 
                        manipulationTarget(p);  
                        break;
                    }
                    case 1:{
                        Manipulations.activity_swap(p, targR, targH);            
                        break;
                    }
                    case 2:{
                        Manipulations.snoop(p, targR, targH);
                        break;
                    }
                    case 3:{
                        cold_shoulder(p, targR, targH);
                    }
                    case 4:{
                        Manipulations.interest_swap(p, targR, targH);
                        break;
                    }
                    default:
                        System.out.println("\t Incorrect selection - enter again");
                }//end switch
            } catch (InputMismatchException e) {    // catches if the player enters a ch rather than an int
                System.err.println("Caught Input Mismatch: " + e.getMessage());
                System.out.println( "\t Incorrect selection - enter again");
                }//end try/catch  
        }//end if     
        
        if (targR.value == 4){                                                                           // If target is in Love...
            System.out.println("\t 0 - Never mind, go back");            
            System.out.println("\t 1 - " + manipulations[0] );
            System.out.println("\t 2 - " + manipulations[1] );
            System.out.println("\t 3 - " + manipulations[2] ); 
            System.out.println("\t 4 - " + manipulations[3] );            
            try{
                action = input.nextInt();
                switch (action){
                    case 0:{ 
                        manipulationTarget(p);  
                        break;
                    }
                    case 1:{
                        Manipulations.activity_swap(p, targR, targH);            
                        break;
                    }
                    case 2:{
                        Manipulations.snoop(p, targR, targH);
                        break;
                    }
                    case 3:{
                        cold_shoulder(p, targR, targH);
                    }
                    case 4:{
                        Manipulations.interest_swap(p, targR, targH);
                        break;
                    }                        
                    default:
                        System.out.println("\t Incorrect selection - enter again");
                }//end switch
            } catch (InputMismatchException e) {    // catches if the player enters a ch rather than an int
                System.err.println("Caught Input Mismatch: " + e.getMessage());
                System.out.println( "\t Incorrect selection - enter again");
                }//end try/catch  
        }//end if          
        
    }//end manipulationType    
    
    public static void activity_swap(Classes.Hud p, Classes.RelCard targR, Classes.Hud targH){
        Scanner input  = new Scanner(System.in);                                                            // input scanner   
        int actionTarg;                                                                                         // input variable    
        int actionP;
        Classes.ActCard tempCard;                                                                           // temp variable for use in swapping cards
        
        System.out.println("\t Activity Swap");
        System.out.println(" Exchange one of your activity cards with one of Player " + targR.color + "'s." );
        
        Classes.ActCard[] optionArray = new Classes.ActCard[RunGame.PLAYER_ACTCARDS];        // Array to hold available choices
        int c = 0;                                                  // Choice counter
        
        for (int j=0; j < targH.actCard_slots.length; j++){         // For each of the target's activity slots...
            if (targH.actCard_slots[j] != null)                     // ...if there *is* an activity in that slot...
                optionArray[c] = targH.actCard_slots[j];            // ...put that activity into optionArray at index c.
            c++;                                                    // increment c  
        }//end for
        System.out.println("\n\t Which of " + targH.name + "'s activities would you like to take?");
        System.out.println("\t 0 - Nevermind, go back");
        for (int i=0; i < targH.actCard_slots.length; i++){
            if (targH.actCard_slots[i] != null){
                System.out.println("\t " + (i+1) + " - " + targH.actCard_slots[i].title);
                        System.out.print(   "\t\t\t involves: ");
                             for (int k=0; k < targH.actCard_slots[i].interests.length; k++){                           // For each interest slot (up to 6 slots) on the current activity card...
                                 if (targH.actCard_slots[i].interests[k] != null){                                      // ...if there *is* an interest in this slot...
                                     System.out.print( targH.actCard_slots[i].interests[k]);                            // ...print the interest, and...
                                     if (k < targH.actCard_slots[i].interests.length-1)                                 // ...if the current interest slot is not the last, and...
                                         if(targH.actCard_slots[i].interests[k+1] != null )                             // ...the slot after it is not null...
                                             System.out.print(", ");                                            // ...print a comma!
                                 }//end if
                }//end for
                System.out.println();   // carriage return!                             
            }//end if
        }//end for
        
        // input error check loop
        actionTarg = -1;
        while (actionTarg < 0 || actionTarg > targH.actCard_slots.length)
            actionTarg = input.nextInt();                                                               // scanning for input
        
        if (actionTarg == 0)
            manipulationType(p, targR, targH);                                                                  // 0 input send user back to their choice of manipulation
        
        tempCard = targH.actCard_slots[actionTarg-1];                                                                       // putting selected target card into temp variable
        
        //Choosing your card to give up
        System.out.println("\n\t Which of your activities would you like to give up?");
        System.out.println("\t 0 - Nevermind, go back");
        for (int i=0; i < p.actCard_slots.length; i++){
            if (p.actCard_slots[i] != null){
                System.out.println("\t " + (i+1) + " - " + p.actCard_slots[i].title);
                        System.out.print(   "\t\t\t involves: ");
                             for (int k=0; k < p.actCard_slots[i].interests.length; k++){                           // For each interest slot (up to 6 slots) on the current activity card...
                                 if (p.actCard_slots[i].interests[k] != null){                                      // ...if there *is* an interest in this slot...
                                     System.out.print( p.actCard_slots[i].interests[k]);                            // ...print the interest, and...
                                     if (k < p.actCard_slots[i].interests.length-1)                                 // ...if the current interest slot is not the last, and...
                                         if(p.actCard_slots[i].interests[k+1] != null )                             // ...the slot after it is not null...
                                             System.out.print(", ");                                            // ...print a comma!
                                 }//end if
                }//end for
                System.out.println();   // carriage return!                             
            }//end if
        }//end for        
        
        // input error check loop
        actionP = -1;
        while (actionP < 0 || actionP > p.actCard_slots.length)
            actionP = input.nextInt();                                                               // scanning for input
        
        if (actionP == 0)
            manipulationType(p, targR, targH);                                                                  // 0 input send user back to their choice of manipulation
        
        targH.actCard_slots[actionTarg-1] = p.actCard_slots[actionP-1];                                          // repacing selected target card with selected player card  
        p.actCard_slots[actionP-1] = tempCard;                                                                      // replacing selected player card with target card via the temp variable
        
        //Lowering target's opinion of the player
        lower_affection(p, targH, targR, 1);
        
        Functions.mainMenu(p);
        
    }//end activity_swap
        
    
    public static void snoop(Classes.Hud p, Classes.RelCard targR, Classes.Hud targH){  // Receives player's hud, target's relationship card, target's hud
        
        Scanner input  = new Scanner(System.in);                                                            // input scanner   
        int action = -1;                                                                                    // input variable        
        
        System.out.println("Snooping allows you to learn a relation's interests \n"
                + "         (Will lower relationship level by -1) \n");
        
        System.out.println("Are you sure you would like to snoop on Player " + targH.name + "?");
        
        System.out.println("\t 0 - Never mind, go back");            
        System.out.println("\t 1 - Yes, snoop away!");
            
        // input error check loop
        action = -1;
        while (action < 0 || action > 1)
            action = input.nextInt();                                                                       // scanning for input       
        
        if (action == 0)
            manipulationType(p, targR, targH);
        
        if (action == 1){
            System.out.println("\t Player " + targH.name + "'s interests: ");
            for (int i=0; i<targH.intCard_slots.length; i++){                                                   // For each of the target's interest slots...
                if (targH.intCard_slots[i] != null){                                                            // ...if there IS an interest in that slot...
                System.out.println("\t\t" + targH.intCard_slots[i].title);                                      // ...print interest
                }//end if
            }//end for        
        }//end if

        lower_affection(p, targH, targR, 1);                                                                 // Lowering interest level by 1
        
        Functions.mainMenu(p);
        
        
    }//end snoop
    
    public static void cold_shoulder(Classes.Hud p, Classes.RelCard targR, Classes.Hud targH ){
        // Lowers player's opinion of a relation of her choice
        Scanner input  = new Scanner(System.in);                                                            // input scanner   
        int action = -1;                                                                                    // input variable        
        
        System.out.println(" Giving your target the Cold Shoulder will lower your feelings for them by 1  \n"
                        + "\t (Will lower target's feelings for you by 1) \n");
        
        System.out.println(" This manipulation is a Power Play; "
                            + "it requires the target likes you as much or more than you like them \n");
        
        System.out.println(" Are you sure you would like to give Player " + targH.name + " the Cold Shoulder?");
        
        System.out.println("\t 0 - Never mind, go back");            
        System.out.println("\t 1 - Yes, ignore their pants off!");
            
        // input error check loop
        action = -1;
        while (action < 0 || action > 1)
            action = input.nextInt();                                                                       // scanning for input       
        
        if (action == 0)
            manipulationType(p, targR, targH);
        
        if (action == 1){        

            System.out.println(" Giving Player " + targH.name + " the cold shoulder...");
            
            if (power_play(p, targH, targR) ){                                                                  // Power Play requirements must be met

                for (int i=0; i<targH.relation_slots.length; i++){                                              // For each of the target's relation slots...
                    if (targH.relation_slots[i] != null){                                                       // ...if there IS a relation in that slot...
                        if (targH.relation_slots[i].color.equals(p.name)){                                      // ...search for the relCard that matches the player...
                            System.out.println("\t Your former feelings toward Player " + 
                                                targH.name + ": " + targH.relation_slots[i].level + 
                                                " (" + targH.relation_slots[i].value + ")");
                            targH.relation_slots[i] = p.my_relCards[targH.relation_slots[i].value-1];           // ...replace relCard with lower one from the player
                            System.out.println("\t Your current feelings toward Player " + 
                                                targH.name + ": " + targH.relation_slots[i].level + 
                                                " (" + targH.relation_slots[i].value + ")" );
                        }//end if
                    }//end if
                }//end for
            }//end if (power play check)

        }//end if
        lower_affection(p, targH, targR, 1);                                                             // Lowering target's interest in the player

        Functions.mainMenu(p);
        
    }//end cold_shoulder
    
    public static void interest_swap(Classes.Hud p, Classes.RelCard targR, Classes.Hud targH){
        
        System.out.println();
        System.out.println(" Interest Swap enables you to exchange one of your interests with a random interest of your target's \n"
                        + "\t (Will lower target's feelings for you by 1) \n");
        
        Scanner input  = new Scanner(System.in);                                                            // input scanner   
        int action = -1;                                                                                    // input variable     
        // temp variables
        Classes.IntCard tempInt;                                                                            // temporary swapper variable
        Classes.IntCard playerInt;                                                                          // player's selected interest
        Classes.IntCard targetInt;                                                                          // target's random interest
        
        System.out.println("\t Which of your interests would you like to swap with Player " + targH.name + "?");

        System.out.println("\t\t 0 - Never mind, go back");           
        for (int i=0; i<p.intCard_slots.length; i++){
            System.out.println("\t\t " + (i+1) + " - " + p.intCard_slots[i].title);                           // Printing player's interests
                    
        }//end for

        // input error check loop
        action = -1;
        while (action < 0 || action > p.intCard_slots.length)
            action = input.nextInt();                                                                       // scanning for input       
        
        if (action == 0)
            manipulationType(p, targR, targH);
        
        
        playerInt = p.intCard_slots[action-1];                                                              // Copying selected intCard to temp variable
        int random = Functions.RandomInteger(targH.intCard_slots.length);
        targetInt = targH.intCard_slots[random];                                                            // Copying a random intCard from target into temp variable
        
        // Checking if player and target have identical interests
        int dupe = 0;                                                                                       // counter variable
        for (int i=0; i<p.intCard_slots.length; i++){
            if ( Arrays.asList(targH.intCard_slots).contains(p.intCard_slots[i]) ){                             // Checking each of player's interests against each of target's
                dupe++;                                                                                     // Counting each duplicate
            }//end if
        }//end for
        if (dupe == p.intCard_slots.length){                                                                // If all interests are the same between player and target...
            System.out.println("Your interests are identical to those of Player " + 
                                targH.name + "; you may not swap interests!");
            Functions.mainMenu(p);                                                                          // ...end interest_swap
        }//end if
            
        
        while (Arrays.asList(p.intCard_slots).contains(targetInt)){                                         // While the player already has this interest...
            random = Functions.RandomInteger(targH.intCard_slots.length);
            targetInt = targH.intCard_slots[random];                                                        // ...we keep getting a new random interest from the target
        }//end while

        p.intCard_slots[action-1] = targetInt;                                                              // Assigning player the new interest
        System.out.println("You have acquired " + p.intCard_slots[action-1].title + 
                            " as a new interest");   
        
        boolean duplicate = false;

        if ( Arrays.asList(targH.intCard_slots).contains(playerInt) ){
            duplicate = true;
        }else{
            duplicate = false;
        }
            
            if (duplicate == true)
                System.out.println("Player " + targH.name + " already has " + 
                                    playerInt.title + " as an interest!");                                  // Target's interests include the player's selection
        
            if (duplicate == false){
                targH.intCard_slots[random] = playerInt;                                                    // Assigning target the player's interest
                System.out.println("Player " + targH.name + " has acquired " + 
                                    targH.intCard_slots[random].title + " as a new interest");
            }//end if
        
        
        
        
        lower_affection(p, targH, targR, 1);
        
        Functions.mainMenu(p);

    }//end interest_swap    
    
    public static void lower_affection(Classes.Hud p, Classes.Hud targH, Classes.RelCard targR, int n){     // receives: player hud, target hud, target relCard, # of levels to lower by
        
        //Lowering target's opinion of the player by n levels
        Scanner input  = new Scanner(System.in);                                                            // input scanner   
        int action = -1;    
        
        System.out.println();   // carriage return
        System.out.println(" Player " + targH.name + " thinks less of you:");
        System.out.println("\t " + targH.name + "'s former feelings toward you: " + 
                            targR.level + " ("+ targR.value + ")" );                                            // Print target's level before anything's been done
        for (int i=0; i < p.relation_slots.length; i++){                                                        // For each of the player's relation slots...
            if (p.relation_slots[i] != null){                                                                   // ...if there IS a relation in that slot...
                if (p.relation_slots[i].color == targH.name){                                                   // ...search for the relation that matches the target...
                    if (p.relation_slots[i].value > 0 && p.relation_slots[i].value != RunGame.HATE_VALUE){   // ...if the current level is neither Stranger nor Hate...
                        p.relation_slots[i] = targH.my_relCards[p.relation_slots[i].value - n];                     // ...replace that relation card with a lower value one from the target's relCards, and...
                        targR = p.relation_slots[i];                                                                // ...reassign target variable to this new card.
                    }else if(p.relation_slots[i].value == 0){                                                   // If target is a Stranger...
                        p.relation_slots[i] = targH.my_relCards[RunGame.HATE_VALUE];                         // ...set target's affection level to Hate
                    }else if(p.relation_slots[i].value == RunGame.HATE_VALUE){                               // If target Hates the player...
                        System.out.println("\t Player" + targH.name + " already hates you!");                   // ...do nothing to the relationship level
                    }//end else if
                }//end if
            }//end if
        }//end for
        System.out.println("\t " + targH.name + "'s current feelings toward you: " + 
                            targR.level + " ("+ targR.value + ")" );                                            // Print the target's new level 

    }//end lower_affection
    
    public static boolean power_play(Classes.Hud p, Classes.Hud targH, Classes.RelCard targR){
        /* Checks if an action meets certain requirements: the target must like the player
         * as much or more than the player likes the target (the player must have "power"
         * in the relationship)
        */
        int playerPower = targR.value;                                                          // Making note of how much the target likes the player
        int targetPower = 0;

        for (int i=0; i<targH.relation_slots.length; i++){                                      // For each of the target's relation slots...
            if (targH.relation_slots[i] != null){                                               // ...if there IS a relation in that slot...
                if (targH.relation_slots[i].color == p.name){                                   // ...find the player's relationship card in the target's slots
                    targetPower = targH.relation_slots[i].value;                                // Make note of how much the player likes the target
                }//end if
            }//end if
        }//end for
        
        if (playerPower >= targetPower){
            System.out.println(" Power Play successful");
            return true;
        }else{
            System.out.println(" Power Play unsuccessful - cannot manipulate Player " + targH.name);
            return false;
        }//end if else
        
        
    }//end power_play
    
}//end file
