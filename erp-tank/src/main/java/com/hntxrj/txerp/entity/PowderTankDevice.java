package com.hntxrj.txerp.entity;

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
    private Integer tankCode;       //罐ID代号

    @Id
    @Column(name = "compid")
    private String compid;          //企业代号

    @Id
    @Column(name = "stirid")
    private String stirId;          //生产线号

    @Column(name = "channel_type")
    private int channelType;        //通道类型
    @Column(name = "out_put_ad_channel")
    private int outPutADChannel;        //采集AD通道号
    @Column(name = "channel_name")
    private String channelName;         //通道名称
    @Column(name = "weigh_scale")
    private BigDecimal weighScale;      //称刻度
    @Column(name = "fractal_said")
    private BigDecimal fractalSaid;        //分称数
    @Column(name = "max_voltage")
    private BigDecimal maxVoltage;      //最大电压值
    @Column(name = "max_weight_value")
    private BigDecimal maxWeightValue;      //最大称重值（kg）
    @Column(name = "resolution_ratio")
    private int ResolutionRatio;        //分辨率
    @Column(name = "percentage_error")
    private BigDecimal percentageError;     //误差百分比
    @Column(name = "electrical_zero")
    private BigDecimal electricalZero;      //零点AD值
    @Column(name = "sto_type")
    private int stoType;            //库位类型
    @Column(name = "is_out_put")
    private boolean isOutPut;       //是否输出电压或者数字量
    @Column(name = "data_value_simulation_increase_unit")
    private int dataValueSimulationIncreaseUnit;        //数据量模拟递增单位
    @Column(name = "data_value_simulation_decrease_unit")
    private int dataValueSimulationDecreaseUnit;        //数据量递减单位
    @Column(name = "current_weight")
    private BigDecimal currentWeight;           //当前重量（kg）
    @Column(name = "minx_warning_weight")
    private BigDecimal minxWarningWeight;       //最小报警重量
    @Column(name = "cans_name")
    private String cansName;            //罐名称
    @Column(name = "measure_type")
    private String measureType;         //罐类别
    @Column(name = "mat_name")
    private String matName;             //材料名称
    @Column(name = "state_tank")
    private int stateTank;              //罐的状态
    @Column(name = "load_mouth_status_channel_in")
    private int loadMouthStatusChannelIn;       //上料口状态通道（in）
    @Column(name = "load_mouth_status")
    private int loadMouthStatus;            //罐的上料口状态
    @Column(name = "load_mouth_open_channel_out")
    private int loadMouthOpenChannelOut;       //上料口开门通道（out）
    @Column(name = "load_mouth_open_status")
    private int loadMouthOpenStatus;            //上料口开门通道状态
    @Column(name = "load_mouth_close_channel_out")
    private int loadMouthCloseChannelOut;       //上料口关门通道（out）
    @Column(name = "load_mouth_close_status")
    private int loadMouthCloseStatus;           //上料口关门通道状态
    @Column(name = "door_open_password")
    private String doorOpenPassword;            //开门密码
    @Column(name = "high_material_level")
    private int highMaterialLevel;              //高料位状态
    @Column(name = "middle_material_level")
    private int middleMaterialLevel;            //中料位状态
    @Column(name = "low_material_level")
    private int lowMaterialLevel;               //低料位状态
    @Column(name = "point_top")
    private int pointTop;                       //罐顶部坐标值
    @Column(name = "point_left")
    private int pointLeft;                      //罐左边坐标值
    @Column(name = "object_height")
    private int objectHeight;                   //罐的高度值
    @Column(name = "object_width")
    private int objectWidth;                    //罐的宽度值
    @Column(name = "recstatus")
    private boolean recStatus;                  //是否启用
    @Column(name = "remarks")
    private String reMarks;                     //备注说明
    @Column(name = "empname")
    private String empName;                     //操作员名
    @Column(name = "create_time")
    private String createTime;                  //创建时间
    @Column(name = "updown")
    private boolean upDown;                     //网络上传标识
    @Column(name = "updownmark")
    private int upDownMark;                     //网络同步标识

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class PowderTankDeviceUPK implements Serializable {

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
