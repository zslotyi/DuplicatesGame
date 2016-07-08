/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duplicatesgame;

import static java.lang.Thread.sleep;
import javafx.application.Platform;

/**
 *
 * @author Zsolt
 */
public class Adder extends Thread implements Runnable {
    private final DuplicatesGameGUI DGG;
    private static boolean stopIt=false;
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
                    for(;!DGG.isGameOver();)
                    {
                        if((DGG.isOn())&&(!stopIt)){
                            if (DGG.getEmptyFieldsSize()>0){
                                Platform.runLater(() -> 
                                            {DGG.putOnEmptyField();
                                             DGG.updateBoard();
                                            });
                                                         
                                                        
                                                            }
                                                    }

                        System.out.println("Sleeping: " + 5*Math.pow(0.9, DGG.getLevel())*1100);
                        sleep((long)(5*Math.pow(0.9, DGG.getLevel())*1100));
                        
                    }
                }
                catch(InterruptedException ie)
                {
                }
            
    }
    static void stopIt(){
        stopIt=true;
    }
}
