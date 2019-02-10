package cryptomessenger;

import java.io.Serializable;

public class Message implements Serializable {
    private String message;
    private String username;
    private Crypter crypter;
    private boolean isAlive = true;
    private int userCount;
    private int userId;

    public Message(String message, String username, Crypter crypter) {
        this.message = message;
        this.username = username;
        this.crypter = crypter;
    }

    public Message(String message, boolean isAlive) {
        this.isAlive = isAlive;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Crypter getCrypter() {
        return crypter;
    }

    public void setCrypter(Crypter crypter) {
        this.crypter = crypter;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }
}
