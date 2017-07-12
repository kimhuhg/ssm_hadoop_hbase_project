package controller;


import beans.MyFieldError;
import beans.MyUser;
import beans.User;
import beans.FormUser;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import service.interfaces.UserService;

import javax.annotation.Resource;
import javax.jws.Oneway;
import javax.management.ObjectName;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Shinelon on 2017/7/8.
 */
@Controller
@RequestMapping(path = "/helloWorld")
public class HelloController {

    //   /helloWorld/say get方法就转到这个方法中
    @RequestMapping(path = "/say")
    public String say() {
        //redirect 重定向 修改浏览器地址  效率低  但是地址都是基地址开始算起
//        return "redirect:/index.html";

        //forward 是在原来的地址的基础上 效率高  但是是站在原来地址上的
        //比如请求/hello/say ,然后 forward:/index.html 页面显示的index.html里面的所有地址都变成/hello/的地址 比如<script src="js/jquery.js"/>
        //就会请求不到，因为实际的它的请求地址是 /hello/js/jquery.js 会出现404 错误
        //要解决 有2个方法 1是html里面的资源调用使用绝对地址，2是redirect
        return "redirect:/index.html";
    }

    @RequestMapping(path = "/goMybatisTest")
    public String goTo() {
        return "redirect:/mybatisTest.html";
    }

    @RequestMapping(path = "/stringResult")
    @ResponseBody
    public String testStringResult() {
        return "吴文涛";
    }

    @RequestMapping(path = "/testException")
    @ResponseBody
    public String testException() {
        System.out.println("控制器方法开始执行，之后要抛出异常了");
        throw new NullPointerException("ahahaha");
    }

    @RequestMapping(path = "/form")
    @ResponseBody
    public Object formHandle(@Valid FormUser formUser, BindingResult result) {
        if (!result.hasErrors())
            return new User(formUser.getId(), formUser.getUsername(), formUser.getPassword());
        else {
            List<FieldError> errors = result.getFieldErrors();
            List<MyFieldError> fieldErrors = new ArrayList<>();
            for (FieldError temp : errors)
                fieldErrors.add(new MyFieldError(temp.getField(), temp.getDefaultMessage()));
            return fieldErrors;
        }
    }


    /*
    * 下面是 mybatis测试，测试单表的增删查改
    * */
    @Resource
    private UserService userService;

    @RequestMapping(path = "/getUserById")
    @ResponseBody
    public Object getUserFromDataByMybatis(Integer id) {
        if (id != null) {
            User user = userService.selectByPrimaryKey(id);
            if (user != null)
                return user;
            else
                return new MyFieldError("userNull", "查询不到用户");
        } else
            return new MyFieldError("username", "用户名不为空");
    }

    @RequestMapping(path = "/updateUserById")
    @ResponseBody
    public Object updateUserById(@Valid User user, BindingResult result) {
        if (!result.hasErrors()) {
            if (userService.updateByPrimaryKey(user) == 0)     //没有更新成功
                return new MyFieldError("updateFail", "更新用户失败，可能原因是不存在该用户");
            else    //更新成功
                return "updateSuccess";
        } else {
            List<FieldError> errors = result.getFieldErrors();
            List<MyFieldError> fieldErrors = new ArrayList<>();
            for (FieldError temp : errors)
                fieldErrors.add(new MyFieldError(temp.getField(), temp.getDefaultMessage()));
            return fieldErrors;
        }
    }

    @RequestMapping(path = "/deleteUserById")
    @ResponseBody
    public Object deleteUserById(Integer id) {
        if (id != null) {
            int result = userService.deleteByPrimaryKey(id);
            if (result == 0)
                return new MyFieldError("deleteFail", "删除用户失败，可能原因是用户不存在");
            else
                return "successDelete";
        } else
            return new MyFieldError("username", "用户名不为空");
    }

    @RequestMapping(path = "/insertUser")
    @ResponseBody
    public Object insertUser(@Valid User user, BindingResult result) {
        if (!result.hasErrors()) {
            int insertResult = userService.insert(user);
            if (insertResult == 0)     //没有插入成功，在插入过程中发生了部分未知异常
                return new MyFieldError("insertFail", "插入用户失败");
            else if (insertResult == -1)
                return new MyFieldError("duplicateInsert", "用户已经存在，不允许重复插入");
            else    //更新成功  insertResult=1
                return "insertSuccess";
        } else {
            List<FieldError> errors = result.getFieldErrors();
            List<MyFieldError> fieldErrors = new ArrayList<>();
            for (FieldError temp : errors)
                fieldErrors.add(new MyFieldError(temp.getField(), temp.getDefaultMessage()));
            return fieldErrors;
        }
    }

    @RequestMapping(path = "/getUserBook")
    @ResponseBody
    public Object getUserBook(Integer id) {
        /*可能查询的用户是存在的，但是他的部分属性信息不存在*/
        if (id != null) {
            MyUser myUser = userService.selectMyUserByPrimaryKey(id);
            return myUser == null ? new MyFieldError("queryFail", "查询用户失败，可能原因是用户不存在") : myUser;
        } else
            return new MyFieldError("username", "用户名不为空");
    }

    @RequestMapping(path = "/transactionalTest")
    @ResponseBody
    public Object transactionalTest(@Valid User user, BindingResult result){
        if (!result.hasErrors()) {
            return userService.transactionalTest(user);
        } else {
            List<FieldError> errors = result.getFieldErrors();
            List<MyFieldError> fieldErrors = new ArrayList<>();
            for (FieldError temp : errors)
                fieldErrors.add(new MyFieldError(temp.getField(), temp.getDefaultMessage()));
            return fieldErrors;
        }
    }
}
