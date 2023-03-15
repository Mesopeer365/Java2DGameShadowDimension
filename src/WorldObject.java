import bagel.Image;

/**
 * Represents any physical object in this game.
 */
public abstract class WorldObject {
    private final Boundary bounds;
    private double xCoordinate, yCoordinate;

    //Img must be a parameter in order to define the boundary
    protected WorldObject(double xCoordinate, double yCoordinate, Image img) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

        bounds = new Boundary(
                yCoordinate,                            //top bound
                yCoordinate + img.getHeight(),          //bot bound
                xCoordinate,                            //left bound
                xCoordinate + img.getWidth()            //right bound
        );
    }

    /**
     * Draws the object model on the screen.
     */
    public void drawObject() {
        getImg().drawFromTopLeft(xCoordinate, yCoordinate);
    }

    protected boolean checkOverlap(WorldObject object) {
        return (getBounds().getTopBound() <= object.getBounds().getBotBound() &&
                getBounds().getBotBound() >= object.getBounds().getTopBound() &&
                getBounds().getLeftBound() <= object.getBounds().getRightBound() &&
                getBounds().getRightBound() >= object.getBounds().getLeftBound());
    }

    //Set methods
    protected void setXCoordinate(double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }
    protected void setYCoordinate(double yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    //Get methods
    protected abstract Image getImg();
    /**
     *
     * @return
     * The boundary of the object
     */
    public Boundary getBounds() {
        return bounds;
    }
    /**
     *
     * @return
     * The x coordinate of the object.
     */
    public double getXCoordinate() {
        return xCoordinate;
    }
    /**
     *
     * @return
     * The y coordinate of the object.
     */
    public double getYCoordinate() {
        return yCoordinate;
    }
}
