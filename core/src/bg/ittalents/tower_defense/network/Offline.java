package bg.ittalents.tower_defense.network;

import com.badlogic.gdx.Gdx;

public class Offline implements INetwork {

    private static final String PATH = "offline/";
    private static final String LEVEL_DATA_FILES = "LevelData";
    private static final String EXTENSION = ".json";

    @Override
    public String getLevelData(int levelNumber) {
        return Gdx.files.internal(PATH + LEVEL_DATA_FILES + levelNumber + EXTENSION).readString();
    }
}
