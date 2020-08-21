package com.hntxrj.txerp.api;

import com.hntxrj.txerp.server.DriverService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DriverApiTest {
    private MockMvc mockMvc;
    private final String COMP_ID = "01";

    @Value("${app.spterp.taskSaleInvoiceUploadPath}")
    private String taskSaleInvoiceUploadPath;

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

    @Test
    @Transactional
    @Rollback
    public void testSaveTaskSaleInvoiceReceiptSign() throws Exception {
        String RECEIPT_NUM = "6";
        String INVOICE_ID = "3";
        String JUMP_VEHICLE = "28米泵";
        File file = new File(taskSaleInvoiceUploadPath + "test" + ".png");
        boolean b = file.createNewFile();

        if (b) {
            //向file文件中写入数据。
            FileOutputStream out = new FileOutputStream(file);
            out.write(JUMP_VEHICLE.getBytes());

            MockMultipartFile firstFile = new MockMultipartFile("file", "test.png",
                    MediaType.TEXT_PLAIN_VALUE, new FileInputStream(file));
            mockMvc.perform(MockMvcRequestBuilders
                    .fileUpload("/driver/saveTaskSaleInvoiceReceiptSign")
                    .file(firstFile)
                    .param("receiptNum", RECEIPT_NUM)
                    .param("invoiceId", INVOICE_ID)
                    .param("compid", COMP_ID)
                    .param("jumpVehicle", JUMP_VEHICLE)
            )
                    .andExpect(jsonPath("$.code").value(0))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
            file.delete();
        }
    }

    @Test
    @Transactional
    @Rollback
    public void testBindDriverToInvoice() throws Exception {
        String ID = "1";
        String COMP_ID = "01";
        String vehiclePump = "test_code";

        mockMvc.perform(post("/driver/bindDriverToInvoice")
                .contentType(MediaType.APPLICATION_JSON)
                .param("id", ID)
                .param("compid", COMP_ID)
                .param("vehiclePump", vehiclePump)
                .accept(MediaType.APPLICATION_JSON)) //执行请求
                .andExpect(jsonPath("$.code").value(0))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
