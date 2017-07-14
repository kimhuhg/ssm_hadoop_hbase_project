package Interceptors;


import org.hibernate.validator.constraints.Range;
import org.springframework.core.MethodParameter;
import org.springframework.validation.BindingResult;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Iterator;


/**
 * Created by Shinelon on 2017/7/9.
 */
public class HelloInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        return true;
//        System.out.println("拦截器开始");
//        throw new NullPointerException("拦截器的空指针异常");
        //拦截器是否在校验之前呢
//        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
