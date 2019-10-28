package com.hntxrj.txerp.util;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 实体类操作
 */
public class EntityTools {
    private final static Integer INTEGER_DEFAULT_VALUE = 0;
    private final static Long LONG_DEFAULT_VALUE = 0L;
    private final static Double DOUBLE_DEFAULT_VALUE = 0.0;
    private final static BigDecimal DECIMAL_DEFAULT_VALUE = new BigDecimal(0.0);
    private final static Date DATE_DEFAULT_VALUE = new Date(0);
    private final static String STRING_DEFAULT_VALUE = "";
    private final static Timestamp TIMESTAMP_DEFAULT_VALUE = new Timestamp(DATE_DEFAULT_VALUE.getTime());

    /**
     * 给实体类设置默认值
     *
     * @param object  实体类
     * @param ignores 要忽略的字段数据（不区分大小写）
     *
     */
    public static void setEntityDefaultValue(Object object, String[] ignores) {
        try {
            Class clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                // 判断是否忽略该字段
                if (isIgnore(fieldName, ignores)) {
                    continue;
                }
                Class fieldClass = field.getType();
                field.setAccessible(true); //设置访问权限
//                System.out.printf("%s----%s \n", fieldName, fieldClass.getName());
                if (isFieldValueNull(fieldName, object)) {
                    if (fieldClass == Integer.class) {
                        field.set(object, INTEGER_DEFAULT_VALUE);
                    } else if (fieldClass == Long.class) {
                        field.set(object, LONG_DEFAULT_VALUE);
                    } else if (fieldClass == Float.class) {
                        field.set(object, DOUBLE_DEFAULT_VALUE);
                    } else if (fieldClass == BigDecimal.class) {
                        field.set(object, DECIMAL_DEFAULT_VALUE);
                    } else if (fieldClass == Date.class) {
                        field.set(object, DATE_DEFAULT_VALUE);
                    } else if (fieldClass == String.class) {
                        field.set(object, STRING_DEFAULT_VALUE); // 设置值
                    } else if (fieldClass == Timestamp.class) {
                        field.set(object, TIMESTAMP_DEFAULT_VALUE);
                    }
                }
                if (fieldClass.getName().equals("java.lang.String")) {
                    String firstLetter = fieldName.substring(0, 1).toUpperCase();
                    String getter = "get" + firstLetter + fieldName.substring(1);
                    Method method = object.getClass().getMethod(getter);
                    String value = method.invoke(object).toString();
                    if (value == null || "null".equals(value)) {
                        field.set(object, STRING_DEFAULT_VALUE);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static void setEntityDefaultValue(Object object) {
        try {
            Class clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                // 判断是否忽略该字段
                Class fieldClass = field.getType();
                field.setAccessible(true); //设置访问权限
//                System.out.printf("%s----%s \n", fieldName, fieldClass.getName());
                if (isFieldValueNull(fieldName, object)) {
                    if (fieldClass == Integer.class) {
                        field.set(object, INTEGER_DEFAULT_VALUE);
                    } else if (fieldClass == Long.class) {
                        field.set(object, LONG_DEFAULT_VALUE);
                    } else if (fieldClass == Float.class) {
                        field.set(object, DOUBLE_DEFAULT_VALUE);
                    } else if (fieldClass == BigDecimal.class) {
                        field.set(object, DECIMAL_DEFAULT_VALUE);
                    } else if (fieldClass == Date.class) {
                        field.set(object, DATE_DEFAULT_VALUE);
                    } else if (fieldClass == String.class) {
                        field.set(object, STRING_DEFAULT_VALUE); // 设置值
                    } else if (fieldClass == Timestamp.class) {
                        field.set(object, TIMESTAMP_DEFAULT_VALUE);
                    }
                }
                if (fieldClass.getName().equals("java.lang.String")) {
                    String firstLetter = fieldName.substring(0, 1).toUpperCase();
                    String getter = "get" + firstLetter + fieldName.substring(1);
                    Method method = object.getClass().getMethod(getter);
                    String value = method.invoke(object).toString();
                    if (value == null || "null".equals(value)) {
                        field.set(object, STRING_DEFAULT_VALUE);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
    /**
     * 是否需要忽略
     *
     * @param fieldName 字段名
     * @param ignores   忽略的数组
     * @return true忽略，false不忽略
     */
    private static boolean isIgnore(String fieldName, String[] ignores) {
        for (String ig : ignores) {
            if (ig.toLowerCase().equals(fieldName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    //判断字段是否为空
    private static boolean isFieldValueNull(String fieldName, Object object) throws ClassNotFoundException {
        boolean isNUll = false;
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = object.getClass().getMethod(getter);
            Object value = method.invoke(object);
            if (value == null || value.toString().equals("null")) {
                isNUll = true;
            }
            return isNUll;
        } catch (Exception e) {
            return isNUll;
        }
    }

    //判断主键是否为空值
    private static boolean isStringFieldValueNull(String fieldName, Object object, Class fieldClass) throws ClassNotFoundException {
        boolean isNUll = false;
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = object.getClass().getMethod(getter);
            Object value = method.invoke(object);
            if (value == null || value.toString().equals("null")) {
                isNUll = true;
            } else {
                if (fieldClass == String.class && StringUtils.isBlank((String) value)) {
                    isNUll = true;
                }
            }
            return isNUll;
        } catch (Exception e) {
            return isNUll;
        }
    }


}
