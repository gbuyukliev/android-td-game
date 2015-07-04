package bg.ittalents.tower_defense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
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
import bg.ittalents.tower_defense.game.objects.CreepSlow;
import bg.ittalents.tower_defense.game.objects.Tower;
import bg.ittalents.tower_defense.game.objects.Wave;
import bg.ittalents.tower_defense.game.ui.Gui;
import bg.ittalents.tower_defense.network.Network;
import bg.ittalents.tower_defense.network.UserInfo;

public class Level implements Disposable {

    public static final int STARTING_LIVES = 30;
    public static final int STARTING_MONEY = 210;
    public static final int MONEY_FOR_COMPLETING_LEVEL = 50;

    private static float waveCoeff = 1f;
    private static float fasterSpawn = 0f;

    private static final int DEFAULT_CURRENT_CREEP = 1;
    public static final float TIME_TILL_NEXT_WAVE = 10f;
    public static final String BUILDABLE_LAYER = "buildable";
    public static final String TAG = Level.class.getName();
    public static final String WAYPOINTS = "waypoints";

    //    private INetwork offline;
    private int lives, money, score, currentWave, currentCreep, currentTowerPrice;
    private float timeSinceSpawn, timeSinceLastWave, textTime;
    private boolean triggerCountTime;
    private boolean isClicked;
    private boolean isPaused;
    private Wave wave;
    private ShapeRenderer shapeRenderer;

    private Gui gui;

    private int tileRows, tileColumns, tileWidth, tileHeight;
//    Vector2 startPosition;

    private int colTower, rowTower;

    private Array<AbstractTower> towers;
    private Array<AbstractCreep> creeps;
    private Array<AbstractProjectile> projectiles;
    private Array<Array<Vector2>> waypoints;
    private int pathCount;

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
        //can use static Network class, to get instance
        Network.getInstance().getLevelData(UserInfo.getUserName(), UserInfo.getLevel());

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
        isPaused = false;
        isClicked = false;

        towers = new Array<AbstractTower>();
        creeps = new Array<AbstractCreep>();
        waypoints = new Array<Array<Vector2>>();
        projectiles = new Array<AbstractProjectile>();

