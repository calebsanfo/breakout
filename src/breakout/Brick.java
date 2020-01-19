package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Brick {
    private Rectangle myBrick = null;
    private int strength;
    private Text strengthText;

    public Brick (Group root, int X, int Y, double size, int initialStrength, String type){
        myBrick = new Rectangle(X, Y, size, size);
        strength = initialStrength;

        strengthText = new Text(""+initialStrength);
        strengthText.setX(X + (size/2) - (strengthText.getBoundsInLocal().getWidth()/2));
        strengthText.setY(Y + (size/2) + (strengthText.getBoundsInLocal().getHeight()/2));
        strengthText.setFill(Color.WHITE);

        root.getChildren().add(myBrick);
        root.getChildren().add(strengthText);
    }

    public void reduceStrength () {
        strength--;
        strengthText.setText(""+strength);
        if (strength == 0){
            myBrick.setVisible(false);
        }
    }

    public Rectangle getBrick (){
        return myBrick;
    }

}
