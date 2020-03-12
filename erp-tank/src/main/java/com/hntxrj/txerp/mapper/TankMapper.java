package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface TankMapper {

    /**
     * 获取每个罐信息显示集合
     *
     * @param compid    企业id
     * @param stirId    线号
     * @param beginTime 开始时间
     * @param endTime   结束时间
     */
    List<PowderTankDevice> powderTanDeviceList(String compid, String stirId, String beginTime, String endTime);

    /**
     * 罐的重量变化信息集合列表
     *
     * @param compid    企业id
     * @param stirId    线号
     * @param tankCode  罐id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     */
    List<WeightChangeRecord> weighChangeRecordList(String compid, String stirId, String tankCode, String beginTime, String endTime);

    /**
     * 罐的上料信息集合列表
     *
     * @param compid    企业id
     * @param stirId    线号
     * @param tankCode  罐id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     */
    List<PowderTankSafeStatusInfor> powderTankSafeStatusInforList(String compid, String stirId, String tankCode, String beginTime, String endTime);

    /**
     * 罐的校准历史记录集合列表
     *
     * @param compid    企业id
     * @param stirId    线号
     * @param tankCode  罐id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     */
    List<PowderTankCalibration> powderTankCalibrationList(String compid, String stirId, String tankCode, String beginTime, String endTime);

    /**
     * 罐报警列表查询
     *
     * @param compid    企业id
     * @param stirId    线号
     * @param tankCode  罐id
     * @param beginTime 开始时间
     * @param endTime   结束时间
     */
    List<PowderTankWarn> powderTankWarnList(String compid, String stirId, String tankCode, String beginTime, String endTime);

    /**
     * 获取罐控制功能信息显示集合
     */
    List<PowderTankControl> powderTankControlList(String compid, String beginTime, String endTime);

    /**
     * 根据企业id和罐代号获取罐控制对象
     */
    PowderTankControl getPowderTankControl(String compid, String tankCode, String stirId);

    /**
     * 修改罐控制功能表中的开门状态
     *
     * @param compid          企业id
     * @param tankCode        罐代号
     * @param stirId          线号
     * @param loadMouthStatus 罐开门状态
     */
    void updatePowderTankControl(String compid, String tankCode, String stirId, Integer loadMouthStatus);

    /**
     * 修改罐基本信息表中的开门状态
     *
     * @param compid          企业id
     * @param tankCode        罐代号
     * @param stirId          线号
     * @param loadMouthStatus 罐开门状态
     */
    void updatePowderTankDevice(String compid, String tankCode, String stirId, Integer loadMouthStatus);

    /**
     * 获取当前企业下最大的罐代号
     */
    Integer getMaxTankCode(String compid);

    /**
     * 验证罐的开门密码是否正确
     *
     * @param compid   企业id
     * @param stirId   线号
     * @param tankCode 罐id
     * @param passWord 罐的开门密码
     */
    PowderTankDevice checkPassword(String compid, String stirId, String tankCode, String passWord);

    /**
     * 获取单个罐的信息
     *
     * @param compid   企业id
     * @param stirId   线号
     * @param tankCode 罐的id
     */
    PowderTankDevice getPowderTanDevice(String compid, String stirId, String tankCode);


}
