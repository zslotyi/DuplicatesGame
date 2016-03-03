/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duplicatesgame;

import static java.lang.Thread.sleep;

/**
 *
 * @author Zsolt
 */
public class Adder implements Runnable {
    private final DuplicatesGameGUI DGG;
    private boolean stopIt=false;
    Adder(DuplicatesGameGUI dgg){
        DGG=dgg;
    }
    Adder(){
        throw new AssertionError("Don't ever call the no-arg constructor on Adder!");
    }
    @Override
    public void run() {
                    if(DGG==null) throw new AssertionError("hmmmm....");
            
                try {
                    for(;((DGG.isOn())&&(!stopIt));)
                    {
                        if (DGG.getEmptyFieldsSize()>0){
                        DGG.putOnEmptyField();
                        System.out.println("Sleeping: " + 5*Math.pow(0.9, DGG.getLevel())*1100);
                        sleep((long)(5*Math.pow(0.9, DGG.getLevel())*1100));
                        }
                        else {
                            DGG.gameOver();
                        }
                    }
                }
                catch(InterruptedException ie)
                {
                }
            
    }
    void stopIt(){
        stopIt=true;
    }
}
