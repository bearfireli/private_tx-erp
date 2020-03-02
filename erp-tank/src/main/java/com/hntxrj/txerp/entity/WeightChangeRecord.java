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
    private String compid;

    @Id
    @Column(name = "stirid")
    private String stirId;

    @Id
    @Column(name = "tank_code")
    private Integer tankCode;

    @Column(name = "current_weight")
    private BigDecimal currentWeight;
    @Column(name = "production_consumption")
    private BigDecimal productionConsumption;
    @Column(name = "feed_quantity")
    private BigDecimal feedQuantity;
    @Column(name = "error_value")
    private BigDecimal errorValue;
    @Column(name = "cans_name")
    private String cansName;
    @Column(name = "measure_type")
    private String measureType;
    @Column(name = "mat_name")
    private String matName;
    @Column(name = "remarks")
    private String reMarks;
    @Column(name = "out_put_ad_channel")
    private int outPutAdChannel;
    @Column(name = "empname")
    private String empName;
    @Column(name = "create_time")
    private String createTime;
    @Column(name = "updown")
    private boolean upDown;
    @Column(name = "updownmark")
    private int upDownMark;



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
