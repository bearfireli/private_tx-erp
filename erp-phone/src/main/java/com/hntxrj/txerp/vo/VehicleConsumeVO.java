package com.hntxrj.txerp.vo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class VehicleConsumeVO {
    private String compid;
    private String taskId;
    private String vehicleId;
    private String placing;
    private String stgId;
    private Integer stirId;
    private String stirName;
    private Double panNum;
    private Timestamp leaveTime;
    private Integer invoiceId;

}
