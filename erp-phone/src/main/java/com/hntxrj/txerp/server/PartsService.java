package com.hntxrj.txerp.server;

import com.hntxrj.txerp.entity.WmConFigureApply;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.*;
import com.hntxrj.txerp.vo.*;

public interface PartsService {

    /**
     * @param compid       企业
     * @param beginTime   开始时间
     * @param endTime     结束时间
     * @param goodsName　　物品名称
     * @param buyer　　　　　申请人
     * @param specification　物品规格
     * @param department　　　申请部门
     * @param requestNumber　　申请单号
     * @param requestStatus　　申请单状态
     * @param requestDep　　　申请描述
     * @param page         分页
     * @param pageSize     每页显示条数
     * @return  　　　　　　　配件列表
     * */
    PageVO<PartsVO> getPartsList(String compid, String  beginTime, String endTime, String goodsName,
                                 String buyer, String specification, String department,
                                 String requestNumber, String requestStatus,
                                 String requestDep,
                                 Integer page,
                                 Integer pageSize);

    /**
     *
     * @param compid       企业
     * @param page         分页
     * @param pageSize     每页显示条数
     * @return        申请人列表
     */
    PageVO<UserVO> getBuyerList(String compid, Integer page, Integer pageSize);

    PageVO<DepartmentVO> getDepartmentList(String compid, Integer page, Integer pageSize);

    PageVO<RequestStatusListVO> getRequestStatusList(String compid, Integer page, Integer pageSize);

    void addWmConFigureApply(WmConFigureApply wmConFigureApply) throws ErpException;

    void editWmConFigureApply(WmConFigureApply wmConFigureApply) throws ErpException;

    PageVO<RequestModeVO> getRequestMode(String compid, Integer page, Integer pageSize);

    PageVO<AccessCatalogVO>  getMnemonicCodeList(String compid, Integer page, Integer pageSize);

    PartsVO getRequestNumberDetail(String requestNumber, String compid);

    void editRequestStatus(String compid, String requestNumber, String requestStatus, String verifyIdOne)
            throws ErpException;

    void cancelRequestStatus(String compid, String requestNumber, String requestStatus, String verifyIdOne);
}
