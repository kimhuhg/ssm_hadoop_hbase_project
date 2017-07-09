package beans;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Shinelon on 2017/7/3.
 */
public class User {
    @NotEmpty(message = "{userDAO.username.empty}")
    @Length(min = 3,max = 6,message = "{userDAO.username.length.error}")
    private String username;

    @NotEmpty(message = "{userDAO.username.empty}")
    @Length(min = 3,max = 6,message = "{userDAO.password.length.error}")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
