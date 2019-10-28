package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.StgidManageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
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
}
