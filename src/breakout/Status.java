package breakout;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Status {
    private int livesLeft;
    private int score;
    private Text livesText;
    private Text scoreText;

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
        scoreText.setX(windowWidth * 1/3 - (scoreText.getBoundsInLocal().getWidth() / 2));
        scoreText.setY(statusHeight/2 + (scoreText.getBoundsInLocal().getHeight() / 2));
        root.getChildren().add(scoreText);
    }

    public int getScore () {
        return score;
    }

    private String getScoreText () {
        return "Score: " + score;
    }

    public void setScore (int s) {
        score = s;
        scoreText.setText(getScoreText());
    }

    private String getLivesText() {
        return "Lives: " + livesLeft;
    }

    public void setLivesLeft (int lives) {
        livesLeft = lives;
        livesText.setText(getLivesText());
    }

    public int getLivesLeft () {
        return livesLeft;
    }

}
