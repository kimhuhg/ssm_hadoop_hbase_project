package service.impl;

import beans.User;
import dao.interfaces.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.interfaces.UserService;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * Created by Shinelon on 2017/7/10.
 */
@Service("userService")
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserByUsername(String username) {
        /*查询不到对应信息，应该告知用户*/
        User user=userMapper.selectByPrimaryKey(username);
        if(user!=null)
            return user;
        else
            return null;
    }
}
