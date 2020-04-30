package com.hntxrj.txerp.server.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.entity.WmConFigureApply;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.mapper.PartsMapper;
import com.hntxrj.txerp.repository.WmConFigureApplyRepository;
import com.hntxrj.txerp.server.PartsService;
import com.hntxrj.txerp.util.EntityTools;
import com.hntxrj.txerp.vo.*;
import com.hntxrj.txerp.vo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author qyb
 * PartsServiceImpl
 * 仓库配件
 * 19-7-9 上午8:51
 **/
@Service
@Scope("prototype")
public class PartsServiceImpl implements PartsService {

    private final PartsMapper partsMapper;
    private final WmConFigureApplyRepository wmConFigureApplyRepository;

    @Autowired
    public PartsServiceImpl(PartsMapper partsMapper, WmConFigureApplyRepository wmConFigureApplyRepository) {
        this.partsMapper = partsMapper;
        this.wmConFigureApplyRepository = wmConFigureApplyRepository;
    }

    @Override
    public PageVO<PartsVO> getPartsList(String compid, String beginTime, String endTime, String goodsName,
                                        String buyer, String specification, String department,
                                        String requestNumber, String requestStatus,
                                        String requestDep,String verifyStatusOne, Integer page, Integer pageSize) {
        PageVO<PartsVO> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize, "CreateTime desc");
        List<PartsVO> partsVOS = partsMapper.getPartsList(
                compid, beginTime, endTime, goodsName, buyer, specification, department,
                requestNumber, requestStatus, requestDep,verifyStatusOne);
        PageInfo<PartsVO> pageInfo = new PageInfo<>(partsVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }

