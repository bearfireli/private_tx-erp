package com.hntxrj.txerp.vo;

import lombok.Data;

import java.util.List;

/**
 * @author qyb
 * @ClassName DirverLEDListVO
 * @Description TODO
 * @Date 19-6-18 下午3:20
 * @Version 1.0
 **/
@Data
public class DirverLEDListVO {


    private Integer status;

    private String statusName;

    List<DriverShiftLEDVO> cars;

}
