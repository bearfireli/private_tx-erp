package com.hntxrj.txerp.api;

import com.hntxrj.txerp.server.EppService;
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
public class EppApiTest {
    private MockMvc mockMvc;

    @Resource
    private EppService eppService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new EppApi(eppService)).build();
    }

    @Test
    @Transactional
    @Rollback
    public void testAddEppInfo() throws Exception {
        String COMP_ID = "01";
        String EPP_NAME = "测试工程名称全称";
        String SHORT_NAME = "测试工程简称";
        String ADDRESS = "盛地大厦";
        String LINK_MAN = "张四";
        String PHONE = "15888888888";
        String REMARKS = "备注";
        mockMvc.perform(post("/api/epp/addEppInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .param("compid", COMP_ID)
                .param("eppName", EPP_NAME)
                .param("shortName", SHORT_NAME)
                .param("address", ADDRESS)
                .param("linkMan", LINK_MAN)
                .param("phone", PHONE)
                .param("remarks", REMARKS)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
