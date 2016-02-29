/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duplicatesgame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import static java.util.Collections.shuffle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Zsolt
 */

public class DuplicatesGameSwing extends DuplicatesGameGUI implements Playable{

private final DuplicatesGame dg;
private final Map gamePanels;
private Container contentPane;
private JFrame frame;
private final ArrayList initialGameElements;
private static DuplicatesGameSwing INSTANCE;
private final static Dimension DMNSN = new Dimension(75,75);
private final static Dimension BTNDMNSN = new Dimension(70,70);
private final static Font BUTTONFONT = new Font("Arial", Font.BOLD, 28);
private JLabel statusField, actualScore, scoreTitle;
private JPanel scoreCard;
private String level = "1";


        public static void main(String[] args) {
        INSTANCE = new DuplicatesGameSwing();
        adderThread=INSTANCE.initAdder();
        adderThread.start();
        System.out.println("Adder thread started");
        }
        Thread initAdder()
        {
            Thread t = initAdder(INSTANCE);
            return t;
        }
        DuplicatesGameSwing(int level){
            dg = DuplicatesGame.getDuplicatesGame(1);
            initialGameElements = new ArrayList(dg.getInitialGameElements());
            currentGameElements = new ArrayList<>();
            possibleGameElements = new ArrayList(dg.getPossibleGameElements());
        
            setUpGUI();
            gamePanels = setUpBoard();
            emptyFields = setUpGameElements();
            showGUI();
        }
        DuplicatesGameSwing(){
            //if no level is provided, we default to level 1
            this(1);
        }
        public DuplicatesGameSwing getDuplicatesGameGUIInstance(){
            assert INSTANCE!=null : "Trying to return an instance before initializing";
            return INSTANCE;
        }
        @Override
        public final void setUpGUI() {
            frame = new JFrame("Duplicates Game - Swing version");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            contentPane = frame.getContentPane();
            GridBagLayout gridBagLayout = new GridBagLayout();
            contentPane.setLayout(gridBagLayout);    
        }

        /**
         * This is the method that makes the final steps in displaying the GUI
         */
    public final void showGUI() {
            frame.pack();
            frame.setVisible(true);
        }
    private Map setUpBoard() {
            assert !GameFields.isEmpty() : "Game Fields should exist at this point";
            Map<GameField,JPanel> tempPanels = new HashMap<>();
            
            for (Iterator it = GameFields.iterator();it.hasNext();)
            {
                GridBagConstraints g = new GridBagConstraints();
                GameField gf = (GameField)it.next();
                g.gridx=gf.getX()-'A';
                g.gridy=gf.getY();
                g.anchor=GridBagConstraints.NORTHWEST;
                g.fill=GridBagConstraints.BOTH;
                g.ipadx=8;
                g.ipady=8;
                JPanel jp = new JPanel();
                jp.setSize(DMNSN);
                contentPane.add(jp,g);
                
                tempPanels.put(gf,jp);
            }
            GridBagConstraints sf = new GridBagConstraints();
            sf.gridy=8;
            sf.gridx=1;
            sf.gridwidth=7;
            
            statusField = new JLabel("Hello! Welcome to the game!");
            statusField.setFont(BUTTONFONT);
            contentPane.add(statusField,sf);
            
            GridBagConstraints sc = new GridBagConstraints();
            sc.gridx=8;
            sc.gridy=1;
            sc.ipadx=20;
            sc.gridheight=8;
            
            scoreCard = new JPanel();
            GridLayout gl = new GridLayout(3,1);
            scoreCard.setLayout(gl);
            scoreTitle = new JLabel("Score: ");
            scoreTitle.setFont(BUTTONFONT);
            actualScore = new JLabel(dg.getActualScore());
            actualScore.setFont(BUTTONFONT);
            scoreCard.add(scoreTitle);
            scoreCard.add(actualScore);
            
            contentPane.add(scoreCard,sc);
            return tempPanels;
        }
    
    @Override
    public final List setUpGameElements(){
        ArrayList<GameField> temp = (ArrayList)GameFields;
        shuffle(temp);
        ArrayList<GameField> tempgf = new ArrayList<>();
        emptyFields=GameFields;
        for(int i=0; (i<initialGameElements.size()*2);i++)
        {
            int e = randomize(14,0);
            currentGameElements.add(initialGameElements.get(e));
            GameField gf = temp.get(i);
                    System.out.println("Nr. of empty fields: " + " " + gf);
                tempgf.add(gf);
                boolean addSuccess = putElement(gf, initialGameElements.get(e));
                    assert addSuccess==true : "We could not add this button: " + initialGameElements.get(e).toString();
        }
        emptyFields.removeAll(tempgf);
        System.out.println("used gamefields: "+ tempgf.size() + " empty fields: " + emptyFields.size());
        return emptyFields;
    }
    @Override
    boolean putElement(GameField gf, Object ge){
        boolean success=false;
        try {    
                JPanel jp = (JPanel)gamePanels.get(gf);
                //jp.setSize(DMNSN);
                //System.out.println("This is the Button we're adding the element to: " + ge);
                JButton jb = new JButton(ge.toString());
                jb.setPreferredSize(BTNDMNSN);
                jb.setFont(BUTTONFONT);
                jb.setBackground(setButtonColor(parseInt(ge.toString())));
                jb.addActionListener((al)->{
                jp.remove(jb);
                jp.validate();
                jp.repaint();
                move();
                removeGameElement(ge,currentGameElements);
                emptyFields.add(gf);
                getWin(dg.getWin(ge, currentGameElements));
                });
                
                ///jb.setSize(DMNSN);
                jp.add(jb);
                jp.validate();
                //jb.validate();
                jp.repaint();
                //jb.repaint();
                success=true;
                //System.out.println("Added this emlement " + ge + " to this field: " + gf);
            }
        catch(ArrayIndexOutOfBoundsException e) {
        
        }
        
        return success;
    }
    @Override
    void getWin(int i){
        switch(i){
            case 1: 
                level = dg.nextLevel();
                statusField.setText("Level: " + level); 
                statusField.setBackground(Color.GREEN); 
                statusField.setOpaque(true); 
                statusField.repaint(); 
                break;
            case -1: statusField.setText("You Lost!!!"); statusField.setBackground(Color.RED); statusField.setOpaque(true); statusField.repaint(); endTheGame(); break;
        }
    }
    @Override
    void move() {
        dg.move();
        actualScore.setText(dg.getActualScore());
        actualScore.repaint();
    }
    private Color setButtonColor(int i){
        Color clr = new Color(230,i*5,255-i*2);
        return clr;
    }
    
    
}
