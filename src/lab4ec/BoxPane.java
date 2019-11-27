package lab4ec;

import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * @author Kai Duty
 * Description: This file will hold MrBill and handle all animation.
 * Key Features: Contains rotation, gravity, and color changes handlers, as well
 *               as boundary checks to keep the animation looking as smooth as
 *               possible.
 * Data Structures: No data structures are used.
 * Unique Techniques: Gravity and rotation are triggered with a boolean,
 *                    allowing for more readable methods.
 */
public class BoxPane extends Pane{
    
    private double x, y = 10;
    private double dx = 1, dy = 1;
    private final Timeline animation;
    private int rotateAngle = 0;
    private boolean isRotating = false;
    private boolean isGravity = false;
    private final IntegerProperty leftScore;
    private final IntegerProperty rightScore;
    private final Text leftScoreText = new Text();
    private final Text rightScoreText = new Text();
    private final Rectangle leftPaddle = new Rectangle(5, 100);
    private final Rectangle rightPaddle = new Rectangle(5, 100);

    //Constructor
    public BoxPane(Pane mrbill){

        //Creates the background image
        ImageView background = new ImageView(
                               new Image("images/background.png"));
        background.fitWidthProperty().bind(widthProperty());
        background.fitHeightProperty().bind(heightProperty());
        
        
        //Creates score board and binds text to the score values
        leftScore = new SimpleIntegerProperty(0);
        rightScore = new SimpleIntegerProperty(0);
        leftScoreText.setFill(Color.WHITE);
        leftScoreText.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD,
                                        25));
        rightScoreText.setFont(Font.font("Monospaced", FontWeight.EXTRA_BOLD,
                                         25));
        rightScoreText.setFill(Color.WHITE);
        leftScoreText.setX(5);
        leftScoreText.setY(20);
        rightScoreText.setY(20);
        leftScoreText.textProperty().bind(leftScore.asString());
        rightScoreText.textProperty().bind(rightScore.asString());
        rightScoreText.xProperty().bind(widthProperty().subtract(35));
        
        //Creates paddles
        leftPaddle.setFill(Color.WHITE);
        rightPaddle.setFill(Color.WHITE);
        rightPaddle.xProperty().bind(widthProperty().subtract(5));
        
        //Place objects into this pane
        getChildren().addAll(background, mrbill, leftScoreText, rightScoreText,
                             leftPaddle, rightPaddle);

        //Create an animation for moving the ball
        animation = new Timeline(
        new KeyFrame(Duration.millis(50), e -> moveBall(mrbill)));
        animation.setCycleCount(Timeline.INDEFINITE);
        //Start animation
        animation.play();
    }
    
    //Setters
    public void resetScore(){
        
        leftScore.setValue(0);
        rightScore.setValue(0);
        dx = 1;
        dy = 1;
        
    }
    public void setRotateAngle(int num, Pane mrbill){
        
        mrbill.setRotate(rotateAngle = num);
    
    }
    //Getters
    public void play(){animation.play();}
    public void pause(){animation.pause();}
    public DoubleProperty rateProperty(){return animation.rateProperty();}
    
    //Handles the toggling rotation
    public void changeRotation(){
        
        if(isRotating)
            isRotating = false;
        else
            isRotating = true;
        
    }
    
    //Handles the toggling gravity, and prevents ball from getting stuck out of
    //bounds
    public void changeGravity(){
        
        if(isGravity){
            
            isGravity = false;
            if(dx > 0)
                dx = 1;
            else
                dx = -1;
            
            if(dy > 0)
                dy = 1;
            else
                dy = -1;
            
        }
        else
            isGravity = true;
        
    }
    
    //Increases the animation speed
    public void increaseSpeed(){animation.setRate(animation.getRate() + 0.1);}
    //Decreases the animation speed
    public void decreaseSpeed(){
        
        animation.setRate(
        animation.getRate() > 0 ? animation.getRate() - 0.1 : 0);

    }
    
    public void leftPaddleUp(){
        
        if(leftPaddle.getY() - 20 >= 0)
            leftPaddle.setY(leftPaddle.getY() - 20);
        
    }
    public void leftPaddleDown(){
        
        if(leftPaddle.getY() + leftPaddle.getHeight() + 20 <= getHeight())
            leftPaddle.setY(leftPaddle.getY() + 20);
        
    }
    public void rightPaddleUp(){
        
        if(rightPaddle.getY() - 20 >= 0)
            rightPaddle.setY(rightPaddle.getY() - 20);
        
    }
    public void rightPaddleDown(){
        
        if(rightPaddle.getY() + rightPaddle.getHeight() + 20 <= getHeight())
            rightPaddle.setY(rightPaddle.getY() + 20);
        
    }

    private void moveBall(Pane mrbill){

        //Check x boundaries
        if (x < 0 || x > getWidth() - mrbill.getWidth()){
            
            //Change ball move direction and increments the score
            dx *= -1;
            
            if(isRotating)
                dx += getRandRotationNum();
            
            if(x >= getWidth() - mrbill.getWidth()){
                
                x = getWidth() - mrbill.getWidth();
                //Checks if the ball hits the paddle
                if(y + mrbill.getHeight() / 2 >= rightPaddle.getY() &&
                   y + mrbill.getHeight() / 2 <= rightPaddle.getY() +
                                                 rightPaddle.getHeight())
                    playHitPaddle();
                else{
                    
                    leftScore.setValue(leftScore.get() + 1);
                    playMiss();
                    
                }

            }

            else{
                
                x = 0;
                
                //Checks if the ball hits the paddle
                if(y + mrbill.getHeight() / 2 >= leftPaddle.getY() &&
                   y + mrbill.getHeight() / 2 <= leftPaddle.getY() +
                                                 leftPaddle.getHeight())
                    playHitPaddle();
                else{
                    
                    rightScore.setValue(rightScore.get() + 1);
                    playMiss();
                    
                }

            }

        }

        //Check y boundaries
        if (y < 0 || y > getHeight() - mrbill.getWidth()){
            
            //Change ball move direction
            dy *= -1;
            if(isRotating)
                dy += getRandRotationNum();
            
            if(y >= getHeight() - mrbill.getWidth())
                y = getHeight() - mrbill.getWidth();
            else
                y = 0;
            
            playHitWall();
            
        }
        
        //Handles gravity
        if(isGravity && dy > 0)
            dy += .05;
        
        if(isGravity && dy < 0)
            dy += .08;
        
        //Handles rotation
        if(isRotating){
            
            rotateAngle += 2;
            mrbill.setRotate(rotateAngle);
            
        }

        // Adjust ball position
        x += dx;
        y += dy;

        //Sets ball position
        mrbill.setTranslateX(x);
        mrbill.setTranslateY(y);
        
    }
    
    //Returns a random num for the randomness of bouncing with rotation
    private double getRandRotationNum(){
        
        if(Math.random() > .5)
            return .2;
        else
            return -.2;
        
    }
    
    private void playHitWall(){
        
        String audioFile = "src/audio/hitWall.wav";
        AudioClip audio = new AudioClip(new File(audioFile).toURI().toString());
        audio.play();
        
    }
    
    private void playHitPaddle(){
        
        String audioFile = "src/audio/hitPaddle.wav";
        AudioClip audio = new AudioClip(new File(audioFile).toURI().toString());
        audio.play();
        
    }
    
    private void playMiss(){
        
        String audioFile = "src/audio/miss.wav";
        AudioClip audio = new AudioClip(new File(audioFile).toURI().toString());
        audio.play();
        
    }

}