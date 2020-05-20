package com.hntxrj.txerp.services;

import com.hntxrj.txerp.server.StockService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
class StockServiceTest {
    private String compid = "01";
    private Integer stirId = 1;


    @Autowired
    private StockService stockService;


    @Test
    void getSelectStock() {
        List<String> selectStock = stockService.getSelectStock(compid);
        assert selectStock != null;
    }
}