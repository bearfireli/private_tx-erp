package com.hntxrj.txerp.server.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.mapper.StgIdMangeMapper;
import com.hntxrj.txerp.server.StgIdMangeService;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.StgidManageVO;
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
     * @param page     页码
     * @param pageSize 每页大小
     * @return 砼标号列表
     */
    @Override
    public PageVO<StgidManageVO> getStgidManage(String compid, String stgId, String grade, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<StgidManageVO> stockInDetailsVOS = stgIdMangeMapper.getStgidManage(compid, stgId, grade);
        for (StgidManageVO stgidManageVO : stockInDetailsVOS) {
            // 保留小数点后两位数
            savePoint(stgidManageVO);
        }
        PageInfo<StgidManageVO> pageInfo = new PageInfo<>(stockInDetailsVOS);
        PageVO<StgidManageVO> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }


    /**
     * 砼标号详情
     *
     * @param compid 企业id
     * @param stgId  砼标号
     * @param grade  强度等级
     */
    @Override
    public StgidManageVO getStgIdManageDetail(String compid, String stgId, String grade) {

        StgidManageVO stgidManageVO = stgIdMangeMapper.getStgidManageDetail(compid, stgId, grade);

        savePoint(stgidManageVO);

        return stgidManageVO;
    }


    /**
     * 编辑砼标号
     *
     * @param compid          企业id
     * @param stgId           砼标号
     * @param grade           强度等级
     * @param pumpPrice       泵送价格
     * @param notPumpPrice    自卸价格
     * @param towerCranePrice 塔吊价格
     */
    @Override
    public void updateStgIdManage(String compid, String stgId, String grade, String pumpPrice, String notPumpPrice, String towerCranePrice) {
        stgIdMangeMapper.updateStgIdManage(grade, pumpPrice, notPumpPrice, towerCranePrice, compid, stgId);
    }


    /**
     * 添加砼标号
     *
     * @param compid          企业id
     * @param stgId           砼标号
     * @param grade           强度等级
     * @param pumpPrice       泵送价格
     * @param notPumpPrice    自卸价格
     * @param towerCranePrice 塔吊价格
     */
    @Override
    public void insertStgIdManage(String compid, String stgId, String grade, String pumpPrice, String notPumpPrice,
                                  String towerCranePrice) {
        //用户如果输入的价格包含空格和/r/t，将其替换掉，防止程序报错。
        pumpPrice = pumpPrice.replaceAll("\\s*", "");
        notPumpPrice = pumpPrice.replaceAll("\\s*", "");
        towerCranePrice = pumpPrice.replaceAll("\\s*", "");
        stgIdMangeMapper.insertStgIdManage(compid, stgId, grade, pumpPrice, notPumpPrice, towerCranePrice);
    }


    /**
     * 删除砼标号
     *
     * @param compid 企业id
     * @param stgId  砼标号
     */
    @Override
    public void deleteStgManage(String compid, String stgId) {
        stgIdMangeMapper.deleteStgIdManage(compid, stgId);
    }

    @Override
    public List<StgidManageVO> getStgidList(String compid) {
        return stgIdMangeMapper.getStgidList(compid);
    }


    private StgidManageVO savePoint(StgidManageVO stgidManageVO) {
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

        return stgidManageVO;

    }
}
