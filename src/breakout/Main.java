package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Time;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    public static final String TITLE = "Example JavaFX";
    public static final int WINDOW_WIDTH = 400;
    public static final int WINDOW_HEIGHT = 600;
    public static final int STATUS_HEIGHT = 35;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final Paint STATUS_BACKGROUND = Color.LIGHTBLUE;
    public static final int MOVER_SPEED = 15;
    public static final int NUMBER_OF_BALLS = 1;
    public static final int NUMBER_OF_BRICKS = 6;
    public static final int BALL_DELAY_MILLIS = 150;
    public static final int INITIAL_LIVES = 3;

    // some things needed to remember during game
    private Scene myScene;
    private ImageView paddle;
    private Line ballPointer;
    private boolean start = false;
    private Ball[] myBalls = new Ball[NUMBER_OF_BALLS];
    private Brick[] myBricks= new Brick[NUMBER_OF_BRICKS];
    private long startTime;
    private int numberOfBallsInPlay;
    private int level;

    private String gameStage = "SPLASH";

    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
        myScene = setupGame(WINDOW_WIDTH, WINDOW_HEIGHT, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        // attach "game loop" to timeline to play it (basically just calling step() method repeatedly forever)
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    // Create the game's "scene": what shapes will be in the game and their starting properties
    private Scene setupGame (int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        Group root = new Group();
        // make some shapes and set their properties

        Image paddleImage = new Image(this.getClass().getClassLoader().getResourceAsStream(("paddle.gif")));
        paddle = new ImageView(paddleImage);

        paddle.setX(width / 2);
        paddle.setY(height - 30);

        for (int i = 0; i<NUMBER_OF_BRICKS; i++ ) {
            myBricks[i] = new Brick(root, 50*i, 100, 50, 10, "NORMAL");
        }

        Status myStatus = new Status(root, INITIAL_LIVES, STATUS_HEIGHT, WINDOW_WIDTH, STATUS_BACKGROUND);

        for (int i = 0; i<NUMBER_OF_BALLS; i++ ) {
            myBalls[i] = (new Ball(i*2, 0, paddle.getBoundsInLocal().getCenterX(),paddle.getBoundsInLocal().getCenterY()-(paddle.getBoundsInLocal().getHeight())-5));
            root.getChildren().add(myBalls[i].getBall());
        }

        ballPointer = new Line(paddle.getBoundsInLocal().getCenterX(), paddle.getBoundsInLocal().getCenterY()-paddle.getBoundsInLocal().getHeight()-myBalls[0].getBall().getRadius(), 100, 100);
        ballPointer.setVisible(false);

        root.getChildren().add(paddle);
        root.getChildren().add(ballPointer);

        // create a place to see the shapes
        Scene scene = new Scene(root, width, height, background);
        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseReleased(e -> handleMouseRelease());
        scene.setOnMouseDragged(e -> handleMouseDrag(e));

        return scene;
    }

    private void layoutBricks () {

    }

    private void setupSplashScreen() {

    }

    private void displaySplashScreen () {

    }


    // Change properties of shapes in small ways to animate them over time
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start
    private void step (double elapsedTime) {
        if (start) {
            long gameTime = System.currentTimeMillis() - startTime;
            int numberOfBallsToUpdate = (int) Math.ceil(gameTime / BALL_DELAY_MILLIS);
            if (numberOfBallsToUpdate > NUMBER_OF_BALLS) numberOfBallsToUpdate = NUMBER_OF_BALLS;
            for (int i = 0; i<numberOfBallsToUpdate; i++ ) {
                myBalls[i].setBallPosition(elapsedTime);
            }
//            ball1.setBallPosition(elapsedTime);
        }
        numberOfBallsInPlay = NUMBER_OF_BALLS;
        for (Ball b: myBalls){
            handleIntersection(b);
            for (Brick brick: myBricks) {
                handleIntersection(b, brick);
            }
            if (!b.isInPlay()) numberOfBallsInPlay--;
        }
        if (numberOfBallsInPlay == 0){
            start = false;
            for (Ball b: myBalls) {
                b.setInPlay(true);
            }
        }
    }

    // Use the ballPointer to calculate the angle the balls should move
    private double calculateInitialBallAngle () {
        return -(Math.atan2(ballPointer.getStartX()-ballPointer.getEndX(), ballPointer.getStartY()-ballPointer.getEndY()) + Math.PI/2);
    }

    // Handle ball intersection with brick
    private void handleIntersection(Ball ball, Brick brick){
        if (ball.getBall().getBoundsInParent().intersects(brick.getBrick().getBoundsInParent())) {
            brick.reduceStrength();
            //ball.setBallAngle(-(ball.getBallAngle() + Math.PI));

            // Check if the ball hit the top or bottom of thr brick
            Shape intersection = Path.intersect(ball.getBall(), brick.getBrick());
//            System.out.println(Math.round(intersection.boundsInParentProperty().get().getCenterY()));
//            System.out.println(brick.getBrick().getY());
//            System.out.println();
//            System.out.println(intersection.boundsInParentProperty().get().getCenterY() - 15 > brick.getBrick().getY() + brick.getBrick().getHeight());
            if ((intersection.boundsInParentProperty().get().getCenterY()) + 2 >= brick.getBrick().getY() && (intersection.boundsInParentProperty().get().getCenterY()) - 2 >= brick.getBrick().getY()){
                System.out.println(intersection.boundsInParentProperty().get().getCenterY());
                ball.setBallDirectionY(-ball.getBallDirectionY());
            } else if ((intersection.boundsInParentProperty().get().getCenterY()) <= brick.getBrick().getY() + brick.getBrick().getHeight()){
                System.out.println(intersection.boundsInParentProperty().get().getCenterY());
                ball.setBallDirectionY(-ball.getBallDirectionY());
            } else {
                ball.setBallAngle(-(ball.getBallAngle() + Math.PI));
            }
        }
    }

    // Handle ball intersection with wall
    private void handleIntersection(Ball ball){
        if (ball.getBall().getCenterX() - ball.getBall().getRadius() <= 0 || ball.getBall().getCenterX() + ball.getBall().getRadius() >= myScene.getWidth()) {
            ball.setBallAngle(-(ball.getBallAngle() + Math.PI));
        }
        if (ball.getBall().getCenterY() - ball.getBall().getRadius() <= STATUS_HEIGHT) {
            ball.setBallDirectionY(-1);
        }
        if (ball.getBall().getCenterY() + ball.getBall().getRadius() >= myScene.getHeight()) {
            ball.setBallDirectionY(0);
            ball.setBallDirectionX(0);
            ball.setInPlay(false);
        }
        if (paddle.getBoundsInParent().intersects(ball.getBall().getBoundsInParent())){
            ball.setBallDirectionY(1);
        }
    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            movePaddle(1);
        }
        else if (code == KeyCode.LEFT) {
            movePaddle(-1);
        }
    }

    private void movePaddle (int direction) {
        if (paddle.getX() + paddle.getBoundsInLocal().getWidth() < myScene.getWidth() && direction == 1) {
            paddle.setX(paddle.getX() + direction * MOVER_SPEED);
        }
        if (paddle.getX() > 0 && direction == -1){
            paddle.setX(paddle.getX() + direction * MOVER_SPEED);
        }
        if (!start) {
            ballPointer.setStartX(paddle.getBoundsInLocal().getCenterX());
            for (Ball b: myBalls){
                b.setBallPosition(paddle.getBoundsInLocal().getCenterX(), paddle.getBoundsInLocal().getCenterY() - (paddle.getBoundsInLocal().getHeight()) - myBalls[0].getBall().getRadius());
            }
        }
    }

    // Change the ballPointer angle when the mouse is dragged
    private void handleMouseDrag (MouseEvent e) {
        if (!start) {
            ballPointer.setVisible(true);
            ballPointer.setEndX(e.getX());
            ballPointer.setEndY(e.getY());
            for (Ball b: myBalls){
                b.setBallPosition(paddle.getBoundsInLocal().getCenterX(), paddle.getBoundsInLocal().getCenterY() - (paddle.getBoundsInLocal().getHeight()) - myBalls[0].getBall().getRadius());
            }
        }
    }

    private void handleMouseRelease () {
        ballPointer.setVisible(false);
        double ballAngle = calculateInitialBallAngle();
        for (Ball b: myBalls){
            b.setAngleAndDirection(1, 1, ballAngle);
        }
        numberOfBallsInPlay = NUMBER_OF_BALLS;
        start = true;
        startTime = System.currentTimeMillis();
    }


    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
