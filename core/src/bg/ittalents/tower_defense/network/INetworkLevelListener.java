package bg.ittalents.tower_defense.network;

import bg.ittalents.tower_defense.game.LevelData;

public interface INetworkLevelListener {
    void onLevelLoaded(LevelData levelData);
    void onError(String message);
}
