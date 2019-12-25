package com.hntxrj.txerp.vo;

import lombok.Data;

import java.util.List;

/**
 * @author qyb
 * @ClassName ShiftListVO
 * @Description TODO
 * @Date 2019/11/21 下午1:27
 * @Version 1.0
 **/
@Data
public class ShiftListVO {
    private String workClass;
    private String workName;
    private  List<DriverShiftListVO> shiftList;
}
