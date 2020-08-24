package com.hntxrj.txerp.server;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.entity.EppInfo;
import com.hntxrj.txerp.entity.PageBean;
import com.hntxrj.txerp.vo.EppDropDownVO;
import com.hntxrj.txerp.vo.EppListVO;
import com.hntxrj.txerp.vo.PageVO;

import javax.transaction.Transactional;

/**
 * 功能:   工程服务接口
 *
 * @author lhr
 * @Auther 李帅
 * @Data 2017-08-11.上午 10:58
 * <p>
 * ============================
 * 重构删除q对象，加入一个查询方法
 * @Data 2019-10-17 08:44:44
 */
public interface EppService {

    /**
     * 加载详情列表
     *
     * @param eppName  工程名
     * @param pageBean 页码相关实体
     * @return
     */
    JSONArray getEppList(String eppName, PageBean pageBean, String compid);


    /**
     * 加载 浇筑部位
     *
     * @param eppCode  工程代号
     * @param pageBean 分页
     * @return
     */
    JSONArray getEppPlacing(String eppCode, String placing, PageBean pageBean, String compid);


    /**
     * 获取工程名称下拉
     *
     * @param eppName  工程名称模糊查询
     * @param compid   企业id
     * @param page     页码
     * @param pageSize 每页数量
     * @return 带分页工程下拉列表
     */
    PageVO<EppDropDownVO> getDropDown(String eppName, String compid, Integer page, Integer pageSize);


    /**
     * 获取工程名称
     *
     * @param eppCode 工程代号
     * @param compid  企业id
     * @return 工程对象
     */
    EppInfo getEppInfo(String eppCode, String compid);


    /**
     * 获取工地端App工程名称下拉
     *
     * @param eppName  工程名称模糊查询
     * @param buildId  企业id
     * @param page     页码
     * @param pageSize 每页数量
     * @return 带分页工程下拉列表
     */
    PageVO<EppDropDownVO> getBuildDropDown(String eppName, Integer buildId, Integer page, Integer pageSize);

    @Transactional
    void addEppInfo(String compid, String eppName, String shortName, String address, String linkMan, String phone,
                    String remarks);

    /**
     * 获取工程名称分页数据
     *
     * @param eppName  工程名称模糊查询
     * @param compid   企业id
     * @param page     页码
     * @param pageSize 每页数量
     * @return 工程名称分页数据
     */
    PageVO<EppListVO> getEppList(String eppName, String compid, Integer page, Integer pageSize);

}
