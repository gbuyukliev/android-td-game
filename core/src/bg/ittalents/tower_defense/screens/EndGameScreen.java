package bg.ittalents.tower_defense.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import bg.ittalents.tower_defense.game.Level;
import bg.ittalents.tower_defense.game.WorldRenderer;
import bg.ittalents.tower_defense.screens.windows.INetworkScreenListener;

public class EndGameScreen extends AbstractGameScreen implements INetworkScreenListener {
    public static final float SCREEN_WIDTH = 800f;
    public static final float SCREEN_HEIGHT = 480f;

    private Stage stage;
    private Skin skin;
    private Texture background;
    private Batch batch;
    private Table table;
    private Label upperText;
    private Label lowerText;
    private TextButton closeButton;
    private Level level;

    public EndGameScreen(Game game, Level level) {
        super(game);
        this.level = level;
    }

    @Override
    public void show() {
        background = new Texture(Gdx.files.internal("end_game_background.jpg"));
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        buildStage();
    }

    private BitmapFont generateFont() {
        BitmapFont font;

        float scale;

        if (Gdx.graphics.getHeight() > Gdx.graphics.getWidth()) {
            scale = Gdx.graphics.getWidth() / WorldRenderer.VIEWPORT ;
        } else {
            scale = Gdx.graphics.getHeight() / WorldRenderer.VIEWPORT ;
        }

        if (scale < 1) {
            scale = 1;
        }

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/FARCEB__.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = (int) (20 * scale);
        fontParameter.flip = true;
        font = generator.generateFont(fontParameter);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        return font;
    }

    private void buildTable() {
        table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);
    }

    private void buildText() {
        Label.LabelStyle lblStyle = new Label.LabelStyle(generateFont(), Color.RED);

        upperText = new Label("Your score is  " + level.getScore() + "!", lblStyle);
        upperText.setAlignment(Align.center);

        table.add(upperText).center().expandX();

        lowerText = new Label("", lblStyle);
        lowerText.setAlignment(Align.center);
        table.row();
        table.add(lowerText).center().expandX();
    }

    private void buildCloseButton() {
        closeButton = new TextButton("OK", skin);
        closeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switchToWindow(INetworkScreenListener.SCREEN.LEVEL_SELECTOR);
            }
        });

        table.row();
        table.add(closeButton).center().expandX();
    }

    private void buildStage() {
        stage = new Stage(new StretchViewport(SCREEN_WIDTH, SCREEN_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        batch = stage.getBatch();

        buildTable();
        buildText();
        buildCloseButton();
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
        stage.getViewport().update(width, height);
    }

    @Override
    public void hide() {
        background.dispose();
        stage.dispose();
        skin.dispose();
    }

    @Override
    public void setStatus(String message) {

    }

    @Override
    public void switchToWindow(SCREEN window) {

    }

    @Override
    public void setPlayerInfo(String userInfoJSON) {

    }
}
