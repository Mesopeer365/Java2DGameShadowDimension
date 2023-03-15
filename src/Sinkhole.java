import bagel.Image;

/**
 * Represents a trap that can damage the player.
 */
public class Sinkhole extends WorldObject implements CanAttack, Removable {
    private final static String NAME = "Sinkhole";
    private final static int DMG = 30;
    private final static Image SINKHOLE_IMG = new Image("res/sinkhole.png");
    private boolean remove = false;

    /**
     * Instantiates the trap
     * @param xCoordinate
     * X coordinate.
     * @param yCoordinate
     * Y coordinate.
     */
    public Sinkhole(int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate, SINKHOLE_IMG);
    }

    @Override
    public void removeObject() {
        remove = true;
    }
    @Override
    public boolean isRemoved() {
        return remove;
    }

    //Get methods
    @Override
    protected Image getImg() {
        return SINKHOLE_IMG;
    }
    @Override
    public int getDMG() {
        return DMG;
    }
    @Override
    public String getName() {
        return NAME;
    }
}
