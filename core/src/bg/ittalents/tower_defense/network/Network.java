package bg.ittalents.tower_defense.network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;

public class Network {
    public static final String URL = "http://192.168.7.61:8080/towerdefense/";
    //    public static final String URL = "http://jsonstub.com/towerdefense/";
    public static final String LOGIN_MANAGER = "LoginManager";
    public static final String REGISTER_MANAGER = "RegisterManager";
    public static final String ACCOUNT_INFO_MANAGER = "ChangeAccountInfoManager";
    public static final String ALL_HIGH_SCORE_MANAGER = "AllHighScoreManager";
    public static final String TOWER_MANAGER = "TowerManager";
    public static final String LEVEL_MANAGER = "LevelManager";
    public static final String LEVEL_END_MANAGER = "LevelEndManager";
    public static final String ALL_LEVEL_SCORE_MANAGER = "AllLevelScoreManager";

    private static INetwork instance;

    public static INetwork getInstance() {
        if (instance == null) {
            instance = new Offline();
        }
        return instance;
    }

    public static void setListener(INetworkLevelListener networkLevelListener) {
        getInstance().setListener(networkLevelListener);
    }

    public static void postRequest(String content, Net.HttpResponseListener httpResponseListener, String manager) {
        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(Network.URL + manager);
        httpRequest.setContent(content);
        Gdx.net.sendHttpRequest(httpRequest, httpResponseListener);
    }

    public static void setOnline(boolean online) {
        if (online) {
            instance = new Online();
        } else {
            new Offline();
        }
    }
}
