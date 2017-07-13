package test;

import beans.MyUser;
import beans.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import service.interfaces.UserService;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by Shinelon on 2017/7/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test/configurationFiles/dispatcher-servlet.xml", "classpath:/test/configurationFiles/applicationContext.xml"})
//当然 你可以声明一个事务管理 每个单元测试都进行事务回滚 无论成功与否  默认是回滚的即true ,所以可以不声明
@Rollback(value = true)
//记得要在XML文件中声明事务哦~~~我是采用注解的方式
@Transactional
public class UserServiceImplTest {

    @Resource
    private UserService userService;

    @Test
    public void testSelectMyUserByPrimaryKey() throws Exception {
        int id=1;
        MyUser myUser=userService.selectMyUserByPrimaryKey(1);
        if(myUser!=null)
            System.out.println(myUser.toString());
        else
            System.out.println("null");
    }

    @Test
    public void testUpdateByPrimaryKey() throws Exception {
        userService.updateByPrimaryKey(new User(1,"123","123456"));
    }
}