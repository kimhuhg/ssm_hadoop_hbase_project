package hadoop.hbase;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.util.StringUtils;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于封装读取到的数据到实体类中
 * @param <T> 实体类类名
 */
public class HbaseFindBuilder<T> {
    private String family;  //列族名字
    private Result result;  //查询结果
    private T tBean; //结果输出的类
    private BeanWrapper beanWrapper; //包装tBean的对象

    /**
     * 依据列族构造HbaseFindBuilder对象
     *
     * @param family 列族
     * @param result 结果
     * @param tBean  结果要填入的实体类
     */
    public HbaseFindBuilder(String family, Result result, T tBean) {
        this.family = family;
        this.result = result;
        this.tBean = tBean;
        //获得类的包装类，用于读取写入属性值等
        beanWrapper=PropertyAccessorFactory.forBeanPropertyAccess(tBean);
    }


    /**
     * 取出写入的实体类
     * @return T 存储读取到的信息的实体类
     */
    public T fetch() {
        return tBean;
    }

    /**
     * 构建列族下面的对应列 写入到类的对应属性中
     * @param map  qualifiers->attribute 与实体类属性一一对应的map
     * @return HbaseFindBuilder<T> 自身，用于流式操作
     */
    public HbaseFindBuilder<T> build(Map<String, String> map){
        if (map == null || map.size() <= 0)
            return this;
        byte[] qualifierByte = null;
        for (String key : map.keySet()) {
            String attribute = map.get(key).trim();
            if (StringUtils.isEmpty(attribute))
                continue;
            qualifierByte = result.getValue(family.getBytes(), key.trim().getBytes());
            if (qualifierByte != null && qualifierByte.length > 0)
                beanWrapper.setPropertyValue(attribute, Bytes.toString(qualifierByte));
        }
        return this;
    }

    /**
     * 取出列族下面的列，要求对象的属性要和对应的qualifier名字一样且大小写敏感
     * @param qualifiers 二级列列表
     * @return HbaseFindBuilder<T> 自身，用于流式操作
     */
    public HbaseFindBuilder<T> build(String... qualifiers){
        if (qualifiers == null || qualifiers.length == 0)
            return this;
        byte[] qualifierByte = null;
        for (String qualifier : qualifiers) {
            if (StringUtils.isEmpty(qualifier))
                continue;
            qualifier = qualifier.trim();
            qualifierByte = result.getValue(family.getBytes(), qualifier.getBytes());
            if (qualifierByte != null && qualifierByte.length > 0)
                beanWrapper.setPropertyValue(qualifier, Bytes.toString(qualifierByte));
        }
        return this;
    }

    /**
     * 将rowkey 写入到实体类的attribute中，要求存在这个属性
     * @param rowKey 对应实体类中的属性名称
     * @return HbaseFindBuilder<T> 自身，用于流式操作
     */
    public HbaseFindBuilder<T> buildRow(String rowKey){
        if (rowKey == null || rowKey.length() == 0)
            return this;
        byte[] rowKeyBytes=result.getRow();
        if (rowKeyBytes!=null&&rowKeyBytes.length>0)
            beanWrapper.setPropertyValue(rowKey,Bytes.toString(rowKeyBytes));
        return this;
    }
}
