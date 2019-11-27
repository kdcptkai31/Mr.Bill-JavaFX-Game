package lab4ec;

import java.io.File;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
 
/**
 * @author Kai Duty
 * Description: This file holds the MrBill face and all sounds.
 * Key Features: Responsible for the reset method, as well as any color or shape
 *               size changes. Sound as well.
 * Data Structures:No data structures used.
 * Unique Techniques: Uses AudioClip instead of mediaPlayer for less overhead.
 */
public class MrBill extends StackPane {
     
    final int MIN_MOUTH_RADIUS = 4;
    final int MAX_MOUTH_RADIUS = 40;
    
    private final Circle leftEye = new Circle(10);
    private final Circle rightEye = new Circle(10);
    private final Circle outerMouth = new Circle(15);
    private final Circle innerMouth = new Circle(10);
    
    //Constructor that builds MrBill's face
    MrBill(){
        
        //Creates face that holds everything
        StackPane face = new StackPane();
        face.setAlignment(Pos.CENTER);
         
        //Creates circles for the eyes
        leftEye.setFill(Color.BLUE);
        rightEye.setFill(Color.BLUE);
         
        //hBox that holds the eyes
        HBox eyes = new HBox();
        eyes.setAlignment(Pos.CENTER);
        eyes.setSpacing(18);
        eyes.getChildren().addAll(leftEye, rightEye);
         
        //nose
        Circle nose = new Circle(6);
        nose.setFill(Color.RED);
         
        //mouth
        StackPane mouth = new StackPane();
        outerMouth.setFill(Color.RED);
        innerMouth.setFill(Color.WHITE);
        mouth.getChildren().addAll(outerMouth, innerMouth);
         
        //vBox that holds the face stack
        VBox vBox = new VBox();
        vBox.setSpacing(5);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(eyes, nose, mouth);
         
        //group that holds the head and the hair
        Group group = new Group();
        Circle head = new Circle(100, 100, 70);
        head.setStroke(Color.BLACK);
        head.setFill(Color.WHITE);
        Arc hair = new Arc(100, 100, 70, 70, 50, 80);
        hair.setType(ArcType.CHORD);
        hair.setFill(Color.YELLOW);
        group.getChildren().addAll(head, hair);
        
        sayHeyKids();
         
        //fills the face with the group and vBox
        face.getChildren().addAll(group, vBox);        
        
        this.getChildren().add(face);
        
    }
    
    //Getters
    Circle getLeftEye(){return leftEye;}
    Circle getRightEye(){return rightEye;}
    
    //Setters
    void setColorRed(Circle c){c.setFill(Color.RED);}
    void setColorGreen(Circle c){c.setFill(Color.GREEN);}
    void setColorBlue(Circle c){c.setFill(Color.BLUE);}
    
    //Checks if enlarging the outer mouth will go above the maximum radius
    //before enlarging both inner and outer mouths
    void enlargeMouth(){
        
        if(outerMouth.getRadius() <= MAX_MOUTH_RADIUS - 2){
            
            outerMouth.setRadius(outerMouth.getRadius() + 2);
            innerMouth.setRadius(innerMouth.getRadius() + 2);
            
        }else
            sayOhNo();
        
    }
    
    //Checks if shrinking the inner mouth will go below the minimum radius
    //before shrinking both inner and outer mouths
    void shrinkMouth(){
        
        if(innerMouth.getRadius() >= MIN_MOUTH_RADIUS + 2){
            
            outerMouth.setRadius(outerMouth.getRadius() - 2);
            innerMouth.setRadius(innerMouth.getRadius() - 2);
            
        }else
            sayOhNo();
        
    }
    
    //Resets to all default colors and radii
    void resetToDefault(){
        
        outerMouth.setRadius(15);
        innerMouth.setRadius(10);
        leftEye.setFill(Color.BLUE);
        rightEye.setFill(Color.BLUE);
        sayHeyKids();
        
    }
    
    //Plays the Oh No sound clip
    private void sayOhNo(){
        
        String audioFile = "src/audio/OhNo.mp3";
        AudioClip audio = new AudioClip(new File(audioFile).toURI().toString());
        audio.play();
        
    }
    
    //Plays the Hey Kids sound clip
    private void sayHeyKids(){
        
        String audioFile = "src/audio/HeyKids.mp3";
        AudioClip audio = new AudioClip(new File(audioFile).toURI().toString());
        audio.play();
        
    }

}