/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duplicatesgame;

/**
 *
 * @author zslotyi
 */
class GameElement {
    private final int i;
    public static GameElement getGameElement(int i) {
        return new GameElement(i);
    }
    public int getI(){
        return i;
    }
    @Override
    public String toString() {
        return ("" + i);
    }
    private GameElement(int i){
        this.i=i;
    }
}
