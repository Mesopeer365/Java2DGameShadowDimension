import bagel.*;
import java.util.ArrayList;

/**
 * Represents the adorable character the player is controlling.
 */
public class Player extends Entity {
    /**
     * The name of the player character.
     */
    public final static String PLAYER_NAME = "Fae";

    private final static Image LEFT_AVATAR = new Image("res/fae/faeLeft.png");
    private final static Image RIGHT_AVATAR = new Image("res/fae/faeRight.png");
    private final static Image LEFT_ATTACK_AVATAR = new Image("res/fae/faeAttackLeft.png");
    private final static Image RIGHT_ATTACK_AVATAR = new Image("res/fae/faeAttackRight.png");
    private final static double STEP_SIZE = 2;
    private final static int MAX_HP = 100;
    private final static int DMG = 20;
    private final static int ATTACK_DURATION_MS = 1000;
    private final static int ATTACK_COOLDOWN_MS = 2000;
    private final static int INVINCIBLE_DURATION_MS = 3000;

    private final double attackWidthDifference = RIGHT_ATTACK_AVATAR.getWidth() - RIGHT_AVATAR.getWidth();

    private boolean attacking = false;
    private double attackCountdown = 0;
    private boolean attackCooldown = false;
    private double cooldownCountdown = 0;


    /**
     * Instantiates the player character.
     * @param xInput
     * Starting x coordinate.
     * @param yInput
     * Starting y coordinate.
     */
    public Player(int xInput, int yInput) {
        super(xInput, yInput, RIGHT_AVATAR, MAX_HP, INVINCIBLE_DURATION_MS);
        setStepSize(STEP_SIZE);
    }

    /**
     * Puts the player character in an attacking state that can damage the enemies.
     * Only works if the state is not on cooldown.
     */
    public void startAttack() {
        if (!attackCooldown && !attacking) {
            getBounds().setRightBound(getXCoordinate() + RIGHT_ATTACK_AVATAR.getWidth());
            if (isFacingLeft())
                moveLeft(attackWidthDifference, false);

            attacking = true;
            attackCountdown = ATTACK_DURATION_MS;
        }
    }

    /**
     * Updates the countdown timers of the player's states and
     * checks if player character is overlapping an enemy while attacking
     * @param timePassedMs
     * The amount of time that passed for each update of the game in ms.
     * @param objects
     * The list of objects in the world.
     */
    public void updatePlayer(double timePassedMs, ArrayList<WorldObject> objects) {
        updateInvincibility(timePassedMs);
        if (attacking) {
            attackCountdown -= timePassedMs;

            if (attackCountdown <= 0) {
                attacking = false;
                attackCooldown = true;
                cooldownCountdown = ATTACK_COOLDOWN_MS;
                getBounds().setRightBound(getXCoordinate() + RIGHT_AVATAR.getWidth());
                if (isFacingLeft())
                    moveRight(attackWidthDifference, false);
            }

            for (WorldObject object : objects)
                if (object instanceof Enemy && checkOverlap(object))
                    hitEntity((Enemy) object);

        } else if (attackCooldown) {
            cooldownCountdown -= timePassedMs;

            if (cooldownCountdown <= 0) {
                attackCooldown = false;
            }
        }
    }

    @Override
    protected void collideObject(double distance, WorldObject object, int direction) {
        if ((object instanceof Sinkhole) && (distance < getStepSize())) {
            collideSinkhole((Sinkhole) object);
        } else if (object instanceof Obstacle) {
            setAllowedStepSize(distance);
        }
    }

    private void collideSinkhole(Sinkhole sinkhole) {
        int dmgTaken = sinkhole.getDMG();
        takeDmg(dmgTaken, sinkhole.getName(), true);
        sinkhole.removeObject();
    }

    //Get methods
    @Override
    protected Image getLeftAvatar() {
        if (attacking)
            return LEFT_ATTACK_AVATAR;
        return LEFT_AVATAR;
    }
    @Override
    protected Image getRightAvatar() {
        if (attacking)
            return RIGHT_ATTACK_AVATAR;
        return RIGHT_AVATAR;
    }
    @Override
    public int getDMG() {
        return DMG;
    }
    @Override
    public String getName() {
        return PLAYER_NAME;
    }
}

