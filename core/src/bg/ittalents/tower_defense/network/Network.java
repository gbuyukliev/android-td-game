package bg.ittalents.tower_defense.network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;

public class Network implements INetwork {

    public static final String URL = "http://192.168.7.189:8080/towerdefense/";

    @Override
    public String getLevelData(int levelNumber) {
        return null;
    }

    public void postLogin(String content) {
        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        httpRequest.setUrl(URL + "LoginManager");
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
