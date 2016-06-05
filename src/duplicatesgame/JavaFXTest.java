/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duplicatesgame;

import javafx.application.Application;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author zslotyi
 */
public class JavaFXTest extends Application {

    public static void main(String[] args){
        Application.launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
    
        primaryStage.setTitle("Java FX Test");
        primaryStage.show();
        
        VBox root = new VBox();
        Text msg = new Text("Hello JavaFX");
        
        root.getChildren().add(msg);
        
    }
    
}
