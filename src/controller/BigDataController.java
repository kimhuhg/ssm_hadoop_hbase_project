package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.interfaces.BigDataService;

import javax.annotation.Resource;

@Controller
@RequestMapping(path = "/bigdata")
public class BigDataController {
    @Resource
    private BigDataService bigDataService;

    @RequestMapping("/get")
    @ResponseBody
    public Object get(String table,String rowKey){
        return bigDataService.getMyResultByRowKey(table,rowKey);
    }
}
