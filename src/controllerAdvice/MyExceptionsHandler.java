package controllerAdvice;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

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
        System.out.println("进入异常处理");
        return "forward:/error.html";
    }


}
