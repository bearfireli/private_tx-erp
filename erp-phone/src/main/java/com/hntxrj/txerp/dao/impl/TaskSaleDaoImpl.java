package com.hntxrj.txerp.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.hntxrj.txerp.dao.TaskSaleDao;
import com.hntxrj.txerp.util.jdbc.procedure.Param;
import com.hntxrj.txerp.util.jdbc.procedure.Procedure;
import com.hntxrj.txerp.util.jdbc.enums.ParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class TaskSaleDaoImpl implements TaskSaleDao {

    private final Procedure procedure;

    @Autowired
    public TaskSaleDaoImpl(Procedure procedure) {
        this.procedure = procedure;
    }

    /**
     * 泵车工作量统计
     *
     * @param mark         1  泵车工作量汇总统计 2 泵车工作量统计（按司机）3泵车工作量明细汇总 4泵车泵工工作量明细汇总 5泵车工作量-工程方量统计
     * @param compid       企业
     * @param opid         用户
     * @param beginTime    开始时间
     * @param endTime      结束时间
     * @param currPage     当前页
     * @param pageSize     页长度
     * @param vehicleid    车号
     * @param personalname 司机
     * @param eppname      工程名称
     * @param stirid       楼号
     * @return
     */


//    @mark INTEGER,
//    @compid  varchar(32),
//    @opid   varchar(32),
//    @beginTime  varchar(32),
//    @endTime   varchar(32),
//    @PageSize  INTEGER,
//    @currPage  INTEGER ,
//
//    @VehicleID  VARCHAR(32),
//    @PersonalName varchar(32),
//    @Eppname  VARCHAR(32),
//    @stirid   varchar(32)
    @Override
    public JSONArray sp_V_VM_PumpTruckPersonalWorksrq(String mark, String compid, String opid, String beginTime, String endTime, Integer currPage, Integer pageSize, String vehicleid, String personalname, String eppname, String stirid) {
        List<Param> comeParam = new ArrayList<>();
        comeParam.add(new Param(1, ParamType.INPARAM.getType(), mark));
        comeParam.add(new Param(2, ParamType.INPARAM.getType(), compid));
        comeParam.add(new Param(3, ParamType.INPARAM.getType(), opid));
        comeParam.add(new Param(4, ParamType.INPARAM.getType(), beginTime));
        comeParam.add(new Param(5, ParamType.INPARAM.getType(), endTime));
        comeParam.add(new Param(6, ParamType.INPARAM.getType(), pageSize));
        comeParam.add(new Param(7, ParamType.INPARAM.getType(), currPage));
        comeParam.add(new Param(8, ParamType.INPARAM.getType(), vehicleid));
        comeParam.add(new Param(9, ParamType.INPARAM.getType(), personalname));
        comeParam.add(new Param(10, ParamType.INPARAM.getType(), eppname));
        comeParam.add(new Param(11, ParamType.INPARAM.getType(), stirid));
        procedure.init("sp_V_VM_PumpTruckPersonalWorksrq", comeParam);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();
    }
}
