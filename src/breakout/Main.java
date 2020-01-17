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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.TimeUnit;

public class Main extends Application {
    public static final String TITLE = "Example JavaFX";
    public static final int WINDOW_WIDTH = 400;
    public static final int WINDOW_HEIGHT = 600;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final Paint HIGHLIGHT = Color.OLIVEDRAB;
    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final int BOUNCER_SPEED = 30;
    public static final Paint MOVER_COLOR = Color.PLUM;
    public static final int MOVER_SIZE = 50;
    public static final int MOVER_SPEED = 15;
    public static final Paint GROWER_COLOR = Color.BISQUE;
    public static final double GROWER_RATE = 1.1;
    public static final int GROWER_SIZE = 50;
    public static final int NUMBER_OF_BALLS = 100;

    // some things needed to remember during game
    private Scene myScene;
    private ImageView myBouncer;
    private ImageView paddle;
    private Circle newBouncer;
    private Rectangle myMover;
    private Rectangle myGrower;
    private int dir = 1;
    private Ball ball1;
    private Line ballPointer;
    private boolean start = false;


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
        System.out.println( "Path: " + getClass().getResource("/").toExternalForm());
        Image image = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        myBouncer = new ImageView(image);
        Image paddleImage = new Image(this.getClass().getClassLoader().getResourceAsStream(("paddle.gif")));
        paddle = new ImageView(paddleImage);

        //newBouncer = new Circle(width/2, height/2, 5, Color.GREY);
        // x and y represent the top left corner, so center it in window
        myBouncer.setX(width / 2 - myBouncer.getBoundsInLocal().getWidth() / 2);
        myBouncer.setY(height / 2 - myBouncer.getBoundsInLocal().getHeight() / 2);
        paddle.setX(width / 2);
        paddle.setY(height - 20);
        ball1 =  new Ball(1,0,paddle.getBoundsInLocal().getCenterX(),paddle.getBoundsInLocal().getCenterY()-(paddle.getBoundsInLocal().getHeight())-5);
        myMover = new Rectangle(width / 2 - MOVER_SIZE / 2, height / 2 - 100, MOVER_SIZE, MOVER_SIZE);
        myMover.setFill(MOVER_COLOR);
        myGrower = new Rectangle(width / 2 - GROWER_SIZE / 2, height / 2 + 50, GROWER_SIZE, GROWER_SIZE);
        myGrower.setFill(GROWER_COLOR);
        ballPointer = new Line(paddle.getBoundsInLocal().getCenterX(), paddle.getBoundsInLocal().getCenterY()-paddle.getBoundsInLocal().getHeight()-ball1.myBall.getRadius(), 100, 100);
        ballPointer.setVisible(false);
        System.out.println(paddle.getBoundsInLocal().getCenterX());
        // order added to the group is the order in which they are drawn
        root.getChildren().add(myBouncer);
        root.getChildren().add(ball1.myBall);
//        root.getChildren().add(newBouncer);
        root.getChildren().add(myMover);
        root.getChildren().add(myGrower);
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

    // Change properties of shapes in small ways to animate them over time
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start
    private void step (double elapsedTime) {

        if (start) {
            ball1.setBallPosition(elapsedTime);
        }
        if (paddle.getBoundsInParent().intersects(ball1.myBall.getBoundsInParent())){
            System.out.println("intersection");
        }
        handleIntersection(ball1);

    }

    // Use the ballPointer to calculate the angle the balls should move
    private double calculateInitialBallAngle () {
        return -(Math.atan2(ballPointer.getStartX()-ballPointer.getEndX(), ballPointer.getStartY()-ballPointer.getEndY()) + Math.PI/2);
    }

    // Handle ball intersection with brick
    private void handleIntersection(Ball ball, Brick brick){

    }

    // Handle ball intersection with wall
    private void handleIntersection(Ball ball){
        if (ball.myBall.getCenterX() - ball.myBall.getRadius() <= 0 || ball.myBall.getCenterX() + ball.myBall.getRadius() >= myScene.getWidth()) {
            ball.setBallAngle(-(ball.getBallAngle() + Math.PI));
        }
        if (ball.myBall.getCenterY() - ball.myBall.getRadius() <= 0) {
            ball.setBallDirectionY(-1);
        }
        if (paddle.getBoundsInParent().intersects(ball.myBall.getBoundsInParent())){
            ball.setBallDirectionY(1);
        }
    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            paddle.setX(paddle.getX() + MOVER_SPEED);
            if (!start) {
                ballPointer.setStartX(paddle.getBoundsInLocal().getCenterX());
                ball1.setBallPosition(paddle.getBoundsInLocal().getCenterX(), paddle.getBoundsInLocal().getCenterY() - (paddle.getBoundsInLocal().getHeight()) - ball1.myBall.getRadius());
            }
        }
        else if (code == KeyCode.LEFT) {
            paddle.setX(paddle.getX() - MOVER_SPEED);
            if (!start) {
                ballPointer.setStartX(paddle.getBoundsInLocal().getCenterX());
                ball1.setBallPosition(paddle.getBoundsInLocal().getCenterX(), paddle.getBoundsInLocal().getCenterY() - (paddle.getBoundsInLocal().getHeight()) - ball1.myBall.getRadius());
            }
        }
    }

    // Change the ballPointer angle when the mouse is dragged
    private void handleMouseDrag (MouseEvent e) {
        if (!start) {
            ballPointer.setVisible(true);
            ballPointer.setEndX(e.getX());
            ballPointer.setEndY(e.getY());
        }
    }

    private void handleMouseRelease () {
        ballPointer.setVisible(false);
        ball1.setAngleAndDirection(1, 1, calculateInitialBallAngle());
        start = true;
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
