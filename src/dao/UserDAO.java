package dao;

import interfaces.group.FirstValidate;
import interfaces.group.SecondValidate;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.GroupSequence;
import javax.validation.constraints.AssertTrue;


/**
 * Created by Shinelon on 2017/7/3.
 */

@GroupSequence({FirstValidate.class, SecondValidate.class , UserDAO.class})
public class UserDAO {
    @NotEmpty(message = "{userDAO.username.empty}",groups = {FirstValidate.class})
    @Length(min = 3, max = 6, message = "{userDAO.username.length.error}",groups = {SecondValidate.class})
    private String username;

    @NotEmpty(message = "{userDAO.password.empty}",groups = {FirstValidate.class})
    @Length(min = 3, max = 6, message = "{userDAO.password.length.error}",groups = {SecondValidate.class})
    private String password;

    private String confirm;

    @AssertTrue(message = "{userDAO.confirm.error}",groups = {SecondValidate.class})
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
}
