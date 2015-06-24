package bg.ittalents.tower_defense;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

import bg.ittalents.tower_defense.game.Assets;
import bg.ittalents.tower_defense.screens.LoginScreen;

public class TowerDefense extends Game {

	@Override
	public void create () {
		// Set Libgdx log level
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		// Load assets
		Assets.instance.init(new AssetManager());
		// Start game at menu screen
		setScreen(new LoginScreen(this));
//		setScreen(new GameScreen(this));
	}
}
