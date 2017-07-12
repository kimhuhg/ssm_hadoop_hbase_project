package beans;

import validate.interfaces.group.FirstValidate;
import validate.interfaces.group.SecondValidate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.GroupSequence;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;


/**
 * Created by Shinelon on 2017/7/3.
 */

@GroupSequence({FirstValidate.class, SecondValidate.class , FormUser.class})
public class FormUser {
    @NotNull(message = "{formUser.id.empty}",groups = {FirstValidate.class})
    private Integer id;

    @NotEmpty(message = "{formUser.username.empty}",groups = {FirstValidate.class})
    @Length(min = 3, max = 6, message = "{formUser.username.length.error}",groups = {SecondValidate.class})
    private String username;

    @NotEmpty(message = "{formUser.password.empty}",groups = {FirstValidate.class})
    @Length(min = 3, max = 6, message = "{formUser.password.length.error}",groups = {SecondValidate.class})
    private String password;

    private String confirm;

    @AssertTrue(message = "{formUser.confirm.error}",groups = {SecondValidate.class})
    private boolean isConfirm() {
        if (password != null && !password.isEmpty() && confirm != null && !confirm.isEmpty())
            return this.password.equals(confirm);
        return false;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
