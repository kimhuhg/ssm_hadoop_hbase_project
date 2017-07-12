package dao;

import beans.MyBook;

public interface MyBookMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MyBook record);

    int insertSelective(MyBook record);

    MyBook selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MyBook record);

    int updateByPrimaryKey(MyBook record);
}