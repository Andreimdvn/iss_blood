package Model;

public abstract class UserInfo {
    private String username;

    public UserInfo(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
