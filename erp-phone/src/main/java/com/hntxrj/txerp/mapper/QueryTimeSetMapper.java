package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.QueryTimeSetVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QueryTimeSetMapper {
    /*查询默认时间*/
    List<QueryTimeSetVO> getQueryTimeSetList(String compid);
    /*删除默认时间*/
    void deleteQueryTimeSet(String compid, String queryName);
    /*添加默认时间*/
    void insetQueryTimeSet(String compid, String queryName, int queryTime, String queryStartTime, String queryStopTime,
               int recStatus, int upDownMark, int upDown);
}
