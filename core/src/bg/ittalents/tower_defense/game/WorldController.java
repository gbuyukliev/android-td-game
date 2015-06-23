package bg.ittalents.tower_defense.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import bg.ittalents.tower_defense.utils.CameraHelper;

public class WorldController extends InputAdapter implements Disposable {

    private Level level;
    CameraHelper cameraHelper;
    private Game game;

    private float scale;
    private int lastPointerPositionX;
    private int lastPointerPositionY;
    private int touchedPositionX;
    private int touchedPositionY;

    private WorldRenderer worldRenderer;


    public WorldController(Game game) {
        Gdx.input.setInputProcessor(this);
        worldRenderer = new WorldRenderer(this);
        level = worldRenderer.getLevel();
        cameraHelper = new CameraHelper(new Vector2(level.getWidth() / 2, level.getHeight() / 2));
        this.game = game;
        updateScale();
    }

    public void update(float deltaTime) {
        level.update(deltaTime);
    }

    public void updateScale() {
        scale = WorldRenderer.VIEWPORT / Gdx.graphics.getHeight();
    }

    private void moveCamera(float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        float viewportWidth = WorldRenderer.VIEWPORT * Gdx.graphics.getWidth() / Gdx.graphics.getHeight();

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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        lastPointerPositionX = screenX;
        lastPointerPositionY = screenY;

        touchedPositionX = screenX;
        touchedPositionY = screenY;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (screenX == touchedPositionX && screenY == touchedPositionY) {
//            level.spawnCreep();
            worldRenderer.handleTouch(screenX, screenY);
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        int dX = lastPointerPositionX - screenX;
        int dY = screenY - lastPointerPositionY;
        moveCamera(dX * scale, dY * scale);
        lastPointerPositionX = screenX;
        lastPointerPositionY = screenY;
        return true;
    }


    public float getScale() {
        return scale;
    }

    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        worldRenderer.render();
    }

    public void resize(int width, int height) {
        worldRenderer.resize(width, height);
    }

    @Override
    public void dispose() {
        worldRenderer.dispose();
    }
}
