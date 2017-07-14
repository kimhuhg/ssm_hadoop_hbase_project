package service.impl;

import beans.MyUser;
import beans.User;
import dao.MyUserMapper;
import dao.UserMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.interfaces.UserService;

import javax.annotation.Resource;

/**
 * Created by Shinelon on 2017/7/10.
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        try {
            return userMapper.deleteByPrimaryKey(id);
        }catch (DataIntegrityViolationException e){
            return -1;
        }
    }

    @Override
    public int insert(User user) {
        //判断用户是否存在
        //0表示查询不存在,就执行插入操作       1表示查询存在，就返回-1 表示用户已经存在
        if (queryUserIsExist(user.getUsername()) == 0)
            return userMapper.insert(user);
        else
            return -1; //用户已经存在
    }

    @Override
    public int queryUserIsExist(String username) {
        //这有2个解决方案，一个是在sql语句上优化，利用limit和count  一个是利用Integer，空值依然可以存储,现在用的是sql  用Integer需要判断一下null
        return userMapper.queryUserIsExist(username);
//        if (result != null)
//            return result;
//        else
//            return 0;
    }

    @Transactional
    @Override
    public User transactionalTest(User user) {
        System.out.println("更新user结果（之后会发生异常）测试事务回滚：" + updateByPrimaryKey(user));
        int i = 1 / 0;//发现异常 就会不执行之后的，开始事务回滚
        return selectByPrimaryKey(user.getId());
    }

    //MyUser有关
    @Resource
    private MyUserMapper myUserMapper;

    //1对多开始
    @Override
    public MyUser selectMyUserByPrimaryKey(Integer id) {
        //没有找到的话null，找得到就有值
        return myUserMapper.selectByPrimaryKey(id);
    }


}
