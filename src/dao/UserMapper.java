package dao;

import beans.User;

public interface UserMapper {
    /**
     * 删除对应id 的user
     * @param id 用户的id
     * @return int类型 1表示删除成功，0表示删除失败，可能原因是用户不存在,-1表示发生了外键约束异常
     * @exception org.springframework.dao.DataIntegrityViolationException 删除有外键约束book的user抛出
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增user到user表中
     * @param record 需要新增的用户
     * @return int类型 1表示新增成功，0表示新增失败，发生了未知原因，-1表示发生了新增冲突异常
     * @exception org.springframework.dao.DuplicateKeyException 当新增已经存在的user的时候抛出，解决方法在insert前查询是否存在
     */
    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int queryUserIsExist(String username);
}