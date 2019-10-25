package com.hntxrj.server.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.mapper.StgIdMangeMapper;
import com.hntxrj.server.StgIdMangeService;
import com.hntxrj.vo.PageVO;
import com.hntxrj.vo.StgidManageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/*砼标号管理*/
@Service
@Scope("prototype")
public class StgIdMangeServiceImpl implements StgIdMangeService {
    private final StgIdMangeMapper stgIdMangeMapper;

    @Autowired
    public StgIdMangeServiceImpl(StgIdMangeMapper stgIdMangeMapper) {
        this.stgIdMangeMapper = stgIdMangeMapper;
    }

    /**
     * 砼标号管理
     *
     * @param compid   企业id
     * @param stgId    砼标号
     * @param grade    强度等级
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public PageVO<StgidManageVO> getStgidManage(String compid, String stgId, String grade, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<StgidManageVO> stockInDetailsVOS = stgIdMangeMapper.getStgidManage(compid, stgId, grade);
        for (StgidManageVO stgidManageVO : stockInDetailsVOS) {
            // 保留小数点后两位数
            if (stgidManageVO.getPumpPrice() != null && !stgidManageVO.getPumpPrice().equals("")) {
                String getPumpPrice = stgidManageVO.getPumpPrice();
                getPumpPrice = getPumpPrice.substring(0, getPumpPrice.indexOf(".") + 3);
                stgidManageVO.setPumpPrice(getPumpPrice);
            }
            if (stgidManageVO.getNotPumpPrice() != null && !stgidManageVO.getNotPumpPrice().equals("")) {
                String getNotPumpPrice = stgidManageVO.getNotPumpPrice();
                getNotPumpPrice = getNotPumpPrice.substring(0, getNotPumpPrice.indexOf(".") + 3);
                stgidManageVO.setNotPumpPrice(getNotPumpPrice);
            }
            if (stgidManageVO.getTowerCranePrice() != null && !stgidManageVO.getTowerCranePrice().equals("")) {
                String getTowerCranePrice = stgidManageVO.getTowerCranePrice();
                getTowerCranePrice = getTowerCranePrice.substring(0, getTowerCranePrice.indexOf(".") + 3);
                stgidManageVO.setTowerCranePrice(getTowerCranePrice);
            }
            if (stgidManageVO.getPriceDifference() != null && !stgidManageVO.getPriceDifference().equals("")) {
                String getPriceDifference = stgidManageVO.getPriceDifference();
                getPriceDifference = getPriceDifference.substring(0, getPriceDifference.indexOf(".") + 3);
                stgidManageVO.setPriceDifference(getPriceDifference);
            }
        }
        PageInfo<StgidManageVO> pageInfo = new PageInfo<>(stockInDetailsVOS);
        PageVO<StgidManageVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }
}
