package com.hntxrj.vo;

import lombok.Data;

import java.util.List;

@Data
public class ProductDriverLEDListVO {

    private String stirID;
    private String stirName;

    List<ProductDriverLEDVo> cars;
}
