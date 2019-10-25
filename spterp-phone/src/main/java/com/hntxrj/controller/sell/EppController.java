package com.hntxrj.controller.sell;

import com.hntxrj.entity.PageBean;
import com.hntxrj.server.EppService;
import com.hntxrj.vo.JsonVo;
import com.hntxrj.vo.JsonVoAndPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 功能:   工程控制器
 *
 * @Auther 李帅
 * @Data 2017-08-11.上午 10:40
 */
@RestController
@RequestMapping("/produce")
@Scope("prototype")
@EnableAsync
public class EppController {

    private final EppService eppService;

    @Autowired
    public EppController(EppService eppService) {
        this.eppService = eppService;

    }

    /**
     * 加载工程列
     *
     * @param currPage 当前页码
     * @return
     */
    @RequestMapping("/getEppList")
    public JsonVo getEppList(@RequestParam(value = "currPage", defaultValue = "0") Integer currPage, String eppName, String compid) {

        JsonVoAndPage vo = new JsonVoAndPage();
        if (compid == null || compid.equals("")) {
            vo.setCode(1);
            vo.setMsg("error,compid is null");
        } else {
            vo.setCode(0);
            vo.setMsg("ok");
            PageBean pb = new PageBean(10, currPage);
            vo.setData(eppService.getEppList(eppName == null || eppName.equals("") ? null : eppName, pb, compid));
            vo.setPageBean(pb);
        }
        return vo;
    }

    @RequestMapping("/getEppInfo")
    public JsonVo getEppInfo(String eppCode, String compid) {
        JsonVoAndPage vo = new JsonVoAndPage();
        if (compid == null || compid.equals("")) {
            vo.setCode(1);
            vo.setMsg("error,compid is null");
        } else {
            vo.setData(eppService.getEppInfo(eppCode, compid));
        }
        return vo;
    }

    /**
     * 加载浇筑部位
     *
     * @param currPage 当前页码
     * @param placing  浇筑部位
     * @param eppCode  工程代号
     * @return jsoN
     */
    @RequestMapping("/getEppPlacing")
    public JsonVo getEppPlacing(@RequestParam(value = "currPage", defaultValue = "0") Integer currPage,
                                String eppCode,
                                String placing,
                                String compid) {

        JsonVoAndPage vo = new JsonVoAndPage();
        vo.setCode(0);
        vo.setMsg("ok");
        //创建分页
        PageBean pb = new PageBean(10, currPage);
        //调用dao
        vo.setData(eppService.getEppPlacing(eppCode == null || eppCode.equals("") ? null : eppCode, placing == null || placing.equals("") ? null : placing, pb, compid));
        vo.setPageBean(pb);
        return vo;
    }

}
