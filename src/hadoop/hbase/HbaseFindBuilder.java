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
    }


    /**
     * 取出列族下面的对应列 写入到类的对应属性中
     * @param map qualifiers与实体类属性一一对应map
     * @return T 存储读取到的信息的实体类
     */
    public T fetch(Map<String, String> map) {
        if (map == null || map.size() <= 0)
            return null;
        //将类的所有属性 写入到fieldsMap中
        Map<String, PropertyDescriptor> fieldsMap = new HashMap<>();
        PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(tBean.getClass());
        for (PropertyDescriptor p : propertyDescriptors)
            if (p.getWriteMethod() != null)
                fieldsMap.put(p.getName(), p);
        //获得类的包装类，用于读取写入属性值等
        BeanWrapper beanWrapper=PropertyAccessorFactory.forBeanPropertyAccess(tBean);

        PropertyDescriptor p = null;
        byte[] qualifierByte = null;
        for (String key : map.keySet()) {
            String attribute = map.get(key).trim();
            if (StringUtils.isEmpty(attribute))
                continue;
            p = fieldsMap.get(attribute);
            qualifierByte = result.getValue(family.getBytes(), key.trim().getBytes());
            if (qualifierByte != null && qualifierByte.length > 0)
                beanWrapper.setPropertyValue(attribute, Bytes.toString(qualifierByte));
        }
        return tBean;
    }

    /**
     * 取出列族下面的列，要求对象的属性要和对应的qualifier名字一样且大小写敏感
     * @param qualifiers 二级列列表
     * @return T 存储读取到的信息的实体类
     */
    public T fetch(String... qualifiers) {
        if (qualifiers == null || qualifiers.length == 0)
            return null;
        //获得类的包装类，用于读取写入属性值等
        BeanWrapper beanWrapper=PropertyAccessorFactory.forBeanPropertyAccess(tBean);
        byte[] qualifierByte = null;
        for (String qualifier : qualifiers) {
            if (StringUtils.isEmpty(qualifier))
                continue;
            qualifier = qualifier.trim();
            qualifierByte = result.getValue(family.getBytes(), qualifier.getBytes());
            if (qualifierByte != null && qualifierByte.length > 0)
                beanWrapper.setPropertyValue(qualifier, Bytes.toString(qualifierByte));
        }
        return tBean;
    }
}
