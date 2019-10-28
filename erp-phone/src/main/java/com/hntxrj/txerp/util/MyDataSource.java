package com.hntxrj.txerp.util;

import lombok.Data;

/**
 * 数据库参数
 * Created by 刘浩然 on 2017/8/23.
 */
@Data
public class MyDataSource {
    private String url;

    private String username;

    private String password;

    private String driverClassName;
}
