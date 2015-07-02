package bg.ittalents.tower_defense.network;

import bg.ittalents.tower_defense.game.waves.LevelData;

public interface INetwork {
    LevelData getLevelData(String username, int levelNumber);
    void setListener(INetworkLevelListener networkLevelListener);
}
