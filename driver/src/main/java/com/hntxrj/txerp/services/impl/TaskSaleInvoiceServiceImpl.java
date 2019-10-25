package com.hntxrj.txerp.services.impl;

import com.alibaba.fastjson.JSONObject;
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

    @Value("${app.host}")
    private String host;

    @Override
    public TaskSaleInvoiceDetailVO getTaskSaleInvoice(Integer id, String compid) {

        OkHttpClient client = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("compid", compid)
                .add("id", String.valueOf(id))
                .build();
        Request request = new Request.Builder()
                .url(host + "api/taskPlan/getTaskSaleInvoiceDetail")
                .post(formBody)
                .build();
        try {
            Response resp = client.newCall(request).execute();
            if (!resp.isSuccessful()) {
                log.error("获取小票详情失败-错误代号：" + resp.code());
                return null;
            }
            log.info("请求结果:" + resp.body());
            ResultVO resultVO = JSONObject.parseObject(String.valueOf(resp.body()), ResultVO.class);
            TaskSaleInvoiceDetailVO taskSaleInvoiceDetailVO =
                    JSONObject.parseObject(resultVO.getData().toString(), TaskSaleInvoiceDetailVO.class);
            return taskSaleInvoiceDetailVO;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
