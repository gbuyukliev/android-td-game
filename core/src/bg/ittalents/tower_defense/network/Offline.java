package bg.ittalents.tower_defense.network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import bg.ittalents.tower_defense.game.LevelData;

class Offline implements INetwork {
    private static final String PATH = "offline/";
    private static final String LEVEL_DATA_FILES = "LevelData";
    private static final String EXTENSION = ".json";

    @Override
    public void getLevelData(String username, int levelNumber) {
        String levelPath = PATH + LEVEL_DATA_FILES + levelNumber + EXTENSION;

        if (Gdx.files.internal(levelPath).exists()) {
            String levelJSON = Gdx.files.internal(levelPath).readString();

            Json json = new Json();
            json.setTypeName(null);
            json.setUsePrototypes(false);
            json.setIgnoreUnknownFields(true);
            json.setOutputType(JsonWriter.OutputType.json);
            LevelData levelData = json.fromJson(LevelData.class, levelJSON);

//        return levelData;
//        Gdx.app.debug("JSON", levelData.toString());
        }
    }

    @Override
    public void saveScore(String username, int level, int score) {

    }

    @Override
    public void setListener(INetworkLevelListener networkLevelListener) {

    }
}