    /**
     * @param compid   企业
     * @param page     分页
     * @param pageSize 每页显示条数
     * @return 申请人列表
     */
    @Override
    public PageVO<UserVO> getBuyerList(String compid, String searchName,Integer page, Integer pageSize) {
        PageVO<UserVO> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize);
        List<UserVO> buyerVOS = partsMapper.getBuyerList(
                compid,searchName);
        PageInfo<UserVO> pageInfo = new PageInfo<>(buyerVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public PageVO<DepartmentVO> getDepartmentList(String compid, Integer page, Integer pageSize) {
        PageVO<DepartmentVO> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize);
        List<DepartmentVO> buyerVOS = partsMapper.getDepartmentList(
                compid);
        PageInfo<DepartmentVO> pageInfo = new PageInfo<>(buyerVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public PageVO<RequestStatusListVO> getRequestStatusList(String compid, Integer page, Integer pageSize) {
        PageVO<RequestStatusListVO> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize);
        List<RequestStatusListVO> buyerVOS = partsMapper.getRequestStatusList(compid);
        PageInfo<RequestStatusListVO> pageInfo = new PageInfo<>(buyerVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public void addWmConFigureApply(WmConFigureApply wmConFigureApply) throws ErpException {
        if (StringUtils.isEmpty(wmConFigureApply.getCompid())) {
            throw new ErpException(ErrEumn.ADD_TASK_NOT_FOUND_COMPID);
        }

        int page = 0;
        int pageSize = 1;
        //获取年
        SimpleDateFormat sdf2 = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yy-MM-dd");
        Date date = new Date();
        String year = sdf2.format(date);
        String requestNumber = year.replace("-", "");
        PageHelper.startPage(page, pageSize, "RequestNumber desc");
        PartsVO selectid = partsMapper.selectid(wmConFigureApply.getCompid(), requestNumber);

        //判断查出的数据是否为null
        if (selectid != null) {
            int id = Integer.parseInt(selectid.getRequestNumber().substring((selectid.getRequestNumber().length() - 4),
                    selectid.getRequestNumber().length())) + 1;
            String ids = String.valueOf(id);
            if (ids.length() == 1) {
                ids = "000" + ids;
            }
            if (ids.length() == 2) {
                ids = "00" + ids;
            }
            if (ids.length() == 3) {
                ids = "0" + ids;
            }
            requestNumber = requestNumber + ids;
        } else {
            //默认赋值
            requestNumber = requestNumber + "0001";
        }
        wmConFigureApply.setRequestNumber(requestNumber);

        SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
        wmConFigureApply.setCreateTime(sdf.format(new Date()));

        wmConFigureApply.setStockStatus("1");
        wmConFigureApply.setSerialnumber(1);
        wmConFigureApply.setRecStatus(true);
        wmConFigureApply.setRequestStatus(1);
        wmConFigureApply.setUpDownMark(0);
        String[] time = new String[12];
        time[0] = "verifyTimeOne";
        time[1] = "verifyTimeTWo";
        time[2] = "verifytimethree";
        time[3] = "verifytimefour";
        time[4] = "verifytimefive";
        time[5] = "verifytimesix";
        time[6] = "verifyStatusOne";
        time[7] = "verifystatustwo";
        time[8] = "verifystatusthree";
        time[9] = "verifystatusfour";
        time[10] = "verifystatusfive";
        time[11] = "verifystatussix";
        EntityTools.setEntityDefaultValue(wmConFigureApply, time);
        try {
            wmConFigureApplyRepository.save(wmConFigureApply);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErpException(ErrEumn.ADD_WMCONFIGUREAPPLY_ERROR);
        }
    }

    @Override
    public void editWmConFigureApply(WmConFigureApply wmConFigureApply) throws ErpException {

        String[] time = new String[12];
        time[0] = "verifyTimeOne";
        time[1] = "verifyTimeTWo";
        time[2] = "verifytimethree";
        time[3] = "verifytimefour";
        time[4] = "verifytimefive";
        time[5] = "verifytimesix";
        time[6] = "verifyStatusOne";
        time[7] = "verifystatustwo";
        time[8] = "verifystatusthree";
        time[9] = "verifystatusfour";
        time[10] = "verifystatusfive";
        time[11] = "verifystatussix";
        EntityTools.setEntityDefaultValue(wmConFigureApply, time);
        try {
            wmConFigureApplyRepository.save(wmConFigureApply);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErpException(ErrEumn.EDIT_WMCONFIGUREAPPLY_ERROR);
        }
    }

    @Override
    public PageVO<RequestModeVO> getRequestMode(String compid, Integer page, Integer pageSize) {
        PageVO<RequestModeVO> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize);
        List<RequestModeVO> buyerVOS = partsMapper.getRequestMode(
                compid);
        PageInfo<RequestModeVO> pageInfo = new PageInfo<>(buyerVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public PageVO<AccessCatalogVO> getMnemonicCodeList(String compid, Integer page, Integer pageSize) {
        PageVO<AccessCatalogVO> pageVO = new PageVO<>();
        PageHelper.startPage(page, pageSize);
        List<AccessCatalogVO> buyerVOS = partsMapper.getMnemonicCodeList(
                compid);
        PageInfo<AccessCatalogVO> pageInfo = new PageInfo<>(buyerVOS);
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public PartsVO getRequestNumberDetail(String requestNumber, String compid) {

        return partsMapper.getRequestNumberDetail(requestNumber, compid);
    }

    @Override
    public void editRequestStatus(String compid, String requestNumber, String requestStatus, String verifyIdOne)
            throws ErpException {
        try {
            //获取签收时间
            Date date = new Date();
            SimpleDateFormat sdf = com.hntxrj.txerp.core.util.SimpleDateFormatUtil.getSimpleDataFormat("yyyy-MM-dd HH:mm:ss");
            String auditResultOne = "";
            String verifyTimeOne = "";
            boolean verifyStatusOne = false;

            if (Integer.valueOf(requestStatus) == 8) {
                verifyStatusOne = true;
                verifyTimeOne = sdf.format(date);
                auditResultOne = "不批准";
            } else if (Integer.valueOf(requestStatus) < 8) {
                verifyTimeOne = sdf.format(date);
                verifyStatusOne = true;
                auditResultOne = "批准";
            }
            partsMapper.editRequestStatus(compid, requestNumber, requestStatus, verifyIdOne,
                    verifyStatusOne, verifyTimeOne, auditResultOne);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErpException(ErrEumn.VERIFY_TICKET_ERROR);
        }
    }

    @Override
    public void cancelRequestStatus(String compid, String requestNumber, String requestStatus, String verifyIdOne) {
        requestStatus = "1";
        String verifyTimeOne = null;
        String verifyStatusOne = "0";
        String auditResultOne = "";
        verifyIdOne = "";
        partsMapper.cancelRequestStatus(compid, requestNumber, requestStatus, verifyIdOne,
                verifyStatusOne, verifyTimeOne, auditResultOne);
    }


}
