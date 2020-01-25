package breakout;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Status {
    private int livesLeft;
    private int score;
    private Text livesText;
    private Text scoreText;

    /**
     * Constructor for the Status class. This creates and draws a
     * status bar in the window, and initializes the lives left and
     * score.
     *
     * @param root (main Group of the game to add Objects to)
     * @param initialLives (number of lives that a player starts with)
     * @param statusHeight (height in pixels of the status bar)
     * @param windowWidth (width in pixels of the window)
     * @param color (color of the status bar)
     */
    public Status (Group root, int initialLives, int statusHeight, int windowWidth, Paint color) {
        livesLeft = initialLives;
        score = 0;

        // Create the rectangle for the status background
        Rectangle statusBar = new Rectangle(0, 0, windowWidth, statusHeight);
        statusBar.setFill(color);
        root.getChildren().add(statusBar);

        // Create the Lives text field
        livesText = new Text(getLivesText());
        livesText.setX((windowWidth * 2/3) - (livesText.getBoundsInLocal().getWidth() / 2));
        livesText.setY(statusHeight/2 + (livesText.getBoundsInLocal().getHeight() / 2));
        root.getChildren().add(livesText);

        // Create the score text field
        scoreText = new Text(getScoreText());
        scoreText.setX(windowWidth/3 - (scoreText.getBoundsInLocal().getWidth() / 2));
        scoreText.setY(statusHeight/2 + (scoreText.getBoundsInLocal().getHeight() / 2));
        root.getChildren().add(scoreText);
    }

    /**
     * gets the current score
     *
     * @return (int)
     */
    public int getScore () {
        return score;
    }

    /**
     * Converts the score to the
     * String to be displayed
     *
     * @return (String)
     */
    private String getScoreText () {
        return "Score: " + score;
    }

    /**
     * Update and display the score
     *
     * @param s (new score)
     */
    public void setScore (int s) {
        score = s;
        scoreText.setText(getScoreText());
    }

    /**
     * Converts the number of lives left to
     * the text to display in the status bar
     *
     * @return (String)
     */
    private String getLivesText() {
        return "Lives: " + livesLeft;
    }

    /**
     * Updates and displays the number of lives left
     *
     * @param lives (int)
     */
    public void setLivesLeft (int lives) {
        livesLeft = lives;
        livesText.setText(getLivesText());
    }

    /**
     * Gets the number of lives remaining
     *
     * @return (int) number of lives remaining
     */
    public int getLivesLeft () {
        return livesLeft;
    }

    /**
     * Add one to the number of lives left
     */
    public void addLife () {
        this.setLivesLeft(livesLeft+1);
    }

    /**
     * Subtracts one from the number of lives left
     */
    public void subtractLife () {
        this.setLivesLeft(livesLeft-1);
    }
}
