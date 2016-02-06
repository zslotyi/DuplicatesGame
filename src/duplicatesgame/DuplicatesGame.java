/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duplicatesgame;

import java.util.Collection;

/**
 *
 * @author Zsolt
 * @project launch date: 2016. 02. 06. 14:25
 * This is the game where the player needs to remove all duplicates from the field not touching single
 * instances.
 * 
 * This class contains game logic without display implementation details. This class will be constructed by
 * the implementor classes (ie. Swing, Android etc.) that will display any outputs and handle any interactions
 * with the user
 */
public class DuplicatesGame implements Runnable {

    /**
     * This class handles the game logic. It has three embedded, private classes accounting for:
     * - the game elements
     * - and the game fields
     * - and a third one accounting for the Collection implementation used by the game
     * 
     * This class implements the Runnable interface, because putting new game elements on the board is running
     * in a separate thread
     * 
     */
    private enum possibleGameElements{Pikk, Káró, Kör, Bubi}
    private Collection<DuplicatesGameElement> currentGameElements;
    private Collection<GameFields> emptyGameFields;
    private final int LEVEL;
    
    /**
     * ********************** CLASS API BEGINS ************************
     * 
     */
    public DuplicatesGame getDuplicatesGame(int level){
        /**
         * Ensures that the class is singleton and returns the single instance of the class
         */
        return new DuplicatesGame(level);
    }
    /**
     * ********************** CLASS API ENDS ************************
     * 
     */
    private DuplicatesGame(int level) {
    /**
     * Constructor is private, use the API's factory method instead
     */
        LEVEL = level;
    }
    private DuplicatesGame(){
        /**
         *  If no level is given the game starts from level 1
         */
        LEVEL = 1;
    }
    @Override public void run(){
        /**
         *  This method starts and handles putting new GameElements on the board
         *  The run() method has to be called when the first GameElement is removed from the board
         */
        
    }

    private class DuplicatesGameElement {
    
    }
    private class DuplicatesGameList {
        
    }
    private class GameFields {
        
    }
}

