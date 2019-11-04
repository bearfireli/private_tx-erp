package com.hntxrj.txerp.services;

import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.entity.EppAddress;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
class EppAddressServiceTest {

    @Autowired
    private EppAddressService eppAddressService;
    private String eppCode = "G010000002"; // 工程代号
    private String compid = "01"; // 企业id
    private EppAddress addAddress = null; // 添加成功的地址
    private Integer taskSaleInvoiceId = 1; // 小票id
    private JSONObject addressObj = new JSONObject(); // 位置信息
    private String token = "";

    public EppAddressServiceTest() {
        //TODO: 后续需要修改根据实际值进行保存
        addressObj.put("x", 123);
        addressObj.put("y", 345.02);
        addressObj.put("addressName", "奥林匹克体育中心");
    }


    @Test
    void addEppAddress() throws ErpException {
        addAddress = eppAddressService.addEppAddress(compid, eppCode, addressObj.toJSONString(),
                EppAddress.ADDRESS_TYPE_MANUAL_SETTING, "单元测试");
        assert addAddress != null;
    }

    @Test
    void getAddressByEpp() {
        List<EppAddress> dr = eppAddressService.getAddressByEpp(eppCode, compid);
        assert dr.size() > 0;
    }

    @Test
    void delEppAddress() {
        eppAddressService.delEppAddress(addAddress.getId());
    }

    @Test
    void saveDriverLocation() throws ErpException {
        eppAddressService.saveDriverLocation(taskSaleInvoiceId, compid, addressObj.toJSONString(), token);
    }
}