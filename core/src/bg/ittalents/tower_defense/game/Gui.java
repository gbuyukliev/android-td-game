package bg.ittalents.tower_defense.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.awt.Image;
import java.awt.TextField;
import java.io.IOException;
import java.io.InputStream;

public class Gui {
    private static final Texture UPGRADE_TEXTURE;
    private static final Texture SELL_TEXTURE;

    static {
        UPGRADE_TEXTURE = new Texture("option_upgrade.png");
        SELL_TEXTURE = new Texture("option_sell.png");
    }

    private Level level;
    private Stage stage;
    private Skin skin;
    private float aspectRatio;
    private Label textField;
    private Table noTowerTable;
    private Table towerTable;
    private ImageButton upgradeTowerButton;

    public Gui(float aspectRatio, Batch batch) {
        setAspectRatio(aspectRatio);
        stage = new Stage(new StretchViewport(WorldRenderer.VIEWPORT * aspectRatio, WorldRenderer.VIEWPORT), batch);
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        // A table with buttons which only appears if there is an empty tile selected

        noTowerTable = new Table();
        noTowerTable.bottom().left();
        noTowerTable.setFillParent(true);
        noTowerTable.setVisible(false);
        stage.addActor(noTowerTable);

        ImageButton buildTowerButton = new ImageButton(new SpriteDrawable(new Sprite(UPGRADE_TEXTURE)));
        buildTowerButton.addListener(new ChangeListener() {
                                         @Override
                                         public void changed(ChangeEvent event, Actor actor) {
                                             level.buildTower(level.getColTower(), level.getRowTower());
                                             noTowerTable.setVisible(false);
                                             level.setIsClicked(false);
                                         }
                                     }
        );

        noTowerTable.add(buildTowerButton);

        // A table with buttons which only appears if there is a tower selected

        towerTable = new Table();
        towerTable.bottom().left();
        towerTable.setFillParent(true);
        towerTable.setVisible(false);
        stage.addActor(towerTable);

        upgradeTowerButton = new ImageButton(new SpriteDrawable(new Sprite(UPGRADE_TEXTURE)));
        upgradeTowerButton.addListener(new ChangeListener() {
                                           @Override
                                           public void changed(ChangeEvent event, Actor actor) {
                                               Level.Tile tile = level.getTiles()[level.getRowTower()][level.getColTower()];

                                               tile.getTower().upgrade();
                                               towerTable.setVisible(false);
                                               level.setIsClicked(false);
                                           }
                                       }
        );

        towerTable.add(upgradeTowerButton);

        ImageButton sellTowerButton = new ImageButton(new SpriteDrawable(new Sprite(SELL_TEXTURE)));
        sellTowerButton.addListener(new ChangeListener() {
                                        @Override
                                        public void changed(ChangeEvent event, Actor actor) {
                                            Level.Tile tile = level.getTiles()[level.getRowTower()][level.getColTower()];

                                            level.sellTower(tile.getTower());
                                            tile.setBuildable(true);
                                            tile.removeTower();
                                            towerTable.setVisible(false);
                                            level.setIsClicked(false);
                                        }
                                    }
        );

        towerTable.add(sellTowerButton).padLeft(5);

        textField = new Label("Not enough money", skin);
        textField.setPosition(0, 50);
//        textField.setSize(0, 0);
        stage.addActor(textField);
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

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        if (level != null) {
            this.level = level;
        }
    }

    public Table getTowerTable() {
        return towerTable;
    }

    public Table getNoTowerTable() {
        return noTowerTable;
    }

    public ImageButton getUpgradeTowerButton() {
        return upgradeTowerButton;
    }
}