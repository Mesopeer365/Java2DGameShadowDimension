/**
 * Boundary represents the rectangle shape of an object.
 */
public class Boundary {
    private double topBound, botBound, leftBound, rightBound;
    private boolean
            definedTopBound = false,
            definedBotBound = false,
            definedLeftBound = false,
            definedRightBound = false;

    /**
     * Instantiates boundary without defining the bounds.
     * Only used for the World class as its bounds are not determined upon its initialisation.
     */
    public Boundary() {}

    /**
     * Instantiates and defines boundary.
     * @param topBound
     * Y coordinate of the top edge.
     * @param botBound
     * Y coordinate of the bottom edge.
     * @param leftBound
     * X coordinate of the left edge.
     * @param rightBound
     * X coordinate of the right edge.
     */
    public Boundary(double topBound, double botBound, double leftBound, double rightBound) {
        this.topBound = topBound;
        this.botBound = botBound;
        this.leftBound = leftBound;
        this.rightBound = rightBound;

        definedTopBound = definedBotBound = definedLeftBound = definedRightBound = true;
    }

    /**
     * Checks if all the boundary edges are defined
     */
    public boolean isDefined() {
        return (definedTopBound && definedBotBound && definedLeftBound && definedRightBound);
    }

    //Set methods
    /**
     * Sets the top edge and marks it as defined.
     * @param topBound
     * Y coordinate of the new top edge.
     */
    public void setTopBound(double topBound) {
        this.topBound = topBound;
        definedTopBound = true;
    }
    /**
     * Sets the bottom edge and marks it as defined.
     * @param botBound
     * Y coordinate of the new bottom edge.
     */
    public void setBotBound(double botBound) {
        this.botBound = botBound;
        definedBotBound = true;
    }
    /**
     * Sets the left edge and marks it as defined.
     * @param leftBound
     * X coordinate of the new left edge.
     */
    public void setLeftBound(double leftBound) {
        this.leftBound = leftBound;
        definedLeftBound = true;
    }
    /**
     * Sets the right edge and marks it as defined.
     * @param rightBound
     * X coordinate of the new right edge.
     */
    public void setRightBound(double rightBound) {
        this.rightBound = rightBound;
        definedRightBound = true;
    }

    //Get methods
    /**
     *
     * @return
     * Top edge of the boundary.
     */
    public double getTopBound() {
        return topBound;
    }
    /**
     *
     * @return
     * Bottom edge of the boundary.
     */
    public double getBotBound() {
        return botBound;
    }
    /**
     *
     * @return
     * Left edge of the boundary.
     */
    public double getLeftBound() {
        return leftBound;
    }
    /**
     *
     * @return
     * Right edge of the boundary.
     */
    public double getRightBound() {
        return rightBound;
    }
    /**
     *
     * @return
     * X coordinate of the centre of the rectangle.
     */
    public double getCentreX() {
        return (leftBound + rightBound) / 2;
    }
    /**
     *
     * @return
     * Y coordinate of the centre of the rectangle.
     */
    public double getCentreY() {
        return (topBound + botBound) / 2;
    }
}
