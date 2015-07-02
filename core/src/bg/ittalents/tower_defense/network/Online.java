package bg.ittalents.tower_defense.network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;

import bg.ittalents.tower_defense.game.waves.LevelData;

class Online implements INetwork {

    @Override
    public LevelData getLevelData(String username, int levelToPlay) {
        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.GET);
        httpRequest.setUrl(Network.URL + Network.LEVEL_MANAGER);
        httpRequest.setContent("userName=" + username + "&levelToPlay=" + levelToPlay);
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
        return null;
    }

    @Override
    public void setListener(INetworkLevelListener networkLevelListener) {

    }
}
