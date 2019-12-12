package com.hntxrj.txerp.vo;



import lombok.Data;

import java.io.Serializable;

@Data
public class ProductDriverLEDVo implements Serializable {


    /**
     * 线号
     */
    private  String stirId;
    private  String stirName;

    /**
     * 车号
     */
    private  String carID;
}
