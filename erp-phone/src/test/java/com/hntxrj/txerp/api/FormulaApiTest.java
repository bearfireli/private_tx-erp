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
    private final String COMP_ID = "01";                            // 企业id
    private final String IDENTIFICATION_ID = "1";                   // 配比模板类型(1:实际配比,2:理论配比)
    private final String FORMULA_CHECK_CODE = "2019000117";         // 配比检验编号
    private final String THEORY_FORMULA_CODE = "20190501-0002";     // 配比编号
    private final String STG_ID = "C20";                            // 砼标号
    private final String STIR_ID = "1";                             // 线号

    @Resource
    private FormulaService formulaService;


    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FormulaApi(formulaService)).build();
    }

    @Test
    public void testTheoryFormulaList() throws Exception {
        mockMvc.perform(post("/api/formula/theoryFormulaList")
                .contentType(MediaType.APPLICATION_JSON)
                .param("compid", COMP_ID)
                .param("IdentifyNumber", IDENTIFICATION_ID)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void testGetTheoryFormulaMode() throws Exception {
        mockMvc.perform(post("/api/formula/getTheoryFormulaMode")
                .contentType(MediaType.APPLICATION_JSON)
                .param("compid", COMP_ID)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetTheoryFormulaDetail() throws Exception {
        mockMvc.perform(post("/api/formula/getTheoryFormulaDetail")
                .contentType(MediaType.APPLICATION_JSON)
                .param("compid", COMP_ID)
                .param("stirId", STIR_ID)
                .param("stgId", STG_ID)
                .param("formulaCheckCode", FORMULA_CHECK_CODE)
                .param("theoryFormulaCode", THEORY_FORMULA_CODE)
                .param("identifyNumber", IDENTIFICATION_ID)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


}
