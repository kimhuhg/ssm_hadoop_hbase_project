package hadoop.hbase;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 这个类存放静态方法 用于将结果转化成非实体类的结果集
 */
public class HBaseResultTransformer {
    public static String ROW_KEY="rowKey";

    /**
     * 获得与qualifier对应结果的Map
     * @param familyName  列族名
     * @param result      结果集
     * @param qualifiers  列
     * @return Map<String,String> 结果Map
     */
    public static Map<String,String> fetchMap(String familyName, Result result, String...qualifiers){
        if (qualifiers == null || qualifiers.length == 0)
            return null;
        Map<String,String> aaa=new HashMap<>();
        putIntoMap(familyName,result,aaa,qualifiers);
        return aaa.size()>0?aaa:null;
    }

    /**
     * 获得与qualifier对应结果的带上rowKey的Map
     * @param familyName  列族名
     * @param result      结果集
     * @param qualifiers  列
     * @return Map<String,String> 结果Map
     */
    public static Map<String,String> fetchMapWithRowKey(String familyName, Result result, String...qualifiers){
        if (qualifiers == null || qualifiers.length == 0)
            return null;
        Map<String,String> aaa=new HashMap<>();
        aaa.put(ROW_KEY,Bytes.toString(result.getRow()));
        putIntoMap(familyName,result,aaa,qualifiers);
        return aaa.size()>0?aaa:null;
    }

    /**
     * 从result中获取qualifiers的值填入map
     * @param familyName  列族名
     * @param result      结果集
     * @param qualifiers  列
     * @param map   要填入的map
     */
    private static void putIntoMap(String familyName,Result result,Map map,String...qualifiers){
        byte[] qualifierByte = null;
        for (String qualifier : qualifiers) {
            if (StringUtils.isEmpty(qualifier))
                continue;
            qualifier = qualifier.trim();
            qualifierByte = result.getValue(familyName.getBytes(), qualifier.getBytes());
            if (qualifierByte != null && qualifierByte.length > 0)
                map.put(qualifier, Bytes.toString(qualifierByte));
        }
    }

}
