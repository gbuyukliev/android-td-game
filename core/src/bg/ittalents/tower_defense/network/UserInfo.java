package bg.ittalents.tower_defense.network;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

public class UserInfo {
    private static UserInfo instance;

    private String nickName;
    private String userName;
    private boolean spam;
    private String email;
    private int score;
    private int level;

    public static String getAsString() {
        UserInfo instance = getInstance();
        return "UserInfo{" +
                "nickName='" + instance.nickName + '\'' +
                ", userName='" + instance.userName + '\'' +
                ", spam=" + instance.spam +
                ", email='" + instance.email + '\'' +
                '}';
    }

    public static void logAs(String userJson) {
        Json json = new Json();
        json.setTypeName(null);
        json.setUsePrototypes(false);
        json.setIgnoreUnknownFields(true);
        json.setOutputType(JsonWriter.OutputType.json);
        instance = json.fromJson(UserInfo.class, userJson);
    }

    private static UserInfo getInstance() {
        if (instance == null) {
            logInAsGuest();
        }
        return instance;
    }

    public static void logOff() {
        instance = null;
    }

    public static boolean isLogged() {
        return instance != null;
    }

    public static void logInAsGuest() {
        instance = new UserInfo();

        instance.nickName = "Guest";
        instance.userName = "Guest";
        instance.email = "";
    }

    public static void setLevel(int level) {
        getInstance().level = level;
    }

    public static int getLevel() {
         return getInstance().level;
    }

    public static String getUserName() {
        return getInstance().userName;
    }

    public static int getScore() {
        return getInstance().score;
    }

    public static void setScore(int score) {
        getInstance().score = score;
    }

    public static String getNickName() {
        return getInstance().nickName;
    }
}
