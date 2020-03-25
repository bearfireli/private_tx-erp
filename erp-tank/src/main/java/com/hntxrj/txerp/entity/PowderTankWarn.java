package com.hntxrj.txerp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
@Table(name = "pt_powder_tank_warn")
@IdClass(ptPowderTankWarnUPK.class)
public class PowderTankWarn implements Serializable {

    @Id
    @Column(name = "compid")
    private String compid;      //企业代号

    @Id
    @Column(name = "stirid")
    private String stirId;      //生产线号

    @Id
    @Column(name = "tank_code")
    private Integer tankCode;       //罐ID代号

    @Column(name = "cans_name")
    private String cansName;        //罐名称
    @Column(name = "measure_type")
    private String measureType;     //罐类别
    @Column(name = "state_tank")
    private int stateTank;          //罐的状态
    @Column(name = "warn_type")
    private int warnType;           //报警类型
    @Column(name = "remarks")
    private String reMarks;         //备注说明
    @Column(name = "isshow")
    private int isShow;             //是否提醒
    @Column(name = "empname")
    private String empName;         //操作员名
    @Column(name = "create_time")
    private String createTime;      //创建时间
    @Column(name = "updown")
    private boolean upDown;         //网络上传标识
    @Column(name = "updownmark")
    private int upDownMark;         //网络同步标识
}

class ptPowderTankWarnUPK implements Serializable {
    private String compid;
    private String stirId;
    private Integer tankCode;
    private String createTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ptPowderTankWarnUPK that = (ptPowderTankWarnUPK) o;
        return Objects.equals(compid, that.compid) &&
                Objects.equals(stirId, that.stirId) &&
                Objects.equals(tankCode, that.tankCode) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(compid, stirId, tankCode, createTime);
    }
}
