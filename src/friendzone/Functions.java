
package friendzone;

import java.util.Arrays;
import java.util.Random;
import java.util.*;
import java.lang.*;

public class Functions {
    
    public static int RandomInteger(int n) {    // generates a random integer from 0 - n
    Random randomGenerator = new Random();

    int randomInt = randomGenerator.nextInt(n);

    return randomInt;
    }//end random
    
    public static Classes.Player getCrush(Classes.Player p){    // assigns specified player a random crush
        
        Classes.Player[] players = new Classes.Player[RunGame.MAXPLAYERS];           // creating an array of all players
            players[0] = Classes.Player.Blue;
            players[1] = Classes.Player.Red;
            players[2] = Classes.Player.Green;
            players[3] = Classes.Player.Yellow;
            
        Classes.Player randomCrush = players[ RandomInteger(RunGame.MAXPLAYERS) ];   // choosing a random player from the array to be your crush...
        while (randomCrush == p)
            randomCrush = players[ RandomInteger(RunGame.MAXPLAYERS) ];              // ...so long as that player is not you!
        
        return randomCrush;
    }//end getCrush
    
    public static Classes.RelCard[] getRelations( Classes.Player p, Classes.RelCard[] r0, int L0,                     // this player, first relation, their level, 
                                                    Classes.RelCard[] r1, int L1, Classes.RelCard[] r2, int L2 ){       // second relation, their level, third relation, their level
        
        Classes.RelCard[] relationArray = new Classes.RelCard[RunGame.MAXPLAYERS - 1];
        
            relationArray[0] = r0[L0];
            relationArray[1] = r1[L1];
            relationArray[2] = r2[L2];

        return relationArray;
    }
    
    public static Classes.IntCard[] intCardSlotsAssign(Classes.Hud p, Classes.IntCard[] d){
        if (RunGame.DEBUG_METHOD)
        System.out.print("\n" +
                            p.name+"'s turn: \n");
        // Assigning Interest Cards
        for (int i=0; i<p.intCard_slots.length; i++){    
            Classes.IntCard randomCard = d[Functions.RandomInteger(RunGame.INTEREST_CARDS) ];          // pulling a random card from the deck 
                if (RunGame.DEBUG_MAIN){
                    System.out.println( "\t Player " + p.name + " draws " + randomCard.title);
                    if (i>0)    // only print if this isn't the first card
                    System.out.println( "\t\t Checking if player already holds this card...");
                }//end DEBUG_MAIN  
            while (Arrays.asList(p.intCard_slots).contains(randomCard)){        // while we already have this card in one of our slots...
                if (RunGame.DEBUG_MAIN){
                    System.out.println( "\t\t *** Player " + p.name + " already holds " + randomCard.title + "! ***");
                    System.out.println( "\t\t Drawing a new card...");
                }//end DEBUG_MAIN
                randomCard = d[Functions.RandomInteger(RunGame.INTEREST_CARDS) ];              // ... we keep pulling cards 
                if(RunGame.DEBUG_MAIN)
                System.out.println( "\t Player " + p.name + " draws " + randomCard.title);                
            }//end while
            
            if (RunGame.DEBUG_MAIN){
                if (i>0)    // only print if this isn't the first card
                System.out.println( "\t\t Player " + p.name + " doesn't have " + randomCard.title + "!" );  
                System.out.println( "\t\t Placing it into slot #" + (i+1) );  
            }//end DEBUG_MAIN            
            p.intCard_slots[i] = randomCard;
            
        }//end for
        if (RunGame.DEBUG_METHOD){
            System.out.print("\n\t" + p.name+"'s interests: \n");            
            for (int i=0; i<p.intCard_slots.length; i++){
               System.out.println( "\t\tInterest Card #" + (i+1) + ": " + p.intCard_slots[i].title );  // Player's current activity cards
            }//end for
        }//end debug
        
    return p.intCard_slots;        
        
    }//end intCardSlotsAssign    
    
