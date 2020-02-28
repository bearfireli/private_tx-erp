package com.hntxrj.txerp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Data
@Table(name = "pt_powder_tank_calibration")
@IdClass(PowderTankCalibrationUPK.class)
public class PowderTankCalibration implements Serializable {
    @Id
    @Column(name = "compid")
    private String compid;

    @Id
    @Column(name = "stirid")
    private String stirId;

    @Id
    @Column(name = "tank_code")
    private Integer tankCode;

    @Column(name = "out_put_ad_channel")
    private int outPutAdChannel;
    @Column(name = "channel_name")
    private String channelName;
    @Column(name = "weigh_scale")
    private BigDecimal weighScale;
    @Column(name = "fractal_said")
    private BigDecimal fractalSaid;
    @Column(name = "resolution_ratio")
    private int resolutionRatio;
    @Column(name = "electrical_zero")
    private BigDecimal electricalZero;
    @Column(name = "cans_name")
    private String cansName;
    @Column(name = "measure_type")
    private String measureType;
    @Column(name = "mat_name")
    private String matName;
    @Column(name = "state_tank")
    private int stateTank;
    @Column(name = "recstatus")
    private boolean recStatus;
    @Column(name = "remarks")
    private String reMarks;
    @Column(name = "empname")
    private String empName;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "updown")
    private boolean upDown;
    @Column(name = "updownmark")
    private int upDownMark;

}

class PowderTankCalibrationUPK implements Serializable {
    private String compid;
    private String stirId;
    private Integer tankCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PowderTankCalibrationUPK that = (PowderTankCalibrationUPK) o;
        return Objects.equals(compid, that.compid) &&
                Objects.equals(stirId, that.stirId) &&
                Objects.equals(tankCode, that.tankCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(compid, stirId, tankCode);
    }
}
