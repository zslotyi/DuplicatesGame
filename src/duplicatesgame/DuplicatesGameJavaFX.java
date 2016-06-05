/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duplicatesgame;


import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author zslotyi
 * @v2.0 - 2016. 06. 05. 09:11 - complete rewrite after stolen laptop
 * skeleton: 09:38
 * set up game: 14:17
 * game move, win/lose working: 15:06
 * adder thread running: 15:29
 * pause-resume game working: 15:54
 * 
 */
public class DuplicatesGameJavaFX extends DuplicatesGameGUI {
    private GridPane rootNode;
    private DuplicatesGame dg;
    private List initialGameElements;
    private Label emptyFieldCounter, currentGameElementCounter, initialGameElementCounter, levelCounter, scoreCounter, winMessage;
    private String winMessageText;
    private Button pauseButton, resumeButton;
    
    public static void main (String[] args) {
    
        Application.launch(args);
        
    }
     private void initGame() {
            initialGameElements = new ArrayList(dg.getInitialGameElements());
            currentGameElements = new ArrayList<>();
            possibleGameElements = new ArrayList(dg.getPossibleGameElements());
        }
     private void setupBoard() {
         emptyFieldCounter = new Label ("Number of empty fields: " + getEmptyFieldsSize());
         currentGameElementCounter = new Label ("Number of current game elements: " + currentGameElements.size());
         initialGameElementCounter = new Label ("Number of initial game elements: " + initialGameElements.size());
         levelCounter = new Label("Level: " + dg.getLevel());
         scoreCounter = new Label ("Score: " + dg.getActualScore());
         winMessage = new Label ("Welcome to Duplicate Game!");
         pauseButton = new Button ("Pause Game");
         resumeButton = new Button ("Resume Game");
            pauseButton.setOnAction((al)->pauseThread());
            resumeButton.setOnAction((al)->resumeThread());
            resumeButton.setDisable(true);
         
         
         rootNode.add(emptyFieldCounter, 'A',8,6,1);
         rootNode.add(currentGameElementCounter,'A',9,6,1);
         rootNode.add(initialGameElementCounter,'A',10,6,1);
         rootNode.add(levelCounter,'A',11,6,1);
         rootNode.add(scoreCounter,'A',12,6,1);
         rootNode.add(winMessage,'A',13,6,1);
         rootNode.add(pauseButton,'A',14,3,1);
         rootNode.add(resumeButton,'D',14,3,1);
     }
     private void updateBoard() {
         emptyFieldCounter.setText("Number of empty fields: " + emptyFields.size());
         currentGameElementCounter.setText("Number of current game elements: " + currentGameElements.size());
         initialGameElementCounter.setText("Number of initial game elements: " + initialGameElements.size());
         levelCounter.setText("Level: " + dg.getLevel());
         scoreCounter.setText("Score: " + dg.getActualScore());
         winMessage.setText(winMessageText);
     }
     private List setUpGameElements(){
         /**
          *  This method sets up the initial game elements on an empty game field, and returns the list of emptyFields;
          */
         emptyFields=new ArrayList<>(GameFields);
         
         int howManyElements=initialGameElements.size()+5+dg.getLevel(); //This is where we set how many elements we start out the game with
            
            for (int i=0; i<howManyElements; i++)
            {
                putOnEmptyField();
            }
         
         return emptyFields;
     }

    @Override
    boolean putElement(GameField gf, Object ge) {
        boolean success = false;
        try {
            Button actualButton = new Button(ge.toString());
            
                 actualButton.setOnAction((al)->{
                     actualButton.setVisible(false);
                     move(ge,currentGameElements,gf);
                });
            
            rootNode.add(actualButton, gf.getX(), gf.getY());
            success=true;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            
        }
        
        return success;
    }
    private void removeGameElement (GameElement ge, List currentGameElements, GameField gf){
           
        super.removeGameElement(ge,currentGameElements);
        emptyFields.add(gf);
    }

    @Override
    DuplicatesGameGUI getDuplicatesGameGUIInstance() {
        return this;
    }

    @Override            
    void move() {
        throw new AssertionError ("Use the move(Object ge, List currentGameElements, GameField gf) method instead!");
    }
    void move(Object ge, List currentGameElements, GameField gf){
         if(!(ge instanceof GameElement)) {
                   throw new AssertionError("ge should be of GameElement type");
            } 
         else {
                removeGameElement((GameElement)ge,currentGameElements,gf);
                
                switch(dg.getWin(ge, currentGameElements)) {
                    case 1: winMessageText="You won! Congrats";
                        break;
                    case -1: winMessageText="You Lose! Too bad, try again :-(";
                        break;
                    case 0: winMessageText="Leave only one of each number on the field to win!";
                        break;
                }
         }
        
        
        updateBoard();
    }

    @Override
    int resetScore() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    int getLevel() {
        return dg.getLevel();
    }

    @Override
    void gameOver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    void restartAdderThread() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    Thread initAdder()
        {
            Thread t = initAdder(this);
            return t;
        }
    private void pauseThread(){
        endGame=true;
        pauseButton.setDisable(true);
        resumeButton.setDisable(false);
    }
    private void resumeThread(){
        endGame=false;
        pauseButton.setDisable(false);
        resumeButton.setDisable(true);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        dg = DuplicatesGame.getDuplicatesGame();
        
        primaryStage.setTitle("JavaFX implementation of DuplicatesGame");
        rootNode = new GridPane();
        Scene primaryScene = new Scene(rootNode, 600, 700);
        primaryStage.setScene(primaryScene);
        
        /**
         * Initializing game begins here
         *
         */
        initGame();
        setUpGameElements();
        setupBoard(); //additional information displayed
        primaryStage.show();
        adderThread=this.initAdder();
        adderThread.start();
    }
    
}
