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

    String COMP_ID = "01";

    @Test
    @Transactional
    @Rollback
    public void testAddEppInfo() throws Exception {
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

    @Test
    public void testGetContractEppList() throws Exception {
        String eppCode = "G010000176";
        String EPP_NAME = "测试工程名称全称";
        String recStatus = "1";
        String page = "1";
        String pageSize = "10";

        mockMvc.perform(post("/api/epp/getContractEppList")
                .contentType(MediaType.APPLICATION_JSON)
                .param("eppCode", eppCode)
                .param("eppName", EPP_NAME)
                .param("recStatus", recStatus)
                .param("page", page)
                .param("pageSize", pageSize)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetEppInfo() throws Exception {
        String eppCode = "G010000176";

        mockMvc.perform(post("/api/epp/getEppInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .param("eppCode", eppCode)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    @Rollback
    public void testSaveOrUpdateEppInfo() throws Exception {
        String eppCode = "test_code";
        String eppName = "test_accountingAccountCode";
        String shortName = "test_accountingAccountCode";
        String address = "test_accountingAccountCode";
        String linkMan = "test_accountingAccountCode";
        String linkTel = "test_accountingAccountCode";
        String remarks = "test_accountingAccountCode";
        String accountingAccountCode = "test_accountingAccountCode";
        String recStatus = "1";

        mockMvc.perform(post("/api/epp/saveOrUpdateEppInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .param("compid", COMP_ID)
                .param("eppCode", eppCode)
                .param("eppName", eppName)
                .param("shortName", shortName)
                .param("address", address)
                .param("linkMan", linkMan)
                .param("linkTel", linkTel)
                .param("remarks", remarks)
                .param("accountingAccountCode", accountingAccountCode)
                .param("recStatus", recStatus)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    @Rollback
    public void testChangeRecStatus() throws Exception {
        String eppCode = "G010000176";
        String recStatus = "1";

        mockMvc.perform(post("/api/epp/changeRecStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .param("eppCode", eppCode)
                .param("recStatus", recStatus)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
