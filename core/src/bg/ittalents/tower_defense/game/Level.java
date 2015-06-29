package bg.ittalents.tower_defense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.Iterator;

import bg.ittalents.tower_defense.game.objects.AbstractCreep;
import bg.ittalents.tower_defense.game.objects.AbstractProjectile;
import bg.ittalents.tower_defense.game.objects.AbstractTower;
import bg.ittalents.tower_defense.game.Assets;
import bg.ittalents.tower_defense.game.objects.CreepBasic;
import bg.ittalents.tower_defense.game.objects.CreepBoss;
import bg.ittalents.tower_defense.game.objects.CreepFlying;
import bg.ittalents.tower_defense.game.objects.Tower;
import bg.ittalents.tower_defense.game.objects.Wave;

public class Level implements Disposable {

    public static final int STARTING_LIVES = 70;
    public static final int STARTING_MONEY = 100;

    public static final String TAG = Level.class.getName();

    private static final int DEFAULT_CURRENT_CREEP = 1;
    public static final float TIME_TILL_NEXT_WAVE = 10f;

    private Gui gui;

    private int lives;
    private int money;
    private int score;
    private int currentWave;
    private int currentCreep;
    private float timeSinceSpawn;
    private float timeSinceLastWave;
    private boolean triggerCountTime;
    private Wave wave;

    private int tileRows;
    private int tileColumns;
    private int tileWidth;
    private int tileHeight;
    Vector2 startPosition;

    private int colTower;
    private int rowTower;

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

    public class Gui {
        private Stage stage;
        private Skin skin;
        private float aspectRatio;
        private Label lblScore;
        private TextButton testButton;

        public Gui(float aspectRatio, Batch batch) {
            setAspectRatio(aspectRatio);
            stage = new Stage(new StretchViewport(WorldRenderer.VIEWPORT * aspectRatio, WorldRenderer.VIEWPORT), batch);

            skin = new Skin(Gdx.files.internal("data/uiskin.json"));
//        BitmapFont bitmapFont = Assets.instance.fonts.defaultFont;

            lblScore = new Label("Test", skin);
            lblScore.setColor(Color.RED);
            lblScore.setPosition(100, 50);
            lblScore.setSize(80, 30);
            lblScore.setAlignment(Align.center);
            stage.addActor(lblScore);

            testButton = new TextButton("Test", skin);
            testButton.setPosition(0, 0);
            testButton.setSize(80, 30);
            testButton.setVisible(false);
            testButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    buildTower(colTower, rowTower);
                    testButton.setVisible(false);
                }
            });
            stage.addActor(testButton);
        }

        public InputProcessor getInputProcessor() {
            return stage;
        }

        public void setAspectRatio(float aspectRatio) { this.aspectRatio = aspectRatio; }

//    public void update(float deltaTime) {
//        stage.act(deltaTime);
//    }

        public void render(Batch batch) {
            stage.act(Gdx.graphics.getDeltaTime());
            stage.draw();
        }
    }

//    private Batch batch;

    public Level(TiledMap tiledMap, float aspectRatio, Batch batch) {
        gui = new Gui(aspectRatio, batch);

        money = STARTING_MONEY;
        lives = STARTING_LIVES;
        wave = new Wave();
        currentWave = wave.getNumber();
        currentCreep = DEFAULT_CURRENT_CREEP;
        timeSinceSpawn = 0;
        timeSinceLastWave = 0;
        triggerCountTime = false;

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
        AbstractCreep creep;

        if (wave.getTypeOfCreeps().equals("boss")) {
            creep = new CreepBoss(startPosition.x, startPosition.y,
                    Assets.instance.creep.creep1red);
        } else if (wave.getTypeOfCreeps().equals("flying")) {
            creep = new CreepFlying(startPosition.x, startPosition.y,
                    Assets.instance.creep.creep1yellow);
        } else {
            creep = new CreepBasic(startPosition.x, startPosition.y,
                    Assets.instance.creep.creep1blue);
        }

        creep.setWaypoints(wayponts);
        creeps.add(creep);
    }

    public void buildTower(int col, int row) {
        Tower tower = new Tower((col + 0.5f) * tileWidth, (row + 0.5f) * tileHeight,
                Assets.instance.towers.tower[0]);
        towers.add(tower);
        tiles[row][col].tower = tower;
    }

    private void makeTowerButton() {

    }

    public void handleTouch(int mapX, int mapY) {
        int col = mapX / tileWidth;
        int row = mapY / tileHeight;

        if (col >= 0 && col < tileWidth && row >= 0 && row < tileHeight) {
            if (tiles[row][col].buildable) {
                if (tiles[row][col].tower != null) {
                    tiles[row][col].tower.upgrade();
                    Gdx.app.debug(TAG, "upgrading tower");
                } else {
                    colTower = col;
                    rowTower = row;
                    getGui().testButton.setVisible(true);
                    Gdx.app.debug(TAG, "" + towers.size);
                }
            } else {
                spawnCreep();
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

    public int getMoney() {
        return money;
    }

    public int getScore() {
        return score;
    }

    public Gui getGui() { return gui; }

    public boolean isTriggerCountTime() {
        return triggerCountTime;
    }

    public float getTimeSinceLastWave() {
        return timeSinceLastWave;
    }

    public int getCurrentWave() { return currentWave; }

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