package service.interfaces;

import beans.MyUser;
import beans.User;

/**
 * Created by Shinelon on 2017/7/10.
 */
public interface UserService {
    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(User user);

    int deleteByPrimaryKey(Integer id);

    int insert(User user);

    //判断user表内 username对应的行是否存在
    int queryUserIsExist(String username);

    //MyUser 有关的
    MyUser selectMyUserByPrimaryKey(Integer id);

}
