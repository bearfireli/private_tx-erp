package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface TankMapper {

    /**
     * 获取每个罐信息显示集合
     */
    List<PowderTankDevice> powderTanDeviceList(String compid);

    /**
     * 罐的重量变化信息集合列表
     */
    List<WeightChangeRecord> weighChangeRecordList(String compid);

    /**
     * 获取罐上料信息集合列表
     */
    List<PowderTankSafeStatusInfor> powderTankSafeStatusInforList(String compid);

    /**
     * 罐校准历史记录集合列表
     */
    List<PowderTankCalibration> powderTankCalibrationList(String compid);

    /**
     * 罐报警集合列表
     */
    List<PowderTankWarn> powderTankWarnList(String compid);

    /**
     * 获取罐控制功能信息显示集合
     */
    List<PowderTankControl> powderTankControlList(String compid);

    /**
     * 根据企业id和罐代号获取罐控制对象
     */
    PowderTankControl getPowderTankControl(String compid, String tankCode);

    /**
     * 修改开门状态
     *
     * @param compid     企业id
     * @param tankCode   罐代号
     * @param tankStatus 罐开门状态
     */
    void updatePowderTankControl(String compid, String tankCode, Integer tankStatus);
}
