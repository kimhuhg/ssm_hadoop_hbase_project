package service.interfaces;

import test.testBeans.MyResult;

/**
 * Created by Shinelon on 2017/7/27.
 */
public interface BigDataService {
    //获得某一个rowKey下的数据
    public MyResult getMyResultByRowKey(String table,String rowKey);
}
