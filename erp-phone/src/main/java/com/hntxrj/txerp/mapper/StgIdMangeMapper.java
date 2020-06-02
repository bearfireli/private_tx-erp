package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.StgidManageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/*砼标号管理*/
@Mapper
public interface StgIdMangeMapper {
    /**
     * 砼标号管理
     *
     * @param compid    企业id
     * @param stgId  砼标号
     * @param grade  强度等级
     * @return
     */
    List<StgidManageVO> getStgidManage(String compid, String stgId, String grade);

    /**
     * 砼标号详情
     *
     * @param compid    企业id
     * @param stgId  砼标号
     * @param grade  强度等级
     * @return
     */
    StgidManageVO getStgidManageDetail(String compid, String stgId, String grade);

    /**
     * 编辑砼标号
     */
    void updateStgIdManage(String grade, String pumpPrice, String notPumpPrice, String towerCranePrice, String compid, String stgId);

    /**
     *添加砼标号
     */
    void insertStgIdManage(String compid, String stgId, String grade, String pumpPrice, String notPumpPrice, String towerCranePrice);


    /**
     *删除砼标号
     */
    void deleteStgIdManage(String compid, String stgId);

    /**
     * 获取砼价格列表
     * @param compid 企业id
     * @return 砼价格列表
     */
    List<StgidManageVO>  getStgidList(String compid);

    Map<String, String> getByStgId(String compid, String stgId);
}
