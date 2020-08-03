package com.hntxrj.txerp.api;

import com.hntxrj.txerp.server.ConsumeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ConsumeApiTest {
    private MockMvc mockMvc;

    @Resource
    private ConsumeService consumeService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ConsumeApi(consumeService)).build();
    }

    @Test
    @Transactional
    @Rollback
    public void testGetErrorProductList() throws Exception {
        String COMP_ID = "01";
        String BEGIN_TIME = "1556673952000";
        String END_TIME = "1564622752000";
        mockMvc.perform(post("/api/consume/getErrorProductList")
                .contentType(MediaType.APPLICATION_JSON)
                .param("compid", COMP_ID)
                .param("beginTime", BEGIN_TIME)
                .param("endTime", END_TIME)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
