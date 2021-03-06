package bg.ittalents.tower_defense.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.Disposable;

import bg.ittalents.tower_defense.network.Network;
import bg.ittalents.tower_defense.network.UserInfo;
import bg.ittalents.tower_defense.screens.MainScreen;

public class WorldController extends GestureDetector.GestureAdapter implements Disposable {

    private Level level;
//    private Game game;

    private float scale;

    private WorldRenderer worldRenderer;

    public WorldController(Game game) {
        worldRenderer = new WorldRenderer(this, game);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(worldRenderer.getInputProcessor());
        inputMultiplexer.addProcessor(new GestureDetector(this));

        Gdx.input.setInputProcessor(inputMultiplexer);
        level = worldRenderer.getLevel();
//        this.game = game;
        updateScale();
    }

    public void update(float deltaTime) {
        level.update(deltaTime);
    }

    public void updateScale() {
        scale = WorldRenderer.VIEWPORT / Gdx.graphics.getHeight();
    }

//    @Override
//    public boolean keyUp (int keycode) {
//        if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK) {
//            // switch to menu screen
//            game.setScreen(new LoginScreen(game));
//        }
//        return false;
//    }

//    @Override
//    public boolean zoom (float originalDistance, float currentDistance){
//        float zoomLevel = WorldRenderer.VIEWPORT + (currentDistance - originalDistance) / scale;
////        if (zoomLevel > 350f && zoomLevel < 450f) {
////            WorldRenderer.VIEWPORT = zoomLevel;
////        }
////        worldRenderer.updateCamera();
//
//        Gdx.app.debug("Zoom", ""+zoomLevel);
//
//        return true;
//    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        worldRenderer.handleTouch(x, y);
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        worldRenderer.moveCamera(-deltaX * scale, deltaY * scale);
        return true;
    }

    public float getScale() {
        return scale;
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
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