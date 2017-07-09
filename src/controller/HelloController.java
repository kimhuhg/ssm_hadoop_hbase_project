package controller;


import dao.MyFieldError;
import beans.User;
import dao.UserDAO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Shinelon on 2017/7/8.
 */
@Controller
@RequestMapping("/helloWorld")
public class HelloController {

    //   /helloWorld/say get方法就转到这个方法中
    @RequestMapping(path = "/say")
    public String say() {
//        return "redirect:/index.html";     //重定向 修改浏览器地址  效率低
        return "forward:/index.html";   //原来的地址 效率高
    }

    @RequestMapping(path = "/stringResult")
    @ResponseBody
    public String testStringResult() {
        return "吴文涛";
    }

    @RequestMapping(path = "/testException")
    @ResponseBody
    public String testException(){
        System.out.println("控制器方法开始执行，之后要抛出异常了");
        throw new NullPointerException("ahahaha");
    }


    @RequestMapping(path = "/form")
    @ResponseBody
    public Object formHandle(@Valid UserDAO userDAO, BindingResult result) {
        if (!result.hasErrors())
            return new User(userDAO.getUsername(), userDAO.getPassword());
        else {
            List<FieldError> errors = result.getFieldErrors();
            List<MyFieldError> fieldErrors=new ArrayList<>();
            for (FieldError temp : errors)
                fieldErrors.add(new MyFieldError(temp.getField(),temp.getDefaultMessage()));
            return fieldErrors;
        }
    }

}
