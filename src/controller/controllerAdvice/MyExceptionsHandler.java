package controller.controllerAdvice;

import beans.MyFieldError;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;
import java.lang.Exception;

/**
 * Created by Shinelon on 2017/7/9.
 * 全局异常处理类   也拦截拦截器抛出的异常
 */

@ControllerAdvice
public class MyExceptionsHandler {


    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String exceptionHandle(Exception exception) {
//        System.out.println("第一个处理"+exception.getMessage());
        exception.printStackTrace();
        return "redirect:/error.html";
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public String nullPointerExceptionHandle(NullPointerException exception) {
        System.out.println("第一个处理"+exception.getMessage());
        return "redirect:/error.html";
    }



    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handle(ValidationException exception) {
        System.out.println("验证错误"+exception.getCause()+"   "+exception.getMessage());
        return "验证错误";
    }

    //用户插入时候 已经存在用户 就会抛出这个异常，建议在插入的时候 优先判断用户是否存在，就可以避免这个异常
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handleDuplicateKeyException(DuplicateKeyException exception){
        return new MyFieldError("insertUserfail", "controllerAdvice 插入用户失败");
    }
}
