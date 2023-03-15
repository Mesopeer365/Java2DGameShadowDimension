import bagel.Image;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents characters that can have health, move around and inflict damage.
 * Includes the player and enemies.
 */
public abstract class Entity extends WorldObject implements CanAttack{
    private final static double TIMESCALE_MULTIPLIER = 0.5;

    private final Health hp;
    private final double invincibleDurationMs;

    private double stepSize = 0;
    private boolean facingLeft;
    private boolean invincible = false;
    private double invincibleCountdown = 0;
    private double allowedStepSize = stepSize;
    private boolean dead = false;


    protected Entity(int xInput, int yInput, Image avatar, int maxHp, int invincibleDurationMs) {
        super(xInput, yInput, avatar);
        hp = new Health(maxHp);
        this.invincibleDurationMs = invincibleDurationMs;
    }

    /**
     * Inflicts damage on an entity.
     * @param entity
     * The entity that is receiving the damage.
     */
    public void hitEntity(Entity entity) {
        entity.takeDmg(getDMG(), getName(), false);
    }

    /**
     * Receive damage
     * @param dmgTaken
     * The amount of damage being received.
     * @param sourceName
     * The name of the source of damage.
     * @param forceTakeDmg
     * If true, entity will take damage regardless of whether it is invincible.
     */
    public void takeDmg(int dmgTaken, String sourceName, boolean forceTakeDmg) {
        if (invincible && !forceTakeDmg)
            return;

        hp.takeDmg(dmgTaken);
        turnInvincible();

        System.out.println(
                sourceName + " inflicts " + dmgTaken + " damage points on " + getName() + ". " +
                getName() + "'s current health: " + hp.getValue() + "/" + hp.getMaxHp()
        );

        if (hp.getValue() <= hp.getMinHp())
            dead = true;
    }



    /*__________Collision methods__________*/

    /**
     * Moves the entity in a direction,
     * scans through all objects in its path
     * and resolves the collision against them one by one.
     * @param direction
     * The direction the entity is moving towards.
     * The definition for the directions are defined in the ShadowDimension class.
     * @param objectsList
     * List of objects being checked for collision.
     * @param worldBounds
     * The boundary of the world the object is in.
     * @param timescale
     * The game's timescale. Affects the movement speed.
     * Set to 0 if entity is not meant to be affected by timescale.
     */
    public void moveAndCheckCollision(
            int direction, ArrayList<WorldObject> objectsList, Boundary worldBounds, int timescale) {
        setAllowedStepSize(timescale);
        double worldBoundsDistance = worldBoundsDistance(direction, worldBounds);

        if (worldBoundsDistance < allowedStepSize) {
            allowedStepSize = worldBoundsDistance;
            collideWorldBounds();
        }

        //objectsIterator used to remove an object as the list is iterated
        Iterator<WorldObject> objectsIterator = objectsList.iterator();
        while(objectsIterator.hasNext()) {
            WorldObject object = objectsIterator.next();

            if (checkPotentialCollision(object, direction)) {
                double distance = checkDistance(object, direction);
                if (distance < allowedStepSize)
                    collideObject(distance, object, direction);
            }
        }

        move(direction);
    }

    private void setAllowedStepSize(int timescale) {
        allowedStepSize = stepSize;

        if (timescale == 0)
            return;

        double factor = 1 + TIMESCALE_MULTIPLIER * Integer.signum(timescale);
        double power = Math.abs(timescale);

        allowedStepSize *= Math.pow(factor, power);
    }

    private double worldBoundsDistance(int direction, Boundary worldBounds) {
        double worldBoundDistance;
        switch (direction) {
            case (ShadowDimension.LEFT):
                worldBoundDistance = getXCoordinate() - worldBounds.getLeftBound();
                break;
            case (ShadowDimension.RIGHT):
                worldBoundDistance = worldBounds.getRightBound() - getXCoordinate();
                break;
            case (ShadowDimension.UP):
                worldBoundDistance = getYCoordinate() - worldBounds.getTopBound();
                break;
            case (ShadowDimension.DOWN):
                worldBoundDistance = worldBounds.getBotBound() - getYCoordinate();
                break;
            default:
                worldBoundDistance = 0;
        }
        return worldBoundDistance;
    }

