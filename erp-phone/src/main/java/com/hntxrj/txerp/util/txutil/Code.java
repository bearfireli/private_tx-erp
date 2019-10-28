package com.hntxrj.txerp.util.txutil;

import org.apache.commons.codec.digest.Md5Crypt;

import java.nio.charset.StandardCharsets;

/**
 * 编码
 *
 * @author 刘浩然
 * @date 2017/7/26
 */
public class Code {

    private static final String SALT = "sfhugeiowe%$^$$#@#";


    public static String saltEncoderMd5(String str) {
        //加盐
        str = str + SALT;
        return Md5Crypt.md5Crypt(str.getBytes(StandardCharsets.UTF_8));
    }


}
