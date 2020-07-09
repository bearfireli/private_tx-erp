package com.hntxrj.txerp.api;

import com.hntxrj.txerp.server.FormulaService;
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
public class FormulaApiTest {
    private MockMvc mockMvc;

    @Resource
    private FormulaService formulaService;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FormulaApi(formulaService)).build();
    }

    @Test
    public void testTheoryFormulaList() throws Exception {
        String compid = "01";                  // 企业id
        String IdentifyNumber = "1";            // 配比模板类型(1:实际配比,2:理论配比)
        mockMvc.perform(post("/api/formula/theoryFormulaList")
                .contentType(MediaType.APPLICATION_JSON)
                .param("compid", compid)
                .param("IdentifyNumber", IdentifyNumber)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