        loadLevelData(tiledMap);
        initTiles(tiledMap);

    }

    private void initTiles(TiledMap tiledMap) {
        tiles = new Tile[tileRows][tileColumns];
        TiledMapTileLayer mapLayer = (TiledMapTileLayer) tiledMap.getLayers().get(BUILDABLE_LAYER);

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

        int counter = 1;
        String pathStr;
        while ((pathStr = tiledMap.getProperties().get(WAYPOINTS + counter, String.class)) != null) {
            Gdx.app.debug("Path " + counter, pathStr);
            loadWayPoints(pathStr);
            counter++;
        }
        pathCount = counter - 1;
    }

    private void loadWayPoints(String pathStr) {
        Array<Vector2> tempPath = new Array<Vector2>();
        String[] pathWaypoints = pathStr.split("\\|");

        for(String waypoint : pathWaypoints) {
            String[] coordinates = waypoint.split(",");
            if (coordinates.length >= 2) {
                tempPath.add(new Vector2(
                        Float.parseFloat(coordinates[0]) * tileWidth + tileWidth / 2,
                        Float.parseFloat(coordinates[1]) * tileHeight + tileWidth / 2));
                Gdx.app.debug(TAG, "Waypoint(" + tempPath.get(tempPath.size - 1).toString() + ") added");
            }
        }

        this.waypoints.add(tempPath);
    }

    public void spawnCreep() {
        AbstractCreep creep;

        int pathIndex = currentWave % pathCount;
        Array<Vector2> currentPath = this.waypoints.get(pathIndex);

        if (wave.getTypeOfCreeps().equals("boss")) {
            creep = new CreepBoss(currentPath.first().x, currentPath.first().y,
                    Assets.instance.getCreep(Assets.CREEP_BOSS));
        } else if (wave.getTypeOfCreeps().equals("slow")) {
            creep = new CreepSlow(currentPath.first().x, currentPath.first().y,
                    Assets.instance.getCreep(Assets.CREEP_GREEN_2));
        } else if (wave.getTypeOfCreeps().equals("flying")) {
            creep = new CreepFlying(currentPath.first().x, currentPath.first().y,
                    Assets.instance.getCreep(Assets.CREEP_YELLOW_3));
        } else {
            creep = new CreepBasic(currentPath.first().x, currentPath.first().y,
                    Assets.instance.getCreep(Assets.CREEP_BLUE_1));
        }

        creep.setWaypoints(currentPath);
        creeps.add(creep);
    }

    public void buildTower(int col, int row) {
        Tower tower = new Tower((col + 0.5f) * tileWidth, (row + 0.5f) * tileHeight,
                Assets.instance.getTower(Assets.TOWER_TYPE_1), this);

        if (money >= tower.getPrice()) {
            currentTowerPrice = tower.getPrice();
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
        colTower = mapX / tileWidth;
        rowTower = mapY / tileHeight;

        if (colTower >= 0 && colTower < tileWidth && rowTower >= 0 && rowTower < tileHeight) {
            if (tiles[rowTower][colTower].isBuildable()) {
                isClicked = true;

                if (tiles[rowTower][colTower].getTower() != null) {
                    if (!tiles[rowTower][colTower].getTower().isUpgradable()) {
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
        if (!isPaused) {
            updateWave(deltaTime);
            updateCreeps(deltaTime);
            updateProjectiles(deltaTime);
            updateTowers(deltaTime);
        }

        updateWarningText(deltaTime);
    }

    private void updateWarningText(float deltaTime) {
        textTime += deltaTime;

        if (textTime > 3) {
            gui.getWarningTextField().setVisible(false);
        }
    }

    private void spawnCreepInWave() {
        spawnCreep();
        currentCreep++;
        timeSinceSpawn = 0;
    }

    public void spawnWave() {
        currentWave++;
        wave = new Wave(currentWave);

        currentCreep = DEFAULT_CURRENT_CREEP;
        money += MONEY_FOR_COMPLETING_LEVEL;

        if(wave.getNumber() != 1 && wave.getNumber() % 10 == 1) {
            waveCoeff += 0.1f;
            setFasterSpawn(getFasterSpawn() + 0.04f);
        }

        spawnCreepInWave();
    }

    private void updateWave(float deltaTime) {
        timeSinceSpawn += deltaTime;
        timeSinceLastWave += deltaTime;

        if (!isTriggerCountTime() && creeps.size == 0 && (currentCreep > wave.getNumOfCreeps() || wave.getNumber() == 0)) {
            timeSinceLastWave = 0;
            triggerCountTime = true;
        }

        if (timeSinceLastWave > TIME_TILL_NEXT_WAVE && creeps.size == 0 && (wave.getNumber() == 0 || currentCreep > wave.getNumOfCreeps())) {
            spawnWave();
            triggerCountTime = false;
        }

        if (currentCreep <= wave.getNumOfCreeps() && !isTriggerCountTime()) {
            if (wave.getTypeOfCreeps().equals("slow") && timeSinceSpawn > 1.5f - fasterSpawn) {
                spawnCreepInWave();
            } else if (!wave.getTypeOfCreeps().equals("slow") && timeSinceSpawn > 1f - fasterSpawn) {
                spawnCreepInWave();
            }
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

    private void setShapeRenderer(OrthographicCamera camera) {
        if (isClicked()) {
            AbstractTower tower = tiles[rowTower][colTower].getTower();
            int padding = 10;

            camera.update();
            shapeRenderer.setProjectionMatrix(camera.combined);

            if (tower != null) {
                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                shapeRenderer.setColor(0, 1, 0, 0.15f);
                shapeRenderer.circle(colTower * tileWidth + tileWidth / 2, rowTower * tileHeight + tileHeight / 2, tower.getRange());
                shapeRenderer.end();

                Gdx.gl.glDisable(GL20.GL_BLEND);
            } else {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(0, 1, 0, 1);
                shapeRenderer.rect(colTower * tileWidth + padding / 2, rowTower * tileHeight + padding / 2, tileWidth - padding, tileHeight - padding);
                shapeRenderer.end();
            }
        }

        if (isPaused) {
            int width, height;

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(1, 1, 1, 0.20f);

            if (Gdx.graphics.getWidth() > getWidth()) {
                width = Gdx.graphics.getWidth();
            } else {
                width = getWidth();
            }

            if (Gdx.graphics.getHeight() > getHeight()) {
                height = Gdx.graphics.getHeight();
            } else {
                height = getHeight();
            }

            shapeRenderer.rect(0, 0, width, height);
            shapeRenderer.end();

            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
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

        setShapeRenderer(camera);
    }

    @Override
    public void dispose() {
//        if (!shapeRenderer.isDrawing()) {
//            shapeRenderer.dispose();
//        }
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

    public void setTriggerCountTime(boolean triggerCountTime) {
        this.triggerCountTime = triggerCountTime;
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

    public void setIsClicked(boolean isClicked) {
        this.isClicked = isClicked;
    }

    public float getTextTime() {
        return textTime;
    }

    public void setTextTime(float textTime) {
        if (textTime >= 0) {
            this.textTime = textTime;
        }
    }

    public boolean isClicked() {
        return isClicked;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setIsPaused(boolean isPaused) {
        this.isPaused = isPaused;
    }

    public Gui getGui() {
        return gui;
    }

    public int getCurrentTowerPrice() {
        return currentTowerPrice;
    }

    public static void setCoeff(float newWaveCoeff) {
        if (newWaveCoeff > 0) {
            waveCoeff = newWaveCoeff;
        }
    }

    public static float getCoeff() {
        return waveCoeff;
    }

    public static float getFasterSpawn() {
        return fasterSpawn;
    }

    public static void setFasterSpawn(float fasterSpawn) {
        if (fasterSpawn > 0 && fasterSpawn <= 0.7f) {
            Level.fasterSpawn = fasterSpawn;
        }
    }
}