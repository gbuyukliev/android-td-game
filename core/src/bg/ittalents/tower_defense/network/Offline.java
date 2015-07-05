package bg.ittalents.tower_defense.network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import bg.ittalents.tower_defense.game.LevelData;
import bg.ittalents.tower_defense.utils.GamePreferences;

class Offline implements INetwork {
    private static final String PATH = "offline/";
    private static final String LEVEL_DATA_FILES = "LevelData";
    private static final String EXTENSION = ".json";
    private INetworkLevelListener networkLevelListener;

    @Override
    public void getLevelData(String username, int levelNumber) {
        String levelPath = PATH + LEVEL_DATA_FILES + EXTENSION;

        if (Gdx.files.internal(levelPath).exists() && networkLevelListener != null) {
            String levelJSON = Gdx.files.internal(levelPath).readString();

            LevelData levelData = Network.parseJson(levelJSON);
            networkLevelListener.onLevelLoaded(levelData);
        }
    }

    @Override
    public void saveScore(String username, int level, int score) {
        GamePreferences.instance.putLevelScore(level, score);
        GamePreferences.instance.save();
    }

    @Override
    public void setListener(INetworkLevelListener networkLevelListener) {
        this.networkLevelListener = networkLevelListener;
    }
}
