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
    private String compid;

    @Id
    @Column(name = "stirid")
    private String stirId;

    @Id
    @Column(name = "tank_code")
    private Integer tankCode;

    @Column(name = "cans_name")
    private String cansName;
    @Column(name = "measure_type")
    private String measureType;
    @Column(name = "state_tank")
    private int stateTank;
    @Column(name = "warn_type")
    private int warnType;
    @Column(name = "remarks")
    private String reMarks;
    @Column(name = "isshow")
    private int isShow;
    @Column(name = "empname")
    private String empName;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "updown")
    private boolean upDown;
    @Column(name = "updownmark")
    private int upDownMark;
}

class ptPowderTankWarnUPK implements Serializable{
    private String compid;
    private String stirId;
    private Integer tankCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ptPowderTankWarnUPK that = (ptPowderTankWarnUPK) o;
        return Objects.equals(compid, that.compid) &&
                Objects.equals(stirId, that.stirId) &&
                Objects.equals(tankCode, that.tankCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(compid, stirId, tankCode);
    }
}