    //Method exists in case there's a behaviour when colliding worldBounds
    protected void collideWorldBounds() {}
    private boolean checkPotentialCollision(WorldObject object, int direction) {
        switch (direction) {
            case(ShadowDimension.LEFT):
                return (getBounds().getLeftBound() >= object.getBounds().getRightBound() &&
                        (getBounds().getTopBound() < object.getBounds().getBotBound()) &&
                        (getBounds().getBotBound() > object.getBounds().getTopBound()));
            case(ShadowDimension.RIGHT):
                return (getBounds().getRightBound() <= object.getBounds().getLeftBound() &&
                        (getBounds().getTopBound() < object.getBounds().getBotBound()) &&
                        (getBounds().getBotBound() > object.getBounds().getTopBound()));
            case(ShadowDimension.UP):
                return (getBounds().getTopBound() >= object.getBounds().getBotBound() &&
                        (getBounds().getLeftBound() < object.getBounds().getRightBound()) &&
                        (getBounds().getRightBound() > object.getBounds().getLeftBound()));
            case(ShadowDimension.DOWN):
                return (getBounds().getBotBound() <= object.getBounds().getTopBound() &&
                        (getBounds().getLeftBound() < object.getBounds().getRightBound()) &&
                        (getBounds().getRightBound() > object.getBounds().getLeftBound()));
        }
        return false;
    }

    private double checkDistance(WorldObject object, int direction) {
        double distance;
        switch (direction) {
            case (ShadowDimension.LEFT):
                distance = getBounds().getLeftBound() - object.getBounds().getRightBound();
                break;
            case (ShadowDimension.RIGHT):
                distance = object.getBounds().getLeftBound() - getBounds().getRightBound();
                break;
            case (ShadowDimension.UP):
                distance = getBounds().getTopBound() - object.getBounds().getBotBound();
                break;
            case (ShadowDimension.DOWN):
                distance = object.getBounds().getTopBound() - getBounds().getBotBound();
                break;
            default:
                return allowedStepSize;
        }
        return distance;
    }

    /**
     * The behaviour of an entity when colliding with a specific object.
     * @param distance
     * The distance from the object being collided against.
     * @param object
     * The object being collided against.
     * @param direction
     * The direction of the object being collided against relative to the moving entity.
     */
    protected abstract void collideObject(double distance, WorldObject object, int direction);

    /*__________End of collision methods__________*/



    /*__________Move methods__________*/
    private void move(int direction) {
        switch (direction) {
            case(ShadowDimension.RIGHT):
                moveRight(allowedStepSize, true);
                break;
            case(ShadowDimension.UP):
                moveUp(allowedStepSize);
                break;
            case(ShadowDimension.DOWN):
                moveDown(allowedStepSize);
                break;
            case(ShadowDimension.LEFT):
                moveLeft(allowedStepSize, true);
                break;
        }
    }

    protected void moveLeft(double stepSize, boolean turn) {
        setXCoordinate(getXCoordinate() - stepSize);
        super.getBounds().setLeftBound(super.getBounds().getLeftBound() - stepSize);
        super.getBounds().setRightBound(super.getBounds().getRightBound() - stepSize);

        if (turn)
            facingLeft = true;
    }
    protected void moveRight(double stepSize, boolean turn) {
        setXCoordinate(getXCoordinate() + stepSize);
        super.getBounds().setLeftBound(super.getBounds().getLeftBound() + stepSize);
        super.getBounds().setRightBound(super.getBounds().getRightBound() + stepSize);

        if (turn)
            facingLeft = false;
    }
    private void moveUp(double stepSize) {
        setYCoordinate(getYCoordinate() - stepSize);
        super.getBounds().setTopBound(super.getBounds().getTopBound() - stepSize);
        super.getBounds().setBotBound(super.getBounds().getBotBound() - stepSize);
    }
    private void moveDown(double stepSize) {
        setYCoordinate(getYCoordinate() + stepSize);
        super.getBounds().setTopBound(super.getBounds().getTopBound() + stepSize);
        super.getBounds().setBotBound(super.getBounds().getBotBound() + stepSize);
    }

    /*__________End of move methods__________*/



    private void turnInvincible() {
        invincible = true;
        invincibleCountdown = invincibleDurationMs;
    }

    protected void updateInvincibility(double timePassedMs) {
        if (invincible) {
            invincibleCountdown -= timePassedMs;

            if (invincibleCountdown <= 0)
                invincible = false;
        }
    }

    //Set methods
    protected void setStepSize(double stepSize) {
        this.stepSize = stepSize;
    }
    protected void setFacingLeft(boolean isFacingLeft) {
        facingLeft = isFacingLeft;
    }
    protected void setAllowedStepSize(double allowedStepSize) {
        this.allowedStepSize = allowedStepSize;
    }

    //Get methods
    protected abstract Image getLeftAvatar();
    protected abstract Image getRightAvatar();
    protected boolean isFacingLeft() {
        return facingLeft;
    }
    protected boolean isInvincible() {
        return invincible;
    }
    protected double getStepSize(){
        return stepSize;
    }
    protected Image getImg() {
        if (facingLeft)
            return getLeftAvatar();
        else
            return getRightAvatar();
    }
    protected Health getHp() {
        return hp;
    }
    protected boolean isDead() {
        return dead;
    }
}
