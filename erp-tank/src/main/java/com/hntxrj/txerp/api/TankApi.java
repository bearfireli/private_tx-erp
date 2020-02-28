package com.hntxrj.txerp.api;


import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.entity.*;
import com.hntxrj.txerp.service.TankService;
import com.hntxrj.txerp.vo.ResultVO;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/tank")
@Log4j
public class TankApi {

    private final TankService tankService;
    public TankApi(TankService tankService) {
        this.tankService = tankService;
    }

    /**
     * 获取每个罐信息显示集合
     * */
    @PostMapping("/powderTanDeviceList")
    public ResultVO powderTanDeviceList(String compid){
        return ResultVO.create(tankService.powderTanDeviceList(compid));
    }

    /**
     * 保存罐的信息数据
     *
     * @param powderTankDevice 罐对象
     * */
    @PostMapping("/savePowderTanDevice")
    public ResultVO savePowderTanDevice(PowderTankDevice powderTankDevice) throws ErpException {
        tankService.savePowderTanDevice(powderTankDevice);
        return ResultVO.create();
    }


    /**
     * 罐的重量变化信息集合列表
     * */
    @PostMapping("/weighChangeRecordList")
    public ResultVO weighChangeRecordList(String compid){
        return ResultVO.create(tankService.weighChangeRecordList(compid));
    }

    /**
     * 保存罐的重量变化信息
     * */
    @PostMapping("/saveWeighChangeRecord")
    public ResultVO saveWeighChangeRecord(WeightChangeRecord weightChangeRecord){
        tankService.saveWeighChangeRecord(weightChangeRecord)
        return ResultVO.create();
    }

    /**
     * 罐的上料信息集合列表
     * */
    @PostMapping("/powderTankSafeStatusInforList")
    public ResultVO powderTankSafeStatusInforList(String compid){
        return ResultVO.create(tankService.powderTankSafeStatusInforList(compid));
    }

    /**
     * 保存罐的上料信息
     *
     * @param powderTankSafeStatusInfor 罐上料信息对象
     * */
    @PostMapping("/savePowderTankSafeStatusInfor")
    public ResultVO savePowderTankSafeStatusInfor(PowderTankSafeStatusInfor powderTankSafeStatusInfor){
        tankService.savePowderTankSafeStatusInfor(powderTankSafeStatusInfor);
        return ResultVO.create();
    }

    /**
     * 罐的校准历史记录集合列表
     * */
    @PostMapping("/powderTankCalibrationList")
    public ResultVO powderTankCalibrationList(String compid){
        return ResultVO.create(tankService.powderTankCalibrationList(compid));
    }

    /**
     * 保存罐的校准历史记录
     *
     * @param powderTankCalibration 罐校准历史记录对象
     * */
    @PostMapping("/savePowderTankCalibration")
    public ResultVO savePowderTankCalibration(PowderTankCalibration powderTankCalibration){
        tankService.savePowderTankCalibration(powderTankCalibration);
        return ResultVO.create();
    }

    /**
     * 罐报警列表查询
     * */
    @PostMapping("/powderTankWarnList")
    public ResultVO powderTankWarnList(String compid){
        return ResultVO.create(tankService.powderTankWarnList(compid));
    }

    /**
     * 保存罐的报警信息
     *
     * @param powderTankWarn 罐校准历史记录对象
     * */
    @PostMapping("/savePowderTankWarn")
    public ResultVO savePowderTankWarn(PowderTankWarn powderTankWarn){
        tankService.savePowderTankWarn(powderTankWarn);
        return ResultVO.create();
    }


}
