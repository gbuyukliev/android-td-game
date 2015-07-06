package bg.ittalents.tower_defense.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartScreen extends AbstractGameScreen {
    public static final float SCREEN_WIDTH = 800f;
    public static final float SCREEN_HEIGHT = 480f;

    private Texture background;
    private Batch batch;
    private float timer;

    public StartScreen(Game game) {
        super(game);
        timer = 0;
        batch = new SpriteBatch();
    }

    @Override
    public void show() {
        background = new Texture(Gdx.files.internal("splash_img.png"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
        timer+=delta;
        if (timer > 3) {
            getGame().setScreen(new MainScreen(getGame()));
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void hide() {
        background.dispose();
    }
}