package bg.ittalents.tower_defense.screens.windows;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;

import bg.ittalents.tower_defense.screens.AbstractGameScreen;

public class EndGameScreen extends AbstractGameScreen {

    public static final float PADDING = 10f;
    public static final float BUTTON_WIDTH = 100f;
    public static final float SCREEN_WIDTH = 640f;
    public static final float SCREEN_HEIGHT = 360f;

    private Stage stage;
    private Texture background;
    private Batch batch;

    public EndGameScreen(Game game) {
        super(game);
        background = new Texture(Gdx.files.internal("end_game_background.jpg"));
        stage = new Stage();
        batch = stage.getBatch();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        batch.end();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
