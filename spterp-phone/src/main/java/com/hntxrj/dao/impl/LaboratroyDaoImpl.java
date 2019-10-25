package com.hntxrj.dao.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.dao.LaboratroyDao;
import com.hntxrj.dao.UserCompDao;
import com.hntxrj.mapper.FormulaMapper;
import com.hntxrj.util.jdbc.procedure.Param;
import com.hntxrj.util.jdbc.procedure.Procedure;
import com.hntxrj.util.jdbc.enums.ParamType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
public class LaboratroyDaoImpl implements LaboratroyDao {

    private final Procedure procedure;
    private final JdbcTemplate jdbcTemplate;
    private final FormulaMapper formulaMapper;

    @Autowired
    public LaboratroyDaoImpl(Procedure procedure, JdbcTemplate jdbcTemplate, FormulaMapper formulaMapper) {
        this.procedure = procedure;
        this.jdbcTemplate = jdbcTemplate;
        this.formulaMapper = formulaMapper;
    }

    @Resource
    private UserCompDao userCompDao;
    @Value("${app.spterp.erptype}")
    public Integer erpType;

    /**
     * 开具配比
     *
     * @param mark       0位列表
     * @param currPage   当前页
     * @param pageSize   页长度
     * @param taskStatus 状态
     * @param eppName    施工单位
     * @param placing    浇筑部位
     * @param taskId     任务单号
     * @param preTime    开具时间
     * @param endTime    结束时间
     * @return jsoN
     */
    @Override
    public JSONArray spQueryVPTPlanFind(Integer mark, String compid, Integer currPage, Integer pageSize,
                                        Integer taskStatus, String eppName, String placing, String taskId,
                                        String preTime, String endTime, String builderName, Integer FormulaStatus,
                                        String opid) {
        List<Param> params = new ArrayList<>();
        params.add(new Param(1, ParamType.INPARAM.getType(), mark));
        params.add(new Param(2, ParamType.INPARAM.getType(), compid));
        params.add(new Param(3, ParamType.INPARAM.getType(), currPage));
        params.add(new Param(4, ParamType.INPARAM.getType(), pageSize));
        params.add(new Param(5, ParamType.INPARAM.getType(), taskStatus));
        params.add(new Param(6, ParamType.INPARAM.getType(), eppName));
        params.add(new Param(7, ParamType.INPARAM.getType(), placing));
        params.add(new Param(8, ParamType.INPARAM.getType(), taskId));
        params.add(new Param(9, ParamType.INPARAM.getType(), preTime));
        params.add(new Param(10, ParamType.INPARAM.getType(), endTime));
        params.add(new Param(11, ParamType.INPARAM.getType(), builderName));
        params.add(new Param(12, ParamType.INPARAM.getType(), FormulaStatus));
        params.add(new Param(13, ParamType.INPARAM.getType(), opid));
//        Integer currErpType = userCompDao.findUserCompByCompid(compid).getErpType();
//        if (currErpType != null && currErpType.equals(erpType)) {
//            procedure.init("sp_Query_V_PT_PlanFind_Thr", params);
//        } else {
        procedure.init("sp_Query_V_PT_PlanFind", params);
//        }
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return procedure.getResultArray();

    }

    /**
     * 开具审核
     *
     * @param taskId       任务单号
     * @param compid       企业
     * @param opid         用户
     * @param verifystatus 线号审核状态
     * @param stirid       线号
     * @param formulaCode  配比编号
     * @return jsonArray
     */
    @Override
    public JSONArray sp_Verify_LM_TaskTheoryFormula(String compid, String taskId, String opid,
                                                    Integer verifystatus, Integer stirid,
                                                    String formulaCode) {


        List<Param> params = new ArrayList<>();

        params.add(new Param(1, ParamType.INPARAM.getType(), taskId));
        params.add(new Param(2, ParamType.INPARAM.getType(), compid));
        params.add(new Param(3, ParamType.INPARAM.getType(), opid));
        params.add(new Param(4, ParamType.INPARAM.getType(), verifystatus));
        params.add(new Param(5, ParamType.INPARAM.getType(), stirid));
        params.add(new Param(6, ParamType.OUTPARAM.getType(), ""));

        procedure.init("sp_Verify_LM_TaskTheoryFormula", params);
        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONArray resultArray = procedure.getResultArray();

        // 修改任务单状态
        List<Param> params1 = new ArrayList<>();
        params1.add(new Param(1, ParamType.INPARAM.getType(), compid));
        params1.add(new Param(2, ParamType.INPARAM.getType(), taskId));
        params1.add(new Param(3, ParamType.INPARAM.getType(), formulaCode));
        params1.add(new Param(4, ParamType.INPARAM.getType(), 1));
        procedure.init("sp_Update_PT_TaskPlan_FormuInfo", params1);

        try {
            procedure.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultArray;
    }
}
