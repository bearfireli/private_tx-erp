package com.hntxrj.txerp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Data
@Entity
@IdClass(WeightChangeRecordUPK.class)
@Table(name = "pt_weight_change_record")
public class WeightChangeRecord implements Serializable {
    @Id
    @Column(name = "compid")
    private String compid;      //企业代号

    @Id
    @Column(name = "stirid")
    private String stirId;      //生产线号

    @Id
    @Column(name = "tank_code")
    private Integer tankCode;       //罐ID代号

    @Column(name = "current_weight")
    private BigDecimal currentWeight;       //当前重量（kg）
    @Column(name = "production_consumption")
    private BigDecimal productionConsumption;       //生产消耗数量
    @Column(name = "feed_quantity")
    private BigDecimal feedQuantity;        //补料数量
    @Column(name = "error_value")
    private BigDecimal errorValue;          //误差值
    @Column(name = "cans_name")
    private String cansName;                //罐名称
    @Column(name = "measure_type")
    private String measureType;             //罐类型
    @Column(name = "mat_name")
    private String matName;                 //材料名称
    @Column(name = "remarks")
    private String reMarks;                 //说明备注
    @Column(name = "out_put_ad_channel")
    private int outPutAdChannel;            //通道号
    @Column(name = "empname")
    private String empName;                 //操作员名
    @Column(name = "create_time")
    private String createTime;              //创建时间
    @Column(name = "updown")
    private boolean upDown;                 //网络上传标识
    @Column(name = "updownmark")
    private int upDownMark;                 //网络同步标识



}

class WeightChangeRecordUPK implements Serializable{
    private String compid;
    private String stirId;
    private Integer tankCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeightChangeRecordUPK that = (WeightChangeRecordUPK) o;
        return Objects.equals(compid, that.compid) &&
                Objects.equals(stirId, that.stirId) &&
                Objects.equals(tankCode, that.tankCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(compid, stirId, tankCode);
    }
}
