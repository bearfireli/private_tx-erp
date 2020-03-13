package com.hntxrj.txerp.entity;

import lombok.Data;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
@IdClass(PowderTankControlUPK.class)
@Table(name = "pt_powder_tank_control")
public class PowderTankControl implements Serializable {

    @Id
    @Column(name = "compid")
    private String compid;      //企业ID

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
    @Column(name = "door_open_password")
    private String doorOpenPassword;        //开门密码
    @Column(name = "command_type")
    private int commandType;            //命令类型
    @Column(name = "whether_to_execute")
    private int whetherToExecute;       //是否执行
    @Column(name = "remarks")
    private String reMarks;         //备注说明
    @Column(name = "empname")
    private String empName;         //操作员名
    @Column(name = "create_time")
    private String createTime;      //创建时间
    @Column(name = "updown")
    private boolean upDown;         //网络上传标识
    @Column(name = "updownmark")
    private int upDownMark;         //网络同步标识

}

class PowderTankControlUPK implements Serializable {
    private String compid;
    private String stirId;
    private Integer tankCode;
    private String createTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PowderTankControlUPK that = (PowderTankControlUPK) o;
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
