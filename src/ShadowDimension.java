import bagel.*;
import java.io.FileReader;
import java.io.BufferedReader;

/**
 * Code for SWEN20003 Project 2, Semester 2, 2022
 * @author
 * Nhat Long Vu 1266510
 */
public class ShadowDimension extends AbstractGame {
    //Defined variables to be used in other classes
    /**
     * Integer representing the right direction.
     */
    public final static int RIGHT = 0;
    /**
     * Integer representing the up direction.
     */
    public final static int UP    = 1;
    /**
     * Integer representing the left direction.
     */
    public final static int LEFT  = 2;
    /**
     * Integer representing the down direction.
     */
    public final static int DOWN  = 3;
    /**
     * Integer representing direction with the lowest integer value.
     */
    public final static int MIN_DIRECTION = 0;
    /**
     * Integer representing direction with the highest integer value.
     */
    public final static int MAX_DIRECTION = 3;

    //Defined game variables that determine how the game is run
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DIMENSION";
    private final static double REFRESH_RATE_HZ = 60;
    private final static double DEFAULT_TIME_MS = 1000/REFRESH_RATE_HZ;
    private final static double MAX_TIMESCALE = 3;
    private final static double MIN_TIMESCALE = -3;

    //Defined level variables that determine the features of the levels
    private final static int[] MAX_ENTRIES = {60, 29};
    private final static String[] LEVEL_CSV = {"res/level0.csv", "res/level1.csv"};
    private final static int MAX_LEVEL = LEVEL_CSV.length - 1;

    //Defined game states
    private final static int START_STATE = 0;
    private final static int PLAY_STATE = 1;
    private final static int TRANSITION_STATE = 2;
    private final static int WIN_STATE = 3;
    private final static int LOSE_STATE = 4;

    //Related to menu texts
    private final Text gameText = new Text(WINDOW_WIDTH, WINDOW_HEIGHT);

    //Variables related to the current states of the game
    private int timescale = 0;
    private int levelNum = 0;
    private int gameState = START_STATE;

    //Instantiates the level and passes the strings read in readCSV method
    private World level = new World(levelNum, readCSV(LEVEL_CSV[levelNum]));


    /**
     * Instantiates the program with defined variables.
     */
    public ShadowDimension() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }


    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDimension game = new ShadowDimension();
        game.run();
    }


    /**
     * Method used to read file and create objects.
     * @param csvFilepath
     * The path to the file that describes the level being created.
     * @return
     * An array of Strings where each line is an element.
     */
    private String[] readCSV(String csvFilepath) {
        String[] worldEntries = new String[MAX_ENTRIES[levelNum]];
        int entryNum = 0;

        try (BufferedReader levelFile = new BufferedReader(new FileReader(csvFilepath))) {
            String text;
            while ((text = levelFile.readLine()) != null && entryNum < MAX_ENTRIES[levelNum]) {
                worldEntries[entryNum] = text;
                entryNum++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return worldEntries;
    }


    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE))
            Window.close();

        if (input.wasPressed(Keys.L))
            speedUp();
        if (input.wasPressed(Keys.K))
            slowDown();

        switch (gameState) {
            case(START_STATE):
                startingGame(input);
                break;
            case (PLAY_STATE):
                playingGame(input);
                break;
            case (TRANSITION_STATE):
                transitionLevel(input);
                break;
            case (WIN_STATE):
                gameText.writeWinScreen();
                break;
            case (LOSE_STATE):
                gameText.writeLoseScreen();
                break;
        }
    }


    private void speedUp() {
        if (timescale < MAX_TIMESCALE)
            timescale++;

        System.out.println("Sped up, Speed:  " + timescale);
    }

    private void slowDown() {
        if (timescale > MIN_TIMESCALE)
            timescale--;

        System.out.println("Slowed down, Speed:  " + timescale);
    }

    private void startingGame(Input input) {
        gameText.writeStartScreen();
        if (input.wasPressed(Keys.SPACE))
            gameState = PLAY_STATE;
    }

    private void playingGame(Input input) {
        level.updateWorld(DEFAULT_TIME_MS, timescale);
        level.controlPlayer(input);

        if (level.hasWon()) {
            if (levelNum >= MAX_LEVEL)
                gameState = WIN_STATE;
            else
                gameState = TRANSITION_STATE;
        }

        if (level.hasLost())
            gameState = LOSE_STATE;
    }

    private void transitionLevel(Input input) {
        gameText.writeTransitionScreen();
        if (input.wasPressed(Keys.SPACE)) {
            if (levelNum < MAX_LEVEL)
                levelNum++;
            level = new World(levelNum, readCSV(LEVEL_CSV[levelNum]));
            gameState = PLAY_STATE;
        }
    }
}
