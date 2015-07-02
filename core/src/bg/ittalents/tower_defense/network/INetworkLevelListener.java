package bg.ittalents.tower_defense.network;

public interface INetworkLevelListener {
    void onLevelLoaded(UserInfo user);
    void onTowerUpgrade(int towerX, int towerY);
    void onError(String message);
}
