/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duplicatesgame;

/**
 *
 * @author Zsolt
 */
public class Score {
    private int level;
    private int score=0;
    private static final Score INSTANCE = new Score();
    //API Begins
    public static Score getScore(int level){
        INSTANCE.setLevel(level);
        return INSTANCE;
    }
    public static Score getScore(){
        return INSTANCE;
    }
    public Score Add(){
        score+=level*7;
        return INSTANCE;
    }
    public int actualScore(){
        return score;
    }
    int nextLevel() {
        return ++level;
    }
    void resetLevel() {
        level=1;
    }
    void setLevel (int l){
        level=l;
    }
    int getLevel() {
        return level;
    }
    int resetScore() {
        score = 0;
        return score;
    }
    //API Ends
    private Score(){
        
    }
}
