package test.mapperTest;

import beans.User;
import dao.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 测试userMapper，所有测试方法命名为test+userMapper对应方法
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test/configurationFiles/testMapper/applicationContext.xml"})
@Transactional
public class UserMapperTest {
    @Resource
    private UserMapper userMapper;

    @Test
    public void testDeleteByPrimaryKey(){
        //删除不存在的用户
        Assert.assertEquals("deleteByPrimaryKey方法删除不存在id：预期结果和实际结果不一致", 0, userMapper.deleteByPrimaryKey(-1));
        //删除存在的但是没有book的user
        Assert.assertEquals("deleteByPrimaryKey方法删除存在id:预期结果和实际结果不一致", 1, userMapper.deleteByPrimaryKey(1));
        //删除存在的但是有book外键约束的user
        try{
            userMapper.deleteByPrimaryKey(3);
        }catch (org.springframework.dao.DataIntegrityViolationException e){
            System.out.println("删除外键约束的user失败");
        }
    }

    @Test
    public void testInsert(){
        try {
            userMapper.insert(new User(1, "aa", "123456"));//预期新增失败，会抛出DuplicateKeyException异常
        }catch (DuplicateKeyException e){
            System.out.println("新增已存在值 预期出现DuplicateKeyException.class 异常");
        }
        Assert.assertEquals("insert方法新增表内没有的user失败",1, userMapper.insert(new User(5, "aa", "123456")));//预期新增成功
    }

    @Test
    public void testSelectByPrimaryKey(){
        Assert.assertNull(userMapper.selectByPrimaryKey(-1));//预期 不存在的Id 的user 返回结果为Null
        System.out.println(userMapper.selectByPrimaryKey(2));//输出存在的Id 的user的所有信息
    }

    @Test
    public void testUpdateByPrimaryKey(){
        Assert.assertEquals(1,userMapper.updateByPrimaryKey(new User(3,"awwt","123"))); //预期更新存在id的user
        Assert.assertEquals(0,userMapper.updateByPrimaryKey(new User(-1,"awwt","123"))); //预期更新不存在id的user
    }

    @Test
    public void testQueryUserIsExist(){
        Assert.assertEquals(1,userMapper.queryUserIsExist("wwt"));//确定表中存在对应名字的user     返回值为1
        Assert.assertEquals(0,userMapper.queryUserIsExist("aaa"));//确定表中不存在对应名字的user  返回值为0
    }
}
