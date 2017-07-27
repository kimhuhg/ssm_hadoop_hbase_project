package test;

import hadoop.HDFSUtil;
import hadoop.hbase.HbaseFindBuilder;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.data.hadoop.hbase.TableCallback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import test.testBeans.MyResult;
import test.testBeans.TestBean;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test/configurationFiles/testHadoop/applicationContext.xml"})
public class HadoopTest {
    @Resource
    private HbaseTemplate hbaseTemplate;
    private final String encoding = "utf-8";
    private String tableName = "bigtable_hydro";
    private String familyName = "hydro";
    private String rowKey = "09B26011340020140531";

    @Test
    public void testHadoop() throws Exception {
//        //获得文件内容
        String a = new String(HDFSUtil.readFile("/tmp-data/1.txt"), "UTF-8");
        System.out.println(a);
//        //获得指定路径下文件状态
//        FileStatus[] fileStatuses=HDFSUtil.listStatus("/");
//        for (int i = 0; i < fileStatuses.length; i++) {
//            System.out.println(fileStatuses[i].getLen()+"\t"+fileStatuses[i].getPath());
//        }
//        //上传文件到HDFS指定目录
//        HDFSUtil.copyFromLocalFile("C:\\Users\\Shinelon\\Desktop\\大三下实验和作业\\网页设计素材\\1.jpg","/tmp-data/");
//        //删除HDFS指定文件
//        System.out.println(HDFSUtil.deleteFile("/tmp-data/1.jpg"));
        //创建文件
//        String content="我是最帅的";
//        HDFSUtil.createFile("/tmp-data/1.txt",content,"UTF-8");
//        //文件重命名,同时还有移动位置的功能
//        HDFSUtil.renameFile("/2.txt","/tmp-data/1.txt");
        //列出目录下 包括子目录 所有文件信息
//        RemoteIterator<LocatedFileStatus> remoteIterator=HDFSUtil.listFiles("/tmp-data",true);
//        while (remoteIterator.hasNext()){
//            LocatedFileStatus temp=remoteIterator.next();
//            System.out.println(temp.getPath().getName()+"\t"+temp.getOwner());
//        }
        //创建目录
//        HDFSUtil.createDirectory("/tmp-data/test");
        System.out.println("以上测试都已经通过");
    }

    @Test
    public void testHbaseGetDataForBeans() throws Exception {
//         //hbase提供的 访问API
//        Configuration configuration = HBaseConfiguration.create();
//        configuration.addResource("test/configurationFiles/testHadoop/hbase-site.xml");
//        Connection conn = ConnectionFactory.createConnection(configuration);
//        HBaseAdmin hBaseAdmin = (HBaseAdmin) conn.getAdmin();
//        System.out.println(hBaseAdmin.tableExists(tableName));

        Map<String, String> map = new HashMap<>();
        map.put("Z", "z");
        map.put("ZRCD", "zrcd");
        map.put("STCD", "stcd");
        map.put("Q", "q");
        map.put("TM", "tm");
//        Scan scan=new Scan();
//        scan.addFamily(familyName.getBytes());
//        scan.setStartRow("08B26011340020150716".getBytes()); //设置开始 rowkey
//        scan.setStopRow("09B26011340020150511".getBytes()); //设置结束 rowkey
//        scan.setFilter(new SingleColumnValueFilter(
//                familyName.getBytes(),"Z".getBytes(), CompareFilter.CompareOp.EQUAL,"18.1".getBytes())); //查询Z 的值为18.1的行
//        scan.setFilter(new RowFilter(CompareFilter.CompareOp.EQUAL,
//                new RegexStringComparator("06B.*2014.*")));
//        //获得所有符合scan 过滤过的数据
//        List<MyResult> myResults=hbaseTemplate.find(tableName,scan,
//            (result, i) -> new HbaseFindBuilder<>(familyName,result,new MyResult()).build(map).fetch()
//        );
        //获得所有table下的family数据
        List<MyResult> myResults = hbaseTemplate.find(tableName, familyName,
                (result, i) -> new HbaseFindBuilder<>(familyName, result, new MyResult()).build(map).fetch()
        );
        for (MyResult myResult : myResults)
            System.out.println(myResult);

//        //获得某一条数据
//        MyResult myResult=hbaseTemplate.get(tableName,rowKey,familyName,
//                (result, i) -> new HbaseFindBuilder<>(familyName,result,new MyResult()).build(map).fetch());
//        System.out.println(myResult);
    }

    @Test
    public void testHbasePutData() {
        hbaseTemplate.put(tableName, rowKey, familyName, "ZRCD", "123".getBytes());
    }

    @Test
    public void testHbaseDeleteData() {
        hbaseTemplate.delete(tableName, rowKey, familyName, "ZRCD");
    }

    @Test
    public void testHbaseExcute() {
        Result result = hbaseTemplate.execute(tableName, hTableInterface -> {
            Get get = new Get(rowKey.getBytes());
            return hTableInterface.get(get);
        });
        System.out.println(Bytes.toString(result.getValue(familyName.getBytes(), "Z".getBytes())));
    }

    //这里将所有数据的TM列的末尾 添加了0 原来是2015-05-21 00:00:0 现在是2015-05-21 00:00:00
    @Test
    public void testHbaseUpdataData() {
        //先读取原来数据，将其put进去就好了
        //这里所有的时间戳数据全都有问题
        Map<String, String> map = new HashMap<>();
        map.put("Z", "z");
        map.put("ZRCD", "zrcd");
        map.put("STCD", "stcd");
        map.put("Q", "q");
        map.put("TM", "tm");
        List<TestBean> lists = hbaseTemplate.find(tableName, familyName,
                (result, i) -> new HbaseFindBuilder<>(familyName,result,new TestBean()).buildRow("rowKey").build(map).fetch());
        for (TestBean temp : lists) {
            hbaseTemplate.put(tableName, temp.getRowKey(), familyName, "TM", (temp.getTm().substring(0,temp.getTm().length()-1) + "0").getBytes());
        }
    }


}
