/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duplicatesgame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Zsolt
 */
interface Playable{
    void setUpGUI();
    Collection setUpGameElements();
    void putOnEmptyField();
    void reStartGame(int level);
}
abstract class DuplicatesGameGUI {
    final int BOARD_SIZE;
    final List GameFields;
    static Thread adderThread;
    static List emptyFields, possibleGameElements, currentGameElements;
    private boolean endGame=false;

    int count=0;
    
    abstract boolean putElement(GameField gf, Object ge);
    abstract DuplicatesGameGUI getDuplicatesGameGUIInstance();
    abstract void move();
    abstract int resetScore();
    abstract int getLevel();
    abstract void gameOver();
    abstract void restartAdderThread();
    //API Begins**********************************************************
    public int getEmptyFieldsSize(){
        return emptyFields.size();
    }
    boolean isOn(){
        return !endGame;
    }
    void endTheGame(){
        endGame=true;
        adderThread=null;
    }
    void beginGame(){
        endGame=false;
    }
    //API Ends************************************************************
    DuplicatesGameGUI(int boardSize){
        BOARD_SIZE=boardSize;
        GameFields = initBoard();
    }
    DuplicatesGameGUI(){
        this(6); //value is arbitrary, but we need to have an initial BOARD_SIZE value
    }
    public Collection getBoard(){
        assert !GameFields.isEmpty() : "Game board should have more than zero fields";
        return GameFields;
    }
    private List initBoard(){
        assert(BOARD_SIZE < 8) : "Board size should not be zero " + BOARD_SIZE;
        
        List<GameField> temp = new ArrayList<>();
        
        char x = 'A';
        
        for(int i=0;i<BOARD_SIZE;i++)
        {
            char ch = x++;
                for(int e=1;e<(BOARD_SIZE+1);e++)
                {
                    temp.add(new GameField(ch,e));
                    assert (temp.size()<50) : "Temp size should be smaller than 50";
                }
        }
        return temp;
    }
    Thread initAdder(DuplicatesGameGUI dgg){
        Thread t = new Thread(new Adder(dgg));
        return t;
    }
    public void putOnEmptyField() {
        assert emptyFields.size() > 0 : "you have to have more than zero empty fields";
        
        int e=0;
            if (emptyFields.size()>1)
                e = randomize(emptyFields.size()-1,0);
            else
                e=0;
            
        int k = randomize(possibleGameElements.size()-1,0);
        GameField gf = (GameField)emptyFields.get(e);
        
        boolean success = putElement(gf,possibleGameElements.get(k));
            assert success == true : "We couldn't place this element here: " + emptyFields.get(e).toString();
            
        int s = emptyFields.size();
        removeGameElement(gf,emptyFields);
        //System.out.println(gf2.getClass());   System.out.println(gf2);
        assert emptyFields.size()==s-1 : "Something went wrong with the removing stuff";
        
        boolean adds;
        adds = addGameElement(possibleGameElements.get(k),currentGameElements);
            assert adds==true : "We cound't add the game element to the currentGameElements list";
        
            
    }
    Object removeGameElement(Object ge, List li) {
            return li.remove(ge);
            //System.out.println(currentGameElements.size());
    }
    boolean addGameElement(Object ge, List li){ 
        return li.add(ge);
    }
    int randomize(int high, int low) {
        Random r = new Random();
        return r.nextInt(high) + low;
    }
    void getWin(int i){
        switch(i){
            case 1: System.out.println("You Won!!!"); endGame=true; break;
            case -1: System.out.println("You lost!"); endGame=true; break;
        }
    }
    class GameField{
        private final char x;
        private final int y;
        public char getX(GameField gf){
            return gf.x;
        }
        public char getX() {
            return x;
        }
        public int getY(GameField gf){
            return gf.y;
        }
        public int getY() {
            return y;
        }
        public int[] getFieldParameters(GameField gf){
            
            return new int[] {gf.x, gf.y};
        }
        private GameField(char x, int y)
        {
            this.x=x;
            this.y=y;
        }
        public String toString()
        {
            StringBuilder sb = new StringBuilder().append(x).append(y);
            return new String(sb);
        }
    }
}