    public static Stack shuffleActCardDeck (Stack s, Classes.ActCard[] d){     // receives actCardStack, actCardDeck
        while ( s.size() != RunGame.ACTIVITY_CARDS ){
            int r = Functions.RandomInteger(RunGame.ACTIVITY_CARDS) + 1;     // get random#      
            if (d[r] != null){  // if there is a card in that index...
                s.push(d[r]);   // ...push that card on to the stack...
                d[r] = null;    // ...and delete that card
            }else{              // if there isn't a card in that index...
            r = Functions.RandomInteger(RunGame.ACTIVITY_CARDS) + 1;     // get random#   
            }//end if/else
        }//end while
        return s;   // return the stack when its size == the number of activity cards
    }//end create_actCardStack
    
    public static Classes.ActCard[] actCardSlotsAssign(Classes.Hud p, Stack<Classes.ActCard> d){   // receives the player, the actCardStack; returns the player's three activity cards
        if (RunGame.DEBUG_METHOD)
        System.out.print("\n" + p.name+"'s turn: \n");
        
        // Assigning Activity Cards
        for (int i=0; i<p.actCard_slots.length; i++){    
            p.actCard_slots[i] = d.pop();   //pop a card off the stack & assign it to the player's current slot
        }//end for
   
    return p.actCard_slots;
    
    }//end actCardSlotsAssign    
        
    // prints current status information for a player
    public static void readOut( Classes.Hud p ){              // my activity cards, my relation_slots,  
        int l;      // loop variable
        System.out.println( "\n\t\t Player " + p.name );                       // my name
        
        System.out.println( "\n My Crush is Player " + p.crush );            // my Crush
        
        System.out.print( "\n My Interests: \n\t" );            // my Interests
        for (l=0; l < p.intCard_slots.length; l++)
            System.out.print( "   " + p.intCard_slots[l].title + "   " );     
        System.out.println();
        
        System.out.print( "\n My Activities: \n\t" );           // my Activities
        for (l=0; l<p.actCard_slots.length; l++)
            System.out.print( "   " + p.actCard_slots[l].title + "   " );
        System.out.println();
        
        System.out.println( "\n My Relations: " );            // array print
        for (l=0; l<RunGame.MAXPLAYERS - 1; l++)
            System.out.println( p.relation_slots[l].intro );
        
        
        System.out.println( "\t_________________________________________" );
        
        mainMenu( p );

    }//end readOut
    
    public static void mainMenu( Classes.Hud p ){

        System.out.println( "\n You may perform one INVITATION and one MANIPULATION per turn." );
        System.out.println( " Choose your action... " );
        System.out.println( "\t 1 - Invitation" );
        System.out.println( "\t 2 - Manipulation" );
        System.out.println( " Other options... ");
        System.out.println( "\t 3 - view your current status");
        System.out.println( "\t_________________________________________");

        int action;                                 // input variable
        Scanner input  = new Scanner(System.in);    // input scanner

        try{
            action = input.nextInt();
            switch (action){
                case 0: break;
                case 1:{
                    System.out.println("\t INVITATION"); 
                    player_invitation(p);            // CALLING function: player_invitation
                    break;
                }
                case 2:{
                    System.out.println("\t MANIPULATION"); 
                    Manipulations.manipulationTarget(p);
                    break;
                }
                case 3:
                    Functions.readOut(p); break;
                default:
                    System.out.println("\t Incorrect selection - enter again");
            }//end switch
        } catch (InputMismatchException e) {    // catches if the player enters a ch rather than an int
            System.err.println("Caught Input Mismatch: " + e.getMessage());
            System.out.println( "\t Incorrect selection - enter again");
            }//end try/catch             
    }//end mainMenu
    
