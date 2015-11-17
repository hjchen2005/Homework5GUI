package edu.cuny.brooklyn.cisc3120.homework4.gui;

import edu.cuny.brooklyn.cisc3120.homework4.core.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MainGUI extends Application {
    private static Log log=LogFactory.getLog(MainGUI.class);
    @Override
    public void start(Stage primaryStage) {
       try{
            Image image = new Image("Image/questionmark.jpg");
            ImageView imageView=new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(75);
            imageView.setPreserveRatio(true);
        } catch(Exception ex){
            log.error("oops!", ex.getCause()) ;
        }
        Button btn = new Button();
        btn.setText("Enter game");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                try{
                    Configuration myConfig = new Configuration(16, 4);

                // Resolve and inject dependencies.
                    IChooser myChooser = new RandomChooser(myConfig);
                    GuessingGameGUI gui = new GuessingGameGUI(myConfig);
                    Game myGame = new Game(myChooser, gui, gui, myConfig);
                    gui.display();
                    myGame.play();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
              }
            });
        
        StackPane root = new StackPane();
        //root.getChildren().add(imageView);
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 400, 250);
        
        primaryStage.setTitle("Guessing Game");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }    
}
