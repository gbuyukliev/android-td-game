package bg.ittalents.tower_defense.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import bg.ittalents.tower_defense.game.WorldController;
import bg.ittalents.tower_defense.game.WorldRenderer;

public class GameScreen extends AbstractGameScreen {

    private WorldController worldController;
    private boolean paused;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        worldController = new WorldController(getGame());
    }

    @Override
    public void render(float deltaTime) {
        // Do not update game world when paused.
        if (!paused && deltaTime < 1f) {
            // Update game world by the time that has passed
            // since last rendered frame.
            worldController.update(deltaTime);
            worldController.render();
        }
    }

    @Override
    public void resize(int width, int height) {
        worldController.resize(width, height);
    }

    @Override
    public void pause() {
        paused = true;
    }

    @Override
    public void resume() {
        super.resume();
        // Only called on Android!
        paused = false;
    }

    @Override
    public void hide() {
        worldController.dispose();
        Gdx.input.setCatchBackKey(false);
    }
}
