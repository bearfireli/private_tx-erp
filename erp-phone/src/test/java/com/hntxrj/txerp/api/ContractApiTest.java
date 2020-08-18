package com.hntxrj.txerp.api;


import com.hntxrj.txerp.server.ContractService;
import com.hntxrj.txerp.server.SalesmanService;
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
public class ContractApiTest {
    private MockMvc mockMvc;

    @Resource
    private ContractService contractService;

    @Resource
    private SalesmanService salesmanService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ContractApi(contractService,salesmanService)).build();
    }


    @Test
    @Transactional
    @Rollback
    public void testGetTaskSaleInvoiceList() throws Exception {
        String COM_PID = "01";      // 企业id
        String taskId = "p012000001";      // 任务单号
        String appendContractNum = "0";      // 任务单号
        mockMvc.perform(post("/api/contract/appendContractNum")
                .contentType(MediaType.APPLICATION_JSON)
                .param("compid", COM_PID)
                .param("taskId", taskId)
                .param("appendContractNum", appendContractNum)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
