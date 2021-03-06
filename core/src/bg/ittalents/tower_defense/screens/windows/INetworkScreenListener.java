package bg.ittalents.tower_defense.screens.windows;

public interface INetworkScreenListener {
    enum SCREEN {
        LOGIN, REGISTER, LEVEL_SELECTOR, ACCOUNT_INFO, TOP_PLAYERS, OPTIONS
    }

    void setStatus(String message);
    void switchToWindow(SCREEN window);
    void setPlayerInfo(String userInfoJSON);
}
