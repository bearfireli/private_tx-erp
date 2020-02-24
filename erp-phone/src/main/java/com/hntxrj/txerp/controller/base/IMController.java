package com.hntxrj.txerp.controller.base;


import com.hntxrj.txerp.server.PushInfoService;
import com.hntxrj.txerp.server.PushTypeService;
import com.hntxrj.txerp.vo.JsonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 功能:   Im
 *
 * @Auther 陈世强
 * @Data 2020-02-18.上午 15:81
 */
@RestController
@Scope("prototype")
@EnableAsync
public class IMController {
    private final PushTypeService pushTypeService;
    private final PushInfoService pushInfoService;

    @Autowired
    public IMController(PushTypeService pushTypeService,PushInfoService pushInfoService) {
        this.pushTypeService = pushTypeService;
        this.pushInfoService = pushInfoService;
    }

    //获取推送类型
    @RequestMapping(value = "/getPushType")
    public JsonVo getPushType() {
        JsonVo jsonVo = new JsonVo();
        jsonVo.setCode(0);
        jsonVo.setMsg("ok");
        jsonVo.setData(pushTypeService.getPushType());
        return jsonVo;

    }
    //保存收信人
    @RequestMapping(value = "/saveRecipient")
    public JsonVo saveRecipient(List<String> name, String compid, int typeId ) {
        JsonVo jsonVo = new JsonVo();
        if(pushInfoService.saveRecipient(name,compid,typeId)){
            jsonVo.setCode(0);
            jsonVo.setMsg("成功！");
        }else {
            jsonVo.setCode(1);
            jsonVo.setMsg("失败！");
        }

        return jsonVo;

    }



}
