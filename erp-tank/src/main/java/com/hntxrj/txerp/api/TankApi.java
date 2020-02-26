package com.hntxrj.txerp.api;


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

    @PostMapping("/powderTanDeviceList")
    public ResultVO powderTanDeviceList(){
        tankService.powderTanDeviceList();
        return ResultVO.create();
    }

}
