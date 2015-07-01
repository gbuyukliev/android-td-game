package bg.ittalents.tower_defense.network;

public class UserInfo {
    private String nickName;
    private String userName;
    private boolean spam;
    private String email;
    private int score;

    @Override
    public String toString() {
        return "UserInfo{" +
                "nickName='" + nickName + '\'' +
                ", userName='" + userName + '\'' +
                ", spam=" + spam +
                ", email='" + email + '\'' +
                '}';
    }

    public static UserInfo createGuessUser() {
        UserInfo userInfo = new UserInfo();

        userInfo.nickName = "Guess";
        userInfo.userName = "Guess";
        userInfo.email = "";

        return userInfo;
    }
}
