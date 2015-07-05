package bg.ittalents.tower_defense.network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import bg.ittalents.tower_defense.game.LevelData;

class Offline implements INetwork {
    private static final String PATH = "offline/";
    private static final String LEVEL_DATA_FILES = "LevelData";
    private static final String EXTENSION = ".json";
    private INetworkLevelListener networkLevelListener;

    @Override
    public void getLevelData(String username) {
        String levelPath = PATH + LEVEL_DATA_FILES + EXTENSION;

        if (Gdx.files.internal(levelPath).exists() && networkLevelListener != null) {
            String levelJSON = Gdx.files.internal(levelPath).readString();

            Json json = new Json();
            json.setTypeName(null);
            json.setUsePrototypes(false);
            json.setIgnoreUnknownFields(true);
            json.setOutputType(JsonWriter.OutputType.json);
            LevelData levelData = json.fromJson(LevelData.class, levelJSON);

//            Gdx.app.debug("asd", levelData.toString());

            networkLevelListener.onLevelLoaded(levelData);
        }
    }

    @Override
    public void saveScore(String username, int level, int score) {

    }

    @Override
    public void setListener(INetworkLevelListener networkLevelListener) {
        this.networkLevelListener = networkLevelListener;
    }
}
