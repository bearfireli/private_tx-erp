package com.hntxrj.txerp.api;

import com.hntxrj.txerp.api.BuilderApi;
import com.hntxrj.txerp.server.BuilderService;
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
}
