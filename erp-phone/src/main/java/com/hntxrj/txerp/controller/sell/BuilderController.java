package com.hntxrj.txerp.controller.sell;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.entity.PageBean;
import com.hntxrj.txerp.server.BuilderService;
import com.hntxrj.txerp.util.jdbc.GetMsg;
import com.hntxrj.txerp.vo.JsonVo;
import com.hntxrj.txerp.vo.JsonVoAndPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

/**
 * 功能:   施工单位控制器
 *
 * @Auther 李帅
 * @Data 2017-08-11.上午 10:41
 */
@RestController
@RequestMapping("/produce")
@Scope("prototype")
@EnableAsync
public class BuilderController {

    private final BuilderService builderService;

    @Autowired
    public BuilderController(BuilderService builderService) {
        this.builderService = builderService;
    }


    /**
     * 加载工程列
     *
     * @param currPage 当前页码
     * @return
     */
    @RequestMapping("/getBuilderList")
    public JsonVo getEppList(@RequestParam(value = "currPage", defaultValue = "0") Integer currPage, String builderName, String compid) {
        JsonVoAndPage vo = new JsonVoAndPage();
        //参数不能为空
        if (compid == null || compid.equals("")) {
            //为空直接返回  code = 1
            vo.setCode(1);
            vo.setMsg("error,compid is null");
        } else {
            //否则  成功
            vo.setCode(0);
            vo.setMsg("ok");
            PageBean pb = new PageBean(10, currPage);
            //调用dao
            vo.setData(builderService.getEppList(builderName == null || "".equals(builderName) ? null : builderName, pb, compid));
            vo.setPageBean(pb);
        }
        return vo;
    }

    @RequestMapping("/getBuilder")
    public JsonVo getEppList(String builderCode, String compid) {
        JsonVoAndPage vo = new JsonVoAndPage();

        //参数不能为空
        if (compid == null || compid.equals("")) {
            //为空直接返回  code = 1
            vo.setCode(1);
            vo.setMsg("error,compid is null");
        } else {
            //否则  成功
            vo.setCode(0);
            vo.setMsg("ok");
            vo.setData(builderService.getBuilderInfo(builderCode, compid));
        }

        return vo;
    }

    /**
     * 添加修改删除  施工单位
     *
     * @param mark             操作标识 0 插入 1 更新 2 删除
     * @param builderName      施工单位名称
     * @param builderShortName 施工单位名称简称
     * @param address          地址
     * @param corporation      法人
     * @param fax              传真
     * @param linkTel          联系电话
     * @return
     */
    @RequestMapping("/insertUpDelBuilder")
    public JsonVo insertUpDelSMBuilderInfo(String compid,

                                             String opid,
                                             Integer mark,
                                             String builderCode,
                                             String builderName,
                                             String builderShortName,
                                             String address,
                                             String corporation,
                                             String fax,
                                             String linkTel,
                                             HttpServletRequest request) {
        JsonVo vo = new JsonVo();
        //设置code = 1
        vo.setCode(1);
        //参数不能为空
        if (compid == null || compid.equals("")) {
            vo.setMsg("error,compid is null");
        } else if (opid == null || opid.equals("")) {
            vo.setMsg("error,opid is null");
        } else {
            //设置code = 0  成功
            vo.setCode(0);
            JSONArray jsonArray = builderService.insertUpDel_SM_BUILDERINFO(mark, compid, opid, builderCode, builderName, builderShortName,
                    address, new Timestamp(System.currentTimeMillis()), corporation, fax, linkTel, (byte) 1);

            System.out.println(vo);
            System.out.println(jsonArray);
            GetMsg.getJsonMsg(vo, jsonArray);
        }
        return vo;
    }
}
