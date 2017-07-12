package beans;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class User {

    @NotNull(message = "{formUser.id.empty}")
    private Integer id;

    @NotEmpty(message = "{formUser.username.empty}")
    @Length(min = 3, max = 6, message = "{formUser.username.length.error}")
    private String username;

    @NotEmpty(message = "{formUser.username.empty}")
    @Length(min = 3, max = 6, message = "{formUser.password.length.error}")
    private String password;

    public User(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}