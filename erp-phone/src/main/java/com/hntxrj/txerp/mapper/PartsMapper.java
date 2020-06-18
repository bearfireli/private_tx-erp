package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.WmConFigureApply;
import com.hntxrj.txerp.vo.*;
import com.hntxrj.txerp.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PartsMapper {
    List<PartsVO> getPartsList(String compid, String beginTime,
                               String endTime, String goodsName,
                               String buyer, String specification,
                               String department, String requestNumber,
                               String requestStatus, String requestDep, String verifyStatusOne);

    List<UserVO> getBuyerList(String compid, String searchName);

    List<DepartmentVO> getDepartmentList(String compid);

    List<RequestStatusListVO> getRequestStatusList(String compid);

    PartsVO selectid(String compid, String requestNumber);

    List<RequestModeVO> getRequestMode(String compid);

    List<AccessCatalogVO> getMnemonicCodeList(String compid);

    PartsVO getRequestNumberDetail(String requestNumber, String compid);

    void editRequestStatus(String compid, String requestNumber, String requestStatus, String verifyIdOne,
                           Boolean verifyStatusOne, String verifyTimeOne, String auditResultOne);

    void cancelRequestStatus(String compid, String requestNumber, String requestStatus, String verifyIdOne,
                             String verifyStatusOne, String verifyTimeOne, String auditResultOne);

    // 获取配件单号获取仓库配件详情
    WmConFigureApply getConFigureApply(String compid, String requestNumber);
}
