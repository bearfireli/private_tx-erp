package com.hntxrj.txerp.services;

import com.hntxrj.txerp.api.StockApi;
import com.hntxrj.txerp.server.impl.StockServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class StockServiceTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Resource
    private StockServiceImpl stockService;

    @Before
    public void setup() {
        // 实例化mockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(new StockApi(stockService)).build();
    }

    @Test
    public void testGetRealStock() throws Exception {
        mockMvc.perform(post("/api/stock/getStock")
                .contentType(MediaType.APPLICATION_JSON)
                .param("stirId", "1")
                .param("compid", "01")
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}