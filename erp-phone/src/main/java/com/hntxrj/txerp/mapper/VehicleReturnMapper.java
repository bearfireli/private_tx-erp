package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.VehicleReturnVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface VehicleReturnMapper {
    /**
     * 退转剩列表
     *
     * @param compid     　企业id
     * @param vehicleId  　车号
     * @param inEppName  　原工程名称
     * @param outEppName 　转入工程名称
     * @param remarks    　备注原因
     * @param beginTime  　开始时间
     * @param endTime    　结束时间
     */
    List<VehicleReturnVO> vehicleReturnList(String compid, String vehicleId, String inEppName, String outEppName,
                                            String remarks, String beginTime, String endTime);

    /**
     * 退转剩详情
     *
     * @param compid    　企业id
     * @param vehicleId 　车号
     * @param taskIdOld 　原任务单号
     * @param sendTime  　派车时间
     */
    VehicleReturnVO vehicleReturnDetail(String compid, String vehicleId, String taskIdOld, String sendTime);
}
