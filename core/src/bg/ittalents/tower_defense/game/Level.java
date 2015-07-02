package bg.ittalents.tower_defense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import java.util.Iterator;

import bg.ittalents.tower_defense.game.objects.AbstractCreep;
import bg.ittalents.tower_defense.game.objects.AbstractProjectile;
import bg.ittalents.tower_defense.game.objects.AbstractTower;
import bg.ittalents.tower_defense.game.objects.CreepBasic;
import bg.ittalents.tower_defense.game.objects.CreepBoss;
import bg.ittalents.tower_defense.game.objects.CreepFlying;
import bg.ittalents.tower_defense.game.objects.Tower;
import bg.ittalents.tower_defense.game.objects.Wave;
<<<<<<< HEAD
+import bg.ittalents.tower_defense.network.INetwork;
+import bg.ittalents.tower_defense.network.Network;
=======
import bg.ittalents.tower_defense.network.INetwork;
import bg.ittalents.tower_defense.network.Network;
>>>>>>> fe4beaccdf82eca7ab5e05dd63f59d90fe2b2389

public class Level implements Disposable {

    public static final int STARTING_LIVES = 70;
    public static final int STARTING_MONEY = 200;

    public static final String TAG = Level.class.getName();

    private static final int DEFAULT_CURRENT_CREEP = 1;
    public static final float TIME_TILL_NEXT_WAVE = 10f;

    private INetwork offline;
    private int lives, money, score, currentWave, currentCreep;
    private float timeSinceSpawn, timeSinceLastWave;
    private boolean triggerCountTime, isClicked;
    private Wave wave;
    private ShapeRenderer shapeRenderer;

    private Gui gui;

    private int tileRows, tileColumns, tileWidth, tileHeight;
    Vector2 startPosition;

    private int colTower, rowTower;

    private Array<AbstractTower> towers;
    private Array<AbstractCreep> creeps;
    private Array<AbstractProjectile> projectiles;
    private Array<Vector2> waypoints;

    private Tile[][] tiles;

    public class Tile {
        private boolean buildable;
        private AbstractTower tower;

        Tile() {
            setBuildable(false);
            tower = null;
        }

        public void removeTower() {
            tower = null;
        }

        public AbstractTower getTower() {
            return tower;
        }

        public boolean isBuildable() {
            return buildable;
        }

        public void setBuildable(boolean buildable) {
            this.buildable = buildable;
        }
    }



//    private Batch batch;

