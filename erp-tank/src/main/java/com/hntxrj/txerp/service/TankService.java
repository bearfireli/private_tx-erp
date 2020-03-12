package com.hntxrj.txerp.service;


import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.entity.*;
import com.hntxrj.txerp.vo.ResultVO;

import java.util.List;

public interface TankService {


    /**
     * 获取每个罐信息显示集合
     */
    List<PowderTankDevice> powderTanDeviceList(String compid,String stirId,String beginTime,String engTime);

    /**
     * 保存罐的信息数据
     *
     * @param powderTankDevice 罐对象
     */
    void savePowderTanDevice(PowderTankDevice powderTankDevice) throws ErpException;

    PowderTankDevice getPowderTanDevice(String compid, String stirId, String tankCode);

    /**
     * 获取罐控制功能信息显示集合
     */
    List<PowderTankControl> powderTankControlList(String compid,String beginTime,String endTime);

    /**
     * 保存罐控制功能信息
     *
     * @param powderTankControl 罐控制对象
     */
    void savePowderTankControl(PowderTankControl powderTankControl);

    /**
     * 修改罐的开关门状态
     *
     * @param compid       企业id
     * @param tankCode     罐代号
     * @param tankStatus   罐开门状态
     * @param doorPassword 罐开门密码
     */
    void updateDoorStatus(String compid, String tankCode, Integer tankStatus, String doorPassword) throws ErpException;

    /**
     * 罐的重量变化信息集合列表
     */
    List<WeightChangeRecord> weighChangeRecordList(String compid,String beginTime,String endTime);

    /**
     * 保存罐的重量变化信息
     *
     * @param weightChangeRecord 罐重量对象
     */
    void saveWeighChangeRecord(WeightChangeRecord weightChangeRecord);

    /**
     * 获取罐上料信息集合列表
     */
    List<PowderTankSafeStatusInfor> powderTankSafeStatusInforList(String compid,String beginTIme,String endTime);

    /**
     * 保存罐上料信息
     */
    void savePowderTankSafeStatusInfor(PowderTankSafeStatusInfor powderTankSafeStatusInfor);

    /**
     * 罐校准历史记录集合列表
     */
    List<PowderTankCalibration> powderTankCalibrationList(String compid,String beginTime,String endTime);


    /**
     * 保存罐校准历史记录
     */
    void savePowderTankCalibration(PowderTankCalibration powderTankCalibration);

    /**
     * 罐报警信息集合列表
     */
    List<PowderTankWarn> powderTankWarnList(String compid,String beginTime,String endTime);

    /**
     * 保存罐报警信息
     */
    void savePowderTankWarn(PowderTankWarn powderTankWarn);


    /**
     * 验证罐的开门密码是否正确
     *
     * @param compid 企业id
     * @param stirId 线号
     * @param tankCode 罐id
     * @param passWord 罐的开门密码
     */
    Boolean checkPassword(String compid, String stirId, String tankCode, String passWord);


}
