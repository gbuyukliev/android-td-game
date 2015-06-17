package bg.ittalents.tower_defense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import bg.ittalents.tower_defense.game.objects.AbstractCreep;
import bg.ittalents.tower_defense.game.objects.AbstractTower;
import bg.ittalents.tower_defense.game.objects.Assets;
import bg.ittalents.tower_defense.game.objects.Creep;
import bg.ittalents.tower_defense.game.objects.Tower;
import bg.ittalents.tower_defense.game.objects.Wall;
import bg.ittalents.tower_defense.utils.Constants;

public class Level {

    public static final String TAG = Level.class.getName();

    private int rows;
    private int columns;

    Vector2 startPosition;

//    TiledMap tiledMap;
//    OrthogonalTiledMapRenderer mapRenderer;
    private Array<AbstractTower> towers;
    private Array<AbstractCreep> creeps;
    private Array<Wall> walls;
    private Array<Vector2> wayponts;

    public Level(String fileName) {
//        TmxMapLoader tmxMapLoader = new TmxMapLoader();
//        tiledMap = tmxMapLoader.load("levels/TD.tmx");
//        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        towers = new Array<AbstractTower>();
        creeps = new Array<AbstractCreep>();
        walls = new Array<Wall>();
        wayponts = new Array<Vector2>();

        startPosition = new Vector2();
        init(fileName);
    }

    private void init(String fileName) {
        load(fileName);
        loadWayPoints(fileName);

        Tower tower1 = new Tower(new Vector2(2 * Constants.TILE_WIDTH, 6 * Constants.TILE_HEIGHT),
                Assets.instance.towers.tower[0]);
        Tower tower2 = new Tower(new Vector2(4 * Constants.TILE_WIDTH, 4 * Constants.TILE_HEIGHT),
                Assets.instance.towers.tower[1]);
        Tower tower3 = new Tower(new Vector2(6 * Constants.TILE_WIDTH, 2 * Constants.TILE_HEIGHT),
                Assets.instance.towers.tower[2]);
        Tower tower4 = new Tower(new Vector2(10 * Constants.TILE_WIDTH, 4 * Constants.TILE_HEIGHT),
                Assets.instance.towers.tower[3]);
        Tower tower5 = new Tower(new Vector2(12 * Constants.TILE_WIDTH, 6 * Constants.TILE_HEIGHT),
                Assets.instance.towers.tower[4]);
        Tower tower6 = new Tower(new Vector2(12 * Constants.TILE_WIDTH, 2 * Constants.TILE_HEIGHT),
                Assets.instance.towers.tower[5]);
        Tower tower7 = new Tower(new Vector2(2 * Constants.TILE_WIDTH, 4 * Constants.TILE_HEIGHT),
                Assets.instance.towers.tower[6]);
        towers.add(tower1);
        towers.add(tower2);
        towers.add(tower3);
        towers.add(tower4);
        towers.add(tower5);
        towers.add(tower6);
        towers.add(tower7);


//        build();
    }

//    private void build() {
//
//    }

    private void load(String fileName) {
        String levelStr = Gdx.files.internal(fileName + ".lvl").readString();

        String[] levelRows = levelStr.split("[\r\n]+");

        rows = levelRows.length;
        columns = levelRows[0].length();

        Gdx.app.debug(TAG, rows + "x" + columns);

        for (int row = 0; row < rows; row++) {
            String currentRow = levelRows[row];
            for (int col = 0; col < columns; col++) {
                char currentTile = currentRow.charAt(col);

                if (currentTile == '#') {
                    Vector2 currentPosition = new Vector2(col * Constants.TILE_WIDTH,
                            row * Constants.TILE_HEIGHT);
                    walls.add(new Wall(currentPosition));
                } else if (currentTile == 'S') {
                    startPosition.y = row * Constants.TILE_HEIGHT;
                    startPosition.x = col * Constants.TILE_WIDTH;
                } else if (currentTile == 'W') {
                    this.wayponts.add(new Vector2(row * Constants.TILE_HEIGHT,
                            col * Constants.TILE_WIDTH));
                }
            }
        }
    }

    private void loadWayPoints(String fileName) {
        String pathStr = Gdx.files.internal(fileName + ".path").readString();
        String[] pathWaypoints = pathStr.split("[\r\n]+");

        for(String waypoint : pathWaypoints) {
            String[] coordinates = waypoint.split(",");
            this.wayponts.add(new Vector2(
                    Float.parseFloat(coordinates[0]) * Constants.TILE_WIDTH,
                    Float.parseFloat(coordinates[1]) * Constants.TILE_HEIGHT));

            Gdx.app.debug(TAG, "Waypoint(" + coordinates[0] + ", " + coordinates[1] + ") added");
        }
    }

    public void spawnCreep() {
        Creep creep = new Creep(startPosition, Assets.instance.creep.creep1blue);
        creep.setWaypoints(wayponts);
        creeps.add(creep);
    }

    public void update(float deltaTime) {
        for (AbstractCreep creep : creeps) {
            creep.update(deltaTime);
        }

        for (AbstractTower tower : towers) {
            if (!tower.hasTarget()) {
                for (AbstractCreep creep : creeps) {
                    if (tower.isInRange(creep)) {
                        tower.setFoe(creep);
                        break;
                    }
                }
            }
            tower.update(deltaTime);
        }
    }

    public int getWidth() {
        return columns * Constants.TILE_WIDTH;
    }

    public int getHeight() {
        return rows * Constants.TILE_HEIGHT;
    }

    public void render(SpriteBatch batch) {
        for (AbstractCreep creep : creeps) {
            if(creep.isVisible()) {
                creep.render(batch);
            }
        }

        for (Wall wall : walls) {
            wall.render(batch);
        }

        for (AbstractTower tower : towers) {
            tower.render(batch);
        }

//        mapRenderer.render();
    }
}
