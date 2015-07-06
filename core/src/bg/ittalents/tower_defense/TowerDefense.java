package bg.ittalents.tower_defense;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

import bg.ittalents.tower_defense.game.Assets;
import bg.ittalents.tower_defense.screens.MainScreen;
import bg.ittalents.tower_defense.utils.AudioManager;

public class TowerDefense extends Game {

	@Override
	public void create () {
		// Set Libgdx log level
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		// Load assets
		Assets.instance.init(new AssetManager());
		// Start game at menu screen
		setScreen(new MainScreen(this));
//		Assets.instance.getMusic().setLooping(true);
//		Assets.instance.getMusic().setVolume(0.3f);
//		Assets.instance.getMusic().play();
		AudioManager.instance.play(Assets.instance.getMusic());
	}
}
