package com.hntxrj.txerp.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProductDriverLEDListVO {

    private String stirID;
    private String stirName;

    List<ProductDriverLEDVo> cars;
}
