package com.hntxrj.txerp.server.impl;

import com.hntxrj.txerp.timsdk.ImService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class ImServiceImplTest {

    @Autowired
    private ImService imService;

    @Test
    void accountImport() {
        if (imService.accountImport("test" + System.currentTimeMillis(), "test nick", null, 0)) {
            System.out.println("导入账号成功！");
        } else {
            System.out.println("导入账号失败！");
        }
    }

    @Test
    void queryState() {
        System.out.println(imService.queryState(new String[]{"zzlhr"}));
    }

    @Test
    void sendMsg() {
        imService.sendMsg();
    }
}