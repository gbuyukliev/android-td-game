package bg.ittalents.tower_defense.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;

import bg.ittalents.tower_defense.utils.CameraHelper;
import bg.ittalents.tower_defense.utils.Constants;

public class WorldController extends InputAdapter {

    public Level level;
    public CameraHelper cameraHelper;
    Game game;

    private float scale;
    private int lastPointerPositionX;
    private int lastPointerPositionY;
    private int touchedPositionX;
    private int touchedPositionY;

    public WorldController(Game game) {
        Gdx.input.setInputProcessor(this);
        level = new Level("levels/basic");
        cameraHelper = new CameraHelper(new Vector2(level.getWidth() / 2, level.getHeight() / 2));
        this.game = game;
        updateScale();
    }

    public void update(float deltaTime) {
        level.update(deltaTime);
    }

    public void updateScale() {
        scale = Constants.VIEWPORT_HEIGHT / Gdx.graphics.getHeight();
    }

    private void moveCamera(float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        float viewportWidth = Constants.VIEWPORT_HEIGHT * Gdx.graphics.getWidth() / Gdx.graphics.getHeight();

        if (x < (viewportWidth / 2)) {
            x = (viewportWidth / 2);
        } else if (x > level.getWidth() - (viewportWidth / 2)) {
            x = (level.getWidth() - (viewportWidth / 2));
        }

        if (y < (Constants.VIEWPORT_HEIGHT / 2)) {
            y = (Constants.VIEWPORT_HEIGHT / 2);
        } else if (y > level.getHeight() - (Constants.VIEWPORT_HEIGHT / 2)) {
            y = (level.getHeight() - (Constants.VIEWPORT_HEIGHT / 2));
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
            level.spawnCreep();
        }
        return super.touchUp(screenX, screenY, pointer, button);
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
}