    public static void player_invitation( Classes.Hud p ){      // receives the player's Hud, list of all players' Huds
        
        System.out.println( " Which would you like to arrange?" );
        System.out.println( "\t 0 - Nevermind, go back");
        System.out.println( "\t 1 - Jaunt" );
        System.out.println( "\t 2 - Date" );
        System.out.println( "\t_________________________________________");
            
            int action;                                 // input variable
            Scanner input  = new Scanner(System.in);    // input scanner
            
            try{
                action = input.nextInt();
                switch (action){
                    case 0:{
                        mainMenu(p);
                    }
                    case 1:{
                        System.out.println("\t JAUNT"); 
                        getJaunt(p);                    // CALLING function: getJaunt
                        break;
                    }
                    case 2:{
                        System.out.println("\t DATE"); 
                        getDate(p);
                        break;
                    }
                    default:
                        System.out.println("\t Incorrect selection - enter again");
                }//end switch
            } catch (InputMismatchException e) {    // catches if the player enters a ch rather than an int
                System.err.println(" Caught Input Mismatch: function: player_invitation: " + e.getMessage());
                //System.out.println( "\t Incorrect selection - enter again");
                } 
    }//end player_invitation
    
    public static void getDate( Classes.Hud p ){
        boolean done = false;
        int optionCounter = 0;
        int selectionCounter = 0;
        Classes.RelCard[] dateOptions_relCard = new Classes.RelCard[RunGame.MAXPLAYERS-1];       // holds all relations the player is currently *able* to date        
        Classes.RelCard[] dateInvite_relCard = new Classes.RelCard[RunGame.MAXDATES];            // holds the rel. card of whomever the player invites on the date
        Classes.Hud[] dateInvite_hud = new Classes.Hud[RunGame.MAXPLAYERS-1];                    // holds the Hud of whomever the player invites on the date       
        Scanner input  = new Scanner(System.in);                                                    // input scanner   
        int action;                                                                                 // input variable
        
        for (int j=0; j < p.relation_slots.length; j++){                                            // For all player slots...
            if (p.relation_slots[j] != null){                                                       // ...if there *is* a player in that slot...
                if ( Arrays.asList(dateInvite_relCard).contains(p.relation_slots[j]) == false  &&   // ...if they aren't invited yet, and...   
                   (p.relation_slots[j].value == 1 || p.relation_slots[j].value == 3 ||             // ...they are of level: Acquaintance, or Admirerer, or...
                    p.relation_slots[j].value == 4) ){                                              // ...Love...
                    dateOptions_relCard[selectionCounter] = p.relation_slots[j];                       // ...put them in the dateOptions array
                    selectionCounter++;
                }//end if
            }//end if
        }//end for         
        
        while (done == false){                                                                      // finished with input condition
            if (dateInvite_relCard[0] == null && dateOptions_relCard[0] != null){
                System.out.println( " Whom would you like to invite on a Date?"); 
                System.out.println( "\t 0 - No one, that's who! (Go back)"); 

            }//end if
            else if (dateOptions_relCard[0] != null) {
                System.out.println( " You've chosen to invite " + dateInvite_relCard[0].color +
                                    " on a date.");
                System.out.println( "\t Are you sure?");
                System.out.println( "\t 0 - No, let me choose again");
                System.out.println( "\t 1 - Yes, let's do this!");
                action = input.nextInt();                                                               // scanning for input
                if (action == 0)
                    getDate( p );                                                          // previous function
                if (action == 1)
                    selectActivities(p, dateInvite_relCard, dateInvite_hud, false);        // CALLING the activity select function 
            }//end else
                for (int j=0; j < p.relation_slots.length; j++){                                        // For all player slots...
                    if (p.relation_slots[j] != null){                                                       // ...if there *is* a player in that slot...
                        if ( Arrays.asList(dateInvite_relCard).contains(p.relation_slots[j]) == false  &&   // ...if they aren't invited yet, and...   
                           (p.relation_slots[j].value == 1 || p.relation_slots[j].value == 3 ||             // ...they are of level: Acquaintance, or Admirerer, or...
                            p.relation_slots[j].value == 4) ){                                              // ...Love...

                            System.out.println( "\t " + (optionCounter+1) + " - " +                         // ...print their option number, and...
                                                dateOptions_relCard[optionCounter].color + 
                                                ": " + dateOptions_relCard[optionCounter].level );          // ...print their color, and current relationship level to the player
                            optionCounter++;                    
                        }//end if
                    }//end if
                }//end for        
                if (dateOptions_relCard[0] == null){                                                        // If there are no dateable relations...
                    System.out.println( " None of your relations are dateable at "
                                        + "their current level.");
                    //System.out.println( "\t Try a Jaunt instead...");
                    done = true;
                    System.out.println( "\t 0 - Go back to Invitations"); 

                action = -1;
                while (action < 0 || action > 0)
                    action = input.nextInt();                                                                       // scanning for input

                if (action == 0){
                    done = true;
                    player_invitation(p);                                                          // CALLING function: player_invitation
                }//end if
            }//end if 
            
            action = -1;
            while (action < 0 || action > dateOptions_relCard.length)
                action = input.nextInt();

            dateInvite_relCard[0] = dateOptions_relCard[action-1];                                          // put selected relation into the dateInvite array

            // putting selection in the hud invitee array
            for (int k=0; k < p.playersList.length; k++){                                                       // for each player slot in the game...
                if (p.playersList[k] != null){                                                                // ...if there *is* a player in that slot...
                    if ( p.playersList[k].name == dateOptions_relCard[action-1].color ){                      // ...if the player's name matches the color of the selected relation...
                        dateInvite_hud[0] = p.playersList[k];                                                 // ...put that player in the Hud invitees array
                    }//end if        
                }//end if 
            }//end for
        }//end while
        
    }//end getDate
    

