import bagel.DrawOptions;
import bagel.Font;
import bagel.util.Colour;

/**
 * Represents the health point of an entity
 */
public class Health {
    private final static String FONT_FILEPATH = "res/frostbite.ttf";
    private final static double MIN_HP = 0;
    private final static double WOUNDED_VALUE = 0.65;
    private final static double DANGER_VALUE = 0.35;
    private final static Colour HEALTHY_HP = new Colour(0, 0.8, 0.2); //green
    private final static Colour WOUNDED_HP = new Colour(0.9, 0.6, 0); //orange
    private final static Colour DANGER_HP = new Colour(1, 0, 0);      //red

    private final double maxHp;

    private Colour currentColour = HEALTHY_HP;
    private double value;


    /**
     * Instantiates an entity's health
     * @param maxHp
     * The maximum and stating health point
     */
    public Health(double maxHp) {
        this.maxHp = maxHp;
        value = maxHp;
    }

    /**
     * Deduct the health point.
     * Cannot go below the minimum.
     * @param dmgTaken
     * The amount being deducted.
     */
    public void takeDmg(int dmgTaken) {
        if ((value - MIN_HP) > dmgTaken) {
            value -= dmgTaken;
            setCurrentColour();
        } else {
            value = MIN_HP;
            setCurrentColour();
        }
    }

    /**
     * Draw the numerical value of the health.
     * @param fontSize
     * The size of the health number.
     * @param xCoordinate
     * X coordinate of the place it is drawn.
     * @param yCoordinate
     * Y coordinate of the place it is drawn.
     */
    public void drawHp(int fontSize, double xCoordinate, double yCoordinate) {
        Font hpFont = new Font(FONT_FILEPATH, fontSize);
        int percent = (int) ((value/maxHp)*100);
        DrawOptions colourOption =
                new DrawOptions().setBlendColour(currentColour);

        hpFont.drawString(
                percent + "%", xCoordinate, yCoordinate, colourOption);
    }

    private void setCurrentColour() {
        if (value/maxHp < DANGER_VALUE)
            currentColour = DANGER_HP;
        else if (value/maxHp < WOUNDED_VALUE)
            currentColour = WOUNDED_HP;
        else
            currentColour = HEALTHY_HP;
    }

    //Get methods
    /**
     *
     * @return
     * The current health point remaining.
     */
    public int getValue() {
        return (int) value;
    }
    /**
     *
     * @return
     * The minimum health point possible, typically 0.
     */
    public int getMinHp() {
        return (int) MIN_HP;
    }
    /**
     *
     * @return
     * The maximum health point value.
     */
    public int getMaxHp() {
        return (int) maxHp;
    }
}
