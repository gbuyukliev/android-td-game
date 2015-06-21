package bg.ittalents.tower_defense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

import java.util.Iterator;

import bg.ittalents.tower_defense.game.objects.AbstractCreep;
import bg.ittalents.tower_defense.game.objects.AbstractProjectile;
import bg.ittalents.tower_defense.game.objects.AbstractTower;
import bg.ittalents.tower_defense.game.objects.Assets;
import bg.ittalents.tower_defense.game.objects.Background;
import bg.ittalents.tower_defense.game.objects.Creep;
import bg.ittalents.tower_defense.game.objects.Tower;

public class Level implements Disposable {

    public static final String TAG = Level.class.getName();

    private int tileRows;
    private int tileColumns;
    private int tileWidth;
    private int tileHeight;
    Vector2 startPosition;

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Background background;

    private Array<AbstractTower> towers;
    private Array<AbstractCreep> creeps;
    private Array<AbstractProjectile> projectiles;
    private Array<Vector2> wayponts;

    private Batch batch;

    public Level(String fileName) {

        towers = new Array<AbstractTower>();
        creeps = new Array<AbstractCreep>();
        wayponts = new Array<Vector2>();
        projectiles = new Array<AbstractProjectile>();

        startPosition = new Vector2();
        init(fileName);
        background = new Background(getWidth(), getHeight());
    }

    private void init(String fileName) {
        load(fileName);
        loadWayPoints(fileName);

        Tower tower1 = new Tower(2.5f * tileWidth, 6.5f * tileHeight,
                Assets.instance.towers.tower[0]);
        Tower tower2 = new Tower(4.5f * tileWidth, 4.5f * tileHeight,
                Assets.instance.towers.tower[1]);
        Tower tower3 = new Tower(6.5f * tileWidth, 2.5f * tileHeight,
                Assets.instance.towers.tower[2]);
        Tower tower4 = new Tower(10.5f * tileWidth, 4.5f * tileHeight,
                Assets.instance.towers.tower[3]);
        Tower tower5 = new Tower(12.5f * tileWidth, 6.5f * tileHeight,
                Assets.instance.towers.tower[4]);
        Tower tower6 = new Tower(12.5f * tileWidth, 2.5f * tileHeight,
                Assets.instance.towers.tower[5]);
        Tower tower7 = new Tower(2.5f * tileWidth, 4.5f * tileHeight,
                Assets.instance.towers.tower[6]);
        towers.add(tower1);
        towers.add(tower2);
        towers.add(tower3);
        towers.add(tower4);
        towers.add(tower5);
        towers.add(tower6);
        towers.add(tower7);
    }


    private void load(String fileName) {
        tiledMap = new TmxMapLoader().load(fileName);
        tileColumns = tiledMap.getProperties().get("width", Integer.class);
        tileRows = tiledMap.getProperties().get("height", Integer.class);
        tileWidth = tiledMap.getProperties().get("tilewidth", Integer.class);
        tileHeight = tiledMap.getProperties().get("tileheight", Integer.class);
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        batch = mapRenderer.getBatch();

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

    public void update(float deltaTime) {
        for (Iterator<AbstractCreep> creepIterator = creeps.iterator(); creepIterator.hasNext();) {
            AbstractCreep creep = creepIterator.next();
            if (creep.isVisible()) {
                creep.update(deltaTime);
            } else {
                creepIterator.remove();
            }
        }

        for (Iterator<AbstractProjectile> projectileIterator = projectiles.iterator(); projectileIterator.hasNext();) {
            AbstractProjectile projectile = projectileIterator.next();
            if (projectile.isVisible()) {
                projectile.update(deltaTime);
            } else {
                projectileIterator.remove();
            }
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
            if(tower.isReady() && tower.hasTarget()) {
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

    public void render(OrthographicCamera camera) {

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        background.render(batch);

        batch.end();
        mapRenderer.setView(camera);
        mapRenderer.render();
        batch.begin();

        for (AbstractCreep creep : creeps) {
            if(creep.isVisible()) {
                creep.render(batch);
            }
        }
        for (AbstractTower tower : towers) {
            tower.render(batch);
        }
        for (AbstractProjectile projectile : projectiles) {
            if(projectile.isVisible()) {
                projectile.render(batch);
            }
        }
        batch.end();
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
        tiledMap.dispose();
    }
}
