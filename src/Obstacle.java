import bagel.Image;

/**
 * Represents a wall that hinders an entity's movement.
 */
public class Obstacle extends WorldObject {
    private final static Image WALL_IMG = new Image("res/wall.png");
    private final static Image TREE_IMG = new Image("res/tree.png");
    private final boolean organic;


    /**
     * Instantiates the obstacle
     * @param xCoordinate
     * X coordinate.
     * @param yCoordinate
     * Y coordinate.
     * @param organic
     * If true, the obstacle is an organic bare tree.
     * If false, the obstacle is an inorganic white wall.
     */
    public Obstacle(int xCoordinate, int yCoordinate, boolean organic) {
        super(xCoordinate, yCoordinate,
                organic ? TREE_IMG : WALL_IMG);
        this.organic = organic;
    }

    @Override
    protected Image getImg() {
        if (organic)
            return TREE_IMG;
        return WALL_IMG;
    }
}
