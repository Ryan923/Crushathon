package friendzone;

public class Classes {
    
    public static enum Player {      // player color names
        Blue,
        Red,
        Yellow,
        Green
       
    }//end Player    
    
    public static enum PlayerType {      // player color names
        Human,
        AI
       
    }//end Player      
    
    public static enum Relationship {   // the 5 relationship levels
        Hate,
        Stranger,
        Acquaintance,
        Friend,
        Admirer,
        Love
    }//end Relationship    
    
    public static class RelCard {  // Relationship card template
        int value;
        Classes.Player color;
        String intro;
        Classes.Relationship level;
        String[] invitation = new String[6];
        String[] manipulation = new String[6];
        String[] betrayalBomb = new String[6];
        String info;
        
    }//end RelCard    
    
    public static class Hud{        // this is all the information relevant to a specific player - source for a future GUI
        PlayerType type;            // tells if player is Human or AI
        Player name;                // this is one of the four Player enums: Blue, Red, Green, or Yellow
        Player crush;               // The player's crush, assigned early in the game; also a Player enum; obviously can't == name      
        IntCard[] intCard_slots = new IntCard[4];           // Players each draw 4 Interest Cards
        Classes.ActCard[] actCard_slots = new Classes.ActCard [RunGame.PLAYER_ACTCARDS];          // Player holds the max # of Activity Cards.
        RelCard[] relation_slots = new Classes.RelCard[RunGame.MAXPLAYERS - 1];      // list of all rival rel. cards collected;
        RelCard[] my_relCards = new RelCard[6];             // stack of player's own relationship cards 
        Hud[] playersList = new Hud[RunGame.MAXPLAYERS];
        String[][] rivalInterests = 
                new String[RunGame.MAXPLAYERS][RunGame.PLAYER_INTCARDS];    // Holds discovered interests of other players; [player's name][player's interests]
        int turnCount = 0;  // This is incremented each time the player finishes a turn

    }//end Hud    
    
    public static enum Interest {   // Interests appear both on Interest Cards and Activity Cards
        
        Art,
        Reading,
        Dancing,
        Dining,
        Games,
        Outdoors,
        Sports,
        Swimming,
        Technology,
        Theater
        
    }//end Interest
    
    public static class IntCard {    // Interest Card template
        String intro = "Your interests include";    // the lead-in text on every card
        Interest title;                              // the interest is an Interest enum
        String info;                                // A description of the interest
        String image;                               // Nothing for now, but potentially an icon for a future GUI
    }//end IntCard    
    
    public static class ActCard{    // Activity Card template
        String title;
        String description;
        int maxInterests = 6;       // the maximum number of interests an activity card may have

        Interest[] interests = new Interest[3];       // array of interests
    }//end ActCard
    
    
}//end file
