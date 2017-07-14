package dao;

import beans.MyBook;
import org.springframework.dao.DataIntegrityViolationException;

public interface MyBookMapper {
    /**
     * 删除book表中对应id的行（书）
     * @param id  book的id
     * @return int类型 1表示删除成功 0表示没有成功，可能原因是对应id不存在
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增书信息
     * @param record  书
     * @return  int类型  1表示新增成功，0表示新增失败,发生了未知不算异常的错误
     * @exception org.springframework.dao.DuplicateKeyException  当Insert book时候，book已经存在抛出
     */
    int insert(MyBook record);

    int insertSelective(MyBook record);

    /**
     * 获得对应id的书信息
     * @param id   书的Id
     * @return  MyBook类型 Null表示没有对应的书  Mybook为对应的书
     */
    MyBook selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MyBook record);

    /**
     * 更新对应书的信息
     * @param record   包含新信息的书，用于更新它的id对应的书信息
     * @return  int类型，1表示更新成功 0表示更新失败，可能原因是不存在对应的书
     * @exception org.springframework.dao.DataIntegrityViolationException  更新存在外键约束的书的信息的时候违反了外键约束
     */
    int updateByPrimaryKey(MyBook record);
}