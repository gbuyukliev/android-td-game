package bg.ittalents.tower_defense.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
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

import bg.ittalents.tower_defense.game.Level;
import bg.ittalents.tower_defense.game.WorldRenderer;

public class Gui {
    private Level level;
    private Stage stage;
    private Skin skin;
    private float aspectRatio;
    private Label warningTextField;
    private Table noTowerTable;
    private Table towerTable;
    private ImageButton upgradeTowerButton;
    private ImageButton buildTowerButton;
    private ImageButton sellTowerButton;
    private ImageButton pauseButton;

    // A table with buttons which only appears if there is an empty tile selected

    public void buildNoTowerTable() {
        noTowerTable = new Table();
        noTowerTable.bottom().left();
        noTowerTable.setFillParent(true);
        noTowerTable.setVisible(false);
        stage.addActor(noTowerTable);
    }

    public void buildBuildTowerButton() {
        buildTowerButton = new MyButton(Textures.UPGRADE_BUTTON_BLUE, Textures.UPGRADE_BUTTON_CLICKED_BLUE);
        buildTowerButton.addListener(new ChangeListener() {
                                         @Override
                                         public void changed(ChangeEvent event, Actor actor) {
                                             if (level.getMoney() < level.getCurrentTowerPrice()) {
                                                 level.setTextTime(0);
                                                 warningTextField.setText("Not enough money to buy a tower!");
                                                 warningTextField.setVisible(true);
                                             }

                                             level.buildTower(level.getColTower(), level.getRowTower());
                                             noTowerTable.setVisible(false);
                                             level.setIsClicked(false);
                                         }
                                     }
        );

        noTowerTable.add(buildTowerButton).size(40, 40).pad(5);
    }

    // A table with buttons which only appears if there is a tower selected

    public void buildTowerTable() {
        towerTable = new Table();
        towerTable.bottom().left();
        towerTable.setFillParent(true);
        towerTable.setVisible(false);
        stage.addActor(towerTable);
    }

    public void buildUpgradeTowerButton() {
//        Drawable buttonUp = new SpriteDrawable(new Sprite(Textures.UPGRADE_BUTTON));
//        Drawable buttonDown = new SpriteDrawable(new Sprite(Textures.UPGRADE_BUTTON_CLICKED));

        upgradeTowerButton = new MyButton(Textures.UPGRADE_BUTTON_BLUE, Textures.UPGRADE_BUTTON_CLICKED_BLUE);
//        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
//        style.imageDisabled = buttonDown;
//        upgradeTowerButton.setStyle(style);

        upgradeTowerButton.addListener(new ChangeListener() {
                                           @Override
                                           public void changed(ChangeEvent event, Actor actor) {
                                               Level.Tile tile = level.getTiles()[level.getRowTower()][level.getColTower()];

                                               if (level.getMoney() < tile.getTower().getUpgradePrice()) {
                                                   level.setTextTime(0);
                                                   warningTextField.setText("Not enough money for an upgrade!");
                                                   warningTextField.setVisible(true);
                                               }

                                               tile.getTower().upgrade();
                                               towerTable.setVisible(false);
                                               level.setIsClicked(false);
                                           }
                                       }
        );

        towerTable.add(upgradeTowerButton).size(40, 40).pad(5);
    }

    public void buildSellTowerButton() {
        sellTowerButton = new MyButton(Textures.SELL_BUTTON_BLUE, Textures.SELL_BUTTON_CLICKED_BLUE);
        sellTowerButton.addListener(new ChangeListener() {
                                        @Override
                                        public void changed(ChangeEvent event, Actor actor) {
                                            Level.Tile tile = level.getTiles()[level.getRowTower()][level.getColTower()];

                                            level.sellTower(tile.getTower());
                                            tile.removeTower();
                                            towerTable.setVisible(false);
                                            level.setIsClicked(false);
                                        }
                                    }
        );

        towerTable.add(sellTowerButton).size(40, 40);
    }

    public void buildWarningTextField() {
        Table warningTextTable = new Table();
        warningTextTable.setFillParent(true);
        warningTextTable.center();
        warningTextField = new Label("", skin);
        warningTextField.setColor(Color.RED);
        warningTextField.setVisible(false);
        warningTextField.setAlignment(Align.center);
        warningTextTable.add(warningTextField).center().expandX().padTop(210);
        stage.addActor(warningTextTable);
    }

    public void buildPause() {
        Drawable buttonUp1 = new SpriteDrawable(new Sprite(Textures.PAUSE_BUTTON));
        Drawable buttonDown1 = new SpriteDrawable(new Sprite(Textures.PAUSE_BUTTON_CLICKED));

        Drawable buttonUp2 = new SpriteDrawable(new Sprite(Textures.RESUME_BUTTON));
        Drawable buttonDown2 = new SpriteDrawable(new Sprite(Textures.RESUME_BUTTON_CLICKED));

        final ImageButton.ImageButtonStyle style1 = new ImageButton.ImageButtonStyle();
        style1.imageUp = buttonUp1;
        style1.imageDown = buttonDown1;

        final ImageButton.ImageButtonStyle style2 = new ImageButton.ImageButtonStyle();
        style2.imageUp = buttonUp2;
        style2.imageDown = buttonDown2;

        Table pauseTable = new Table();
        pauseTable.setFillParent(true);
        pauseTable.bottom().right();
        pauseButton = new ImageButton(style1);
        pauseButton.addListener(new ChangeListener() {
                                    @Override
                                    public void changed(ChangeEvent event, Actor actor) {
                                        if (level.isPaused() == false) {
                                            pauseButton.setStyle(style2);
                                            level.setIsPaused(true);
                                        } else {
                                            pauseButton.setStyle(style1);
                                            level.setIsPaused(false);
                                        }
                                    }
                                }
        );

        pauseTable.add(pauseButton).size(40, 40).pad(5);
        stage.addActor(pauseTable);
    }

    public Gui(float aspectRatio, Batch batch) {
        setAspectRatio(aspectRatio);
        stage = new Stage(new StretchViewport(WorldRenderer.VIEWPORT * aspectRatio, WorldRenderer.VIEWPORT), batch);
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        buildNoTowerTable();
        buildBuildTowerButton();
        buildTowerTable();
        buildUpgradeTowerButton();
        buildSellTowerButton();
        buildWarningTextField();
        buildPause();
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

    public Label getWarningTextField() {
        return warningTextField;
    }
}