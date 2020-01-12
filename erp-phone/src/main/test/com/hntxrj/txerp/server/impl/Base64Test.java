package com.hntxrj.txerp.server.impl;

import java.util.Arrays;
import java.util.Base64;

public class Base64Test {
    public static void main(String[] args) {
        String str = "asfadgdsgjbrehtverhjvrghvwqghevqwghce qwhgceqw";
        System.out.println(Base64.getUrlEncoder().withoutPadding().encodeToString(str.getBytes()));
        System.out.println(Base64.getUrlEncoder().encodeToString(str.getBytes()));
        System.out.println(Base64.getEncoder().encodeToString(str.getBytes()));
        System.out.println(Base64.getMimeEncoder().encodeToString(str.getBytes()));

    }
}
