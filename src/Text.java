import bagel.*;

/**
 * Represents the texts that will be printed in the menus.
 * Although this class can be implemented in ShadowDimension class itself,
 * creating a separate Text class makes the ShadowDimension class much cleaner
 * due to the sheer number of definitions related to text.
 */
public class Text {
    //Default font definitions
    private final static String FONT_FILEPATH = "res/frostbite.ttf";
    private final static int DEFAULT_FONT_SIZE = 75;
    private final Font DEFAULT_FONT =
            new Font(FONT_FILEPATH, DEFAULT_FONT_SIZE);

    //Instruction font definitions
    private final static int INSTRUCTION_FONT_SIZE = 40;
    private final Font INSTRUCTION_FONT =
            new Font(FONT_FILEPATH, INSTRUCTION_FONT_SIZE);

    //Title text definitions
    private final static String TITLE = "SHADOW DIMENSION";
    private final static int TITLE_X = 260;
    private final static int TITLE_Y = 250;

    //Start screen instruction definitions
    private final static String START_STRING_1 = "PRESS SPACE TO START";
    private final static int START_STRING_1_X = TITLE_X + 90;
    private final static int START_STRING_1_Y = TITLE_Y + 190;
    private final static String START_STRING_2 = "USE ARROW KEYS TO FIND GATE";
    private final static int START_STRING_2_X = TITLE_X + 35;
    private final static int START_STRING_2_Y = TITLE_Y + 190 + DEFAULT_FONT_SIZE;

    //Transition screen instruction definitions
    private final static String TRANSITION_STRING_1 = START_STRING_1;
    private final static int TRANSITION_STRING_1_X = 350;
    private final static int TRANSITION_STRING_1_Y = 350;
    private final static String TRANSITION_STRING_2 = "PRESS A TO ATTACK";
    private final static int TRANSITION_STRING_2_X = 372;
    private final static int TRANSITION_STRING_2_Y = TRANSITION_STRING_1_Y + DEFAULT_FONT_SIZE;
    private final static String TRANSITION_STRING_3 = "DEFEAT NAVEC TO WIN";
    private final static int TRANSITION_STRING_3_X = 360;
    private final static int TRANSITION_STRING_3_Y = TRANSITION_STRING_2_Y + DEFAULT_FONT_SIZE;

    //Win/lose screen text definitions
    private final static String WIN_TEXT = "CONGRATULATIONS!";
    private final static String LOSE_TEXT = "GAME OVER!";

    private final double windowWidth;
    private final double windowHeight;


    /**
     * Instantiates the set of texts.
     * @param windowWidth
     * The width of the game's window.
     * @param windowHeight
     * The height of the game's window.
     */
    public Text(int windowWidth, int windowHeight) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    /**
     * Write the message when starting the game.
     */
    public void writeStartScreen() {
        DEFAULT_FONT.drawString(TITLE, TITLE_X, TITLE_Y);
        INSTRUCTION_FONT.drawString(START_STRING_1,
                START_STRING_1_X,
                START_STRING_1_Y);
        INSTRUCTION_FONT.drawString(
                START_STRING_2,
                START_STRING_2_X,
                START_STRING_2_Y);
    }

    /**
     * Write the message when transitioning between levels.
     */
    public void writeTransitionScreen() {
        INSTRUCTION_FONT.drawString(TRANSITION_STRING_1,
                TRANSITION_STRING_1_X,
                TRANSITION_STRING_1_Y);
        INSTRUCTION_FONT.drawString(TRANSITION_STRING_2,
                TRANSITION_STRING_2_X,
                TRANSITION_STRING_2_Y);
        INSTRUCTION_FONT.drawString(TRANSITION_STRING_3,
                TRANSITION_STRING_3_X,
                TRANSITION_STRING_3_Y);
    }

    /**
     * Write the message when the game is won.
     */
    public void writeWinScreen() {
        DEFAULT_FONT.drawString(WIN_TEXT,
                (windowWidth/2) - (DEFAULT_FONT.getWidth(WIN_TEXT) / 2),
                (windowHeight/2) + ((double) DEFAULT_FONT_SIZE / 2));
    }

    /**
     * Write the message when the game is lost.
     */
    public void writeLoseScreen() {
        DEFAULT_FONT.drawString(LOSE_TEXT,
                (windowWidth/2) - (DEFAULT_FONT.getWidth(LOSE_TEXT) / 2),
                (windowHeight/2) + ((double) DEFAULT_FONT_SIZE / 2));
    }
}
