package bg.ittalents.tower_defense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class Gui {
    private Level level;
    private Stage stage;
    private Skin skin;
    private float aspectRatio;
    private Label lblScore;
    private TextButton towerButton;
    private TextButton sellTowerButton;

    public Gui(float aspectRatio, Batch batch) {
        setAspectRatio(aspectRatio);
        stage = new Stage(new StretchViewport(WorldRenderer.VIEWPORT * aspectRatio, WorldRenderer.VIEWPORT), batch);
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
//        BitmapFont bitmapFont = Assets.instance.fonts.defaultFont;

//        lblScore = new Label("Test", skin);
//        lblScore.setColor(Color.RED);
//        lblScore.setPosition(100, 50);
//        lblScore.setSize(80, 30);
//        lblScore.setAlignment(Align.center);
//        stage.addActor(lblScore);

        towerButton = new TextButton("", skin);
        towerButton.setPosition(0, 0);
        towerButton.setSize(80, 30);
        towerButton.setVisible(false);
        towerButton.addListener(new ChangeListener() {
                                    @Override
                                    public void changed(ChangeEvent event, Actor actor) {
                                        if (level.getBuildStatus().equals("upgrade")) {
                                            level.getTiles()[level.getRowTower()][level.getColTower()].getTower().upgrade();
                                        } else if (level.getBuildStatus().equals("build")) {
                                            level.buildTower(level.getColTower(), level.getRowTower());
                                        }
                                        towerButton.setVisible(false);
                                        sellTowerButton.setVisible(false);
                                        level.setIsClicked(false);
                                    }
                                }
        );

        stage.addActor(towerButton);

        sellTowerButton = new TextButton("Sell", skin);
        getSellTowerButton().setPosition(90, 0);
        getSellTowerButton().setSize(80, 30);
        getSellTowerButton().setVisible(false);
        getSellTowerButton().addListener(new ChangeListener() {
                                             @Override
                                             public void changed(ChangeEvent event, Actor actor) {
                                                 level.sellTower(level.getTiles()[level.getRowTower()][level.getColTower()].getTower());
                                                 level.getTiles()[level.getRowTower()][level.getColTower()].setBuildable(true);
                                                 level.getTiles()[level.getRowTower()][level.getColTower()].removeTower();
                                                 towerButton.setVisible(false);
                                                 sellTowerButton.setVisible(false);
                                                 level.setIsClicked(false);
                                             }
                                         }
        );

        stage.addActor(getSellTowerButton());
    }

    public InputProcessor getInputProcessor() {
        return stage;
    }

    public void setAspectRatio(float aspectRatio) { this.aspectRatio = aspectRatio; }

//    public void update(float deltaTime) {
//        stage.act(deltaTime);
//    }

    public void render(Batch batch) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public TextButton getTowerButton() {
        return towerButton;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        if (level != null) {
            this.level = level;
        }
    }

    public TextButton getSellTowerButton() {
        return sellTowerButton;
    }
}