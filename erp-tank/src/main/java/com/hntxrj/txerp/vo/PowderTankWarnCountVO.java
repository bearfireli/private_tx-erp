package com.hntxrj.txerp.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PowderTankWarnCountVO implements Serializable {
    //0正常 1 高料位报警 2低料位报警 3 空罐报警 4设备异常 5其他
    private int normalNum;          //正常
    private int highLevelNum;       //高料位报警
    private int lowLevelNum;        //低料位报警
    private int emptyNum;           //空罐报警
    private int machineErrorNum;    //设备异常
    private int otherNum;           //其他

}
