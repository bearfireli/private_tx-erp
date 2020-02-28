package com.hntxrj.txerp.service;


import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.entity.*;

import java.util.List;

public interface TankService {


    /**
     * 获取每个罐信息显示集合
     * */
    List<PowderTankDevice> powderTanDeviceList(String compid);

    /**
     * 保存罐的信息数据
     *
     * @param powderTankDevice 罐对象
     * */
    void savePowderTanDevice(PowderTankDevice powderTankDevice) throws ErpException;

    /**
     * 罐的重量变化信息集合列表
     * */
    List<WeightChangeRecord> weighChangeRecordList(String compid);

    /**
     * 保存罐的重量变化信息
     *
     * @param weightChangeRecord 罐重量对象
     * */
    void saveWeighChangeRecord(WeightChangeRecord weightChangeRecord);

    /**
     * 获取罐上料信息集合列表
     */
    List<PowderTankSafeStatusInfor> powderTankSafeStatusInforList(String compid);

    /**
     * 保存罐上料信息
     */
    void savePowderTankSafeStatusInfor(PowderTankSafeStatusInfor powderTankSafeStatusInfor);

    /**
     * 罐校准历史记录集合列表
     * */
    List<PowderTankCalibration> powderTankCalibrationList(String compid);


    /**
     * 保存罐校准历史记录
     * */
    void savePowderTankCalibration(PowderTankCalibration powderTankCalibration);

    /**
     * 罐报警信息集合列表
     * */
    List<PowderTankWarn> powderTankWarnList(String compid);

    /**
     * 保存罐报警信息
     * */
    void savePowderTankWarn(PowderTankWarn powderTankWarn);
}
