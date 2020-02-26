package com.hntxrj.txerp.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PowderTankDevice implements Serializable {

    private int tankCode;

    private String compid;

    private String stirId;

    private int channelType;

    private int outPutADChannel;

    private String channelName;

    private BigDecimal weighScale;

    private BigDecimal fracSaid;

    private BigDecimal maxVoltage;

    private BigDecimal maxWeightValue;

    private int ResolutionRatio;

    private BigDecimal percentageError;

    private BigDecimal electricalZero;

    private int stoType;

    private boolean isOutPut;

    private int dataValueSimulationIncreaseUnit;

    private int dataValueSimulationDecreaseUnit;

    private BigDecimal currentWeight;

    private BigDecimal minxWarningWeight;

    private String cansName;

    private String measureType;

    private String matName;

    private int stateTank;

    private int loadMouthStatusChannelIn;

    private int loadMouthStatus;

    private int loadMouthOpenChannelOut;

    private int loadMouthOpenStatus;

    private int loadMouthCloseChannelOut;

    private int loadMouthCloseStatus;

    private String doorOpenPassword;

    private int highMaterialLevel;

    private int middleMaterialLevel;

    private int lowMaterialLevel;

    private int pointTop;

    private int pointLeft;

    private int objectHeight;

    private int objectWidth;

    private boolean recStatus;

    private String reMarks;

    private String empName;

    private String createTime;

    private boolean upDown;

    private int upDownMark;

}
