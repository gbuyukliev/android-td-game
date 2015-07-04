package bg.ittalents.tower_defense.screens.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpStatus;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

import bg.ittalents.tower_defense.network.INetworkLevelListener;
import bg.ittalents.tower_defense.network.Network;
import bg.ittalents.tower_defense.network.UserInfo;

public class TopPlayersWindow extends Window implements INetworkLevelListener{

    private INetworkScreenListener networkScreenListener;

    public TopPlayersWindow(Skin skin, INetworkScreenListener networkScreenListener) {
        super("Top Players", skin);
        this.networkScreenListener = networkScreenListener;
//        requestTopPlayersList();
        Network.setListener(this);
        Network.getInstance().getTopPlayers();
    }

    @Override
    public void onLevelLoaded(UserInfo user) {

    }

    @Override
    public void onTowerUpgrade(int towerX, int towerY) {

    }

    @Override
    public void onError(String message) {

    }

//    private void requestTopPlayersList() {
//        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.GET);
//        httpRequest.setUrl(Network.URL + Network.ALL_HIGH_SCORE_MANAGER);
//        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
//            @Override
//            public void handleHttpResponse(final Net.HttpResponse httpResponse) {
//                Gdx.app.debug("URL", Network.URL + Network.ALL_HIGH_SCORE_MANAGER);
//                Gdx.app.debug("Login Headers", httpResponse.getResultAsString());
//                if (httpResponse.getStatus().getStatusCode() == HttpStatus.SC_OK) {
//                    final String result = httpResponse.getResultAsString();
//
//                    Gdx.app.postRunnable(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            if (result == null) {
////                                networkScreen.setStatus("Incorrect data from server");
//                                return;
//                            }
//
////                            Network.setOnline(true);
////                            networkScreen.setPlayerInfo(result);
////                            networkScreen.switchToWindow(INetworkScreenListener.SCREEN.LEVEL_SELECTOR);
////                            Gdx.app.debug("Login POST", "" + httpResponse.getHeader("Reason"));
////                            Gdx.net.cancelHttpRequest(httpRequest);
//
//                        }
//                    });
//                } else {
//                    String errorMessage = httpResponse.getHeader("Error");
//                    if (errorMessage == null) {
//                        errorMessage = "Wrong username or password!";
//                    }
////                    networkScreen.setStatus(errorMessage);
//                    Gdx.app.debug("Login POST", "" + errorMessage);
////                    Gdx.net.cancelHttpRequest(httpRequest);
//                }
//            }
//
//            @Override
//            public void failed(Throwable t) {
////                networkScreen.setStatus("Server is not responding");
////                Gdx.app.debug("Login POST", "FAILED" + t.getMessage());
//            }
//
//            @Override
//            public void cancelled() {
//                Gdx.app.postRunnable(new Runnable() {
//                    @Override
//                    public void run() {
////                                btnLogin.setDisabled(false);
////                                btnLogin.setTouchable(Touchable.enabled);
//                        Gdx.app.debug("Login POST", "CANCELLED");
//                    }
//                });
//            }
//        });
//    }
}
