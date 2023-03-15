import bagel.DrawOptions;
import bagel.Image;

/**
 * Represents the fire an enemy produces that hurts the player
 */
public class Fire extends WorldObject {
    private final Enemy hostEnemy;
    private final Image img;
    private final int direction;


    /**
     * Instantiates the fire.
     * @param xCoordinate
     * X coordinate of spawn location.
     * @param yCoordinate
     * Y coordinate of spawn location.
     * @param img
     * The image of the fire.
     * @param hostEnemy
     * The enemy producing the fire.
     * @param direction
     * The direction the fire is being produced relative to the enemy.
     */
    public Fire(double xCoordinate, double yCoordinate, Image img, Enemy hostEnemy, int direction) {
        super(xCoordinate, yCoordinate, img);
        this.img = img;
        this.hostEnemy = hostEnemy;
        this.direction = direction;
    }

    /**
     * Draws the fire depending on the direction it is fired in.
     */
    public void drawFire() {
        DrawOptions rotation = new DrawOptions();

        switch (direction) {
            case (Enemy.NW):
                rotation.setRotation(Math.PI);
                break;
            case (Enemy.NE):
                rotation.setRotation(3*Math.PI/2);
                break;
            case (Enemy.SW):
                rotation.setRotation(Math.PI/2);
                break;
            case (Enemy.SE):
        }

        img.drawFromTopLeft(getXCoordinate(), getYCoordinate(), rotation);
    }

    /**
     * Check if the player overlaps with the fire and damages them.
     * @param player
     * Player being targeted.
     */
    public void collidePlayer(Player player) {
        if (checkOverlap(player))
            hostEnemy.hitEntity(player);
    }

    @Override
    protected Image getImg() {
        return img;
    }
}
