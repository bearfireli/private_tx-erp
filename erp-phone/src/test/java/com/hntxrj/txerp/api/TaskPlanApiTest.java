package com.hntxrj.txerp.api;


import com.hntxrj.txerp.server.TaskPlanService;
import com.hntxrj.txerp.server.TaskSaleInvoiceService;
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
public class TaskPlanApiTest {
    private MockMvc mockMvc;

    @Resource
    private TaskPlanService taskPlanService;
    @Resource
    private TaskSaleInvoiceService taskSaleInvoiceService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new TaskPlanApi(taskPlanService,
                taskSaleInvoiceService)).build();
    }


    @Test
    public void testGetTaskSaleInvoiceList() throws Exception {
        String COM_PID = "01";      // 企业id
        mockMvc.perform(post("/api/taskPlan/getTaskSaleInvoiceList")
                .contentType(MediaType.APPLICATION_JSON)
                .param("compid", COM_PID)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
