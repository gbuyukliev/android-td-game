package bg.ittalents.tower_defense.network;

import bg.ittalents.tower_defense.game.waves.LevelData;

public interface INetwork {
    LevelData getLevelData(int levelNumber);
}
