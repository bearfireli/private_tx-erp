package com.hntxrj.txerp.services;

import com.hntxrj.txerp.api.DriverApi;
import com.hntxrj.txerp.server.DriverService;
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
public class DriverApiTest {
    private MockMvc mockMvc;

    @Resource
    private DriverService DriverService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DriverApi(DriverService)).build();
    }


    @Test
    public void testUpdateVehicleStatus() throws Exception {
        // 车辆状态
        String VEHICLE_STATUS = "16";
        // 企业id
        String COMP_ID = "01";
        // 回厂类型 1:自动回厂； 0:手动回厂
        String TYPE = "1";
        // 司机代号
        String PERSON_CODE = "R010000003";

        mockMvc.perform(post("/driver/updateVehicleStatus")
                .contentType(MediaType.APPLICATION_JSON)
                .param("compid", COMP_ID)
                .param("driverCode", PERSON_CODE)
                .param("vehicleStatus", VEHICLE_STATUS)
                .param("type", TYPE)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
