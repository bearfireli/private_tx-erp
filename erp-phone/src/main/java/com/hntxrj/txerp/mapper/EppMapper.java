package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.EppInfo;
import com.hntxrj.txerp.vo.EppDropDownVO;
import com.hntxrj.txerp.vo.EppInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EppMapper {


    /**
     * 获取工程名称下拉
     *
     * @param eppName 工程名称模糊查询
     * @param compid  企业id
     * @return 带分页工程下拉列表
     */
    List<EppDropDownVO> getDropDown(String compid, String eppName);

    /**
     * 获取工程名称
     *
     * @param eppCode 工程代号
     * @param compid  企业id
     * @return 工程对象
     */
    EppInfo getEppInfo(String eppCode, String compid);

    /**
     * 获取工地端App工程名称下拉
     *
     * @param contractDetailCodes 子合同集合
     * @param contractUIDList     主合同集合
     * @param eppName             工程名称（模糊查询）
     * @return 带分页工程下拉列表
     */
    List<EppDropDownVO> getBuildDropDown(List<String> contractDetailCodes, List<String> contractUIDList, String eppName);

    // 根据企业id和创建时间获取新添加的工程名称
    EppInfo getEppInfoByCreateTime(String compid, String createTime);

    // 获取企业最大的工程代号
    String getMaxEppCode(String compid);

    // 添加工程名称
    void addEppInfo(String compid, String eppName, String eppCode, String shortName, String address, String linkMan,
                    String phone, String remarks,String nowDate);

    /**
     * 获取工程名称分页数据
     * @param eppCode
     * @param eppName
     * @param recStatus
     * @param compid
     * @return
     */
    List<EppInfoVO> getEppList(String eppCode, String eppName, Integer recStatus, String compid);

    /**
     * 获取工程名称 VO
     *
     * @param eppCode 工程代号
     * @param compid  企业id
     * @return 工程对象
     */
    EppInfoVO getEppInfoVO(String eppCode, String compid);

    /**
     * 新建工程名称
     * @param eppInfoVO
     */
    void insertEppInfo(EppInfoVO eppInfoVO);

    /**
     * 更新工程名称
     * @param eppInfoVO
     */
    void updateEppInfo(EppInfoVO eppInfoVO);

    /**
     * 修改启用状态
     * @param eppCode
     * @param compid
     * @param recStatus
     */
    void changeEppRecStatus(String eppCode, String compid, Integer recStatus);
}
