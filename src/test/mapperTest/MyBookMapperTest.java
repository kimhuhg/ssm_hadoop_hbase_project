package test.mapperTest;

import beans.MyBook;
import dao.MyBookMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 测试MyBookMapper，所有测试方法命名为test+MyBookMapper对应方法
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test/configurationFiles/testMapper/applicationContext.xml"})
//声明事务 只要声明了这个，默认自带回滚，无论成功与否，不声明事务，就会对数据库数据影响比如删除，更新等
//如果需要某个方法能在数据库中显示结果就在对应方法上添加@Rollback(false)即可 比如Insert方法
@Transactional
public class MyBookMapperTest {
    /**
     * 现在数据库里面的book数据一共有4个
     * id分别为 1 2 3 4
     */

    @Resource
    private MyBookMapper myBookMapper;

    @Test
    public void testDeleteByPrimaryKey() {
        Assert.assertEquals("deleteByPrimaryKey方法删除不存在id：预期结果和实际结果不一致", 0, myBookMapper.deleteByPrimaryKey(-1));
        Assert.assertEquals("deleteByPrimaryKey方法删除存在id:预期结果和实际结果不一致", 1, myBookMapper.deleteByPrimaryKey(1));
    }

    @Test
//    @Rollback(false)
    public void testInsert() {
        try {
            myBookMapper.insert(new MyBook(1, "追风筝的人", 3));//预期新增失败，会抛出DuplicateKeyException异常
        }catch (DuplicateKeyException e){
            System.out.println("新增已存在值 预期出现DuplicateKeyException.class 异常");
        }
        Assert.assertEquals("insert方法新增表内没有的book失败",1,myBookMapper.insert(new MyBook(10, "追风筝的人", 3)));//预期新增成功
    }

    @Test
    public void testSelectByPrimaryKey() {
        Assert.assertNull(myBookMapper.selectByPrimaryKey(-1));//预期 不存在的Id 的书 返回结果为Null
        Assert.assertEquals("选择失败",new MyBook(1,"追风筝的人",3),myBookMapper.selectByPrimaryKey(1));//预期 存在的Id 的书 相同
    }

    @Test
    public void testUpdateByPrimaryKey() {
        Assert.assertEquals(1,myBookMapper.updateByPrimaryKey(new MyBook(1,"123",3))); //预期更新存在id并且符合userid外键约束的书
        Assert.assertEquals(0,myBookMapper.updateByPrimaryKey(new MyBook(-1,"123",3))); //预期更新不存在id但符合userid外键约束的书
        Assert.assertEquals(0,myBookMapper.updateByPrimaryKey(new MyBook(-1,"123",-1))); //预期更新不存在id也不符合userid外键约束的书
        try {
            myBookMapper.updateByPrimaryKey(new MyBook(1, "123", 10));//预期更新存在id但是符合userid外键约束的书，会产生异常
        }catch (DataIntegrityViolationException e){
            System.out.println("更新表中书信息，出现外键约束异常");
        }
    }

}
