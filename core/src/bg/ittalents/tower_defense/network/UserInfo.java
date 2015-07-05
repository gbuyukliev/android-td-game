package bg.ittalents.tower_defense.network;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import bg.ittalents.tower_defense.utils.GamePreferences;

public class UserInfo {
    private static UserInfo instance;

    private String nickName;
    private String userName;
    private boolean spam;
    private String email;
    private int score;
    private int level;
    private TreeMap<String, Integer> levels;

    private UserInfo() {
        levels = new TreeMap<>();
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "nickName='" + nickName + '\'' +
                ", userName='" + userName + '\'' +
                ", spam=" + spam +
                ", email='" + email + '\'' +
                ", score=" + score +
                ", level=" + level +
                ", levels=" + levels +
                '}';
    }

    public static String getAsString() {
        if (instance != null) {
            return instance.toString();
        } else {
            return "";
        }
    }

    public static Map<String, Integer> getScores() {
        if (Network.isOnline()) {
            return Collections.unmodifiableMap(instance.levels);
        } else {
            return GamePreferences.instance.getLevelScores();
        }
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
