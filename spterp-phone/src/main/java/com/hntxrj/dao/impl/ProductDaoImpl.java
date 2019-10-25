package com.hntxrj.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.dao.ProductDao;
import com.hntxrj.dao.UserAdminDao;
import com.hntxrj.dao.UserCompDao;
import com.hntxrj.entity.PageBean;
import com.hntxrj.entity.UserComp;
import com.hntxrj.util.jdbc.procedure.Param;
import com.hntxrj.util.jdbc.procedure.Procedure;
import com.hntxrj.util.jdbc.enums.ParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 功能:   生产模块Dao实现类
 *
 * @Auther 李帅
 * @Data 2017-09-04.下午 6:40
 */
@Repository
@Scope("prototype")
public class ProductDaoImpl implements ProductDao {

    private final Procedure procedure;

    @Autowired
    public ProductDaoImpl(Procedure procedure) {
        this.procedure = procedure;
    }

    @Resource
    private UserCompDao userCompDao;
    @Value("${app.spterp.erptype}")
    public Integer erpType;


    /**
     * 生产查询统计
     *
     * @param mark       // 1 查询统计
     * @param compid     企业ID
     * @param begintime  起始时间
     * @param endtime    结束时间
     * @param taskId     任务单ID
     * @param taskStatus 任务单状态
     * @param pageBean   分页
     * @return
     */
    @Override
    public JSONArray productTotal(Integer mark, String compid, String begintime, String endtime, String taskId, String taskStatus, PageBean pageBean, String opid) {
        List<Param> params = new ArrayList<Param>();
        params.add(new Param(1, ParamType.INPARAM.getType(), mark));
        params.add(new Param(2, ParamType.INPARAM.getType(), compid));
        params.add(new Param(3, ParamType.INPARAM.getType(), begintime));
        params.add(new Param(4, ParamType.INPARAM.getType(), endtime));
        params.add(new Param(5, ParamType.INPARAM.getType(), taskId));
        params.add(new Param(6, ParamType.INPARAM.getType(), taskStatus));
        params.add(new Param(7, ParamType.INPARAM.getType(), pageBean.getPageCurr()));
        params.add(new Param(8, ParamType.INPARAM.getType(), pageBean.getPageSize()));
        params.add(new Param(9, ParamType.INPARAM.getType(), opid));

        procedure.init("sp_Query_Product_Total", params);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* 存储页面总数 */
        if (!procedure.getResultArray().isEmpty()) {
            String str = procedure.getResultArray().getJSONArray(1).toString();
            str = str.substring(str.indexOf(":") + 1, str.indexOf("}"));
            System.out.println("总数为:" + str);
            if (str.matches("[0-9]*")) {
                pageBean.setRecordCount(Integer.parseInt(str));
            }
        }
        return procedure.getResultArray();
    }


    /**
     * 销售方量统计
     *
     * @param mark        标记
     * @param beginTime   起始时间
     * @param endTime     结束时间
     * @param builderName 施工单位名称
     * @param eppName     工程名称
     * @param pageBean    分页
     * @return
     */
    @Override
    public JSONArray builderSalaTotal(Integer mark, String beginTime, String endTime, String builderName,
                                      String eppName, String compid, PageBean pageBean, String opid) {
        List<Param> params = new ArrayList<Param>();
        params.add(new Param(1, ParamType.INPARAM.getType(), mark));
        params.add(new Param(2, ParamType.INPARAM.getType(), beginTime));
        params.add(new Param(3, ParamType.INPARAM.getType(), endTime));
        params.add(new Param(4, ParamType.INPARAM.getType(), builderName));
        params.add(new Param(5, ParamType.INPARAM.getType(), eppName));
        params.add(new Param(6, ParamType.INPARAM.getType(), pageBean.getPageCurr()));
        params.add(new Param(7, ParamType.INPARAM.getType(), pageBean.getPageSize()));
        params.add(new Param(8, ParamType.INPARAM.getType(), compid));

        procedure.init("sp_Query_BuilderSala_Total", params);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* 存储页面总数 */
        if (!procedure.getResultArray().isEmpty()) {
            String str = procedure.getResultArray().getJSONArray(1).toString();
            str = str.substring(str.indexOf(":") + 1, str.indexOf("}"));
            System.out.println("总数为:" + str);
            if (str.matches("[0-9]*")) {
                pageBean.setRecordCount(Integer.parseInt(str));
            }
        }
        return procedure.getResultArray();
    }

