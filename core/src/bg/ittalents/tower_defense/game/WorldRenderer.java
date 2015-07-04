package bg.ittalents.tower_defense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import bg.ittalents.tower_defense.network.UserInfo;
import bg.ittalents.tower_defense.utils.CameraHelper;

public class WorldRenderer implements Disposable {
    public static final float VIEWPORT = 400f;
    public static final String LEVELS_PATH = "levels/level";
    public static final String LEVELS_EXTENSION = ".tmx";
    private float scale;
    private float aspectRatio;

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;

    private Level level;
    private OrthographicCamera camera;
    private OrthographicCamera cameraGUI;
    private CameraHelper cameraHelper;

    private bg.ittalents.tower_defense.game.ui.Gui gui;
    private WorldController worldController;
    private Batch batch;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        batch = new SpriteBatch();

        int levelNum = UserInfo.getLevel();

        tiledMap = new TmxMapLoader().load(LEVELS_PATH + levelNum + LEVELS_EXTENSION);
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, batch);

        aspectRatio = Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        gui = new bg.ittalents.tower_defense.game.ui.Gui(aspectRatio, batch);
        this.level = new Level(tiledMap, gui);
        gui.setLevel(this.level);
        init();
    }

    private void init() {
        camera = new OrthographicCamera(WorldRenderer.VIEWPORT * aspectRatio,
                WorldRenderer.VIEWPORT);
        camera.position.set(50, 0, 0);
        camera.update();

        cameraGUI = new OrthographicCamera(WorldRenderer.VIEWPORT * aspectRatio,
                WorldRenderer.VIEWPORT);
        cameraGUI.position.set(0, 0, 0);
        cameraGUI.setToOrtho(true); // flip y-axis
        cameraGUI.update();

        cameraHelper = new CameraHelper(new Vector2(level.getWidth() / 2, level.getHeight() / 2));
    }

    public void render() {
//        Gdx.graphics.setTitle("Tower Defense -- FPS: " + Gdx.graphics.getFramesPerSecond());
        renderWorld();
        renderGui();
        gui.render(batch);
    }

    public void moveCamera(float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        float viewportWidth = WorldRenderer.VIEWPORT * aspectRatio;

        if (x < (viewportWidth / 2)) {
            x = (viewportWidth / 2);
        } else if (x > level.getWidth() - (viewportWidth / 2)) {
            x = (level.getWidth() - (viewportWidth / 2));
        }

        if (y < (WorldRenderer.VIEWPORT / 2)) {
            y = (WorldRenderer.VIEWPORT / 2);
        } else if (y > level.getHeight() - (WorldRenderer.VIEWPORT / 2)) {
            y = (level.getHeight() - (WorldRenderer.VIEWPORT / 2));
        }

        cameraHelper.setPosition(x, y);
    }

    private void renderWorld() {
        cameraHelper.applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        mapRenderer.setView(camera);
        mapRenderer.render();
        level.render(batch, camera);
    }

    private void renderGui() {
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();
        renderGuiFpsCounter();
        renderGuiScore();
        batch.end();
    }


    private void renderGuiScore() {
        BitmapFont font = Assets.instance.getFont();

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float y = 5;

        font.draw(batch, "Wave: " + level.getCurrentWave(), 5, y);
        font.draw(batch, "Money: " + level.getMoney(), screenWidth / 2 - 130 / scale, y);
        font.draw(batch, "Score: " + level.getScore(), screenWidth / 2 + 60 / scale, y);
        font.draw(batch, "Lives: " + level.getLives(), screenWidth - 75 / scale, y);

        if (level.isTriggerCountTime()) {
            font.draw(batch, "TIME TILL NEXT WAVE: " + (int) (Level.TIME_TILL_NEXT_WAVE - level.getTimeSinceLastWave()), screenWidth / 2 - 80 / scale, screenHeight / 2);
        }
    }

    public InputProcessor getInputProcessor() {
        return gui.getInputProcessor();
    }

    private void renderGuiFpsCounter() {
        int fps = Gdx.graphics.getFramesPerSecond();
        BitmapFont font = Assets.instance.getFont();

        float x = cameraGUI.viewportWidth / 2 - 30 / scale;
        float y = cameraGUI.viewportHeight - 15 / scale;
        if (fps >= 45) {
            // 45 or more FPS show up in green
            font.setColor(0, 1, 0, 1);
        } else if (fps >= 30) {
            // 30 or more FPS show up in yellow
            font.setColor(1, 1, 0, 1);
        } else {
            // less than 30 FPS show up in red
            font.setColor(1, 0, 0, 1);
        }

        font.draw(batch, "FPS: " + fps, x, y);
        font.setColor(1, 1, 1, 1); // white
    }

    public void handleTouch(float screenX, float screenY) {
        int mapX = (int) (screenX * scale + camera.position.x - (camera.viewportWidth / 2));
        int mapY = (int) ((Gdx.graphics.getHeight() - screenY) * scale +
                camera.position.y - (camera.viewportHeight / 2));

        if (mapX < 0 || mapX > level.getWidth() || mapY < 0 || mapY > level.getHeight()) {
            return;
        }
        else {
            level.handleTouch(mapX, mapY);
        }

//        Gdx.app.debug("Touch coordinates", x + ", " + y);
//        Gdx.app.debug("Level size", level.getWidth() + ", " + level.getHeight());
//        Gdx.app.debug("Graphics size", Gdx.graphics.getWidth() + ", " + Gdx.graphics.getHeight());
//        Gdx.app.debug("Camera size", camera.viewportWidth + ", " + camera.viewportHeight);
//        Gdx.app.debug("Camera position", camera.position.toString());
    }

    public void resize(int width, int height) {
        aspectRatio = width / (float) height;
        gui.resize(width, height);
        Gdx.app.debug("Aspect", "" + aspectRatio);
        camera.viewportWidth = (WorldRenderer.VIEWPORT * aspectRatio);
        camera.update();
        worldController.updateScale();
        scale = worldController.getScale();
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
        tiledMap.dispose();
        level.dispose();
        gui.dispose();
    }

    public Level getLevel() {
        return level;
    }
}