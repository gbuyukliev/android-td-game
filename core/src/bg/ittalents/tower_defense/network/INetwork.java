package bg.ittalents.tower_defense.network;

public interface INetwork {
    void getLevelData(String username, int levelNumber);
    void setListener(INetworkLevelListener networkLevelListener);
    void saveScore(String username, int level, int score);
}
