package bg.ittalents.tower_defense.network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import bg.ittalents.tower_defense.game.waves.LevelData;

public class Offline implements INetwork {
    private static final String PATH = "offline/";
    private static final String LEVEL_DATA_FILES = "LevelData";
    private static final String EXTENSION = ".json";

    @Override
    public LevelData getLevelData(int levelNumber) {
        String levelJSON = Gdx.files.internal(PATH + LEVEL_DATA_FILES + levelNumber + EXTENSION).readString();

        Json json = new Json();
        json.setTypeName(null);
        json.setUsePrototypes(false);
        json.setIgnoreUnknownFields(true);
        json.setOutputType(JsonWriter.OutputType.json);
        LevelData levelData = json.fromJson(LevelData.class, levelJSON);
        return levelData;
//        Gdx.app.debug("JSON", levelData.toString());
    }
}
