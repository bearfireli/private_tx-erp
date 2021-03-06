package com.hntxrj.txerp.server.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.SyncPlugin;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.core.util.SimpleDateFormatUtil;
import com.hntxrj.txerp.dao.BuilderDao;
import com.hntxrj.txerp.entity.BuilderInfo;
import com.hntxrj.txerp.entity.PageBean;
import com.hntxrj.txerp.mapper.BuilderMapper;
import com.hntxrj.txerp.mapper.ConstructionMapper;
import com.hntxrj.txerp.server.BuilderService;
import com.hntxrj.txerp.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 功能:   施工单位服务接口实现层
 * <p>
 * 李帅
 * 2017-08-11.上午 11:13
 */
@Service
@Scope("prototype")

public class BuilderServiceImpl implements BuilderService {

    private final BuilderDao builderDao;
    private final BuilderMapper builderMapper;
    private final ConstructionMapper constructionMapper;
    private final SyncPlugin syncPlugin;
    private final SimpleDateFormat sdf = SimpleDateFormatUtil.getDefaultSimpleDataFormat();


    @Autowired
    public BuilderServiceImpl(BuilderDao builderDao, BuilderMapper builderMapper, ConstructionMapper constructionMapper, SyncPlugin syncPlugin) {
        this.builderDao = builderDao;
        this.builderMapper = builderMapper;
        this.constructionMapper = constructionMapper;
        this.syncPlugin = syncPlugin;
    }


    /**
     * 加载详情列表
     *
     * @param builderName 工程名
     * @param pageBean    页码相关实体
     * @param compid      企业id
     */
    @Override
    public JSONArray getEppList(String builderName, PageBean pageBean, String compid) {
        return builderDao.getBuilderList(builderName, pageBean, compid);
    }


    /**
     * 添加修改删除  施工单位
     *
     * @param Mark             操作标识 0 插入 1 更新 2 删除
     * @param compid           企业ID
     * @param opid             操作员ID
     * @param BuilderCode      施工单位代号
     * @param BuilderName      施工单位名
     * @param BuilderShortName 施工单位名简称
     * @param Address          地址
     * @param CreateTime       创建时间
     * @param Corporation      法人
     * @param Fax              传真
     * @param LinkTel          联系电话
     * @param RecStatus        记录状态(有效)   0未启用 1启用(0无效1有效)
     */
    @Override
    public JSONArray insertUpDel_SM_BUILDERINFO(Integer Mark, String compid, String opid, String BuilderCode,
                                                String BuilderName, String BuilderShortName, String Address,
                                                Timestamp CreateTime, String Corporation, String Fax,
                                                String LinkTel, byte RecStatus) {
        return builderDao.insertUpDel_SM_BUILDERINFO(Mark, compid, opid, BuilderCode, BuilderName, BuilderShortName,
                Address, CreateTime, Corporation, Fax, LinkTel, RecStatus);
    }

