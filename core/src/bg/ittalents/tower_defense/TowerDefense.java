package bg.ittalents.tower_defense;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

import bg.ittalents.tower_defense.game.objects.Assets;
import bg.ittalents.tower_defense.screens.GameScreen;

public class TowerDefense extends Game {

	@Override
	public void create () {
		// Set Libgdx log level
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		// Load assets
		Assets.instance.init(new AssetManager());
		// Start game at menu screen
		setScreen(new GameScreen(this));
	}
}
