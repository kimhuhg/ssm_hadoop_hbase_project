package service.impl;

import hadoop.hbase.HBaseResultBuilder;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Service;
import service.interfaces.BigDataService;
import test.testBeans.MyResult;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Shinelon on 2017/7/27.
 */
@Service
public class BigDataServiceImpl implements BigDataService {
    @Resource
    private HbaseTemplate hbaseTemplate;

    @Override
    public MyResult getMyResultByRowKey(String table,String rowKey) {
        Map<String, String> map = new HashMap<>();
        map.put("Z", "z");
        map.put("ZRCD", "zrcd");
        map.put("STCD", "stcd");
        map.put("Q", "q");
        map.put("TM", "tm");
        return hbaseTemplate.get(table,rowKey,
                (result, i) -> new HBaseResultBuilder<>("hydro",result,new MyResult()).build(map).fetch());
    }
}
