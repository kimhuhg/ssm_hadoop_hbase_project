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

    @ExceptionHandler(NullPointerException.class)
    public String nullPointerExceptionHandle(NullPointerException exception) {
        System.out.println("第一个处理"+exception.getMessage());
        return "redirect:/error.html";
    }
}
