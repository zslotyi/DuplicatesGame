/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duplicatesgame;


import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author zslotyi
 * @v2.0 - 2016. 06. 05. 09:11 - complete rewrite after stolen laptop
 * skeleton: 09:38
 * set up game: 14:17
 * game move, win/lose working: 15:06
 * adder thread running: 15:29
 * pause-resume game working: 15:54
 * start new game working - 2016. 07. 08. 14:36
 * css+layout working - 2016. 07. 08. 14:36
 * disable all buttons when winning or losing - 14:47
 * Adder thread stops when closing the window - 15:27
 * Increasing levels when winning the game - 15:42
 * Counting scores - 15:49
 * empty fields counter updates after adding new element by Adder thread - 16:06
 * duplicate counter working - 16:14
 * 
 * 
 * 
 * 
 */
public class DuplicatesGameJavaFX extends DuplicatesGameGUI {
    private GridPane rootNode, scoreNode, boardNode;
    private DuplicatesGame dg;
    private List initialGameElements;
    private Label emptyFieldCounter, currentGameElementCounter, initialGameElementCounter, levelCounter, scoreCounter, winMessage, duplicateCounter;
    private String winMessageText;
    private Button pauseButton, resumeButton, clearButton;
    private Scene primaryScene;
    
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
         clearButton = new Button ("Restart Game");
         duplicateCounter = new Label ("Number of duplicates remaining: " + dg.getNumberOfDuplicates(currentGameElements));
            pauseButton.setOnAction((al)->pauseThread());
            resumeButton.setOnAction((al)->resumeThread());
            resumeButton.setDisable(true);
            clearButton.setOnAction((al)->reStartGame(dg.getLevel()));
         
         
         scoreNode.add(emptyFieldCounter, 'A',8,6,1);
         scoreNode.add(currentGameElementCounter,'A',9,6,1);
         scoreNode.add(initialGameElementCounter,'A',10,6,1);
         scoreNode.add(levelCounter,'A',11,6,1);
         scoreNode.add(scoreCounter,'A',12,6,1);
         scoreNode.add(winMessage,'A',13,6,1);
         scoreNode.add(pauseButton,'A',14,3,1);
            pauseButton.getStyleClass().add("button" + "-" + "pause");
         scoreNode.add(resumeButton,'D',14,3,1);
            resumeButton.getStyleClass().add("button" + "-" + "resume");
         scoreNode.add(clearButton,'A',15,6,1);
            clearButton.getStyleClass().add("button" + "-" + "resume");
         scoreNode.add(duplicateCounter,'A',16,6,1);
     }
     @Override
     void updateBoard() {
         emptyFieldCounter.setText("Number of empty fields: " + emptyFields.size());
         currentGameElementCounter.setText("Number of current game elements: " + currentGameElements.size());
         initialGameElementCounter.setText("Number of initial game elements: " + initialGameElements.size());
         duplicateCounter.setText("Number of duplicates remaining: " + dg.getNumberOfDuplicates(currentGameElements));
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
            actualButton.getStyleClass().add("button" + "-" + ge.toString());
            System.out.println("added");
                 actualButton.setOnAction((al)->{
                     actualButton.setVisible(false);
                     move(ge,currentGameElements,gf);
                });
            
            boardNode.add(actualButton, gf.getX(), gf.getY());
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
                            int level = dg.getLevel();
                            reStartGame(++level);
                        break;
                    case -1: winMessageText="You Lose! Too bad, try again :-(";
                            gameOver();
                        break;
                    case 0: winMessageText="Leave only one of each number on the field to win!";
                        dg.move();
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
                            disableAllButtons();
                            endGame=true;
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
    private void importCSS(){
        String css = this.getClass().getResource("_duplicatesgame.css").toExternalForm();
        primaryScene.getStylesheets().add(css);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        dg = DuplicatesGame.getDuplicatesGame();
        
        primaryStage.setTitle("JavaFX implementation of DuplicatesGame");
        rootNode = new GridPane();
        boardNode = new GridPane();
            
        scoreNode = new GridPane();
            
        rootNode.add(boardNode,1,1);
        rootNode.add(scoreNode,1,2);
        primaryScene = new Scene(rootNode, 600, 700);
        importCSS();
        primaryStage.setScene(primaryScene);
        
        /**
         * Initializing game begins here
         *
         */
        initGame();
        setUpGameElements();
        setupBoard(); //additional information displayed
        primaryStage.show();
         primaryStage.setOnCloseRequest((WindowEvent e) -> {
             try {
                 setGameOver();
                 Platform.exit();
             } catch (Exception e1) {
                 e1.printStackTrace();
             }
        });
        adderThread=this.initAdder();
        adderThread.start();
            scoreNode.getStyleClass().add("boardsize");
            boardNode.getStyleClass().add("boardsize");
        
       
    }
    public final void reStartGame(int level){
         /*         - empty all the fields
                    - begin a new initial collection with dg.newGame();
                    - put the new collection on random fields
                    - create the actual empty fields collection
         */
         emptyGameFields();
         currentGameElements=null;
         initialGameElements=null;
         dg.newGame(level);
         initGame();
         setUpGameElements();
         updateBoard();
    }
    private void emptyGameFields(){
       /**
        * Remove all the Game Elements from the Board
        */
       if(currentGameElements.size()<=0) {
           throw new AssertionError("When emptying the Game Fields, the Game Fiels size should be larger than zero");
       
        } else {   
           /*for (Object ge : currentGameElements)
           {    
               if (!(ge instanceof GameElement))
               {
                   throw new AssertionError("Something went wrong here");
               }
               else
               {
                   super.removeGameElement(ge,currentGameElements);
               }
               
           }*/
           for (Node child : boardNode.getChildren()) {
               
               if (!(child instanceof Button))
               {
                   throw new AssertionError("We found something we shouldn't have found");
               }
               else
               {
                   child.setVisible(false);
               }
               
           }
           System.out.println("Board Cleared");
         }
    }
    private void disableAllButtons() {
               if(currentGameElements.size()<=0) {
           throw new AssertionError("When emptying the Game Fields, the Game Fiels size should be larger than zero");
       
        } else {   
           /*for (Object ge : currentGameElements)
           {    
               if (!(ge instanceof GameElement))
               {
                   throw new AssertionError("Something went wrong here");
               }
               else
               {
                   super.removeGameElement(ge,currentGameElements);
               }
               
           }*/
           for (Node child : boardNode.getChildren()) {
               
               if (!(child instanceof Button))
               {
                   throw new AssertionError("We found something we shouldn't have found");
               }
               else
               {
                   child.setDisable(true);
               }
               
           }
           System.out.println("Board Cleared");
         }
    }
}
 