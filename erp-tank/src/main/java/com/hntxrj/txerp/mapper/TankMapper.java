package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface TankMapper {

    List<PowderTankDevice> powderTanDeviceList(String compid);

    List<WeightChangeRecord> weighChangeRecordList(String compid);

    List<PowderTankSafeStatusInfor> powderTankSafeStatusInforList(String compid);

    List<PowderTankCalibration> powderTankCalibrationList(String compid);

    List<PowderTankWarn> powderTankWarnList(String compid);
}
