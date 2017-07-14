package service.interfaces;

import beans.MyUser;
import beans.User;


/**
 * 用于业务处理
 */
public interface UserService {
    /**
     * 获取对应id号的user
     * @param id 用户id
     * @return null表示对应id用户不存在  user为对应id用户
     */
    User selectByPrimaryKey(Integer id);

    /**
     * 向数据库中user表更新user
     * @param user 传递一个非空用户类 用于向user表中对应的user更新信息
     * @return 返回更新的行数  0表示没能找到对应的user更新，1表示更新成功
     */
    int updateByPrimaryKey(User user);

    /**
     * 删除id对应的用户
     * @param id  用户id
     * @return 1表示删除成功  0表示用户不存在，删除失败 -1表示删除的时候出现外键约束异常，删除失败
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @param user 将要插入数据库的用户
     * @return 1表示插入成功  0表示插入过程中发生未知意外  -1表示用户已经存在，插入失败
     */
    int insert(User user);

    /**
     * 判断username对应用户是否存在
     * @param username  用户名
     * @return   1表示存在  0表示不存在
     */
    int queryUserIsExist(String username);

    /**
     * 获取id对应的用户
     * @param id   用户id
     * @return  null表示没有id对应的用户   MyUser为对应id的用户
     */
    MyUser selectMyUserByPrimaryKey(Integer id);

    //事务测试
    User transactionalTest(User user);

}
