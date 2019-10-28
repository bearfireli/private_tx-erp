package com.hntxrj.txerp.services.impl;

import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.web.ResultVO;
import com.hntxrj.txerp.services.TaskSaleInvoiceService;
import com.hntxrj.txerp.vo.TaskSaleInvoiceDetailVO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class TaskSaleInvoiceServiceImpl implements TaskSaleInvoiceService {

    @Value("${app.erp-phone}")
    private String erpPhone;

    @Override
    public TaskSaleInvoiceDetailVO getTaskSaleInvoice(Integer id, String compid) throws ErpException {

        OkHttpClient client = new OkHttpClient();

        FormBody formBody = new FormBody.Builder()
                .add("compid", compid)
                .add("id", String.valueOf(id))
                .build();
        Request request = new Request.Builder()
                .header("pid", "25")
                .header("app_token", "38275t285gbhfd")
                .url(erpPhone + "api/taskPlan/getTaskSaleInvoiceDetail")
                .post(formBody)
                .build();

        try {
            Response resp = client.newCall(request).execute();
            if (!resp.isSuccessful()) {
                log.error("获取小票详情失败-错误代号：" + resp.code());
                return null;
            }
            if (resp.body() != null) {
                String respBody = resp.body().string();
                log.info("【resultBody】respBody={}", respBody);

                ResultVO resultVO = JSONObject.parseObject(respBody, ResultVO.class);
                if (resultVO.getCode() != 0) { // ResultVO code不为0说明异常，将异常抛出。
                    throw new ErpException(resultVO.getCode(), resultVO.getMsg());
                }
                return JSONObject.parseObject(resultVO.getData().toString(), TaskSaleInvoiceDetailVO.class);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
