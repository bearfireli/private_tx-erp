package com.hntxrj.txerp.controller.base;

import com.hntxrj.txerp.entity.PageBean;
import com.hntxrj.txerp.server.FullDownService;
import com.hntxrj.txerp.vo.JsonVo;
import com.hntxrj.txerp.vo.JsonVoAndPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 功能:  下拉控制器
 *
 * @Auther 李帅
 * @Data 2017-08-14.上午 11:00
 */
@RequestMapping("/fullDown")
@RestController
@Scope("prototype")
@EnableAsync
public class FullDownController {

    private final FullDownService fullDownService;

    @Autowired
    public FullDownController(FullDownService fullDownService) {
        this.fullDownService = fullDownService;
    }


    /**
     * 加载下拉列表
     *
     * @param id       1 浇筑方式 2  水泥品种 3  石料要求 4  泵送否 5 塌落度 6  任务单类型 7 企业信息下拉
     * @param response
     * @return
     */
    @RequestMapping(value = "/type/{id}.do")
    public JsonVo getFullDown(@PathVariable int id, HttpServletResponse response) {
        JsonVo vo = new JsonVo();
        vo.setData(fullDownService.loadFullDown(id));
        vo.setCode(0);
        vo.setMsg("ok");
        return vo;
    }

    /**
     * 加载砼标号下拉
     *
     * @param currPage      页码
     * @param CContractCode 子合同代号
     * @param ContractUID   合同UID
     * @return
     */
    @RequestMapping("/getStgid")
    public JsonVoAndPage getStgid(@RequestParam(value = "currPage", defaultValue = "0") Integer currPage, String CContractCode, String ContractUID) {
        JsonVoAndPage vo = new JsonVoAndPage();
        //调用dao
        vo.setCode(0);
        vo.setMsg("ok");
        PageBean pb = new PageBean(1000, currPage);
        vo.setData(fullDownService.loadStgId(ContractUID, CContractCode, pb));
        vo.setPageBean(pb);
        return vo;
    }

    /**
     * 下拉查询带参数带分页
     *
     * @param currPage 当前页
     * @param param    参数
     * @param type     类型
     * @param compid   企业id
     * @return
     */
    @RequestMapping("/getFullDown.do")
    public JsonVoAndPage getFullDown(@RequestParam(value = "currPage", defaultValue = "0") Integer currPage, String param, Integer type, String compid) {
        JsonVoAndPage vo = new JsonVoAndPage();
        if (type != null) {
            PageBean pb = new PageBean(10, currPage);
            vo.setCode(0);
            vo.setMsg("ok");
            vo.setPageBean(pb);
            vo.setData(fullDownService.getFullDown(type, param, pb, compid));
        } else {
            vo.setCode(0);
            vo.setMsg("error,type is Null");
        }
        return vo;
    }
}
