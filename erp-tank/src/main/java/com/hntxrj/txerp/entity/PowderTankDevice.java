package com.hntxrj.txerp.entity;

import com.sun.org.apache.xpath.internal.operations.Equals;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@Entity
@IdClass(PowderTankDeviceUPK.class)
@Table(name = "pt_powder_tank_device")
public class PowderTankDevice implements Serializable {
    @Id
    @Column(name = "tank_code")
    private Integer tankCode;

    @Id
    @Column(name = "compid")
    private String compid;

    @Id
    @Column(name = "stirid")
    private String stirId;

    @Column(name = "channel_type")
    private int channelType;
    @Column(name = "out_put_ad_channel")
    private int outPutADChannel;
    @Column(name = "channel_name")
    private String channelName;
    @Column(name = "weigh_scale")
    private BigDecimal weighScale;
    @Column(name = "fractal_said")
    private BigDecimal fracSaid;
    @Column(name = "max_voltage")
    private BigDecimal maxVoltage;
    @Column(name = "max_weight_value")
    private BigDecimal maxWeightValue;
    @Column(name = "resolution_ratio")
    private int ResolutionRatio;
    @Column(name = "percentage_error")
    private BigDecimal percentageError;
    @Column(name = "electrical_zero")
    private BigDecimal electricalZero;
    @Column(name = "sto_type")
    private int stoType;
    @Column(name = "is_out_put")
    private boolean isOutPut;
    @Column(name = "data_value_simulation_increase_unit")
    private int dataValueSimulationIncreaseUnit;
    @Column(name = "data_value_simulation_decrease_unit")
    private int dataValueSimulationDecreaseUnit;
    @Column(name = "current_weight")
    private BigDecimal currentWeight;
    @Column(name = "minx_warning_weight")
    private BigDecimal minxWarningWeight;
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
    @Column(name = "point_top")
    private int pointTop;
    @Column(name = "point_left")
    private int pointLeft;
    @Column(name = "object_height")
    private int objectHeight;
    @Column(name = "object_width")
    private int objectWidth;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
class PowderTankDeviceUPK implements Serializable{

    private Integer tankCode;
    private String compid;
    private String stirId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PowderTankDeviceUPK that = (PowderTankDeviceUPK) o;
        return Objects.equals(compid, that.compid) &&
                Objects.equals(stirId, that.stirId) &&
                Objects.equals(tankCode, that.tankCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(compid, stirId, tankCode);
    }


}
