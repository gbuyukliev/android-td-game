package bg.ittalents.tower_defense.network;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;

public class Network implements INetwork {

    public static final String URL = "http://192.168.7.43:8080/towerdefense/";
//    public static final String URL = "http://jsonstub.com/towerdefense/";
    public static final String LOGIN_MANAGER = "LoginManager";
    public static final String REGISTER_MANAGER = "RegisterManager";
    public static final String ACCOUNT_INFO_MANAGER = "ChangeAccountInfoManager";
    public static final String ALL_HIGH_SCORE_MANAGER = "AllHighScoreManager";
    public static final String TOWER_MANAGER = "TowerManager";
    public static final String LEVEL_MANAGER = "LevelManager";
    public static final String LEVEL_END_MANAGER = "LevelEndManager";
    public static final String ALL_LEVEL_SCORE_MANAGER = "AllLevelScoreManager";

    private Game game;

    public Network(Game game) {
        this.game = game;
    }

    @Override
    public String getLevelData(int levelNumber) {
        return null;
    }

    public void postLogin(String content) {
        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(URL + LOGIN_MANAGER);
        httpRequest.setContent(content);
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {

            }

            @Override
            public void failed(Throwable t) {

            }

            @Override
            public void cancelled() {

            }
        });
    }
}
