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
    private final WeighChangeRecordRepository weighChangeRecordRepository;
    private final PowderTankSafeStatusInforRepository powerTankSafeStatusInforRepository;
    private final PowderTankCalibrationRepository powderTankCalibrationRepository;
    private final PowderTankWarnRepository powderTankWarnRepository;

    @Autowired
    public TankServiceImpl(TankMapper tankMapper, PowderTankDeviceRepository powderTankDeviceRepository,
                           WeighChangeRecordRepository weighChangeRecordRepository,
                           PowderTankSafeStatusInforRepository powerTankSafeStatusInforRepository,
                           PowderTankCalibrationRepository powderTankCalibrationRepository,
                           PowderTankWarnRepository powderTankWarnRepository) {
        this.tankMapper = tankMapper;
        this.powderTankDeviceRepository = powderTankDeviceRepository;
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
                throw new ErpException(ErrEumn.ADD_CONTRACT_NOT_FOUND_BUILDERCODE);
            }
            if (powderTankDevice.getStirId() == null) {
                throw new ErpException(ErrEumn.ADD_CONTRACT_NOT_FOUND_BUILDERCODE);
            }
            powderTankDeviceRepository.save(powderTankDevice);
        }

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
        powderTankWarnRepository.save(powderTankWarn);
    }
}
