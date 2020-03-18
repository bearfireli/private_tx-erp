package com.hntxrj.txerp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@Entity
@Table(name = "pt_power_tank_safe_status_infor")
@IdClass(PowderTankSafeStatusInforUPK.class)
public class PowderTankSafeStatusInfor implements Serializable {
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
    private String measureType;     //罐类型
    @Column(name = "mat_name")
    private String matName;         //材料名称
    @Column(name = "state_tank")
    private int stateTank;          //罐的状态
    @Column(name = "load_mouth_status_channel_in")
    private int loadMouthStatusChannelIn;       //上料口状态通道（in）
    @Column(name = "load_mouth_status")
    private int loadMouthStatus;            //罐的上料口状态
    @Column(name = "load_mouth_open_channel_out")
    private int loadMouthOpenChannelOut;        //上料口开门通道（out）
    @Column(name = "load_mouth_open_status")
    private int loadMouthOpenStatus;        //上料口开门通道状态
    @Column(name = "load_mouth_close_channel_out")
    private int loadMouthCloseChannelOut;       //上料口关门通道（out）
    @Column(name = "load_mouth_close_status")
    private int loadMouthCloseStatus;           //上料口关门通道状态
    @Column(name = "door_open_password")
    private String doorOpenPassword;            //开门密码
    @Column(name = "high_material_level")
    private int highMaterialLevel;          //高料位状态
    @Column(name = "middle_material_level")
    private int middleMaterialLevel;        //中料位状态
    @Column(name = "low_material_level")
    private int lowMaterialLevel;           //低料位状态
    @Column(name = "remarks")
    private String reMarks;                 //备注说明
    @Column(name = "empname")
    private String empName;                 //操作员名
    @Column(name = "create_time")
    private String createTime;              //创建时间
    @Column(name = "updown")
    private boolean upDown;                 //网络上传表示
    @Column(name = "updownmark")
    private int upDownMark;                 //网络同步标识
    @Column(name = "current_weight")
    private BigDecimal currentWeight;       //当前重量
    @Column(name = "feed_quantity")
    private BigDecimal feedQuantity;        //补料数量


}

class PowderTankSafeStatusInforUPK implements Serializable {
    private String compid;
    private String stirId;
    private Integer tankCode;
    private String createTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PowderTankSafeStatusInforUPK that = (PowderTankSafeStatusInforUPK) o;
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
