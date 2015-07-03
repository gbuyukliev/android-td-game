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

    @Override
    public String toString() {
        return "UserInfo{" +
                "nickName='" + nickName + '\'' +
                ", userName='" + userName + '\'' +
                ", spam=" + spam +
                ", email='" + email + '\'' +
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

    public static UserInfo getInstance() {
        if (instance == null) {
            logAsGuess();
        }

        return instance;
    }

    public static void logOff() {
        instance = null;
    }

    public static boolean isLogged() {
        return instance != null;
    }

    public static void logAsGuess() {
        instance = new UserInfo();

        instance.nickName = "Guess";
        instance.userName = "Guess";
        instance.email = "";
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getNickName() {
        return nickName;
    }
}