    public static void getJaunt(Classes.Hud p ){ // coming from player_invitation(); receives player's Hud, playersList of all players' Huds
        /* Simpatico - each invitee must be of the same relCard level  */

    //try{
        int fullCounter = 0;
        boolean done = false;
        
        Classes.RelCard[] jauntInvites_relCard = new Classes.RelCard[RunGame.MAXPLAYERS-1];      // holds the rel. cards of all the relations invited on the jaunt
        Classes.Hud[] jauntInvites_hud = new Classes.Hud[RunGame.MAXPLAYERS-1];                  // holds the Huds of all the players invited on the jaunt 
        int action;                                 // input variable
        Scanner input  = new Scanner(System.in);    // input scanner           
    
        while (done == false){
            if (jauntInvites_relCard[0] == null){                                                           // if there are not yet any invitees...
                    System.out.println( " Whom would you like to invite?" );
                    System.out.println( " (All invitees must be of the same relationship level) " );
            }else {                                                                                         // If there is at least one invitee...
                    System.out.println( " Anyone else?");
                    System.out.println( "\t 0 - Nope, that's all");
                }//end else
            // Selection list
            for (int j=0; j < p.relation_slots.length; j++){                                                                    // For all possible invitees...
                if ( Arrays.asList(jauntInvites_relCard).contains(p.relation_slots[j]) == false ){                              // ...if they aren't invited yet...
                    if (jauntInvites_relCard[0] != null){                                                                       // If at least one person has already been invited, and...
                        if (p.relation_slots[j].value == jauntInvites_relCard[0].value){                                        // ...if this invitee is of the same rel. value as the first invitee...
                            System.out.println( "\t " + (j+1) + " - " + p.relation_slots[j].color +                             // ...print their selection number, color, and...
                                                ": " + p.relation_slots[j].level );                                             // ...current relationship level to the player
                        }//end if
                    }else{
                        System.out.println( "\t " + (j+1) + " - " + p.relation_slots[j].color +                                 // ...print their selection number, color, and...
                                            ": " + p.relation_slots[j].level );                                                 // ...current relationship level to the player
                    }//end if else
                }//end if
            }//end for

            action = -1;
            while (action < 0 || action > jauntInvites_relCard.length){                     // Incorrect selection check
                action = input.nextInt();                                                   // scanning for input (pause)  
            }//end while
            
         
            if (jauntInvites_relCard[0] != null && action == 0){                            // if at least one invitee has been selected and the player chooses 0...
                done = true;
                break;                                                                      // break out of while: done loop
            }//end if
            // putting selection in the relCard invitees array
            if (Arrays.asList(jauntInvites_relCard).contains(p.relation_slots[action-1]) == false){             // If selected relation is not already invited...
                    jauntInvites_relCard[fullCounter] = p.relation_slots[action-1];                             // ...add selected relation to relation invite array, and...
                        for (int k=0; k < p.playersList.length; k++){                                           // ...for each Hud slot in the game...
                            if (p.playersList[k] != null){                                                      // ...if there *is* a Hud in that slot...
                                if ( p.playersList[k].name == p.relation_slots[action-1].color &&               // ...if the Hud's name matches the color of the selected relation, and...
                                    Arrays.asList(jauntInvites_hud).contains(p.playersList[k]) == false ){      // ...selected relation is not already invited...
                                    jauntInvites_hud[fullCounter] = p.playersList[k];                           // ...put that Hud in the Hud invitees array
                                }//end if
                            }//end if
                        }//end for  
                        fullCounter++;                                                                          // incrementing the full invite list counter
            }//end if
            else System.out.println(" That player has already been invited!");

            if (fullCounter == p.relation_slots.length)                                                     // if the full invite list counter is maxed, end the done loop!
                done = true;

        }//end while: done


        if (RunGame.DEBUG_METHOD){
        System.out.println("#1: "+jauntInvites_relCard[0].color);
        if (jauntInvites_relCard[1] != null)
        System.out.println("#2: "+jauntInvites_relCard[1].color);
        if (jauntInvites_relCard[2] != null)
        System.out.println("#3: "+jauntInvites_relCard[2].color);
        }//end debug
        
        selectActivities(p, jauntInvites_relCard, jauntInvites_hud, true);                   // CALLING the activity select function 
    /*
    } catch (Exception e) {   
                System.err.println(" ERROR in function: getJaunt: " + e.getMessage() + "\n Try again..." );
                getJaunt( p );
                //System.out.println( "\t Incorrect selection - enter again");
                }        
        */
    }//end getJaunt
    
