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

import bg.ittalents.tower_defense.game.objects.Background;
import bg.ittalents.tower_defense.utils.CameraHelper;

public class WorldRenderer implements Disposable {
    public static final float VIEWPORT = 350f;
    private float scale;
    private float aspectRatio;

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Background background;

    private Level level;
    private OrthographicCamera camera;
    private OrthographicCamera cameraGUI;
    private CameraHelper cameraHelper;

    private Gui gui;
    private WorldController worldController;
    private Batch batch;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        batch = new SpriteBatch();

        tiledMap = new TmxMapLoader().load("levels/level1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, batch);

        aspectRatio = Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        level = new Level(tiledMap);
        gui = new Gui(level, aspectRatio, batch);
        background = new Background(level.getWidth(), level.getHeight());
        init();
    }

    private void init() {
        camera = new OrthographicCamera(WorldRenderer.VIEWPORT * aspectRatio,
                WorldRenderer.VIEWPORT);
        camera.position.set(0, 0, 0);
        camera.update();

        cameraGUI = new OrthographicCamera(WorldRenderer.VIEWPORT * aspectRatio,
                WorldRenderer.VIEWPORT);
        cameraGUI.position.set(0, 0, 0);
        cameraGUI.setToOrtho(true); // flip y-axis
        cameraGUI.update();

        cameraHelper = new CameraHelper(new Vector2(camera.viewportWidth / 2, camera.viewportHeight / 2));
    }

    public void render() {
//        Gdx.graphics.setTitle("Tower Defense -- FPS: " + Gdx.graphics.getFramesPerSecond());
        renderWorld();
        renderGui();
        gui.render(batch);
    }

    public void moveCamera(float x, float y) {
        final float LEVEL_BORDER = 20f;
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        float viewportWidth = WorldRenderer.VIEWPORT * aspectRatio;

        if (x < (viewportWidth / 2) - LEVEL_BORDER) {
            x = (viewportWidth / 2) - LEVEL_BORDER;
        } else if (x > level.getWidth() - (viewportWidth / 2) + LEVEL_BORDER) {
            x = (level.getWidth() - (viewportWidth / 2) + LEVEL_BORDER);
        }

        if (y < (WorldRenderer.VIEWPORT / 2) - LEVEL_BORDER) {
            y = (WorldRenderer.VIEWPORT / 2) - LEVEL_BORDER;
        } else if (y > level.getHeight() - (WorldRenderer.VIEWPORT / 2) + LEVEL_BORDER) {
            y = (level.getHeight() - (WorldRenderer.VIEWPORT / 2) + LEVEL_BORDER);
        }

        cameraHelper.setPosition(x, y);
    }

    private void renderWorld() {
        cameraHelper.applyTo(camera);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        background.render(batch);
        batch.end();

        mapRenderer.setView(camera);
        mapRenderer.render();

        level.render(batch);
    }

    private void renderGui() {
        batch.setProjectionMatrix(cameraGUI.combined);
        batch.begin();
        renderGuiFpsCounter();
        renderGuiScore();
        batch.end();
    }


    private void renderGuiScore() {
        BitmapFont fpsFont = Assets.instance.fonts.defaultFont;

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        float y = 5;

        fpsFont.draw(batch, "Wave: " + level.getCurrentWave(), 5, y);
        fpsFont.draw(batch, "Money: " + level.getMoney(), screenWidth / 2 - 120 / scale, y);
        fpsFont.draw(batch, "Score: " + level.getScore(), screenWidth / 2 + 80 / scale, y);
        fpsFont.draw(batch, "Lives: " + level.getLives(), screenWidth - 60 / scale, y);

        if (level.isTriggerCountTime()) {
            fpsFont.draw(batch, "TIME TILL NEXT WAVE: " + (int) (Level.TIME_TILL_NEXT_WAVE - level.getTimeSinceLastWave()), screenWidth / 2 - 50 / scale, screenHeight / 2);
        }
    }

    public InputProcessor getInputProcessor() {
        return gui.getInputProcessor();
    }

    private void renderGuiFpsCounter() {
        int fps = Gdx.graphics.getFramesPerSecond();
        BitmapFont fpsFont = Assets.instance.fonts.defaultFont;

        float x = cameraGUI.viewportWidth - 45 / scale;
        float y = cameraGUI.viewportHeight - 10 / scale;
        if (fps >= 45) {
            // 45 or more FPS show up in green
            fpsFont.setColor(0, 1, 0, 1);
        } else if (fps >= 30) {
            // 30 or more FPS show up in yellow
            fpsFont.setColor(1, 1, 0, 1);
        } else {
            // less than 30 FPS show up in red
            fpsFont.setColor(1, 0, 0, 1);
        }

        fpsFont.draw(batch, "FPS: " + fps, x, y);
        fpsFont.setColor(1, 1, 1, 1); // white
    }

    public void handleTouch(int screenX, int screenY) {
        int mapX = (int) (screenX * scale + camera.position.x - (camera.viewportWidth / 2));
        int mapY = (int) ((Gdx.graphics.getHeight() - screenY) * scale +
                camera.position.y - (camera.viewportHeight / 2));

        level.handleTouch(mapX, mapY);

//        Gdx.app.debug("Touch coordinates", x + ", " + y);
//        Gdx.app.debug("Level size", level.getWidth() + ", " + level.getHeight());
//        Gdx.app.debug("Graphics size", Gdx.graphics.getWidth() + ", " + Gdx.graphics.getHeight());
//        Gdx.app.debug("Camera size", camera.viewportWidth + ", " + camera.viewportHeight);
//        Gdx.app.debug("Camera position", camera.position.toString());
    }

    public void resize(int width, int height) {
        aspectRatio = width / (float) height;
        gui.setAspectRatio(aspectRatio);
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
    }

    public Level getLevel() {
        return level;
    }
}