    @Override
    public PageVO<BuilderDropDownVO> getBuilderDropDown(String compid, String builderName, Integer page,
                                                        Integer pageSize) {
        PageHelper.startPage(page, pageSize, "createTime desc");
        List<BuilderDropDownVO> builderDropDownVOS = builderMapper.getBuilderDropDown(compid, builderName);
        PageInfo<BuilderDropDownVO> pageInfo = new PageInfo<>(builderDropDownVOS);
        PageVO<BuilderDropDownVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public BuilderInfo getBuilderInfo(String builderCode, String compid) {
        return builderMapper.getBuilderInfo(builderCode, compid);
    }


    /**
     * 工地端App产销统计查询
     *
     * @param buildId   　施工方用户id
     * @param eppCode   　工程代码
     * @param placing   　浇筑部位
     * @param taskId    　　任务单号
     * @param stgId     　　　砼标号
     * @param beginTime 　　开始时间
     * @param endTime   　　　结束时间
     * @param page      　　　　页数
     * @param pageSize  　　每页显示多少条
     */
    @Override
    public PageVO<ConcreteVO> getBuilderConcreteCount(Integer buildId, String eppCode, String placing, String taskId,
                                                      String stgId, String beginTime, String endTime, Integer type,
                                                      Integer page, Integer pageSize) throws ErpException {
        PageVO<ConcreteVO> pageVO = new PageVO<>();
        if (type == null) {
            type = 1;
        }

        //检测施工方用户是否绑定合同
        Map<String, List<String>> map = checkContract(buildId);
        List<String> contractDetailCodes = map.get("contractDetailCodes");
        List<String> contractUIDList = map.get("contractUIDList");

        PageHelper.startPage(page, pageSize);
        //查询施工方用户关联合同下的产销统计
        List<ConcreteVO> concreteVOS = builderMapper.getBuilderConcreteCount(contractDetailCodes, contractUIDList,
                eppCode, placing, taskId, stgId, beginTime, endTime, type);


        for (ConcreteVO concreteVO : concreteVOS) {
            // 保留小数点后两位数
            if (concreteVO.getProduceNum() != null && !"".equals(concreteVO.getProduceNum())) {
                String produceNum = concreteVO.getProduceNum();
                produceNum = produceNum.substring(0, produceNum.indexOf(".") + 3);
                concreteVO.setProduceNum(produceNum);
            }
            if (concreteVO.getSaleNum() != null && !"".equals(concreteVO.getSaleNum())) {
                String saleNum = concreteVO.getSaleNum();
                saleNum = saleNum.substring(0, saleNum.indexOf(".") + 3);
                concreteVO.setSaleNum(saleNum);
            }
        }
        PageInfo<ConcreteVO> pageInfo = new PageInfo<>(concreteVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }


    /**
     * 查询产销统计中发货方量统计
     *
     * @param buildId   　施工方用户id
     * @param eppCode   　工程代码
     * @param placing   　浇筑部位
     * @param taskId    　　任务单号
     * @param stgId     　　　砼标记
     * @param beginTime 　　开始时间
     * @param endTime   　　　结束时间
     * @param type      　　type: 查询时间类型; 1:派车时间；0:离场时间
     */
    @Override
    public Map<String, BigDecimal> getBuilderConcreteSum(Integer buildId, String eppCode, String placing,
                                                         String taskId, String stgId, String beginTime,
                                                         String endTime, Integer type) throws ErpException {


        if (type == null) {
            type = 1;
        }
        Map<String, BigDecimal> concreteMap = new HashMap<>();
        //检测施工方用户是否绑定合同
        Map<String, List<String>> map = checkContract(buildId);
        List<String> contractDetailCodes = map.get("contractDetailCodes");
        List<String> contractUIDList = map.get("contractUIDList");
        BigDecimal totalSaleNum = builderMapper.getBuilderConcreteSum(contractDetailCodes, contractUIDList,
                eppCode, placing, taskId, stgId, beginTime, endTime, type);

        concreteMap.put("totalSaleNum", totalSaleNum);

        return concreteMap;
    }

    @Override
    public PageVO<TaskSaleInvoiceListVO> getBuildTaskSaleInvoiceList(Integer buildId, String beginTime, String endTime,
                                                                     String eppCode, Byte upStatus, String builderCode,
                                                                     String taskId, String placing, String taskStatus,
                                                                     Integer page, Integer pageSize) throws ErpException {
        PageVO<TaskSaleInvoiceListVO> pageVO = new PageVO<>();
        //检测施工方用户是否绑定合同
        Map<String, List<String>> map = checkContract(buildId);
        List<String> contractDetailCodes = map.get("contractDetailCodes");
        List<String> contractUIDList = map.get("contractUIDList");

        PageHelper.startPage(page, pageSize, "SendTime desc");
        List<TaskSaleInvoiceListVO> taskSaleInvoiceLists = builderMapper.getBuildTaskSaleInvoiceList(contractDetailCodes,
                contractUIDList, beginTime, endTime, eppCode, upStatus, builderCode, taskId, placing, taskStatus);
        PageInfo<TaskSaleInvoiceListVO> pageInfo = new PageInfo<>(taskSaleInvoiceLists);
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public PageVO<SendCarListVO> getBuildSendCarList(Integer buildId, String searchWords, Integer page,
                                                     Integer pageSize) throws ErpException {
        PageVO<SendCarListVO> pageVO = new PageVO<>();

        //检测施工方用户是否绑定合同
        Map<String, List<String>> map = checkContract(buildId);
        List<String> contractDetailCodes = map.get("contractDetailCodes");
        List<String> contractUIDList = map.get("contractUIDList");

        PageHelper.startPage(page, pageSize);
        List<SendCarListVO> sendCarList = builderMapper.getBuildSendCarList(contractDetailCodes, contractUIDList,
                searchWords);


        //查询出所有正在生产的任务单号集合。
        List<String> taskIds = new ArrayList<>();
        for (SendCarListVO sendCarListVO : sendCarList) {
            if (sendCarListVO.getTotalProduceNum() == null) {
                sendCarListVO.setTotalProduceNum("0.0");
            }
            if (sendCarListVO.getTaskId() != null) {
                taskIds.add(sendCarListVO.getTaskId());
            }

        }
        //根据任务单号集合查询出所有的车号。
        List<DispatchVehicle> cars = new ArrayList<>();
        if (taskIds.size() > 0) {
            cars = builderMapper.getCarsByTaskIds(contractDetailCodes, contractUIDList, taskIds);
        }
        String carID = "";
        //根据每个车辆的任务单号，把所有车辆关联到调度派车列表中
        for (SendCarListVO sendCarListVO : sendCarList) {
            List<DispatchVehicle> dispatchVehicleList = new ArrayList<>();
            for (DispatchVehicle car : cars) {
                //判断重复的车号
                if (!carID.equals(car.getCarID())) {
                    //判断厦门华信特殊情况（先打票，再生产）
                    if ("24".equals(sendCarListVO.getCompid())) {
                        if (car.getTaskStatus() == 1 && car.getInvoiceType() == 4) {
                            car.setVehicleStatus("3");
                            car.setStatusName("生产");
                        }
                    }
                    //根据任务单号关联调度派车列表和其对应的车辆
                    if (sendCarListVO.getTaskId().equals(car.getTaskId())) {
                        dispatchVehicleList.add(car);
                    }
                }
                carID = car.getCarID();
            }
            sendCarListVO.setCars(dispatchVehicleList);
        }

        PageInfo<SendCarListVO> pageInfo = new PageInfo<>(sendCarList);

        pageVO.format(pageInfo);
        return pageVO;
    }


    @Override
    public TaskPlanVO getBuildTaskPlanDetail(String compid, String taskId, Integer buildId) throws ErpException {
        //检测施工方用户是否绑定合同
        Map<String, List<String>> map = checkContract(buildId);
        List<String> contractDetailCodes = map.get("contractDetailCodes");
        List<String> contractUIDList = map.get("contractUIDList");

        List<String> ppCodes = builderMapper.getBuildPPCodeByTaskId(compid, taskId, contractUIDList, contractDetailCodes);
        TaskPlanVO taskPlanVO = builderMapper.getBuildTaskPlanByTaskId(compid, taskId, contractUIDList, contractDetailCodes);

        if (taskPlanVO == null) {
            throw new ErpException(ErrEumn.TASK_NOT_EXIT_ERROR);
        }

        taskPlanVO.setPpCodes(ppCodes);
        return taskPlanVO;
    }

    @Override
    public TaskSaleInvoiceDetailVO getBuildTaskSaleInvoiceDetail(String compid, Integer id, Integer buildId) throws ErpException {

        //检测施工方用户是否绑定合同
        Map<String, List<String>> map = checkContract(buildId);
        List<String> contractDetailCodes = map.get("contractDetailCodes");
        List<String> contractUIDList = map.get("contractUIDList");
        return builderMapper.getTaskSaleInvoiceDetailVO(id, compid, contractUIDList, contractDetailCodes);
    }

    @Override
    public TaskSaleInvoiceDetailVO checkTaskSaleInvoice(Integer buildId, String compid, Integer id) throws ErpException {
        Map<String, List<String>> map = checkContract(buildId);
        List<String> contractDetailCodes = map.get("contractDetailCodes");
        List<String> contractUIDList = map.get("contractUIDList");
        TaskSaleInvoiceDetailVO taskSaleInvoiceDetailVO = builderMapper.getTaskSaleInvoiceDetailVO(id,
                compid, contractUIDList, contractDetailCodes);
        if (taskSaleInvoiceDetailVO == null) {
            throw new ErpException(ErrEumn.TASK_SALE_INVOICE_NOT_EXIT);
        }
        return taskSaleInvoiceDetailVO;
    }

    @Override
    public void addBuilderInfo(String compid, String builderName, String builderShortName, String address,
                               String corporation, String fax, String phone) {
        String nowDate = sdf.format(new Date());
        String builderCode = getBuilderCode(compid);
        builderMapper.addBuilderInfo(compid, builderCode, builderName, builderShortName, address, corporation,
                fax, phone, nowDate);
        BuilderInfo builderInfo = builderMapper.getBuilderInfo(builderCode, compid);
        try {
            syncPlugin.save(builderInfo, "SM_BuilderInfo", "INS", compid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // 验证施工方用户是否绑定合同，返回绑定的合同列表
    private Map<String, List<String>> checkContract(Integer buildId) throws ErpException {
        Map<String, List<String>> map = new HashMap<>();
        List<String> contractDetailCodes = constructionMapper.getContractCodeList(buildId);
        List<String> contractUIDList = constructionMapper.getContractUID(buildId);
        if (contractDetailCodes.size() == 0 || contractUIDList.size() == 0) {
            //说明施工方用户未绑定合同
            throw new ErpException(ErrEumn.NOT_BIND_CONTRACT);
        }
        map.put("contractUIDList", contractUIDList);
        map.put("contractDetailCodes", contractDetailCodes);
        return map;
    }


    private String getBuilderCode(String compid) {
        String builderCode = builderMapper.getMaxBuilderCode(compid);
        //正则匹配找出eppCode中最大的数值
        Pattern compile = Pattern.compile("[0-9]\\d*$");
        Matcher matcher = compile.matcher(builderCode);
        String preMaxCode = "";
        while (matcher.find()) {
            preMaxCode = matcher.group(0);
        }

        //获取最大数值所在的索引，将之前的字符串分割，之后的数值加1
        int startIndex = builderCode.indexOf(preMaxCode);
        String preBuilderCode = builderCode.substring(0, startIndex);

        int length = preMaxCode.length();
        int maxId = Integer.parseInt(preMaxCode);

        StringBuilder maxCode = new StringBuilder(String.valueOf(maxId + 1));

        //将加1之后的数值转换成字符串
        int dValue = length - maxCode.length();
        if (dValue > 0) {
            for (int i = 0; i < dValue; i++) {
                maxCode.insert(0, "0");
            }
        }
        return preBuilderCode + maxCode;
    }


}
