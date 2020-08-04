package com.hntxrj.txerp.api;

import com.hntxrj.txerp.server.BuilderService;
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
public class BuilderApiTest {
    private MockMvc mockMvc;
    @Resource
    private BuilderService builderService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new BuilderApi(builderService)).build();
    }

    @Test
    public void testGetBuildTaskSaleInvoiceList() throws Exception {
        mockMvc.perform(post("/api/builder/getBuildTaskSaleInvoiceList")
                .contentType(MediaType.APPLICATION_JSON)
                .param("buildId", "1")
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    @Rollback
    public void testAddBuilderInfo() throws Exception {
        String COMP_ID = "01";
        String BUILDER_NAME = "测试施工单位全称";
        String BUILDER_SHORT_NAME = "测试施工单位简称";
        String ADDRESS = "盛地大厦";
        String CORPORATION = "张四";
        String FAX = "5847-5877";
        String PHONE = "15874524885";
        mockMvc.perform(post("/api/builder/addBuilderInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .param("compid", COMP_ID)
                .param("builderName", BUILDER_NAME)
                .param("builderShortName", BUILDER_SHORT_NAME)
                .param("address", ADDRESS)
                .param("Corporation", CORPORATION)
                .param("fax", FAX)
                .param("phone", PHONE)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
