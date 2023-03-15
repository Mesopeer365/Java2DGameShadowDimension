import bagel.Image;

/**
 * A boss enemy that aims to defeat the player.
 * Kill it to beat the level.
 */
public class Navec extends Enemy {
    private final static String BOSS_NAME = "Navec";
    private final static Image LEFT_AVATAR = new Image("res/navec/navecLeft.png");
    private final static Image RIGHT_AVATAR = new Image("res/navec/navecRight.png");
    private final static Image LEFT_INVINCIBLE_AVATAR = new Image("res/navec/navecInvincibleLeft.png");
    private final static Image RIGHT_INVINCIBLE_AVATAR = new Image("res/navec/navecInvincibleRight.png");
    private final static Image FIRE_IMG = new Image("res/navec/navecFire.png");
    private final static int MAX_HP = 80;
    private final static int DMG = 20;
    private final static int DETECT_RADIUS = 200;


    /**
     * Instantiates the Navec boss.
     * @param xInput
     * Starting x coordinate.
     * @param yInput
     * Starting y coordinate.
     */
    public Navec(int xInput, int yInput) {
        super(xInput, yInput, MAX_HP, DETECT_RADIUS, LEFT_AVATAR, true);
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

    @Override
    public int getDMG() {
        return DMG;
    }
    @Override
    public String getName() {
        return BOSS_NAME;
    }
}
