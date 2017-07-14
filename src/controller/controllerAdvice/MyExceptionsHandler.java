package controller.controllerAdvice;

import beans.MyFieldError;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.validation.ValidationException;
import java.lang.Exception;

/**
 * Created by Shinelon on 2017/7/9.
 * 全局异常处理类   也拦截拦截器抛出的异常
 */
//@EnableWebMvc
@ControllerAdvice
public class MyExceptionsHandler {


    @ExceptionHandler(Exception.class)
    public String exceptionHandle(Exception exception) {
        exception.printStackTrace();
        return "redirect:/error.html";
    }

    @ExceptionHandler(TypeMismatchException.class)
    public String typeMismatchExceptionHandle(TypeMismatchException exception){
        exception.printStackTrace();
        return "redirect:/error.html";
    }

    /**
     * NumberFormatException 异常处理
     * @param exception NumberFormatException 当要求输入int类型时候，输入了带非数字字符的字符串
     * @return 返回跳转页面
     */
    @ExceptionHandler(NumberFormatException.class)
    public String numberFormatExceptionHandle(NumberFormatException exception){
        exception.printStackTrace();
        return "redirect:/error.html";
    }

    @ExceptionHandler(NullPointerException.class)
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
