package controller;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 在web开始和结束的时候执行的
 */
@Service
public class WebInitAndDestory {
    @PostConstruct
    public void init(){
        System.out.println("web开始了");
    }

    @PreDestroy
    public void destory(){
        System.out.println("Web结束了");
    }
}
