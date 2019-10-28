package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.base.Feedback;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedbackMapper {

    List<Feedback> getFeedbackByPid(Integer pid);

}
