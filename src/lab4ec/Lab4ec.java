package lab4ec;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Kai Duty
 * Description: This file will display the boxPane and hold the buttons for
 *              the MrBill options.
 * Key Features: Has radio buttons for changing eye color, push buttons for
 *               activating gravity and rotation, and buttons for enlarging or
 *               shrinking the mouth. When rotating, the wall collision course
 *               takes a random detour.
 * Data Structures: No data structures are used.
 * Unique Techniques: Stores all methods that change MrBill in that file, so
 *                    getters and setters are used for all real-time changes.
 */
public class Lab4ec extends Application {
    
    private MrBill mrBill = new MrBill();
    
    @Override
    public void start(Stage primaryStage) {

        //Holds everything
        BorderPane borderPane = new BorderPane();
        
        //Center pane that does all annimation
        BoxPane boxPane = new BoxPane(mrBill);
        borderPane.setCenter(boxPane);
        
        //Sets up the left, right, and bottom sides of the borderpane
        setUpColorOptions(borderPane);
        setUpMouthOptions(borderPane);
        setUpMrBillOptions(borderPane, boxPane);
        
        //Sets up scene and stage
        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setTitle("Ohh Nooo!!!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        //Reset with 'R' handler
        scene.setOnKeyPressed(e -> handleKeyStrokes(e, boxPane));
        
        //Makes the eye color changeable by clicking
        mrBill.getLeftEye().addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            
            if(e.getButton() == MouseButton.PRIMARY)
                mrBill.setColorRed(mrBill.getLeftEye());
            else if(e.getButton() == MouseButton.SECONDARY)
                mrBill.setColorGreen(mrBill.getLeftEye());
            
        });
        mrBill.getRightEye().addEventHandler(MouseEvent.MOUSE_CLICKED, e ->{
            
            if(e.getButton() == MouseButton.PRIMARY)
                mrBill.setColorRed(mrBill.getRightEye());
            else if(e.getButton() == MouseButton.SECONDARY)
                mrBill.setColorGreen(mrBill.getRightEye());
            
        });
    }
    
    //Sets up the push buttons to open or close mr bill's mouth
    private void setUpMouthOptions(BorderPane bp){
        
        //Holds both enlarge and shrink buttons
        HBox buttons = new HBox(20);
        buttons.setStyle("-fx-background-color:black;"
                       + "-fx-border-color: white;");  
        buttons.setAlignment(Pos.CENTER);
        Button enlarge = new Button();
        Button shrink = new Button();
        enlarge.setText("Enlarge");
        shrink.setText("Shrink");
        
        //Buttons actions
        enlarge.setOnAction(e ->{mrBill.enlargeMouth();});
        shrink.setOnAction(e ->{mrBill.shrinkMouth();});
        
        //Fills buttons node
        buttons.getChildren().addAll(enlarge, shrink);
        bp.setBottom(buttons);
        
    }
    
    //Sets up the radio buttons on the left that can change the eye color
    private void setUpColorOptions(BorderPane bp){
        
        ToggleGroup colorButtons = new ToggleGroup();
        VBox colorOptions = new VBox();
        colorOptions.setAlignment(Pos.CENTER_LEFT);
        colorOptions.setStyle("-fx-background-color:black;"
                            + "-fx-border-color: white;");        
        
        //Red button
        RadioButton redButton = new RadioButton();
        redButton.setPadding(new Insets(20, 10, 20, 10));
        redButton.setToggleGroup(colorButtons);
        redButton.setFocusTraversable(false);
        redButton.setText("Red");
        redButton.setTextFill(Color.WHITE);
        redButton.setOnMouseClicked(e -> eyesToRed());
        
        //Green button
        RadioButton greenButton = new RadioButton();
        greenButton.setPadding(new Insets(20, 10, 20, 10));
        greenButton.setToggleGroup(colorButtons);
        greenButton.setFocusTraversable(false);
        greenButton.setText("Green");
        greenButton.setTextFill(Color.WHITE);
        greenButton.setOnMouseClicked(e -> eyesToGreen());

        //Blue button
        RadioButton blueButton = new RadioButton();
        blueButton.setPadding(new Insets(20, 10, 20, 10));
        blueButton.setToggleGroup(colorButtons);
        blueButton.setFocusTraversable(false);
        blueButton.setText("Blue");
        blueButton.setTextFill(Color.WHITE);
        blueButton.setOnMouseClicked(e -> eyesToBlue());
        
        colorOptions.getChildren().addAll(redButton, greenButton, blueButton);
        bp.setLeft(colorOptions);
    }
    
    //Sets up the rotate and gravity buttons
    private void setUpMrBillOptions(BorderPane bp, BoxPane boxp){
        
        VBox mrBillOptions = new VBox();
        mrBillOptions.setAlignment(Pos.CENTER);
        mrBillOptions.setStyle("-fx-background-color:black;"
                             + "-fx-border-color: white;");  
        
        //Rotation
        CheckBox rotateBox = new CheckBox("Rotate");
        rotateBox.setTextFill(Color.WHITE);
        rotateBox.setFocusTraversable(false);
        rotateBox.setPadding(new Insets(20, 10, 20, 10));
        rotateBox.setOnMouseClicked(e -> boxp.changeRotation());
        
        //Gravity
        CheckBox gravityBox = new CheckBox("Gravity");
        gravityBox.setTextFill(Color.WHITE);
        gravityBox.setFocusTraversable(false);    
        gravityBox.setOnMouseClicked(e -> boxp.changeGravity());
        
        mrBillOptions.getChildren().addAll(rotateBox, gravityBox);
        bp.setRight(mrBillOptions);
        
    }
    
    //Handles events linked to R, UP, and DOWN, and paddle controls
    private void handleKeyStrokes(KeyEvent e, BoxPane b){
        
        if(e.getCode() == KeyCode.R){
            
            b.setRotateAngle(0, mrBill);
            b.resetScore();
            mrBill.resetToDefault();
            
        }
        
        if(e.getCode() == KeyCode.UP)
            b.increaseSpeed();
        
        if(e.getCode() == KeyCode.DOWN)
            b.decreaseSpeed();
        
        if(e.getCode() == KeyCode.A)
            b.leftPaddleUp();
        
        if(e.getCode() == KeyCode.Z)
            b.leftPaddleDown();
        
        if(e.getCode() == KeyCode.K)
            b.rightPaddleUp();
        
        if(e.getCode() == KeyCode.M)
            b.rightPaddleDown();
        
        
    }
    
    //Changes eyes to red
    private void eyesToRed(){
        
        mrBill.setColorRed(mrBill.getLeftEye());
        mrBill.setColorRed(mrBill.getRightEye());
        
    }
    
    //Changes eyes to green
    private void eyesToGreen(){
        
        mrBill.setColorGreen(mrBill.getLeftEye());
        mrBill.setColorGreen(mrBill.getRightEye());
        
    }
    
    //Changes eyes to blue
    private void eyesToBlue(){
        
        mrBill.setColorBlue(mrBill.getLeftEye());
        mrBill.setColorBlue(mrBill.getRightEye());
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}