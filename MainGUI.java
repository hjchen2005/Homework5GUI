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
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javax.swing.JOptionPane;

public class MainGUI extends Application {
    private static Log log=LogFactory.getLog(MainGUI.class);
    Stage window;
    @Override
    public void start(Stage primaryStage) {
        window=primaryStage;
        BorderPane borderPane = new BorderPane();
        StackPane root = new StackPane();
        Image image = new Image("file:Image/questionmark.jpg");
        //try{
            
            ImageView imageView=new ImageView();
            imageView.setImage(image);
            //imageView.setFitWidth(75);
            imageView.setPreserveRatio(true);
            imageView.fitWidthProperty().bind(primaryStage.widthProperty()); 
            //root.getChildren().add(imageView);
       // }catch(Exception ex){
         //   log.error("oops!", ex.getCause()) ;
        //}
       Label label1 = new Label("Name:");
        TextField textField = new TextField ();
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    String name = textField.getText(); //send this name to GuessGameGUI
                // clear text
                textField.setText("");
            }
           }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField);
        hb.setSpacing(10);
        Button btn = new Button();
        btn.setText("Enter game");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                try{
                    Configuration myConfig = new Configuration(16, 8);

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
        
        
        //root.getChildren().add(imageView);
      
        //root.getChildren().addAll(imageView,hb,btn);
        borderPane.setCenter(imageView);
        borderPane.setTop(hb);
        borderPane.setBottom(btn);
        Scene scene = new Scene(borderPane, 400, 600);
        
        window.setTitle("Guessing Game");
        window.setScene(scene);
        window.setOnCloseRequest(e->{
            e.consume();
            closeProgram();
        });
        window.show();
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }    

    private void closeProgram() {
        int dialogButton = JOptionPane.YES_NO_OPTION;
        JOptionPane.showConfirmDialog (null,"Do you want to close");
        if(dialogButton == JOptionPane.YES_OPTION)
            window.close();
    }
}

