package bg.ittalents.tower_defense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import java.util.Iterator;

import bg.ittalents.tower_defense.game.objects.AbstractCreep;
import bg.ittalents.tower_defense.game.objects.AbstractProjectile;
import bg.ittalents.tower_defense.game.objects.AbstractTower;
import bg.ittalents.tower_defense.game.objects.Assets;
import bg.ittalents.tower_defense.game.objects.Creep;
import bg.ittalents.tower_defense.game.objects.Tower;

public class Level implements Disposable {

    public static final int STARTING_LIVES = 10;
    public static final int STARTING_MONEY = 100;

    public static final String TAG = Level.class.getName();

    private int lives;
    private int money;
    private int score;

    private int tileRows;
    private int tileColumns;
    private int tileWidth;
    private int tileHeight;
    Vector2 startPosition;

    private Array<AbstractTower> towers;
    private Array<AbstractCreep> creeps;
    private Array<AbstractProjectile> projectiles;
    private Array<Vector2> wayponts;

    private Tile[][] tiles;

    private class Tile {
        boolean buildable;
        AbstractTower tower;

        Tile() {
            buildable = false;
            tower = null;
        }
    }

//    private Batch batch;

    public Level(TiledMap tiledMap) {

        money = STARTING_MONEY;
        lives = STARTING_LIVES;

        towers = new Array<AbstractTower>();
        creeps = new Array<AbstractCreep>();
        wayponts = new Array<Vector2>();
        projectiles = new Array<AbstractProjectile>();
        startPosition = new Vector2();

        loadLevelData(tiledMap);
        initTiles(tiledMap);

    }

    private void initTiles(TiledMap tiledMap) {
        tiles = new Tile[tileRows][tileColumns];
        TiledMapTileLayer mapLayer = (TiledMapTileLayer) tiledMap.getLayers().get("wall_layer");

        for (int row = 0; row < tileRows; row++) {
            for (int col = 0; col < tileColumns; col++) {
                tiles[row][col] = new Tile();
                if (mapLayer.getCell(col, row) != null &&
                        mapLayer.getCell(col, row).getTile() != null) {
                    tiles[row][col].buildable = true;
                }
            }
        }

//        mapLayer.getCell(0, 0).getTile();


//        Gdx.app.debug(TAG, "" + mapLayer.getCell(1, 0).getTile().toString());

//        Tower tower1 = new Tower(2.5f * tileWidth, 6.5f * tileHeight,
//                Assets.instance.towers.tower[0]);
//        Tower tower2 = new Tower(4.5f * tileWidth, 4.5f * tileHeight,
//                Assets.instance.towers.tower[1]);
//        Tower tower3 = new Tower(6.5f * tileWidth, 2.5f * tileHeight,
//                Assets.instance.towers.tower[2]);
//        Tower tower4 = new Tower(10.5f * tileWidth, 4.5f * tileHeight,
//                Assets.instance.towers.tower[3]);
//        Tower tower5 = new Tower(12.5f * tileWidth, 6.5f * tileHeight,
//                Assets.instance.towers.tower[4]);
//        Tower tower6 = new Tower(12.5f * tileWidth, 2.5f * tileHeight,
//                Assets.instance.towers.tower[5]);
//        Tower tower7 = new Tower(2.5f * tileWidth, 4.5f * tileHeight,
//                Assets.instance.towers.tower[6]);
//        towers.add(tower1);
//        towers.add(tower2);
//        towers.add(tower3);
//        towers.add(tower4);
//        towers.add(tower5);
//        towers.add(tower6);
//        towers.add(tower7);
    }

    private void loadLevelData(TiledMap tiledMap) {
        tileColumns = tiledMap.getProperties().get("width", Integer.class);
        tileRows = tiledMap.getProperties().get("height", Integer.class);
        tileWidth = tiledMap.getProperties().get("tilewidth", Integer.class);
        tileHeight = tiledMap.getProperties().get("tileheight", Integer.class);

        loadWayPoints(tiledMap.getProperties().get("waypoints", String.class));
    }

    private void loadWayPoints(String pathStr) {
        String[] pathWaypoints = pathStr.split("\\|");

        for(String waypoint : pathWaypoints) {
            String[] coordinates = waypoint.split(",");
            if (coordinates.length >= 2) {
                this.wayponts.add(new Vector2(
                        Float.parseFloat(coordinates[0]) * tileWidth + tileWidth / 2,
                        Float.parseFloat(coordinates[1]) * tileHeight + tileWidth / 2));
                Gdx.app.debug(TAG, "Waypoint(" + wayponts.get(wayponts.size - 1).toString() + ") added");
            }
        }

        if (wayponts.size > 0) {
            startPosition = wayponts.first();
        }
    }

    public void spawnCreep() {
        Creep creep = new Creep(startPosition.x, startPosition.y,
                Assets.instance.creep.creep1blue);
        creep.setWaypoints(wayponts);
        creeps.add(creep);
    }

    public void buildTower(int x, int y) {
        Tower tower = new Tower((x + 0.5f) * tileWidth, (y + 0.5f) * tileHeight,
                Assets.instance.towers.tower[0]);
        towers.add(tower);
    }

    public void handleTouch(int mapX, int mapY) {
        int col = mapX / tileWidth;
        int row = mapY / tileHeight;

        if(tiles[row][col].buildable) {
            buildTower(col, row);
        } else {
            spawnCreep();
        }
    }

    public void update(float deltaTime) {
        updateCreeps(deltaTime);
        updateProjectiles(deltaTime);
        updateTowers(deltaTime);

//        Gdx.app.debug(TAG, "Money: " + money + ", Score: " + score + ", Lives: " + lives);
    }

    private void updateCreeps(float deltaTime) {
        for (Iterator<AbstractCreep> creepIterator = creeps.iterator(); creepIterator.hasNext();) {
            AbstractCreep creep = creepIterator.next();
            if (creep.isVisible()) {
                creep.update(deltaTime);
            } else {
                if (creep.isDead()) {
                    money += creep.getReward();
                    score += creep.getReward();
                } else {
                    lives--;
                }
                creepIterator.remove();
            }
        }
    }

    private void updateProjectiles(float deltaTime) {
        for (Iterator<AbstractProjectile> projectileIterator = projectiles.iterator(); projectileIterator.hasNext();) {
            AbstractProjectile projectile = projectileIterator.next();
            if (projectile.isVisible()) {
                projectile.update(deltaTime);
            } else {
                projectileIterator.remove();
            }
        }
    }

    private void updateTowers(float deltaTime) {
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
            if(tower.isReadyToShoot() && tower.hasTarget()) {
                projectiles.add(tower.shoot());
            }
        }
    }

    public int getWidth() {
        return tileColumns * tileWidth;
    }

    public int getHeight() {
        return tileRows * tileHeight;
    }

    public int getLives() {
        return lives;
    }

    public int getMoney() {
        return money;
    }

    public int getScore() {
        return score;
    }

    public void render(Batch batch) {
        batch.begin();

        for (AbstractCreep creep : creeps) {
            creep.render(batch);
        }
        for (AbstractTower tower : towers) {
            tower.render(batch);
        }
        for (AbstractProjectile projectile : projectiles) {
            projectile.render(batch);
        }

        batch.end();
    }

    @Override
    public void dispose() {
    }
}
