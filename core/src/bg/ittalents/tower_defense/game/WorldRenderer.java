package bg.ittalents.tower_defense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Disposable;

public class WorldRenderer implements Disposable {
    public static final float VIEWPORT = 350f;

    private OrthographicCamera camera;
    private WorldController worldController;
    private FPSLogger fpsLogger;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
        fpsLogger = new FPSLogger();
    }

    private void init() {
        camera = new OrthographicCamera(WorldRenderer.VIEWPORT,
                WorldRenderer.VIEWPORT);
        camera.position.set(0, 0, 0);
        camera.update();
    }

    public void render() {
        fpsLogger.log();
//        Gdx.graphics.setTitle("Tower Defense -- FPS: " + Gdx.graphics.getFramesPerSecond());
        renderWorld();
    }

    private void renderWorld() {
        worldController.cameraHelper.applyTo(camera);

        worldController.level.render(camera);
    }

    public void resize(int width, int height) {
        camera.viewportWidth = (WorldRenderer.VIEWPORT / height) *
                width;
        camera.update();
        worldController.updateScale();
    }

    @Override
    public void dispose() {
        worldController.level.dispose();
    }
}
