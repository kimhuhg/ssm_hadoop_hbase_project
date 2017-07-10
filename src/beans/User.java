package beans;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class User {

    @NotEmpty(message = "{formUser.username.empty}")
    @Length(min = 3,max = 6,message = "{formUser.username.length.error}")
    private String username;

    @NotEmpty(message = "{formUser.username.empty}")
    @Length(min = 3,max = 6,message = "{formUser.password.length.error}")
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
        super();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}