    /**
     * 小票签收查询
     *
     * @param mark        标记 1加载列表  2详情
     * @param compid      企业ID
     * @param id          id
     * @param begintime   起始时间
     * @param endtime     结束时间
     * @param vehicleID   车号
     * @param taskId      任务单号
     * @param InvoiceType 小票类型
     * @param placeStyle  浇筑方式
     * @param placing     浇筑部位
     * @param builderName 施工单位
     * @param stgId       搅拌楼
     * @param eppName     工程名称
     * @param pageBean    分页
     * @return
     */
    @Override
    public JSONArray deliveryTicketTotal(Integer mark, String compid, Integer id, String begintime, String endtime,
                                         String vehicleID, String taskId, String InvoiceType, String placeStyle,
                                         String placing, String builderName, String stgId, String eppName, PageBean pageBean, String opid) {
        List<Param> parms = new ArrayList<Param>();
        //标记   1加载列表  2详情
        parms.add(new Param(1, ParamType.INPARAM.getType(), mark));
        //企业ID
        parms.add(new Param(2, ParamType.INPARAM.getType(), compid));
        //id
        parms.add(new Param(3, ParamType.INPARAM.getType(), id));
        //起始时间
        parms.add(new Param(4, ParamType.INPARAM.getType(), begintime));
        //结束时间
        parms.add(new Param(5, ParamType.INPARAM.getType(), endtime));
        //车号
        parms.add(new Param(6, ParamType.INPARAM.getType(), vehicleID));
        //任务单号
        parms.add(new Param(7, ParamType.INPARAM.getType(), taskId));
        //小票类型
        parms.add(new Param(8, ParamType.INPARAM.getType(), InvoiceType));
        //浇筑方式
        parms.add(new Param(9, ParamType.INPARAM.getType(), placeStyle));
        //浇筑部位
        parms.add(new Param(10, ParamType.INPARAM.getType(), placing));
        //施工单位
        parms.add(new Param(11, ParamType.INPARAM.getType(), builderName));
        //搅拌楼
        parms.add(new Param(12, ParamType.INPARAM.getType(), stgId));
        //工程名称
        parms.add(new Param(13, ParamType.INPARAM.getType(), eppName));
        // 当前页码
        parms.add(new Param(14, ParamType.INPARAM.getType(), pageBean.getPageCurr()));
        // 显示数据条数
        parms.add(new Param(15, ParamType.INPARAM.getType(), pageBean.getPageSize()));
        //登录用户
        parms.add(new Param(16, ParamType.INPARAM.getType(), opid));
        //TODO:小票签收查询
        Integer currErpType = userCompDao.findUserCompByCompid(compid).getErpType();
        if (currErpType != null && currErpType == erpType) {
            procedure.init("sp_Query_Delivery_ticket_Thr", parms);
        } else {
            procedure.init("sp_Query_Delivery_ticket", parms);
        }
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mark == 1) {
            /* 存储页面总数 */
            if (!procedure.getResultArray().isEmpty()) {
                String str = procedure.getResultArray().getJSONArray(1).toString();
                str = str.substring(str.indexOf(":") + 1, str.indexOf("}"));
                System.out.println("总数为:" + str);
                if (str.matches("[0-9]*")) {
                    pageBean.setRecordCount(Integer.parseInt(str));
                }
            }
        }
        return procedure.getResultArray();
    }


    /**
     * 调度派车
     *
     * @param mark        1加载列表
     * @param compid      企业ID
     * @param begintime   起始时间
     * @param endtime     结束时间
     * @param taskId      任务单号
     * @param taskStatus  任务状态
     * @param placing     浇筑部位
     * @param builderName 施工单位
     * @param eppName     工程名称
     * @param pageBean    分页
     * @return
     */
    @Override
    public JSONArray vehicleCarTotal(Integer mark, String compid, String begintime, String endtime, String taskId, Integer taskStatus,
                                     String placing, String builderName, String eppName, PageBean pageBean) {
        List<Param> params = new ArrayList<Param>();
        //标记   1加载列表
        params.add(new Param(1, ParamType.INPARAM.getType(), mark));
        //企业ID
        params.add(new Param(2, ParamType.INPARAM.getType(), compid));
        //起始时间
        params.add(new Param(3, ParamType.INPARAM.getType(), begintime));
        //结束时间
        params.add(new Param(4, ParamType.INPARAM.getType(), endtime));
        //任务单号
        params.add(new Param(5, ParamType.INPARAM.getType(), taskId));
        //任务状态
        params.add(new Param(6, ParamType.INPARAM.getType(), taskStatus));
        //浇筑部位
        params.add(new Param(7, ParamType.INPARAM.getType(), placing));
        //施工单位
        params.add(new Param(8, ParamType.INPARAM.getType(), builderName));
        //工程名称
        params.add(new Param(9, ParamType.INPARAM.getType(), eppName));
        // 当前页码
        params.add(new Param(10, ParamType.INPARAM.getType(), pageBean.getPageCurr()));
        // 显示数据条数
        params.add(new Param(11, ParamType.INPARAM.getType(), pageBean.getPageSize()));

        procedure.init("sp_Query_VehicleCar", params);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* 存储页面总数 */
        if (!procedure.getResultArray().isEmpty()) {
            String str = procedure.getResultArray().getJSONArray(1).toString();
            str = str.substring(str.indexOf(":") + 1, str.indexOf("}"));
            System.out.println("总数为:" + str);
            if (str.matches("[0-9]*")) {
                pageBean.setRecordCount(Integer.parseInt(str));
            }
        }
        return procedure.getResultArray();
    }


    /**
     * 调度管理修改
     * <p>
     * version 1.1
     *
     * @param mark     标记  1 加载列表 2 详情
     * @param compid   企业ID
     * @param taskID   任务单号
     * @param pageBean 分页
     * @return
     */
    @Override
    public JSONArray vehicleCarTotal_Two(Integer mark, String compid, String taskID, PageBean pageBean, String opid) {
        List<Param> params = new ArrayList<Param>();
        params.add(new Param(1, ParamType.INPARAM.getType(), mark));
        params.add(new Param(2, ParamType.INPARAM.getType(), compid));
        params.add(new Param(3, ParamType.INPARAM.getType(), taskID));
        params.add(new Param(4, ParamType.INPARAM.getType(), pageBean.getPageCurr()));
        params.add(new Param(5, ParamType.INPARAM.getType(), pageBean.getPageSize()));
        params.add(new Param(6, ParamType.INPARAM.getType(), opid));

        procedure.init("sp_Query_VehicleCar_Two", params);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mark == 1) {
            if (!procedure.getResultArray().isEmpty()) {
                String str = procedure.getResultArray().getJSONArray(1).toString();
                str = str.substring(str.indexOf(":") + 1, str.indexOf("}"));
                System.out.println("总数为:" + str);
                if (str.matches("[0-9]*")) {
                    pageBean.setRecordCount(Integer.parseInt(str));
                }
            }
        }

        return procedure.getResultArray();
    }
}
