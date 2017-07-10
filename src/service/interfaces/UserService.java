package service.interfaces;

import beans.User;

/**
 * Created by Shinelon on 2017/7/10.
 */
public interface UserService {
    public User getUserByUsername(String username);
}
