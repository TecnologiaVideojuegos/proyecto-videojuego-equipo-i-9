package game;

public class player {

    private static int credits = 0;
    private static int lives;
    private static final int STARTINGCREDITS = 200;
    private static final int STARTINGLIVES = 20;
    private static player instance = null;

    public static synchronized player getPlayer() {
        if (instance == null) {
            instance = new player();
        }
        return instance;
    }

    public void reset() {
        lives = STARTINGLIVES;
        credits = STARTINGCREDITS;
    }

    public void addCredits(int amount) {
        credits += amount;
    }

    public void addLife() {
        lives++;
    }

    public void decreaseLife() {
        lives--;
    }

    public int getLives() {
        return lives;
    }

    public static int getSTARTINGLIVES() {
        return STARTINGLIVES;
    }

    public static int getSTARTINGCREDITS() {
        return STARTINGCREDITS;
    }

    public static void setLives(int lives) {
        player.lives = lives;
    }

    public int getCredits() {
        return credits;
    }

    public static void setCredits(int credits) {
        player.credits = credits;
    }

}
