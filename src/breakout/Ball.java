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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;

public class Ball {
    private static final int SPEED = 200;
    private static final int SIZE = 5;
    private static final Paint BALL_COLOR = Color.GRAY;
    private double ballDirectionX = 1;
    private double ballDirectionY = 1;
    private double ballAngle;
    public Circle myBall;

    public Ball (double direction, double angle, double X, double Y) {
        ballAngle = angle;
        myBall = new Circle(X, Y, SIZE, BALL_COLOR);
    }

    // Set the position of the ball on the screen
    public void setBallPosition (double elapsedTime) {
        myBall.setCenterX(myBall.getCenterX() + ballDirectionX * SPEED * elapsedTime * Math.cos(ballAngle));
        myBall.setCenterY(myBall.getCenterY() + ballDirectionY * SPEED * elapsedTime * Math.sin(ballAngle));
    }

    public void setBallPosition (double X, double Y) {
        myBall.setCenterX(X);
        myBall.setCenterY(Y);
    }

    public void setAngleAndDirection (double directionX, double directionY, double angle) {
        ballDirectionX = directionX;
        ballDirectionY = directionY;
        ballAngle = angle;
    }

    public double getBallDirectionX () {
        return ballDirectionX;
    }
    public double getBallDirectionY () {
        return ballDirectionY;
    }

    public void setBallDirectionX(double dir) {
        ballDirectionX = dir;
    }

    public void setBallDirectionY(double dir) {
        ballDirectionY = dir;
    }

    public void setBallAngle (double angle) {
        ballAngle = angle;
    }

    public double getBallAngle () {
        return ballAngle;
    }

    private int getBounceAngle(int angle, int direction){


        return 0;
    }
}
