package test.mapperTest;

import beans.MyUser;
import dao.MyUserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 测试MyUserMapper，所有测试方法命名为test+MyUserMapper对应方法
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test/configurationFiles/testMapper/applicationContext.xml"})
@Transactional
public class MyUserMapperTest {
    /**
     * 现在数据库里面的user数据一共有4个
     * id分别为 1 2 3 4
     */
    @Resource
    private MyUserMapper myUserMapper;

    @Test
    public void testDeleteByPrimaryKey(){
        Assert.assertEquals("deleteByPrimaryKey方法删除不存在id：预期结果和实际结果不一致", 0, myUserMapper.deleteByPrimaryKey(-1));
        Assert.assertEquals("deleteByPrimaryKey方法删除存在id:预期结果和实际结果不一致", 1, myUserMapper.deleteByPrimaryKey(1));
        //删除存在的但是有book外键约束的user
        try{
            myUserMapper.deleteByPrimaryKey(3);
        }catch (org.springframework.dao.DataIntegrityViolationException e){
            System.out.println("删除外键约束的user失败");
        }
    }

    @Test
    public void testInsert(){
        try {
            myUserMapper.insert(new MyUser(1, "aa", "123456"));//预期新增失败，会抛出DuplicateKeyException异常
        }catch (DuplicateKeyException e){
            System.out.println("新增已存在值 预期出现DuplicateKeyException.class 异常");
        }
        Assert.assertEquals("insert方法新增表内没有的user失败",1, myUserMapper.insert(new MyUser(5, "aa", "123456")));//预期新增成功
    }

    @Test
    public void testSelectByPrimaryKey(){
        Assert.assertNull(myUserMapper.selectByPrimaryKey(-1));//预期 不存在的Id 的user 返回结果为Null
        System.out.println(myUserMapper.selectByPrimaryKey(2));//输出存在的Id 的user的所有信息  这个id 对应的user 没有book
        System.out.println(myUserMapper.selectByPrimaryKey(3));//输出存在的Id 的user的所有信息  这个id 对应的user 有2本book
    }

    @Test
    public void testUpdateByPrimaryKey(){
        Assert.assertEquals(1,myUserMapper.updateByPrimaryKey(new MyUser(3,"awwt","123"))); //预期更新存在id的user
        Assert.assertEquals(0,myUserMapper.updateByPrimaryKey(new MyUser(-1,"awwt","123"))); //预期更新不存在id的user
    }
}
