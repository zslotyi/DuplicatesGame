/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duplicatesgame;

import java.util.ArrayList;
import java.util.Collection;
import static java.util.Collections.shuffle;
import java.util.HashSet;
import java.util.List;

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
public class DuplicatesGame {

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
    private final Collection possibleGameElements;
    private Collection initialGameElements;
    private static DuplicatesGame INSTANCE;
    private final Score SCORE;
    private final DuplicatesGame.DuplicatesGameList ge;
    /**
     * ********************** CLASS API BEGINS ************************
     * Factory methods: getDuplicatesGame(int level); getDuplicatesGame(); (level defaults to 1);
     * @param level
     * @return DuplicatesGame game instance
     */
    public static DuplicatesGame getDuplicatesGame(int level){
        /**
         * Ensures that the class is singleton and returns the single instance of the class
         */
        if (INSTANCE!=null)
        {
            return INSTANCE;
        }
        else
        {
            INSTANCE = new DuplicatesGame(level);
        }
        return INSTANCE;
    }
    public static DuplicatesGame getDuplicatesGame(){
        if (INSTANCE!=null)
        {return INSTANCE;}
        
        return getDuplicatesGame(1);
    }
    public Collection getPossibleGameElements(){
        return possibleGameElements;
    }
    public Collection getInitialGameElements(){
        assert initialGameElements.size()>0 : "We should have more than zero initial game elements at this point";
        return initialGameElements;
    }
    /**
     * This method checks if the game has ended with the current move.
     * It returns
     *      1 if the player has won
     *      -1 if the player has lost
     *      0 if the game continues
     * @param ge
     * @param currentGameElements
     * @return int
     */
    public int getWin(Object ge, List currentGameElements){
        if(!currentGameElements.contains(ge)){
            //If there isn't another instance of the removed element in the list, you won;
            return -1;
        }
        else if(new HashSet(currentGameElements).size()<currentGameElements.size()) {
            //We still have duplicates --> the game moves on
            return 0;
        }
        return 1; //You won!!!!
    }
    /**
     * This method will return the actual score of the game, based on the class' SCORE instance
     * @return 
     */
    String getActualScore(){
        return " " + SCORE.actualScore();
    }
    String nextLevel() {
        return " " + SCORE.nextLevel();
    }
    void move() {
        SCORE.Add();
    }
    void newGame(){
        initialGameElements = ge.getInitCollection();
    }
    /**
     * ********************** CLASS API ENDS ************************
     * 
     */
    private DuplicatesGame(int level) {
    /**
     * Constructor is private, use the API's factory method instead
     */
        assert(INSTANCE==null) : "Constructor called after game already initialized"; 
                
        SCORE = Score.getScore(level);
        ge = this.new DuplicatesGameList();
        possibleGameElements = ge.getDuplicatesGameList();
        initialGameElements = ge.getInitCollection();
        
    }
    private DuplicatesGame(){
        /**
         *  If no level is given the game starts from level 1
         */
        this(1);
    }


 
    private class DuplicatesGameList {
        private final Collection possibleGameElements;
        private DuplicatesGameList GAMELISTINSTANCE;
        //Class API Begins
        Collection getDuplicatesGameList() {
        /**
         * Returns all possible game elements.
         */
            assert possibleGameElements.size()>0 : "We should know the possible game elements by now";
            return possibleGameElements;
        }
        Collection getInitCollection(){
            /**
             * Returns a subset of all possible game elements. This subset is randomized,
             * and it can be used to produce an initial setup of the game (to make sure that
             * there are duplicates when the game begins.
             */
        List<Integer> temp = new ArrayList(this.getDuplicatesGameList());
        shuffle(temp);
        return temp.subList(0,15); //value is arbitrary. for testing purposes we are dealing with a 7x7 field, and an initial set of 30 game elements
        }
        //Class API Ends
        private DuplicatesGameList(){
            
            possibleGameElements = initGameElements();
        }
        private Collection initGameElements(){
            //this method initializes the possible Game Elements list
            Collection<Integer> tempElements = new ArrayList<>();
            
                for(int i=0;i<50;i++)
                {
                    tempElements.add(i);
                }
            return tempElements;
        }
        
    }
}

