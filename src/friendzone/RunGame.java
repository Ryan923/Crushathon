package friendzone;

import java.util.*;
import java.lang.*;

public class RunGame {
    // Global variables
    public static final int MAXPLAYERS = 4;                 // max. number of players (this will change)
    public static final int INTEREST_CARDS = 10;;           // # of interest cards in deck
    public static final int ACTIVITY_CARDS = 30;            // # of activity cards in deck, minus USED pseudocard
    public static final int PLAYER_ACTCARDS = 3;            // how many activity cards a player may hold
    public static final int PLAYER_INTCARDS = 4;            // how many interest cards a player may hold
    public static final int MAXDATES = 1;                   // how many people you may simultaneously invite on a date
    public static final int MANIPULATIONS = 4;
    
    public static final boolean DEBUG_MAIN = false;               // set as true for minimal test messages
    public static final boolean DEBUG_METHOD = false;      // set as true for verbose test messages
    public static final boolean DEBUG_MANIPULATIONS = false;
    public static final boolean GAME = true;                // standard game messages
    public static final boolean GUI = true;
    public static final boolean AI_TEST = true;             // For testing AI
    
    //Relationship Card globals
    public static final int HATE_VALUE = 5;                 // the value of a hate card
    
    public static void main(String[] args){
        
        int i;  // for all your iteration needs
        
        boolean winCondition = false;
        
    // Interest Cards
        Classes.IntCard art = new Classes.IntCard();
            art.title = Classes.Interest.Art;
            
        Classes.IntCard books = new Classes.IntCard();
            books.title = Classes.Interest.Reading; 
            
        Classes.IntCard dancing = new Classes.IntCard();
            dancing.title = Classes.Interest.Dancing;
            
        Classes.IntCard dining = new Classes.IntCard();
            dining.title = Classes.Interest.Dining;
            
        Classes.IntCard games = new Classes.IntCard();
            games.title = Classes.Interest.Games;
            
        Classes.IntCard outdoors = new Classes.IntCard();
            outdoors.title = Classes.Interest.Outdoors; 
            
        Classes.IntCard sports = new Classes.IntCard();
            sports.title = Classes.Interest.Sports;  
            
        Classes.IntCard swimming = new Classes.IntCard();
            swimming.title = Classes.Interest.Swimming;   
            
        Classes.IntCard technology = new Classes.IntCard();
            technology.title = Classes.Interest.Technology;  
            
        Classes.IntCard theater = new Classes.IntCard();
            theater.title = Classes.Interest.Theater;     
            
    // Interest Card array (for generating random interests)
        Classes.IntCard[] intCardDeck = { art, books, dancing, dining, games, outdoors, 
                                  sports, swimming, technology, theater };      // current count: 10        
    
    // Activity Cards   // for some reason, the enums are not tracking by unaddressed symbol name. Perhaps they can't (?)
        Classes.ActCard null_actCard = new Classes.ActCard();   // a "NULL card" for use in the used mask (actCardDeck_mask) - is element 0 in the deck    
            null_actCard.title = "Null Act Card";
        
        Classes.ActCard aquarium = new Classes.ActCard();  
            aquarium.title = "Aquarium";
            aquarium.description = "Swim with the dolphins! (or just learn about them if you wish)";
            aquarium.interests[0] = Classes.Interest.Reading;
            aquarium.interests[1] = Classes.Interest.Swimming;   
            
        Classes.ActCard art_house_film = new Classes.ActCard();
            art_house_film.title = "Art House Film";
            art_house_film.description = "Hopefully appeals to people of all intelligences";
            art_house_film.interests[0] = Classes.Interest.Art;    
            art_house_film.interests[1] = Classes.Interest.Theater;
            
        Classes.ActCard auto_racing = new Classes.ActCard();
            auto_racing.title = "Auto Racing";
            auto_racing.description = "Admire the drivers' reflexes as well as the incredible machines that actually do the work";
            auto_racing.interests[0] = Classes.Interest.Sports;    
            auto_racing.interests[1] = Classes.Interest.Technology;            
            
        Classes.ActCard beach_cookout = new Classes.ActCard();
            beach_cookout.title = "Beach Cookout";
            beach_cookout.description = "Coal-cooked food, sun-baked skin, and - 30 minutes later - you may swim";
            beach_cookout.interests[0] = Classes.Interest.Dining;    
            beach_cookout.interests[1] = Classes.Interest.Outdoors;
            beach_cookout.interests[2] = Classes.Interest.Swimming;             
            
        Classes.ActCard book_reading = new Classes.ActCard();
            book_reading.title = "Book Reading";
            book_reading.description = "What's even more fun than reading? Listening to someone else reading!";
            book_reading.interests[0] = Classes.Interest.Reading;    
            book_reading.interests[1] = Classes.Interest.Theater;
            
        Classes.ActCard car_show = new Classes.ActCard();
            car_show.title = "Car Show";
            car_show.description = "Some of the stranger prototypes will appeal to those with an artistic eye";
            car_show.interests[0] = Classes.Interest.Art;    
            car_show.interests[1] = Classes.Interest.Technology;  
            
        Classes.ActCard carnival = new Classes.ActCard();
            carnival.title = "Carnival";
            carnival.description = "If you don't care for the rigged games, at least it's a nice day out!";
            carnival.interests[0] = Classes.Interest.Games;    
            carnival.interests[1] = Classes.Interest.Outdoors;             
            
        Classes.ActCard classical_art_museum = new Classes.ActCard();  
            classical_art_museum.title = "Classical Art Museum";
            classical_art_museum.description = "Art lovers and history majors will enjoy these colorful records of the past";
            classical_art_museum.interests[0] = Classes.Interest.Art;
            classical_art_museum.interests[1] = Classes.Interest.Reading; 
            
        Classes.ActCard dance_club = new Classes.ActCard();  
            dance_club.title = "Dance Club";
            dance_club.description = "Strobe lights + dancing = a very intense, however one-note, experience";
            dance_club.interests[0] = Classes.Interest.Dancing;            
            
        Classes.ActCard dance_contest = new Classes.ActCard();  
            dance_contest.title = "Dance Contest";
            dance_contest.description = "Competitive types should enjoy this as much as the light of foot";
            dance_contest.interests[0] = Classes.Interest.Dancing;
            dance_contest.interests[1] = Classes.Interest.Games;              
            
        Classes.ActCard diner = new Classes.ActCard();  
            diner.title = "Diner";
            diner.description = "Cheap, filling, and lacking in thrills";
            diner.interests[0] = Classes.Interest.Dining;
            diner.interests[1] = Classes.Interest.Art;   
            
        Classes.ActCard dinner_theater = new Classes.ActCard();
            dinner_theater.title = "Dinner Theater";
            dinner_theater.description = "Amateur acting is easier to stomach with a good meal in front of you";
            dinner_theater.interests[0] = Classes.Interest.Dining;    
            dinner_theater.interests[1] = Classes.Interest.Theater;   
            
        Classes.ActCard interpretive_dance = new Classes.ActCard();  
            interpretive_dance.title = "Interpretive Dance";
            interpretive_dance.description = "Combining head-scratching abstraction with humans moving their limbs to music";
            interpretive_dance.interests[0] = Classes.Interest.Art;
            interpretive_dance.interests[1] = Classes.Interest.Dancing;               
            
        Classes.ActCard jet_skiing = new Classes.ActCard();
            jet_skiing.title = "Jet Skiing";
            jet_skiing.description = "Make dolphins jealous";
            jet_skiing.interests[0] = Classes.Interest.Outdoors;    
            jet_skiing.interests[1] = Classes.Interest.Swimming;
            jet_skiing.interests[2] = Classes.Interest.Technology;
            
        Classes.ActCard golf = new Classes.ActCard();
            golf.title = "Golf";
            golf.description = "Twelve holes on the greenest turf in town.";
            golf.interests[0] = Classes.Interest.Sports;    
            golf.interests[1] = Classes.Interest.Outdoors;   
            
        Classes.ActCard library = new Classes.ActCard();
            library.title = "Library";
            library.description = "Fun for no one but bookworms";
            library.interests[0] = Classes.Interest.Reading;  
            
        Classes.ActCard masquerade_ball = new Classes.ActCard();
            masquerade_ball.title = "Masquerade Ball";
            masquerade_ball.description = "Food, dancing, and a sea of colorful weirdos";
            masquerade_ball.interests[0] = Classes.Interest.Dancing;    
            masquerade_ball.interests[1] = Classes.Interest.Dining;
            masquerade_ball.interests[2] = Classes.Interest.Theater;  
            
        Classes.ActCard modern_art_museum = new Classes.ActCard();
            modern_art_museum.title = "Modern Art Museum";
            modern_art_museum.description = "The engineering skill in some of these hideous pieces is really quite impressive!";
            modern_art_museum.interests[0] = Classes.Interest.Art;    
            modern_art_museum.interests[1] = Classes.Interest.Technology;            
            
        Classes.ActCard movie = new Classes.ActCard();  // Activity Card: Diner
            movie.title = "Movie";
            movie.description = "Watch a movie";
            movie.interests[0] = Classes.Interest.Theater;
            
        Classes.ActCard photo_safari = new Classes.ActCard();
            photo_safari.title = "Photo Safari";
            photo_safari.description = "Sorry, sportsmen; all you're shooting here are photos";
            photo_safari.interests[0] = Classes.Interest.Art;    
            photo_safari.interests[1] = Classes.Interest.Outdoors;            
            
        Classes.ActCard rhythm_game = new Classes.ActCard();
            rhythm_game.title = "Rhythm Game";
            rhythm_game.description = "Combines dance, tech, and games";
            rhythm_game.interests[0] = Classes.Interest.Dancing;    
            rhythm_game.interests[1] = Classes.Interest.Games;
            rhythm_game.interests[2] = Classes.Interest.Technology;  
            
        Classes.ActCard rollerskating = new Classes.ActCard();
            rollerskating.title = "Roller-Skating";
            rollerskating.description = "Grace and speed in an endless circle";
            rollerskating.interests[0] = Classes.Interest.Dancing;    
            rollerskating.interests[1] = Classes.Interest.Sports;    
            
        Classes.ActCard shakespeare_in_the_park = new Classes.ActCard();
            shakespeare_in_the_park.title = "Shakespeare in the Park";
            shakespeare_in_the_park.description = "Fresh air, free snacks, and a lot of sword fights";
            shakespeare_in_the_park.interests[0] = Classes.Interest.Dining;    
            shakespeare_in_the_park.interests[1] = Classes.Interest.Outdoors;
            shakespeare_in_the_park.interests[2] = Classes.Interest.Theater; 
            
        Classes.ActCard sports_bar = new Classes.ActCard();  
            sports_bar.title = "Sports Bar";
            sports_bar.description = "Even non-sports fans will enjoy the food";
            sports_bar.interests[0] = Classes.Interest.Dining;
            sports_bar.interests[1] = Classes.Interest.Sports;   
            
        Classes.ActCard sports_history_museum = new Classes.ActCard();  
            sports_history_museum.title = "Sports History Museum";
            sports_history_museum.description = "Learn the stories and stats behind humanity's greatest jocks";
            sports_history_museum.interests[0] = Classes.Interest.Reading;
            sports_history_museum.interests[1] = Classes.Interest.Sports;              
            
        Classes.ActCard swimming_pool = new Classes.ActCard();
            swimming_pool.title = "Swimming Pool";
            swimming_pool.description = "Kinda dull, but certainly refreshing";
            swimming_pool.interests[0] = Classes.Interest.Swimming;  
            
        Classes.ActCard trivial_pursuit = new Classes.ActCard();  
            trivial_pursuit.title = "Trivial Pursuit";
            trivial_pursuit.description = "A board game for the sharp of mind";
            trivial_pursuit.interests[0] = Classes.Interest.Reading;
            trivial_pursuit.interests[1] = Classes.Interest.Games;             
            
        Classes.ActCard video_games = new Classes.ActCard();
            video_games.title = "Video Games";
            video_games.description = "They're not just for geeks anymore!";
            video_games.interests[0] = Classes.Interest.Games;    
            video_games.interests[1] = Classes.Interest.Technology;            
            
        Classes.ActCard water_park = new Classes.ActCard();
            water_park.title = "Water Park";
            water_park.description = "Get splashed on purpose.";
            water_park.interests[0] = Classes.Interest.Games;    
            water_park.interests[1] = Classes.Interest.Swimming;   
            
        Classes.ActCard water_polo = new Classes.ActCard();
            water_polo.title = "Water Polo";
            water_polo.description = "A friendly game of wet basketball";
            water_polo.interests[0] = Classes.Interest.Sports;
            water_polo.interests[1] = Classes.Interest.Swimming;      
            
    // Activity Card array (for generating random activities)
        Classes.ActCard[] actCardDeck = { null_actCard, aquarium, art_house_film, auto_racing, beach_cookout, 
            book_reading, car_show, carnival, classical_art_museum, dance_club, 
            dance_contest, diner, dinner_theater, interpretive_dance, jet_skiing, 
            golf, library, masquerade_ball, modern_art_museum, movie, photo_safari, 
            rhythm_game, rollerskating, shakespeare_in_the_park, sports_bar, 
            sports_history_museum, swimming_pool, trivial_pursuit, video_games, 
            water_park, water_polo }; // current count: 31
        
    // Activity Card stack - *i need to randomly draw from the array, null that index, then put it into the stack
        Stack<Classes.ActCard> actCardStack = new Stack();

    // Creating activity stack  
        actCardStack = Functions.shuffleActCardDeck(actCardStack, actCardDeck);

    // Relationship Cards
        // YELLOW
            // Hate Card
        Classes.RelCard yellow_hate = new Classes.RelCard();
            yellow_hate.value = HATE_VALUE;
            yellow_hate.color = Classes.Player.Yellow; 
            yellow_hate.level = Classes.Relationship.Hate;
            yellow_hate.intro = "\t You've earned Player " + yellow_hate.color + "'s " + yellow_hate.level;
            
            
            yellow_hate.invitation[0] =  "INVITATION";
            yellow_hate.invitation[1] =  "   ";
            yellow_hate.invitation[2] =  "   ";        
            // Stranger Card
        Classes.RelCard yellow_str = new Classes.RelCard();
            yellow_str.value = 0;
            yellow_str.color = Classes.Player.Yellow; 
            yellow_str.level = Classes.Relationship.Stranger;
            yellow_str.intro = "\t Player " + yellow_str.color + " is a " + yellow_str.level;
            
            
            yellow_str.invitation[0] =  "INVITATION";
            yellow_str.invitation[1] =  "   - Jaunt (with 1 activity)";
            yellow_str.invitation[2] =  "   - May not date";
            
            // Acquaintance Card
        Classes.RelCard yellow_acq = new Classes.RelCard();
            yellow_acq.value = 1;
            yellow_acq.color = Classes.Player.Yellow; 
            yellow_acq.level = Classes.Relationship.Acquaintance;
            yellow_acq.intro = "\t Player " + yellow_acq.color + " is your " + yellow_acq.level;
            
            
            yellow_acq.invitation[0] =  "INVITATION";
            yellow_acq.invitation[1] =  "   - Jaunt (with 2 activities)";
            yellow_acq.invitation[2] =  "   - Date (with 3 activities)";
            
            yellow_acq.manipulation[0] = "MANIPULATION";
            yellow_acq.manipulation[1] = "   - Activity Swap (SPEND this level)";
            
                if (DEBUG_METHOD){
                System.out.println( yellow_acq.intro );
                System.out.println( "\t    " + yellow_acq.level );
                System.out.println( yellow_acq.invitation[0] );
                System.out.println( yellow_acq.invitation[1] );
                System.out.println( yellow_acq.invitation[2] );
                System.out.println( yellow_acq.manipulation[0] );
                System.out.println( yellow_acq.manipulation[1] );
                System.out.println( "\n\n" );
                }
            
            // Friend Card
        Classes.RelCard yellow_fr = new Classes.RelCard();
            yellow_fr.value = 2;
            yellow_fr.color = Classes.Player.Yellow; 
            yellow_fr.level = Classes.Relationship.Friend;
            yellow_fr.intro = "\t Player " + yellow_fr.color + " is your " + yellow_fr.level;
            
            
            yellow_fr.invitation[0] =  "INVITATION";
            yellow_fr.invitation[1] =  "   - Jaunt (with 3 activities)";
            yellow_fr.invitation[2] =  "   - May NOT date!";
            
            yellow_fr.manipulation[0] = "MANIPULATION";
            yellow_fr.manipulation[1] = "   - Activity Swap (SPEND this level)";
            yellow_fr.manipulation[2] = "   - Snoop (SPEND this level)";
            
            yellow_fr.betrayalBomb[0] = "   - Gossip ";            
            
            // Admirer Card
        Classes.RelCard yellow_adm = new Classes.RelCard(); 
            yellow_adm.value = 3;
            yellow_adm.color = Classes.Player.Yellow; 
            yellow_adm.level = Classes.Relationship.Admirer;
            yellow_adm.intro = "\t Player " + yellow_adm.color + " is your " + yellow_adm.level;
            
            
            yellow_adm.invitation[0] =  "INVITATION";
            yellow_adm.invitation[1] =  "   - Jaunt (with 3 activities)";
            yellow_adm.invitation[2] =  "   - Date (with 4 activities)";
            
            yellow_adm.manipulation[0] = "MANIPULATION";
            yellow_adm.manipulation[1] = "   - Activity Swap (FREE)";
            yellow_adm.manipulation[2] = "   - Snoop (FREE)";
            yellow_adm.manipulation[3] = "   - Cold Shoulder (SPEND this level)";
            yellow_adm.manipulation[3] = "   - Interest Swap (SPEND this level)"; 
            
            yellow_adm.betrayalBomb[0] = "   - Gossip ";
            yellow_adm.betrayalBomb[1] = "   - Stand Up";
            
            // Love Card
        Classes.RelCard yellow_love = new Classes.RelCard();
            yellow_love.value = 4;
            yellow_love.color = Classes.Player.Yellow; 
            yellow_love.level = Classes.Relationship.Love;
            yellow_love.intro = "\t You've earned Player " + yellow_love.color + "'s " + yellow_love.level;
            
            
            yellow_love.invitation[0] =  "INVITATION";
            yellow_love.invitation[1] =  "   - Jaunt (with 3 activities)";
            yellow_love.invitation[2] =  "   - Date (with 4 activities)";
            
            yellow_love.manipulation[0] = "MANIPULATION";
            yellow_love.manipulation[1] = "   - Activity Swap (FREE)";
            yellow_love.manipulation[2] = "   - Snoop (FREE)";
            yellow_love.manipulation[3] = "   - Cold Shoulder (FREE)";
            yellow_love.manipulation[3] = "   - Interest Swap (FREE)";
            
            yellow_love.betrayalBomb[0] = "   - Gossip ";
            yellow_love.betrayalBomb[1] = "   - Stand Up";
                if (DEBUG_METHOD){
                System.out.println( yellow_love.intro );
                System.out.println( "\t    " + yellow_love.level );
                }
                
        // GREEN
            // Hate Card
        Classes.RelCard green_hate = new Classes.RelCard();
            green_hate.value = HATE_VALUE;
            green_hate.color = Classes.Player.Green; 
            green_hate.level = Classes.Relationship.Hate;
            green_hate.intro = "\t You've caused Player " + green_hate.color + " to " + green_hate.level + " you!";
            
            
            green_hate.invitation[0] =  "INVITATION";
            green_hate.invitation[1] =  "   ";
            green_hate.invitation[2] =  "   ";                 
            // Stranger Card            
        Classes.RelCard green_str = new Classes.RelCard();
            green_str.value = 0;
            green_str.color = Classes.Player.Green; 
            green_str.level = Classes.Relationship.Stranger;
            green_str.intro = "\t Player " + green_str.color + " is a " + green_str.level;
            
            
            green_str.invitation[0] =  "INVITATION";
            green_str.invitation[1] =  "   - Jaunt (with 1 activity)";
            green_str.invitation[2] =  "   - May not date";
            
            // Acquaintance Card
        Classes.RelCard green_acq = new Classes.RelCard();
            green_acq.value = 1;
            green_acq.color = Classes.Player.Green; 
            green_acq.level = Classes.Relationship.Acquaintance;
            green_acq.intro = "\t Player " + green_acq.color + " is your " + green_acq.level;
            
            
            green_acq.invitation[0] =  "INVITATION";
            green_acq.invitation[1] =  "   - Jaunt (with 2 activities)";
            green_acq.invitation[2] =  "   - Date (with 3 activities)";
            
            green_acq.manipulation[0] = "MANIPULATION";
            green_acq.manipulation[1] = "   - Activity Swap (SPEND this level)";
            
            // Friend Card
        Classes.RelCard green_fr = new Classes.RelCard();
            green_fr.value = 2;
            green_fr.color = Classes.Player.Green; 
            green_fr.level = Classes.Relationship.Friend;
            green_fr.intro = "\t Player " + green_fr.color + " is your " + green_fr.level;
            
            
            green_fr.invitation[0] =  "INVITATION";
            green_fr.invitation[1] =  "   - Jaunt (with 3 activities)";
            green_fr.invitation[2] =  "   - May NOT date!";
            
            green_fr.manipulation[0] = "MANIPULATION";
            green_fr.manipulation[1] = "   - Activity Swap (SPEND this level)";
            green_fr.manipulation[2] = "   - Snoop (SPEND this level)";
            
            green_fr.betrayalBomb[0] = "   - Gossip ";            
            
            // Admirer Card
        Classes.RelCard green_adm = new Classes.RelCard(); 
            green_adm.value = 3;
            green_adm.color = Classes.Player.Green; 
            green_adm.level = Classes.Relationship.Admirer;
            green_adm.intro = "\t Player " + green_adm.color + " is your " + green_adm.level;
            
            
            green_adm.invitation[0] =  "INVITATION";
            green_adm.invitation[1] =  "   - Jaunt (with 3 activities)";
            green_adm.invitation[2] =  "   - Date (with 4 activities)";
            
            green_adm.manipulation[0] = "MANIPULATION";
            green_adm.manipulation[1] = "   - Activity Swap (FREE)";
            green_adm.manipulation[2] = "   - Snoop (FREE)";
            green_adm.manipulation[3] = "   - Cold Shoulder (SPEND this level)";
            green_adm.manipulation[3] = "   - Interest Swap (SPEND this level)"; 
            
            green_adm.betrayalBomb[0] = "   - Gossip ";
            green_adm.betrayalBomb[1] = "   - Stand Up";
            
            // Love Card
        Classes.RelCard green_love = new Classes.RelCard();
            green_love.value = 4;
            green_love.color = Classes.Player.Green; 
            green_love.level = Classes.Relationship.Love;
            green_love.intro = "\t You've earned Player " + green_love.color + "'s " + green_love.level;
            
            
            green_love.invitation[0] =  "INVITATION";
            green_love.invitation[1] =  "   - Jaunt (with 3 activities)";
            green_love.invitation[2] =  "   - Date (with 4 activities)";
            
            green_love.manipulation[0] = "MANIPULATION";
            green_love.manipulation[1] = "   - Activity Swap (FREE)";
            green_love.manipulation[2] = "   - Snoop (FREE)";
            green_love.manipulation[3] = "   - Cold Shoulder (FREE)";
            green_love.manipulation[3] = "   - Interest Swap (FREE)";
            
            green_love.betrayalBomb[0] = "   - Gossip ";
            green_love.betrayalBomb[1] = "   - Stand Up";
         
        // BLUE
            // Hate Card
        Classes.RelCard blue_hate = new Classes.RelCard();
            blue_hate.value = HATE_VALUE;
            blue_hate.color = Classes.Player.Blue; 
            blue_hate.level = Classes.Relationship.Hate;
            blue_hate.intro = "\t You've caused Player " + blue_hate.color + " to " + blue_hate.level + " you!";
            
            
            blue_hate.invitation[0] =  "INVITATION";
            blue_hate.invitation[1] =  "   ";
            blue_hate.invitation[2] =  "   ";              
            // Stranger Card              
        Classes.RelCard blue_str = new Classes.RelCard();
            blue_str.value = 0;
            blue_str.color = Classes.Player.Blue; 
            blue_str.level = Classes.Relationship.Stranger;
            blue_str.intro = "\t Player " + blue_str.color + " is a " + blue_str.level;
            
            
            blue_str.invitation[0] =  "INVITATION";
            blue_str.invitation[1] =  "   - Jaunt (with 1 activity)";
            blue_str.invitation[2] =  "   - May not date";
            
            // Acquaintance Card
        Classes.RelCard blue_acq = new Classes.RelCard();
            blue_acq.value = 1;
            blue_acq.color = Classes.Player.Blue; 
            blue_acq.level = Classes.Relationship.Acquaintance;
            blue_acq.intro = "\t Player " + blue_acq.color + " is your " + blue_acq.level;
            
            
            blue_acq.invitation[0] =  "INVITATION";
            blue_acq.invitation[1] =  "   - Jaunt (with 2 activities)";
            blue_acq.invitation[2] =  "   - Date (with 3 activities)";
            
            blue_acq.manipulation[0] = "MANIPULATION";
            blue_acq.manipulation[1] = "   - Activity Swap (SPEND this level)";

            // Friend Card
        Classes.RelCard blue_fr = new Classes.RelCard();
            blue_fr.value = 2;
            blue_fr.color = Classes.Player.Blue; 
            blue_fr.level = Classes.Relationship.Friend;
            blue_fr.intro = "\t Player " + blue_fr.color + " is your " + blue_fr.level;
            
            
            blue_fr.invitation[0] =  "INVITATION";
            blue_fr.invitation[1] =  "   - Jaunt (with 3 activities)";
            blue_fr.invitation[2] =  "   - May NOT date!";
            
            blue_fr.manipulation[0] = "MANIPULATION";
            blue_fr.manipulation[1] = "   - Activity Swap (SPEND this level)";
            blue_fr.manipulation[2] = "   - Snoop (SPEND this level)";
            
            blue_fr.betrayalBomb[0] = "   - Gossip ";            
            
            // Admirer Card
        Classes.RelCard blue_adm = new Classes.RelCard(); 
            blue_adm.value = 3;
            blue_adm.color = Classes.Player.Blue; 
            blue_adm.level = Classes.Relationship.Admirer;
            blue_adm.intro = "\t Player " + blue_adm.color + " is your " + blue_adm.level;
            
            
            blue_adm.invitation[0] =  "INVITATION";
            blue_adm.invitation[1] =  "   - Jaunt (with 3 activities)";
            blue_adm.invitation[2] =  "   - Date (with 4 activities)";
            
            blue_adm.manipulation[0] = "MANIPULATION";
            blue_adm.manipulation[1] = "   - Activity Swap (FREE)";
            blue_adm.manipulation[2] = "   - Snoop (FREE)";
            blue_adm.manipulation[3] = "   - Cold Shoulder (SPEND this level)";
            blue_adm.manipulation[3] = "   - Interest Swap (SPEND this level)"; 
            
            blue_adm.betrayalBomb[0] = "   - Gossip ";
            blue_adm.betrayalBomb[1] = "   - Stand Up";
            
            // Love Card
        Classes.RelCard blue_love = new Classes.RelCard();
            blue_love.value = 4;
            blue_love.color = Classes.Player.Blue; 
            blue_love.level = Classes.Relationship.Love;
            blue_love.intro = "\t You've earned Player " + blue_love.color + "'s " + blue_love.level;
            
            
            blue_love.invitation[0] =  "INVITATION";
            blue_love.invitation[1] =  "   - Jaunt (with 3 activities)";
            blue_love.invitation[2] =  "   - Date (with 4 activities)";
            
            blue_love.manipulation[0] = "MANIPULATION";
            blue_love.manipulation[1] = "   - Activity Swap (FREE)";
            blue_love.manipulation[2] = "   - Snoop (FREE)";
            blue_love.manipulation[3] = "   - Cold Shoulder (FREE)";
            blue_love.manipulation[3] = "   - Interest Swap (FREE)";
            
            blue_love.betrayalBomb[0] = "   - Gossip ";
            blue_love.betrayalBomb[1] = "   - Stand Up";            
            
        // RED
            // Hate Card
        Classes.RelCard red_hate = new Classes.RelCard();
            red_hate.value = HATE_VALUE;
            red_hate.color = Classes.Player.Red; 
            red_hate.level = Classes.Relationship.Hate;
            red_hate.intro = "\t You've caused Player " + red_hate.color + " to " + red_hate.level + " you!";
            
            red_hate.invitation[0] =  "INVITATION";
            red_hate.invitation[1] =  "   ";
            red_hate.invitation[2] =  "   ";             
            // Stranger Card              
        Classes.RelCard red_str = new Classes.RelCard();
            red_str.value = 0;
            red_str.color = Classes.Player.Red; 
            red_str.level = Classes.Relationship.Stranger;
            red_str.intro = "\t Player " + red_str.color + " is a " + red_str.level;            
            
            red_str.invitation[0] =  "INVITATION";
            red_str.invitation[1] =  "   - Jaunt (with 1 activity)";
            red_str.invitation[2] =  "   - May not date";
            
            // Acquaintance Card
        Classes.RelCard red_acq = new Classes.RelCard();
            red_acq.value = 1;
            red_acq.color = Classes.Player.Red; 
            red_acq.level = Classes.Relationship.Acquaintance;
            red_acq.intro = "\t Player " + red_acq.color + " is your " + red_acq.level;            
            
            red_acq.invitation[0] =  "INVITATION";
            red_acq.invitation[1] =  "   - Jaunt (with 2 activities)";
            red_acq.invitation[2] =  "   - Date (with 3 activities)";
            
            red_acq.manipulation[0] = "MANIPULATION";
            red_acq.manipulation[1] = "   - Activity Swap (SPEND this level)";

            // Friend Card
        Classes.RelCard red_fr = new Classes.RelCard();
            red_fr.value = 2;
            red_fr.color = Classes.Player.Red; 
            red_fr.level = Classes.Relationship.Friend;
            red_fr.intro = "\t Player " + red_fr.color + " is your " + red_fr.level;            
            
            red_fr.invitation[0] =  "INVITATION";
            red_fr.invitation[1] =  "   - Jaunt (with 3 activities)";
            red_fr.invitation[2] =  "   - May NOT date!";
            
            red_fr.manipulation[0] = "MANIPULATION";
            red_fr.manipulation[1] = "   - Activity Swap (SPEND this level)";
            red_fr.manipulation[2] = "   - Snoop (SPEND this level)";
            
            red_fr.betrayalBomb[0] = "   - Gossip ";            
            
            // Admirer Card
        Classes.RelCard red_adm = new Classes.RelCard(); 
            red_adm.value = 3;
            red_adm.color = Classes.Player.Red; 
            red_adm.level = Classes.Relationship.Admirer;
            red_adm.intro = "\t Player " + red_adm.color + " is your " + red_adm.level;
            
            red_adm.invitation[0] =  "INVITATION";
            red_adm.invitation[1] =  "   - Jaunt (with 3 activities)";
            red_adm.invitation[2] =  "   - Date (with 4 activities)";
            
            red_adm.manipulation[0] = "MANIPULATION";
            red_adm.manipulation[1] = "   - Activity Swap (FREE)";
            red_adm.manipulation[2] = "   - Snoop (FREE)";
            red_adm.manipulation[3] = "   - Cold Shoulder (SPEND this level)";
            red_adm.manipulation[3] = "   - Interest Swap (SPEND this level)"; 
            
            red_adm.betrayalBomb[0] = "   - Gossip ";
            red_adm.betrayalBomb[1] = "   - Stand Up";
            
            // Love Card
        Classes.RelCard red_love = new Classes.RelCard();
            red_love.value = 4;
            red_love.color = Classes.Player.Red; 
            red_love.level = Classes.Relationship.Love;
            red_love.intro = "\t You've earned Player " + red_love.color + "'s " + red_love.level;
            
            red_love.invitation[0] =  "INVITATION";
            red_love.invitation[1] =  "   - Jaunt (with 3 activities)";
            red_love.invitation[2] =  "   - Date (with 4 activities)";
            
            red_love.manipulation[0] = "MANIPULATION";
            red_love.manipulation[1] = "   - Activity Swap (FREE)";
            red_love.manipulation[2] = "   - Snoop (FREE)";
            red_love.manipulation[3] = "   - Cold Shoulder (FREE)";
            red_love.manipulation[3] = "   - Interest Swap (FREE)";
            
            red_love.betrayalBomb[0] = "   - Gossip ";
            red_love.betrayalBomb[1] = "   - Stand Up";  
    // end Relationship Cards               
            
    // RELATIONSHIP CARD ARRAYS
        //Blue (for use by each player in their readOut)
        Classes.RelCard[] relCards_blue = new Classes.RelCard[6];
            relCards_blue[5] =  blue_hate;
            relCards_blue[0] =  blue_str;
            relCards_blue[1] =  blue_acq;
            relCards_blue[2] =  blue_fr;
            relCards_blue[3] =  blue_adm;
            relCards_blue[4] =  blue_love;   
            
        //Red (for use by each player in their readOut)
        Classes.RelCard[] relCards_red = new Classes.RelCard[6];
            relCards_red[5] =   red_hate;
            relCards_red[0] =   red_str;
            relCards_red[1] =   red_acq;
            relCards_red[2] =   red_fr;
            relCards_red[3] =   red_adm;
            relCards_red[4] =   red_love;     
            
        //Green (for use by each player in their readOut)
        Classes.RelCard[] relCards_green = new Classes.RelCard[6];
            relCards_green[5] =     green_hate;
            relCards_green[0] =     green_str;
            relCards_green[1] =     green_acq;
            relCards_green[2] =     green_fr;
            relCards_green[3] =     green_adm;
            relCards_green[4] =     green_love;       
            
        //Yellow (for use by each player in their readOut)
        Classes.RelCard[] relCards_yellow = new Classes.RelCard[6];
            relCards_yellow[5] = yellow_hate;
            relCards_yellow[0] = yellow_str;
            relCards_yellow[1] = yellow_acq;
            relCards_yellow[2] = yellow_fr;
            relCards_yellow[3] = yellow_adm;
            relCards_yellow[4] = yellow_love;              

    // Player Huds
        Classes.Hud playerBlue = new Classes.Hud();
            playerBlue.name = Classes.Player.Blue;
            playerBlue.my_relCards = relCards_blue; // array of the player's own relCards (to be given to those who earn them)
            playerBlue.type = Classes.PlayerType.Human; // sets player to type: Human
        Classes.Hud playerRed = new Classes.Hud();
            playerRed.name = Classes.Player.Red;
            playerRed.my_relCards = relCards_red;
            playerRed.type = Classes.PlayerType.AI;  // sets player to type: AI
        Classes.Hud playerGreen = new Classes.Hud();
            playerGreen.name = Classes.Player.Green;
            playerGreen.my_relCards = relCards_green;
            playerGreen.type = Classes.PlayerType.AI;  // sets player to type: AI
        Classes.Hud playerYellow = new Classes.Hud();   
            playerYellow.name = Classes.Player.Yellow;
            playerYellow.my_relCards = relCards_yellow;
            playerYellow.type = Classes.PlayerType.AI;  // sets player to type: AI
            
        // An array of all the players' Huds   
        Classes.Hud[] playersList = new Classes.Hud[MAXPLAYERS];
            playersList[0] = playerBlue;
            playersList[1] = playerRed;
            playersList[2] = playerGreen;
            playersList[3] = playerYellow;
        // Putting the players array into each player's Hud, for reference in functions    
        playerBlue.playersList =    playersList;
        playerRed.playersList =     playersList;
        playerGreen.playersList =   playersList;
        playerYellow.playersList =  playersList;
            
        // Assigning Interest Cards
        if (DEBUG_MAIN)
            System.out.println( "\nAll players draw their Interest Cards..." );        
        playerBlue.intCard_slots =      Functions.intCardSlotsAssign(playerBlue, intCardDeck);      // sending current player, the card deck 
        playerRed.intCard_slots =       Functions.intCardSlotsAssign(playerRed, intCardDeck);       // sending current player, the card deck  
        playerGreen.intCard_slots =     Functions.intCardSlotsAssign(playerGreen, intCardDeck);     // sending current player, the card deck  
        playerYellow.intCard_slots =    Functions.intCardSlotsAssign(playerYellow, intCardDeck);    // sending current player, the card deck 
        if (DEBUG_MAIN)
            System.out.println();  
        if (DEBUG_MAIN){
            System.out.println("\t Blue's interests: " );
            for (i=0; i<playerBlue.intCard_slots.length; i++)
                System.out.println( "\t\tInterest #" + (i+1) + ": " + playerBlue.intCard_slots[i].title);           
            System.out.println("\t Red's interests" );
            for (i=0; i<playerRed.intCard_slots.length; i++)
                System.out.println( "\t\tInterest #" + (i+1) + ": " + playerRed.intCard_slots[i].title);  
            System.out.println("\t Green's interests" );
            for (i=0; i<playerGreen.intCard_slots.length; i++)
                System.out.println( "\t\tInterest #" + (i+1) + ": " + playerGreen.intCard_slots[i].title);  
            System.out.println("\t Yellow's interests" );
            for (i=0; i<playerYellow.intCard_slots.length; i++)
                System.out.println( "\t\tInterest #" + (i+1) + ": " + playerYellow.intCard_slots[i].title);  
        }//end debug        
        
        // Assigning Activity Cards
        if (DEBUG_MAIN)
            System.out.println( "\nAll players draw their Activity Cards..." );
        playerBlue.actCard_slots =      Functions.actCardSlotsAssign(playerBlue, actCardStack );    // sending current player, the card deck, and the used mask       
        playerRed.actCard_slots =       Functions.actCardSlotsAssign(playerRed, actCardStack );     // sending current player, the card deck, and the used mask  
        playerGreen.actCard_slots =     Functions.actCardSlotsAssign(playerGreen, actCardStack );   // sending current player, the card deck, and the used mask            
        playerYellow.actCard_slots =    Functions.actCardSlotsAssign(playerYellow, actCardStack );  // sending current player, the card deck, and the used mask    
        if(DEBUG_MAIN)
            System.out.println();
        if (DEBUG_MAIN){
            System.out.println("\t Blue's 'home' activities: " );
            for (i=0; i<playerBlue.actCard_slots.length; i++)
                System.out.println( "\t\tActivity #" + (i+1) + ": " + playerBlue.actCard_slots[i].title);           
            System.out.println("\t Red's 'home' activities: " );
            for (i=0; i<playerRed.actCard_slots.length; i++)
                System.out.println( "\t\tActivity #" + (i+1) + ": " + playerRed.actCard_slots[i].title);  
            System.out.println("\t Green's 'home' activities: " );
            for (i=0; i<playerGreen.actCard_slots.length; i++)
                System.out.println( "\t\tActivity #" + (i+1) + ": " + playerGreen.actCard_slots[i].title);  
            System.out.println("\t Yellow's 'home' activities: " );
            for (i=0; i<playerYellow.actCard_slots.length; i++)
                System.out.println( "\t\tActivity #" + (i+1) + ": " + playerYellow.actCard_slots[i].title);  
        }//end debug
        
        // Assigning Crushes
        playerBlue.crush =      Functions.getCrush(playerBlue.name);
        playerRed.crush =       Functions.getCrush(playerRed.name);
        playerGreen.crush =     Functions.getCrush(playerGreen.name);
        playerYellow.crush =    Functions.getCrush(playerYellow.name);
        
        if (DEBUG_MANIPULATIONS == false){
        // Assigning Relations (*currently starting playerBlue with high opinions of other players for testing purposes)
        playerBlue.relation_slots = Functions.getRelations(playerBlue.name, relCards_red, 0, relCards_green, 0, relCards_yellow, 0);
                                                // this player, Red relationship cards, Red's relationship level, etc...
        playerRed.relation_slots = Functions.getRelations(playerRed.name, relCards_blue, 0, relCards_green, 0, relCards_yellow, 0);
        playerGreen.relation_slots = Functions.getRelations(playerGreen.name, relCards_red, 0, relCards_blue, 0, relCards_yellow, 0);
        playerYellow.relation_slots = Functions.getRelations(playerYellow.name, relCards_red, 0, relCards_green, 0, relCards_blue, 0);
        }//end if DEBUG
        if (DEBUG_MANIPULATIONS){
        // Assigning Relations (*currently starting playerBlue with high opinions of other players for testing purposes)
        playerBlue.relation_slots = Functions.getRelations(playerBlue.name, relCards_red, 4, relCards_green, 4, relCards_yellow, 4);
                                                // this player, Red relationship cards, Red's relationship level, etc...
        playerRed.relation_slots = Functions.getRelations(playerRed.name, relCards_blue, 4, relCards_green, 4, relCards_yellow, 4);
        playerGreen.relation_slots = Functions.getRelations(playerGreen.name, relCards_red, 4, relCards_blue, 4, relCards_yellow, 4);
        playerYellow.relation_slots = Functions.getRelations(playerYellow.name, relCards_red, 4, relCards_green, 4, relCards_blue, 4);            
        }//end if DEBUG
        
        // GAME
        if (AI_TEST)
            AI.jaunt(playerRed);
        
        //GUI
        if (GUI){
  
        }//end if(GUI)
        
        // BLUE readout
        if (GAME){
        Functions.readOut(playerBlue);

        // Turn
        while (winCondition == false){
            
            Functions.mainMenu( playerBlue );

            
        
        }//end while: winCondition
        
        }//end global: GAME 
        
        
    }//end main
    
 
    

    

    
   
            
    

    

    

    

    

    

    

    
    
    
}//end file
