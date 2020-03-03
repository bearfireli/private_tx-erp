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
    private String compid;      //企业代号

    @Id
    @Column(name = "stirid")
    private String stirId;      //生产线号

    @Id
    @Column(name = "tank_code")
    private Integer tankCode;       //罐ID代号

    @Column(name = "out_put_ad_channel")
    private int outPutAdChannel;        //采集AD通道号
    @Column(name = "channel_name")
    private String channelName;         //通道名称
    @Column(name = "weigh_scale")
    private BigDecimal weighScale;      //称刻度
    @Column(name = "fractal_said")
    private BigDecimal fractalSaid;     //分称数
    @Column(name = "resolution_ratio")
    private int resolutionRatio;        //分辨率
    @Column(name = "electrical_zero")
    private BigDecimal electricalZero;      //零点AD值
    @Column(name = "cans_name")
    private String cansName;            //罐名称
    @Column(name = "measure_type")
    private String measureType;         //罐类别
    @Column(name = "mat_name")
    private String matName;             //材料状态
    @Column(name = "state_tank")
    private int stateTank;              //罐的状态
    @Column(name = "recstatus")
    private boolean recStatus;          //是否启用
    @Column(name = "remarks")
    private String reMarks;             //备注说明
    @Column(name = "empname")
    private String empName;             //操作员名
    @Column(name = "create_time")
    private String createTime;          //创建时间
    @Column(name = "updown")
    private boolean upDown;             //网络上传标识
    @Column(name = "updownmark")
    private int upDownMark;             //网络同步标识

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
