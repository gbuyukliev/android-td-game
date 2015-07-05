package bg.ittalents.tower_defense.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

import bg.ittalents.tower_defense.game.Assets;
import bg.ittalents.tower_defense.utils.AudioManager;

public abstract class AbstractGameScreen implements Screen {
    private Game game;

    public AbstractGameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void pause() {
        AudioManager.instance.stopMusic();
    }

    @Override
    public void resume() {
        Assets.instance.init(new AssetManager());
        AudioManager.instance.play(Assets.instance.getMusic());
    }

    public void dispose() {
        Assets.instance.dispose();
    }

    public Game getGame() {
        return game;
    }
}
