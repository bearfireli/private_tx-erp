package com.hntxrj.txerp.controller.base;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.entity.PageBean;
import com.hntxrj.txerp.server.SaveStgidServer;
import com.hntxrj.txerp.util.WriteLog;
import com.hntxrj.txerp.vo.JsonVo;
import com.hntxrj.txerp.vo.JsonVoAndPage;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/stgid")
@Scope("prototype")
@EnableAsync
public class SaveStgidController {

    private final SaveStgidServer saveStgidServer;

    private final WriteLog writeLog;

    @Autowired
    public SaveStgidController(SaveStgidServer saveStgidServer, WriteLog writeLog) {
        this.saveStgidServer = saveStgidServer;
        this.writeLog = writeLog;
    }

    /**
     * 砼标号管理     sp_insertUpDel_SM_GradePriceInfo
     *
     * @param mark            0 插入 1 更新 2 删除
     * @param compid          企业
     * @param id              无
     * @param stgid           砼标号
     * @param grade           等级
     * @param pumpprice       泵送价格
     * @param notpumpprice    非泵送价格
     * @param pricedifference 差价
     * @param isdefault       是否默认
     * @param towercraneprice 塔吊价格
     * @param recstatus       状态
     * @param opid            用户
     * @return json
     */
    @RequestMapping(value = "/savestgid")
    public JsonVo savestgid(String mark,
                            String compid,
                            String id,
                            String stgid,
                            String grade,
                            Double pumpprice,
                            Double notpumpprice,
                            Double pricedifference,
                            @RequestParam(defaultValue = "1") Byte isdefault,
                            Double towercraneprice,
                            @RequestParam(defaultValue = "1") Byte recstatus,
                            String opid) {
        JsonVo jsonVo = new JsonVo();
        if (StringUtils.isEmpty(id)) {
            id = null;
        }
//        JSONArray jsonArray = saveStgidServer.sp_insertUpDel_SM_StgidInfoPrice(   mark ,  compid, id , stgid,  grade, pumpprice, notpumpprice, opid);

        JSONArray jsonArray = saveStgidServer.sp_insertUpDel_SM_GradePriceInfo(mark, compid, id, stgid, grade, pumpprice, notpumpprice, pricedifference, isdefault, towercraneprice, recstatus, opid);
        //返回值,判断是否修改成 功
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jsonArray.getJSONArray(0).get(0);
            String result = jsonObject.getString("result");
            //添加成功
            if (result.contains("成功")) {
                jsonVo.setCode(0);
                jsonVo.setMsg(result);
                jsonVo.setData(jsonArray);
                return jsonVo;
                //添加失败
            } else {
                jsonVo.setCode(1);
                jsonVo.setMsg(result);
                jsonVo.setData(jsonArray);
                return jsonVo;
            }

        } catch (Exception e) {
            e.printStackTrace();
            jsonVo.setCode(1);
            jsonVo.setData(jsonArray);
            writeLog.init("砼标号添加", "1", e.toString(), opid);
            try {
                writeLog.write();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return jsonVo;
        }

    }

    /**
     * 查看砼标号   存储过程  sp_quancha_SM_GradePriceInfo
     *
     * @param opid     用户
     * @param compid   企业
     * @param stgid    砼标号
     * @param grade    强度等级
     * @param PageSize 页长度
     * @param currPage 当前页
     * @return json
     */
    @RequestMapping(value = "/spquanchaSMGradePriceInfo")
    public JsonVo sp_quancha_SM_GradePriceInfo(String opid,
                                               String compid,
                                               String stgid,
                                               String grade,
                                               Integer PageSize,
                                               Integer currPage) {
        JsonVoAndPage jsonVoAndPage = new JsonVoAndPage();
        if (StringUtils.isEmpty(stgid)) {
            stgid = null;
        }
        if (StringUtils.isEmpty(grade)) {
            grade = null;
        }
        JSONArray jsonArray = saveStgidServer.sp_quancha_SM_GradePriceInfo(opid, compid, stgid, grade, PageSize, currPage);
        //创建分页
        PageBean pageBean = new PageBean(PageSize, currPage);
        if (jsonArray != null && jsonArray.size() > 0) {
            //获取分页总条数
            JSONObject jsonObject = (JSONObject) jsonArray.getJSONArray(1).get(0);
            String result = jsonObject.getString("recordCount");
            pageBean.setRecordCount(Integer.parseInt(result));
            jsonVoAndPage.setCode(0);
            jsonVoAndPage.setMsg("ok");
            jsonVoAndPage.setData(jsonArray);
            jsonVoAndPage.setPageBean(pageBean);
            return jsonVoAndPage;
        } else {
            jsonVoAndPage.setCode(1);
            jsonVoAndPage.setData(jsonArray);
            jsonVoAndPage.setMsg("on");
            return jsonVoAndPage;
        }
    }


}
