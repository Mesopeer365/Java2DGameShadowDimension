import bagel.Image;
import java.lang.Math;
import java.util.Random;

/**
 * An abstract class that represents entities that aim to defeat the player.
 */
public abstract class Enemy extends Entity {
    /**
     * Integer representing the North East direction.
     */
    public final static int NE = 4;
    /**
     * Integer representing the North West direction.
     */
    public final static int NW = 5;
    /**
     * Integer representing the South West direction.
     */
    public final static int SW = 6;
    /**
     * Integer representing the South East direction.
     */
    public final static int SE = 7;
    /**
     * Integer representing the fact the enemy is not moving.
     */
    public final static int STATIONARY = -1;

    private final static int ENEMY_HP_FONT_SIZE = 15;
    private final static int ENEMY_HP_Y_SHIFT = -6;
    private final static double MAX_STEP_SIZE = 0.7;
    private final static double MIN_STEP_SIZE = 0.2;
    private final static int INVINCIBLE_DURATION_MS = 3000;

    private final int detectRadius;

    private Fire fire;
    private boolean activeFire = false;
    private int movement = STATIONARY;


    protected Enemy(int xInput, int yInput, int maxHp, int detectRadius,
                    Image avatar, boolean alwaysAggressive) {
        super(xInput, yInput, avatar, maxHp, INVINCIBLE_DURATION_MS);
        this.detectRadius = detectRadius;
        randomInitialisation(alwaysAggressive);
    }

    /**
     * Counts down the enemy's invincibility timer and
     * detects if player is within range to shoot fire.
     * @param timePassedMs
     * The amount of time that passed for each update of the game in ms.
     * @param player
     * Player being targeted.
     */
    public void updateEnemy(double timePassedMs, Player player) {
        updateInvincibility(timePassedMs);
        detectPlayer(player);

        if (activeFire) {
            fire.drawFire();
            fire.collidePlayer(player);
        }
    }

    /**
     * Draws the enemy's model and its health on its top left corner.
     */
    @Override
    public void drawObject() {
        getImg().drawFromTopLeft(getXCoordinate(), getYCoordinate());
        getHp().drawHp(ENEMY_HP_FONT_SIZE, getXCoordinate(), getYCoordinate() + ENEMY_HP_Y_SHIFT);
    }

    private void randomInitialisation(boolean alwaysAggressive) {
        Random rand = new Random();
        setFacingLeft(rand.nextBoolean());

        boolean aggressive = alwaysAggressive || rand.nextBoolean();
        if (aggressive) {
            double stepSize = rand.nextDouble() * (MAX_STEP_SIZE - MIN_STEP_SIZE) + MIN_STEP_SIZE;
            setStepSize(stepSize);
            movement = rand.nextInt(ShadowDimension.MAX_DIRECTION + 1) + ShadowDimension.MIN_DIRECTION;
        }
    }

    private void detectPlayer(Player player) {
        double playerXCoordinate = player.getBounds().getCentreX();
        double playerYCoordinate = player.getBounds().getCentreY();

        if (!checkRadius(playerXCoordinate, playerYCoordinate)) {
            activeFire = false;
            return;
        }

        activeFire = true;
        double xCoordinate = getBounds().getCentreX();
        double yCoordinate = getBounds().getCentreY();
        double fireWidth = fireImg().getWidth();
        double fireHeight = fireImg().getHeight();

        if (playerXCoordinate > xCoordinate && playerYCoordinate < yCoordinate) {
            fire = new Fire(getBounds().getRightBound(),
                    getBounds().getTopBound() - fireHeight,
                    fireImg(), this, NE);
        } else if (playerXCoordinate <= xCoordinate && playerYCoordinate < yCoordinate) {
            fire = new Fire(getBounds().getLeftBound() - fireWidth,
                    getBounds().getTopBound() - fireHeight,
                    fireImg(), this, NW);
        } else if (playerXCoordinate > xCoordinate && playerYCoordinate >= yCoordinate) {
            fire = new Fire(getBounds().getRightBound(),
                    getBounds().getBotBound(),
                    fireImg(), this, SE);
        } else {
            fire = new Fire(getBounds().getLeftBound() - fireWidth,
                    getBounds().getBotBound(),
                    fireImg(), this, SW);
        }

    }

    //Checks if a point is within the radius
    private boolean checkRadius(double xCoordinate, double yCoordinate) {
        return (Math.sqrt(
                Math.pow(Math.abs(getBounds().getCentreX() - xCoordinate), 2) +
                Math.pow(Math.abs(getBounds().getCentreY() - yCoordinate), 2))
                <= detectRadius);
    }

    @Override
    protected void collideWorldBounds() {
        flipDirection();
    }

    @Override
    protected void collideObject(double distance, WorldObject object, int direction) {
        setAllowedStepSize(distance);
        flipDirection();
    }

    protected void flipDirection() {
        switch (movement) {
            case(ShadowDimension.RIGHT):
                movement = ShadowDimension.LEFT;
                break;
            case(ShadowDimension.LEFT):
                movement = ShadowDimension.RIGHT;
                break;
            case(ShadowDimension.UP):
                movement = ShadowDimension.DOWN;
                break;
            case(ShadowDimension.DOWN):
                movement = ShadowDimension.UP;
                break;
        }
    }

    //Returns specific avatars to be used by other methods.
    protected abstract Image leftAvatar();
    protected abstract Image rightAvatar();
    protected abstract Image leftInvincibleAvatar();
    protected abstract Image rightInvincibleAvatar();
    protected abstract Image fireImg();

    //Get methods
    @Override
    protected Image getLeftAvatar() {
        if (isInvincible())
            return leftInvincibleAvatar();
        else
            return leftAvatar();
    }
    @Override
    protected Image getRightAvatar() {
        if (isInvincible())
            return rightInvincibleAvatar();
        else
            return rightAvatar();
    }

    /**
     *
     * @return
     * The direction the enemy is moving towards. Enemy.STATIONARY means the enemy is not moving
     */
    public int getMovement() {
        return movement;
    }
}
