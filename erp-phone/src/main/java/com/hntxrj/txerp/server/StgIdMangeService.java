package com.hntxrj.txerp.server;

import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.StgidManageVO;

import java.util.List;

public interface StgIdMangeService {
    /**
     * 砼标号管理
     *
     * @param compid    企业id
     * @param stgId  砼标号
     * @param grade  强度等级
     * @param page    当前页
     * @param pageSize  每页显示数
     * @return  砼标号管理
     */
    PageVO<StgidManageVO> getStgidManage(String compid, String stgId, String grade, Integer page, Integer pageSize);


    /**
     * 砼标号详情
     *
     * @param compid    企业id
     * @param stgId  砼标号
     * @param grade  强度等级
     * @return  砼标号管理
     */
    StgidManageVO getStgIdManageDetail(String compid, String stgId, String grade);


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
    void updateStgIdManage(String compid, String stgId, String grade, String pumpPrice, String notPumpPrice, String towerCranePrice);


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
    void insertStgIdManage(String compid, String stgId, String grade, String pumpPrice, String notPumpPrice, String towerCranePrice);


    /**
     * 删除砼标号
     *
     * @param compid          企业id
     * @param stgId           砼标号
     */
    void deleteStgManage(String compid, String stgId);

    List<StgidManageVO> getStgidList(String compid);
}
