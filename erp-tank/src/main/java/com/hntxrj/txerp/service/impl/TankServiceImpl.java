package com.hntxrj.txerp.service.impl;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.entity.*;
import com.hntxrj.txerp.mapper.TankMapper;
import com.hntxrj.txerp.repository.*;
import com.hntxrj.txerp.service.TankService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @author nsk
 * @create 2020/2/26
 */
@Service
@Scope("prototype")
public class TankServiceImpl implements TankService {

    private final TankMapper tankMapper;
    private final PowderTankDeviceRepository powderTankDeviceRepository;
    private final PowderTankControlRepository powderTankControlRepository;
    private final WeighChangeRecordRepository weighChangeRecordRepository;
    private final PowderTankSafeStatusInforRepository powerTankSafeStatusInforRepository;
    private final PowderTankCalibrationRepository powderTankCalibrationRepository;
    private final PowderTankWarnRepository powderTankWarnRepository;

    @Autowired
    public TankServiceImpl(TankMapper tankMapper, PowderTankDeviceRepository powderTankDeviceRepository,
                           PowderTankControlRepository powderTankControlRepository,
                           WeighChangeRecordRepository weighChangeRecordRepository,
                           PowderTankSafeStatusInforRepository powerTankSafeStatusInforRepository,
                           PowderTankCalibrationRepository powderTankCalibrationRepository,
                           PowderTankWarnRepository powderTankWarnRepository) {
        this.tankMapper = tankMapper;
        this.powderTankDeviceRepository = powderTankDeviceRepository;
        this.powderTankControlRepository = powderTankControlRepository;
        this.weighChangeRecordRepository = weighChangeRecordRepository;
        this.powerTankSafeStatusInforRepository = powerTankSafeStatusInforRepository;
        this.powderTankCalibrationRepository = powderTankCalibrationRepository;
        this.powderTankWarnRepository = powderTankWarnRepository;
    }

    /**
     * 获取每个罐信息显示集合
     */
    @Override
    public List<PowderTankDevice> powderTanDeviceList(String compid) {
        return tankMapper.powderTanDeviceList(compid);
    }

    /**
     * 保存罐的信息数据
     *
     * @param powderTankDevice 罐对象
     */
    @Override
    public void savePowderTanDevice(PowderTankDevice powderTankDevice) throws ErpException {
        if (powderTankDevice != null) {
            if (powderTankDevice.getCompid() == null) {
                throw new ErpException(ErrEumn.TANK_ADD_ERROR);
            }
            if (powderTankDevice.getStirId() == null) {
                throw new ErpException(ErrEumn.STIRID_NULL_ERROR);
            }
            powderTankDevice.setCreateTime(getCurrentTime());
            powderTankDeviceRepository.save(powderTankDevice);
        }

    }

    /**
     * 获取罐控制功能信息显示集合
     */
    @Override
    public List<PowderTankControl> powderTankControlList(String compid) {
        return tankMapper.powderTankControlList(compid);
    }

    /**
     * 保存罐控制功能信息
     *
     * @param powderTankControl 罐控制对象
     */
    @Override
    public void savePowderTankControl(PowderTankControl powderTankControl) {
        powderTankControl.setCreateTime(getCurrentTime());
        powderTankControlRepository.save(powderTankControl);
    }

    /**
     * 修改罐的开关门状态
     *
     * @param compid       企业id
     * @param tankCode     罐代号
     * @param tankStatus   罐开门状态
     * @param doorPassword 罐开门密码
     */
    @Override
    public void updateDoorStatus(String compid, String tankCode, Integer tankStatus, String doorPassword) throws ErpException {
        PowderTankControl powderTankControl = tankMapper.getPowderTankControl(compid, tankCode);
        if (powderTankControl == null) {
            //罐号不存在
            throw new ErpException(ErrEumn.TANK_NULL_ERROR);
        }
        if (!doorPassword.equals(powderTankControl.getDoorOpenPassword())) {
            //密码错误
            throw new ErpException(ErrEumn.PASSWORD_ERROR);
        }
        tankMapper.updatePowderTankControl(compid, tankCode, tankStatus);

    }

    /**
     * 罐的重量变化信息集合列表
     */
    @Override
    public List<WeightChangeRecord> weighChangeRecordList(String compid) {

        return tankMapper.weighChangeRecordList(compid);
    }

    /**
     * 保存罐重量变化信息
     */
    @Override
    public void saveWeighChangeRecord(WeightChangeRecord weightChangeRecord) {
        weightChangeRecord.setCreateTime(getCurrentTime());
        weighChangeRecordRepository.save(weightChangeRecord);
    }


    /**
     * 获取罐上料信息集合列表
     */
    @Override
    public List<PowderTankSafeStatusInfor> powderTankSafeStatusInforList(String compid) {
        return tankMapper.powderTankSafeStatusInforList(compid);
    }

    /**
     * 保存罐上料信息
     */
    @Override
    public void savePowderTankSafeStatusInfor(PowderTankSafeStatusInfor powderTankSafeStatusInfor) {
        powderTankSafeStatusInfor.setCreateTime(getCurrentTime());
        powerTankSafeStatusInforRepository.save(powderTankSafeStatusInfor);
    }

    /**
     * 罐校准历史记录集合列表
     */
    @Override
    public List<PowderTankCalibration> powderTankCalibrationList(String compid) {
        return tankMapper.powderTankCalibrationList(compid);
    }

    /**
     * 保存罐校准历史记录
     */
    @Override
    public void savePowderTankCalibration(PowderTankCalibration powderTankCalibration) {
        powderTankCalibration.setCreateTime(getCurrentTime());
        powderTankCalibrationRepository.save(powderTankCalibration);
    }

    /**
     * 罐报警集合列表
     */
    @Override
    public List<PowderTankWarn> powderTankWarnList(String compid) {
        return tankMapper.powderTankWarnList(compid);
    }

    /**
     * 保存罐报警信息
     */
    @Override
    public void savePowderTankWarn(PowderTankWarn powderTankWarn) {
        powderTankWarn.setCreateTime(getCurrentTime());
        powderTankWarnRepository.save(powderTankWarn);
    }

    //获取当前时间并转换格式
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
