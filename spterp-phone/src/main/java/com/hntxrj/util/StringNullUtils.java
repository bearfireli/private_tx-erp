package com.hntxrj.util;


import org.springframework.util.StringUtils;

public class StringNullUtils {
    /**
     * 判断StringisEmpty  将字符""  转成null
     * @param args 可变参数
     */
    public static  void StringNull(String... args){
        //循环遍历参数数组args
        for (String arg : args) {
            if (StringUtils.isEmpty(arg)){
                 arg = null;
            }
        }
    }
    public static void main(String[] args) {
        String a = "";
        String b = "sss";

//        StringNullUtils.StringNull(a);
        System.out.println("".equals(a));
        StringNullUtils.StringNull("","",a,b);
    }

}


