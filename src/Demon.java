import bagel.*;

/**
 * A minor enemy that aims to defeat the player.
 * Killing it is optional.
 */
public class Demon extends Enemy {
    private final static String NAME = "Demon";
    private final static Image LEFT_AVATAR = new Image("res/demon/demonLeft.png");
    private final static Image RIGHT_AVATAR = new Image("res/demon/demonRight.png");
    private final static Image LEFT_INVINCIBLE_AVATAR = new Image("res/demon/demonInvincibleLeft.png");
    private final static Image RIGHT_INVINCIBLE_AVATAR = new Image("res/demon/demonInvincibleRight.png");
    private final static Image FIRE_IMG = new Image("res/demon/demonFire.png");
    private final static int MAX_HP = 40;
    private final static int DMG = 10;
    private final static int DETECT_RADIUS = 150;


    /**
     * Instantiates a demon.
     * @param xInput
     * Starting x coordinate.
     * @param yInput
     * Starting y coordinate.
     */
    public Demon(int xInput, int yInput) {
        super(xInput, yInput, MAX_HP, DETECT_RADIUS, LEFT_AVATAR, false);
    }

    @Override
    protected Image leftAvatar() {
        return LEFT_AVATAR;
    }
    @Override
    protected Image rightAvatar() {
        return RIGHT_AVATAR;
    }
    @Override
    protected Image leftInvincibleAvatar() {
        return LEFT_INVINCIBLE_AVATAR;
    }
    @Override
    protected Image rightInvincibleAvatar() {
        return RIGHT_INVINCIBLE_AVATAR;
    }
    @Override
    protected Image fireImg() {
        return FIRE_IMG;
    }

    //Get methods
    @Override
    public int getDMG() {
        return DMG;
    }
    @Override
    public String getName() {
        return NAME;
    }
}