    public Level(TiledMap tiledMap, Gui gui) {
<<<<<<< HEAD
        offline = new Network.getInstance();
=======
        //can use static Network class, to get instance
        offline = Network.getInstance();
>>>>>>> fe4beaccdf82eca7ab5e05dd63f59d90fe2b2389
        this.gui = gui;
        shapeRenderer = new ShapeRenderer();
        money = STARTING_MONEY;
        lives = STARTING_LIVES;
        wave = new Wave();
        currentWave = wave.getNumber();
        currentCreep = DEFAULT_CURRENT_CREEP;
        timeSinceSpawn = 0;
        timeSinceLastWave = 0;
        triggerCountTime = false;
        setIsClicked(false);

        towers = new Array<AbstractTower>();
        creeps = new Array<AbstractCreep>();
        waypoints = new Array<Vector2>();
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
                getTiles()[row][col] = new Tile();
                if (mapLayer.getCell(col, row) != null &&
                        mapLayer.getCell(col, row).getTile() != null) {
                    getTiles()[row][col].setBuildable(true);
                }
            }
        }
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
                this.waypoints.add(new Vector2(
                        Float.parseFloat(coordinates[0]) * tileWidth + tileWidth / 2,
                        Float.parseFloat(coordinates[1]) * tileHeight + tileWidth / 2));
                Gdx.app.debug(TAG, "Waypoint(" + waypoints.get(waypoints.size - 1).toString() + ") added");
            }
        }

        if (waypoints.size > 0) {
            startPosition = waypoints.first();
        }
    }

    public void spawnCreep() {
        AbstractCreep creep;

        if (wave.getTypeOfCreeps().equals("boss")) {
            creep = new CreepBoss(startPosition.x, startPosition.y,
                    Assets.instance.creep.get("red1"));
        } else if (wave.getTypeOfCreeps().equals("flying")) {
            creep = new CreepFlying(startPosition.x, startPosition.y,
                    Assets.instance.creep.get("yellow1"));
        } else {
            creep = new CreepBasic(startPosition.x, startPosition.y,
                    Assets.instance.creep.get("blue1"));
        }

        creep.setWaypoints(waypoints);
        creeps.add(creep);
    }

    public void buildTower(int col, int row) {
        Tower tower = new Tower((col + 0.5f) * tileWidth, (row + 0.5f) * tileHeight,
                Assets.instance.towers.tower[0], this);

        if (money >= tower.getPrice()) {
            towers.add(tower);
            getTiles()[row][col].tower = tower;
            money -= tower.getPrice();
        }
    }

    public void sellTower(AbstractTower tower) {
        towers.removeValue(tower, true);
        money += tower.getMoneySpent() / 2;
    }

    public void handleTouch(int mapX, int mapY) {
        int col = mapX / tileWidth;
        int row = mapY / tileHeight;

        if (col >= 0 && col < tileWidth && row >= 0 && row < tileHeight) {
            if (tiles[row][col].isBuildable()) {
                colTower = col;
                rowTower = row;
                isClicked = true;

                if (tiles[row][col].getTower() != null) {
                    if (!tiles[row][col].getTower().isUpgradable()) {
                        gui.getUpgradeTowerButton().setDisabled(true);
                    } else {
                        gui.getUpgradeTowerButton().setDisabled(false);
                    }

                    gui.getTowerTable().setVisible(true);
                    gui.getNoTowerTable().setVisible(false);
                } else {
                    gui.getNoTowerTable().setVisible(true);
                    gui.getTowerTable().setVisible(false);
                }
            } else {
                gui.getTowerTable().setVisible(false);
                gui.getNoTowerTable().setVisible(false);
                isClicked = false;
            }
        }
    }

    public void update(float deltaTime) {
        updateWave(deltaTime);
        updateCreeps(deltaTime);
        updateProjectiles(deltaTime);
        updateTowers(deltaTime);

//        Gdx.app.debug(TAG, "Money: " + money + ", Score: " + score + ", Lives: " + lives);
    }

    private void spawnCreepInWave() {
        spawnCreep();
        currentCreep++;
        timeSinceSpawn = 0;
    }

    private void spawnWave() {
        currentWave++;
        wave = new Wave(currentWave);

        currentCreep = DEFAULT_CURRENT_CREEP;

        spawnCreepInWave();
    }

    private void updateWave(float deltaTime) {
        timeSinceSpawn += deltaTime;
        timeSinceLastWave += deltaTime;

        if (creeps.size == 0 && wave.getNumber() != 1 && wave.getNumber() % 10 == 1 && !isTriggerCountTime()) {
            AbstractCreep.setCoeff(AbstractCreep.getCoeff() + 0.5f);
        }

        if (!isTriggerCountTime() && creeps.size == 0 && (currentCreep > wave.getNumOfCreeps() || wave.getNumber() == 0)) {
            timeSinceLastWave = 0;
            triggerCountTime = true;
        }

        if (timeSinceLastWave > TIME_TILL_NEXT_WAVE && creeps.size == 0 && (wave.getNumber() == 0 || currentCreep > wave.getNumOfCreeps())) {
            spawnWave();
            triggerCountTime = false;
        }

        if (currentCreep <= wave.getNumOfCreeps() && timeSinceSpawn > 1f && !isTriggerCountTime()) {
            spawnCreepInWave();
        }
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

    public void setMoney (int money) {
        if (money >= 0) {
            this.money = money;
        }
    }

    public int getMoney() {
        return money;
    }

    public int getScore() {
        return score;
    }

    public boolean isTriggerCountTime() {
        return triggerCountTime;
    }

    public float getTimeSinceLastWave() {
        return timeSinceLastWave;
    }

    public int getCurrentWave() { return currentWave; }

    public int getColTower() {
        return colTower;
    }

    public int getRowTower() {
        return rowTower;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setIsClicked(boolean isClicked) {
        this.isClicked = isClicked;
    }

    public void render(Batch batch, OrthographicCamera camera) {
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

        if (isClicked()) {
            int padding = 10;

            camera.update();
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(0, 1, 0, 1);
            shapeRenderer.rect(colTower * tileWidth + padding / 2, rowTower * tileHeight + padding / 2, tileWidth - padding, tileHeight - padding);
            shapeRenderer.end();
        }
    }

    @Override
    public void dispose() {
    }
}