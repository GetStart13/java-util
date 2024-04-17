package com.fqq.utils.entity;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * <p> 2022/12/14 </p>
 * 实体工具类
 *
 * @author Fqq
 */
public class EntityUtils {
    private EntityUtils() {

    }

    /**
     * 此方法每次获取都会执行一次循环，Introspector 内部做了缓存
     */
    public static Object findFieldValue(Object entity, String fieldName)
        throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(entity.getClass());
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String propertyName = propertyDescriptor.getName();
            Method readMethod = propertyDescriptor.getReadMethod();
            if (readMethod != null && !"class".equals(propertyName) && Objects.equals(propertyName, fieldName)) {
                return readMethod.invoke(entity);
            }
        }
        throw new RuntimeException("have not get method for fieldName: " + fieldName);
    }

    /**
     * 获取实体属性值
     */
    public static Object getFieldValue(Object entity, String fieldName)
        throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        return new PropertyDescriptor(fieldName, entity.getClass()).getReadMethod().invoke(entity);
    }

    public static <F, C> void copyFromFather(F father, C child) {
        copyFromFather(father, child, false);
    }

    /**
     * 通过 getter、setter 方法复制父类属性到子类，支持同类型间复制
     * 该方式为 浅拷贝
     *
     * @param father     父类
     * @param child      子类
     * @param ignoreNull 是否忽略掉 null 值
     */
    public static <F, C> void copyFromFather(F father, C child, boolean ignoreNull) {

        // 判断是否是父子类关系
        if (!isExtends(father, child)) {
            throw new ClassCastException(father.getClass().getSimpleName()
                + " and "
                + child.getClass().getSimpleName() + "is not inheritance relationship");
        }

        Class<?> fatherClass = father.getClass();
        Field[] fields = fatherClass.getDeclaredFields();
        for (Field field : fields) {
            try {
                if (!Modifier.isStatic(field.getModifiers())) {
                    PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(), fatherClass);
                    Object fatherValue = descriptor.getReadMethod().invoke(father);
                    if (!ignoreNull || fatherValue != null) {
                        descriptor.getWriteMethod().invoke(child, fatherValue);
                    }
                }
            } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                System.out.println("跳过异常时的赋值: " + e.getMessage());
            }
        }
    }

    /**
     * 判断两个类之间的是否是父子关系，JDK 有提供方法 isAssignableFrom()
     * <br/>
     * {@code  Parent.class.isAssignableFrom(Child.class)}
     * <br/>- 此方法意思并非是 父类可由子类声明，而是 子类的实例是否可分配到父类声明
     *
     * @param father 父类
     * @param child  子类
     * @return 是否是父子关系
     */
    public static <F, C> boolean isExtends(F father, C child) {
        return multiExtends(father.getClass(), child.getClass());
    }

    public static boolean multiExtends(Class<?> father, Class<?> child) {
        if (child == Object.class) {
            return false;
        }
        if (father == child) {
            return true;
        }
        return multiExtends(father, child.getSuperclass());
    }

    /**
     * 驼峰命名转下划线命名
     *
     * @param fieldName 字段名
     * @return 下划线字段名
     */
    public static String toUnderscoreCase(String fieldName) {
        StringBuilder stringBuilder = new StringBuilder();
        // Do not add "_" for the first character.
        stringBuilder.append(Character.toLowerCase(fieldName.charAt(0)));
        for (int i = 1; i < fieldName.length(); i++) {
            char c = fieldName.charAt(i);
            if (Character.isLowerCase(c)) {
                stringBuilder
                    .append(c);
            } else {
                stringBuilder
                    .append("_")
                    .append(Character.toLowerCase(c));
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 下划线命名 snake case 转小驼峰（正则匹配）
     *
     * @param columnName 字段名字
     * @return 转化结果
     */
    public static String toUpperCamelCaseRegex(String columnName) {
        // 去除开头下划线
        String replaceFirst = columnName.replaceFirst("^(_+)", "");
        return Pattern
            // 匹配下划线和字母结束的分组
            .compile("(_+[A-Za-z])")
            .matcher(replaceFirst)
            // 将分组去掉下划线，并将字母转大写
            .replaceAll(m -> m.group().replaceFirst("(_+)", "").toUpperCase())
            // 去掉所有下划线和空白字符
            .replaceAll("(_)|(\\s)", "");
    }

    /**
     * 下划线命名 snake case 转小驼峰（常规）
     *
     * @param columnName 字段名字
     * @return 转化结果
     */
    public static String toUpperCamelCase(String columnName) {
        String[] partitions = columnName.split("_");//
        StringBuilder columnNameBuilder = new StringBuilder();
        int i = 0;
        // 第一段分区不大写
        while (i < partitions.length) {
            String partition = partitions[i];
            ++i;
            if (!partition.isBlank()) {
                columnNameBuilder.append(partition);
                break;
            }
        }
        while (i < partitions.length) {
            String partition = partitions[i];
            ++i;
            if (!partition.isBlank()) {
                String letter = partition.substring(0, 1).toUpperCase();
                columnNameBuilder
                    .append(letter)
                    .append(partition.substring(1));
            }
        }
        return columnNameBuilder.toString();
    }
}
