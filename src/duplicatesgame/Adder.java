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
                    for(;DGG.isOn();)
                    {
                    DGG.putOnEmptyField();
                    System.out.println("PutOnEmptyField lefutott. Ennyi üres hely van: " + DGG.getEmptyFieldsSize());
                    sleep(5*1000);
                    }
                }
                catch(InterruptedException ie)
                {
                    ie.printStackTrace();
                }
            
    }
}