    // Allows the player to select activities for a jaunt or date from among their own activity cards and those of their invitees'
    public static void selectActivities(Classes.Hud p, Classes.RelCard[] relList, Classes.Hud[] hudList, boolean type ) {    /* receives player Hud, list of invited relations, 
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
        
        if (action == 0){
            selectActivities(p, relList, hudList, type);                                                   // Re-call this function
        }//end if
        if (action == 1 && type == true){                                                                               // If this is a Jaunt...
            invitationResult(p, relList, hudList, selectionArray, type);                                   // ...call invitationResult 
        }//end if
        if (action == 1 && type == false){                                                                               // If this is a Date...
            invitationResult(p, relList, hudList, selectionArray, type);                                   // ...call invitationResult 
        }//end if        
    /*
    } catch (Exception e){  // if there's an error, recall the function!
        System.err.println( " ERROR in function: selectActivities: " + e.getLocalizedMessage() + "\t Try again..." );
        //selectActivities(p, relList, hudList, playersList, type);
    }//end catch
    */
    } //end selectActivities
    
    public static void invitationResult(Classes.Hud p, Classes.RelCard[] relList, Classes.Hud[] hudList, Classes.ActCard[] activities, boolean type){
        
        Classes.Interest[] interests = new Classes.Interest[ (RunGame.MAXPLAYERS-1) * RunGame.PLAYER_INTCARDS ];             // array to hold all the interests on this jaunt/date
        System.out.println( "\t_________________________________________");
        if (type == true)       // if this is a jaunt...
            System.out.print("Jaunt ");
        if (type == false)      // if this is a date...
            System.out.print("Date ");        
        System.out.println("Results: \n");
        
        int interestIndex = 0;                                                      
        // filling the interests array
        for (int i=0; i<activities.length; i++){                                    // for each slot in imported activities array...
            if (activities[i] != null){                                             // ...if there *is* an activity in that slot...
                for (int j=0; j<activities[i].interests.length; j++){               // ...for each of that activity's interest slots...
                    if (activities[i].interests[j] != null){                        // ...if there *is* an interest in that slot...
                        interests[interestIndex] = activities[i].interests[j];      // ...put that interest into the interests array...
                        interestIndex++;                                            // ...and increment the interests array's index!
                    }//end if
                }//end for
            }//end if
        }//end for

        for (int i=0; i<hudList.length; i++ ){                                                                  // for each invitee slot in hudList...
            int likeCounter = 0;                                                                                // ...set her # of interest likes to 0.
            if (hudList[i] != null){                                                                            // if there *is* an invitee in that slot...
                System.out.println("   Results for Player " + hudList[i].name + ": ");                
                for (int j=0; j<hudList[i].intCard_slots.length; j++){                                          // for each of her interest slots...
                    if (hudList[i].intCard_slots[j] != null){                                                   // ...if there *is* an interest in that slot...
                        if ( Arrays.asList(interests).contains(hudList[i].intCard_slots[j].title) ){            // ...if the interests array contains that interest...
                            likeCounter++;                                                                      // ...increment her like counter, and...
                            System.out.println( "      Player " + hudList[i].name +                             // ...print what she likes.
                                                " enjoys " + hudList[i].intCard_slots[j].title + "! +1");
                        }//end if
                            else{
                                if (RunGame.DEBUG_METHOD)
                                    System.out.println( "\t\t Unpiqued interest!");
                            }//end else   
                    }//end if
                }//end for
                if (likeCounter == 0){
                    System.out.println("\t No interests piqued!");
                }
            
            // Changing the relation's level
                for (int k =0; k < p.relation_slots.length; k++){                                                   // For each of the player's relation slots (3)...
                    if (p.relation_slots[k] != null){                                                               // ...if there *is* a relation in that slot...
                        if (p.relation_slots[k].color == hudList[i].name){                                          // ...find the current invitee's post-invite relation card...
                            if (type == true && p.relation_slots[k].value == likeCounter)                           // If this was a Jaunt, and there was no change in affection level...
                                System.out.print("\t No change in level: " + p.relation_slots[k].level);            // ...add a notification as such.                              
                            if (type == true && p.relation_slots[k].value < likeCounter &&                          // If this is a Jaunt, and her pre-invite level is lower than her post-invite level, and...
                                likeCounter != 4){                                                                  // ...she didn't hit Love level...
                                p.relation_slots[k] = hudList[i].my_relCards[likeCounter];                          // ...replace the old card with the new card of value: likeCounter!
                                System.out.println(p.relation_slots[k].intro);                                      // ...print new level
                            }else if (type == true && p.relation_slots[k].value < likeCounter &&                    // If this is a Jaunt, and her pre-invite level is lower than her post-invite level, and...
                                likeCounter == 4){                                                                  // ...she hit Love level...
                                p.relation_slots[k] = hudList[i].my_relCards[likeCounter-1];                        // ...make her an Admirer (can't fall in love on a jaunt)
                                System.out.println(p.relation_slots[k].intro);                                      // ...print new level
                            }//end else if
                            if (type == false && p.relation_slots[k].value >= likeCounter &&                        // If this is a date, and her pre-invite level is higher or equal to her post-invite level, and...
                                    p.relation_slots[k].value != 4 ){                                               // ...she does not already Love the player...
                                System.out.println(" Date FAILURE: " + p.relation_slots[k].color +                  // ...print date failure message
                                                   "'s Affection Level was not increased! (Lose one level)");
                                Manipulations.lower_affection(p, hudList[i], relList[i], 1);                         // ...lower target's affection by 1
                            } else if (type == false && p.relation_slots[k].value < likeCounter){                   // Else if this is a Date, and pre-invite lvl is less than post-invite...
                                p.relation_slots[k] = hudList[i].my_relCards[likeCounter];                          // ...replace the old card with the new card!
                            }//end else
                            System.out.println("\n");                                                               // 2x carriage return
                            if (p.relation_slots[k].value == 4 && 
                                p.relation_slots[k].color == p.crush){
                                System.out.println(" Your crush - Player " +p.relation_slots[k].color + " - Loves you! \n *** YOU WIN! *** \n" );
                            }//end if
                        }//end if
                    }//end if
                }//end for   
            }//end if
        }//end for
        
        mainMenu( p );
    }//end invitationResult
    

    

    

    
}//end file


