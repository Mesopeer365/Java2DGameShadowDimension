import bagel.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Represents the level in which the game is currently being played.
 */
public class World {
    private final static int INPUT_TYPE = 0;
    private final static int X_INPUT = 1;
    private final static int Y_INPUT = 2;
    private final static int WIN_X_COORDINATE = 950;
    private final static int WIN_Y_COORDINATE = 670;
    private final static String[] BACKGROUND_IMG = {"res/background0.png", "res/background1.png"};

    private final static int PLAYER_HP_FONT_SIZE = 30;
    private final static int PLAYER_HP_X_COORDINATE = 20;
    private final static int PLAYER_HP_Y_COORDINATE = 25;

    private final int levelNum;
    private final ArrayList<WorldObject> objects = new ArrayList<>();
    private final Boundary worldBounds = new Boundary();

    private Player player;
    private Navec navec;
    private Image backgroundImg;


    /**
     * Instantiates the world
     * @param levelNum
     * The current level number.
     * @param worldEntries
     * The set of data to help create the world.
     */
    public World(int levelNum, String[] worldEntries) {
        this.levelNum = levelNum;
        setBackgroundImg();
        for (String worldEntry : worldEntries) {
            if (worldEntry == null)
                break;

            String[] entry = worldEntry.split(",");
            resolveEntry(entry);
        }

        checkError();
    }

    /**
     * Update the world.
     * @param timePassedMs
     * The amount of time that passed for each update of the game in ms.
     * @param timescale
     * The game's timescale. Affects the enemies' speed.
     */
    public void updateWorld(double timePassedMs, int timescale) {
        drawWorld();
        player.updatePlayer(timePassedMs, objects);

        Iterator<WorldObject> objectIterator = objects.iterator();
        while (objectIterator.hasNext()) {
            WorldObject object = objectIterator.next();
            if (object instanceof Enemy) {
                Enemy enemy = (Enemy) object;
                if (enemy.isDead()) {
                    objectIterator.remove();
                } else {
                    int direction = enemy.getMovement();
                    if (direction != Enemy.STATIONARY)
                        enemy.moveAndCheckCollision(direction, objects, worldBounds, timescale);
                    enemy.updateEnemy(timePassedMs, player);
                }
            } else if (object instanceof Removable) {
                if (((Removable) object).isRemoved()) {
                    objectIterator.remove();
                }

            }
        }
    }

    /**
     * Controls the player character.
     * @param input
     * The input used to control the player character.
     */
    public void controlPlayer(Input input) {
        if (input.wasPressed(Keys.A))
            player.startAttack();
        if (input.isDown(Keys.UP))
            movePlayer(ShadowDimension.UP);
        if (input.isDown(Keys.DOWN))
            movePlayer(ShadowDimension.DOWN);
        if (input.isDown(Keys.LEFT))
            movePlayer(ShadowDimension.LEFT);
        if (input.isDown(Keys.RIGHT))
            movePlayer(ShadowDimension.RIGHT);
    }

    /**
     * Shows if the level has been beaten.
     * @return
     * Whether the level has been beaten.
     */
    public boolean hasWon() {
        if (navec != null)
            return navec.isDead();

        return (player.getXCoordinate() >= WIN_X_COORDINATE &&
                player.getYCoordinate() >= WIN_Y_COORDINATE);
    }

    /**
     * Shows if the player has lost.
     * @return
     * Whether the player has lost.
     */
    public boolean hasLost() {
        return (player.getHp().getValue() <= player.getHp().getMinHp());
    }

    private void setBackgroundImg() {
        backgroundImg = new Image(BACKGROUND_IMG[levelNum]);
    }

    //Resolves entries one by one
    private void resolveEntry(String[] entry) {
        int xCoordinate = Integer.parseInt(entry[X_INPUT]);
        int yCoordinate = Integer.parseInt(entry[Y_INPUT]);

        switch(entry[INPUT_TYPE]) {
            case(Player.PLAYER_NAME):
                player = new Player(xCoordinate, yCoordinate);
                break;
            case("Wall"):
                objects.add(new Obstacle(xCoordinate, yCoordinate, false));
                break;
            case("Tree"):
                objects.add(new Obstacle(xCoordinate, yCoordinate, true));
                break;
            case("Sinkhole"):
                objects.add(new Sinkhole(xCoordinate, yCoordinate));
                break;
            case("Demon"):
                Demon demon = new Demon(xCoordinate, yCoordinate);
                objects.add(demon);
                break;
            case("Navec"):
                navec = new Navec(xCoordinate, yCoordinate);
                objects.add(navec);
                break;
            case("TopLeft"):
                worldBounds.setLeftBound(xCoordinate);
                worldBounds.setTopBound(yCoordinate);
                break;
            case("BottomRight"):
                worldBounds.setRightBound(xCoordinate);
                worldBounds.setBotBound(yCoordinate);
                break;
            default:
                break;
        }
    }

    //Checks if input .csv file is missing a crucial entry
    private void checkError() {
        if (player == null) {
            System.out.println("MISSING PLAYER! EXITING!");
            Window.close();
        } else if (!worldBounds.isDefined()) {
            System.out.println("MISSING A CORNER POINT! EXITING!");
            Window.close();
        }
    }

    //Draw the background, the objects in the world and player health
    private void drawWorld() {
        backgroundImg.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        for (WorldObject object : objects)
            object.drawObject();

        player.getHp().drawHp(PLAYER_HP_FONT_SIZE, PLAYER_HP_X_COORDINATE, PLAYER_HP_Y_COORDINATE);
        player.getImg().drawFromTopLeft(player.getXCoordinate(), player.getYCoordinate());
    }

    private void movePlayer(int direction) {
        player.moveAndCheckCollision(direction, objects, worldBounds, 0);
    }
}
