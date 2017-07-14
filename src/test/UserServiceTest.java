package test;

import beans.MyBook;
import beans.MyUser;
import beans.User;
import dao.MyUserMapper;
import dao.UserMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import service.interfaces.UserService;
import javax.annotation.Resource;
import java.util.ArrayList;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test/configurationFiles/testUserService/applicationContext.xml"})
public class UserServiceTest {
        /*
        * 说明：
        * 由于dao层是Mock模拟的，所以假设dao层不存在任何问题
        * 所以对数据库 做以下假设
        * 存在2个表
        * user table:   id  username    password
        * user data:    1   123         123456
        *               2   wwt         123456
        *               3   awwt        123456
        *
        * book table:   id  bookname    userId
        *               1   book1       1
        *               2   book2       1
        *               3   book3       2
        *               4   book4       2
        *
        * */

    private ArrayList<User> users;
    private ArrayList<MyUser> myUsers;
    private ArrayList<MyBook> books;

    @Resource
    @InjectMocks
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private MyUserMapper myUserMapper;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        /*
        * 初始化数据
        * */
        //初始化users
        users=new ArrayList<User>();
        users.add(new User(1,"123","123456"));
        users.add(new User(2,"wwt","123456"));
        users.add(new User(3,"awwt","123456"));

        //初始化books
        books=new ArrayList<MyBook>();
        books.add(new MyBook(1,"book1",1));
        books.add(new MyBook(2,"book2",1));
        books.add(new MyBook(3,"book3",2));
        books.add(new MyBook(4,"book4",2));

        //初始化MyUsers
        myUsers=new ArrayList<MyUser>();
        myUsers.add(new MyUser(1,"123","123456"));
        myUsers.add(new MyUser(2,"wwt","123456"));
        myUsers.add(new MyUser(3,"awwt","123456"));
        ArrayList<MyBook> user1books=new ArrayList<MyBook>();
        user1books.add(books.get(0));
        user1books.add(books.get(1));
        ArrayList<MyBook> user2books=new ArrayList<MyBook>();
        user2books.add(books.get(2));
        user2books.add(books.get(3));
        myUsers.get(0).setBooks(user1books);
        myUsers.get(1).setBooks(user2books);

        /*
        * 方法预定义返回值
        * */
        //selectByPrimaryKey方法
        when(userMapper.selectByPrimaryKey(Mockito.anyInt())).thenReturn(null);
        for (int i = 0; i < users.size(); i++)
            when(userMapper.selectByPrimaryKey(users.get(i).getId())).thenReturn(users.get(i));

        //updateByPrimaryKey方法
        when(userMapper.updateByPrimaryKey(Mockito.any(User.class))).thenReturn(0);
        for (int i = 0; i < users.size(); i++)
            when(userMapper.updateByPrimaryKey(users.get(i))).thenReturn(1);

        //deleteByPrimaryKey方法
        when(userMapper.deleteByPrimaryKey(Mockito.anyInt())).thenReturn(0);
        for (int i = 0; i < users.size(); i++)
            when(userMapper.deleteByPrimaryKey(users.get(i).getId())).thenReturn(1);

        //insert方法
        when(userMapper.insert(Mockito.any(User.class))).thenReturn(1);
        for (int i = 0; i < users.size(); i++)
            when(userMapper.insert(users.get(i))).thenReturn(-1);

        //queryUserIsExist 方法
        when(userMapper.queryUserIsExist(Mockito.anyString())).thenReturn(0);
        when(userMapper.queryUserIsExist("wwt")).thenReturn(1);
        when(userMapper.queryUserIsExist("123")).thenReturn(1);
        when(userMapper.queryUserIsExist("awwt")).thenReturn(1);

        //selectByPrimaryKey 方法的
        when(myUserMapper.selectByPrimaryKey(Mockito.anyInt())).thenReturn(null);
        when(myUserMapper.selectByPrimaryKey(myUsers.get(0).getId())).thenReturn(myUsers.get(0));
        when(myUserMapper.selectByPrimaryKey(myUsers.get(1).getId())).thenReturn(myUsers.get(1));
        when(myUserMapper.selectByPrimaryKey(myUsers.get(2).getId())).thenReturn(myUsers.get(2));

    }

    @Test
    public void testSelectByPrimaryKey(){
        System.out.println(userService.selectByPrimaryKey(-1));//获取不存在的用户
        System.out.println(userService.selectByPrimaryKey(1));//获取存在的用户
        System.out.println(userService.selectByPrimaryKey(2));//获取存在的用户
        System.out.println(userService.selectByPrimaryKey(3));//获取存在的用户
    }

    @Test
    public void testUpdateByPrimaryKey() throws Exception {
        System.out.println(userService.updateByPrimaryKey(new User()));//更新不存在的用户
        System.out.println(userService.updateByPrimaryKey(users.get(0)));//更新存在的用户
        System.out.println(userService.updateByPrimaryKey(users.get(1)));//更新存在的用户
        System.out.println(userService.updateByPrimaryKey(users.get(2)));//更新存在的用户
    }

    @Test
    public void testDeleteByPrimaryKey(){
        System.out.println(userService.deleteByPrimaryKey(0));//删除不存在的用户
        System.out.println(userService.deleteByPrimaryKey(1));//删除存在的用户
        System.out.println(userService.deleteByPrimaryKey(2));//删除存在的用户
        System.out.println(userService.deleteByPrimaryKey(3));//删除存在的用户
        System.out.println(userService.deleteByPrimaryKey(4));//删除不存在的用户
    }

    @Test
    public void testInsert(){
        System.out.println(userService.insert(new User()));  //插入没有的用户
        System.out.println(userService.insert(users.get(0)));   //插入已存在的用户
    }

    @Test
    public void testQueryUserIsExist(){
        System.out.println(userService.queryUserIsExist("wwt"));
    }

    @Test
    public void testSelectMyUserByPrimaryKey() throws Exception {
        int id=1;
        MyUser myUser=userService.selectMyUserByPrimaryKey(id);
        if(myUser!=null)
            System.out.println(myUser.toString());
        else
            System.out.println("null");
    }

}