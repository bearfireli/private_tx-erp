package com.hntxrj.txerp.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.dao.DriverDao;
import com.hntxrj.txerp.util.jdbc.enums.ParamType;
import com.hntxrj.txerp.util.jdbc.procedure.Param;
import com.hntxrj.txerp.util.jdbc.procedure.Procedure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能及介绍：
 * <p>
 * ========================
 * Created with IntelliJ IDEA.
 * User： 李 帅
 * Date：2018/5/29
 * Time：下午5:39
 * ========================
 *
 * @author 李帅
 */
@Component
@Scope("prototype")
public class DriverDaoImpl implements DriverDao {


    @Autowired
    private Procedure procedure;

    /**
     * 司机排班数据获取
     *
     * @param compId        企业id
     * @param stirId        线号
     * @param vehicleStatus 车辆状态
     * @param vehicleClass  班次
     * @return
     */
    @Override
    public JSONArray getDriverScheduling(String compId, String stirId, String vehicleStatus, String vehicleClass) {
        List<Param> param = new ArrayList<Param>();
        param.add(new Param(1, ParamType.INPARAM.getType(), 1));
        param.add(new Param(2, ParamType.INPARAM.getType(), compId));
        param.add(new Param(3, ParamType.INPARAM.getType(), stirId));
        param.add(new Param(4, ParamType.INPARAM.getType(), vehicleStatus));
        param.add(new Param(5, ParamType.INPARAM.getType(), vehicleClass));
        param.add(new Param(6, ParamType.INPARAM.getType(), null));
        param.add(new Param(7, ParamType.INPARAM.getType(), null));
        param.add(new Param(8, ParamType.INPARAM.getType(), null));
        param.add(new Param(9, ParamType.INPARAM.getType(), null));
        param.add(new Param(10, ParamType.INPARAM.getType(), null));
        procedure.init("sp_Query_Driver_Scheduling", param);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }


}
