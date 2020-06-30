package com.hntxrj.txerp.api;


import com.hntxrj.txerp.server.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class StockInApiTest {
    private MockMvc mockMvc;


    @Resource
    private StockInSelectService stockInService;
    @Resource
    private StockInCollectService stockInCollectService;
    @Resource
    private StockInServer stockInServer;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new StockInApi(stockInService,
                stockInCollectService, stockInServer)).build();
    }


    @Test
    public void testGetStockCheck() throws Exception {
        String COM_PID = "01";                  // 企业id
        String STI_CODE = "01201904020001";     // 过磅单号
        mockMvc.perform(post("/api/stockIn/getStockCheck")
                .contentType(MediaType.APPLICATION_JSON)
                .param("compid", COM_PID)
                .param("stICode", STI_CODE)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
