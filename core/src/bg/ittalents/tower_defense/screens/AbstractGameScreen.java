package bg.ittalents.tower_defense.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

import bg.ittalents.tower_defense.game.Assets;

public abstract class AbstractGameScreen implements Screen {
    private Game game;

    public AbstractGameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void pause() {
        Assets.instance.getMusic().pause();
    }

    @Override
    public void resume() {
        Assets.instance.init(new AssetManager());
        Assets.instance.getMusic().setLooping(true);
        Assets.instance.getMusic().setVolume(0.3f);
        Assets.instance.getMusic().play();
    }

    public void dispose() {
        Assets.instance.dispose();
    }

    public Game getGame() {
        return game;
    }

}
