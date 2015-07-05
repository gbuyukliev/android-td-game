package bg.ittalents.tower_defense.network;

public interface INetwork {
    void getLevelData(String username);
    void setListener(INetworkLevelListener networkLevelListener);
    void saveScore(String username, int level, int score);
}
