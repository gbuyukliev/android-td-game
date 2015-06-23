package bg.ittalents.tower_defense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;

import bg.ittalents.tower_defense.game.objects.Assets;
import bg.ittalents.tower_defense.game.objects.Background;

public class WorldRenderer implements Disposable {
    public static final float VIEWPORT = 350f;
    public static final float SCALE = Gdx.graphics.getHeight() / VIEWPORT;

    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Background background;

    private Level level;
    private OrthographicCamera camera;
    private OrthographicCamera cameraGUI;
    private WorldController worldController;
    private Batch batch;

    public Level getLevel() {
        return level;
    }

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        batch = new SpriteBatch();
        tiledMap = new TmxMapLoader().load("levels/level1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, batch);

        level = new Level(tiledMap);

        background = new Background(level.getWidth(), level.getHeight());
        init();
    }

    private void init() {
        camera = new OrthographicCamera(WorldRenderer.VIEWPORT,
                WorldRenderer.VIEWPORT);
        camera.position.set(0, 0, 0);
        camera.update();

        cameraGUI = new OrthographicCamera(WorldRenderer.VIEWPORT,
                WorldRenderer.VIEWPORT);
        cameraGUI.position.set(0, 0, 0);
        cameraGUI.setToOrtho(true); // flip y-axis
        cameraGUI.update();
    }

    public void render() {
//        Gdx.graphics.setTitle("Tower Defense -- FPS: " + Gdx.graphics.getFramesPerSecond());
        renderWorld();
        renderGui();
    }

    private void renderWorld() {
        worldController.cameraHelper.applyTo(camera);
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

        float y = 5;

        fpsFont.draw(batch, "Money: " + level.getMoney(), 5, y);
        fpsFont.draw(batch, "Score: " + level.getScore(), screenWidth / 2 - SCALE * 30, y);
        fpsFont.draw(batch, "Lives: " + level.getLives(), screenWidth - SCALE * 60, y);
    }

    private void renderGuiFpsCounter() {
        int fps = Gdx.graphics.getFramesPerSecond();
        BitmapFont fpsFont = Assets.instance.fonts.defaultFont;

        float x = cameraGUI.viewportWidth - SCALE * 45;
        float y = cameraGUI.viewportHeight - SCALE * 10;
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

    public void resize(int width, int height) {
        camera.viewportWidth = (WorldRenderer.VIEWPORT / height) *
                width;
        camera.update();
        worldController.updateScale();
    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
        tiledMap.dispose();
        level.dispose();
    }
}
