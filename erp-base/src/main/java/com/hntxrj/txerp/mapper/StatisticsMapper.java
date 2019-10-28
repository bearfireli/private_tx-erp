package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.base.Statistics;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StatisticsMapper {

    void insertStatistics(Statistics statistics);


}
