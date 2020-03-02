package com.hntxrj.txerp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@Table(name = "pt_power_tank_safe_status_infor")
@IdClass(PowderTankSafeStatusInforUPK.class)
public class PowderTankSafeStatusInfor implements Serializable {
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
    @Column(name = "mat_name")
    private String matName;
    @Column(name = "state_tank")
    private int stateTank;
    @Column(name = "load_mouth_status_channel_in")
    private int loadMouthStatusChannelIn;
    @Column(name = "load_mouth_status")
    private int loadMouthStatus;
    @Column(name = "load_mouth_open_channel_out")
    private int loadMouthOpenChannelOut;
    @Column(name = "load_mouth_open_status")
    private int loadMouthOpenStatus;
    @Column(name = "load_mouth_close_channel_out")
    private int loadMouthCloseChannelOut;
    @Column(name = "load_mouth_close_status")
    private int loadMouthCloseStatus;
    @Column(name = "door_open_password")
    private String doorOpenPassword;
    @Column(name = "high_material_level")
    private int highMaterialLevel;
    @Column(name = "middle_material_level")
    private int middleMaterialLevel;
    @Column(name = "low_material_level")
    private int lowMaterialLevel;
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

class PowderTankSafeStatusInforUPK implements Serializable{
    private String compid;
    private String stirId;
    private Integer tankCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PowderTankSafeStatusInforUPK that = (PowderTankSafeStatusInforUPK) o;
        return Objects.equals(compid, that.compid) &&
                Objects.equals(stirId, that.stirId) &&
                Objects.equals(tankCode, that.tankCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(compid, stirId, tankCode);
    }
}
