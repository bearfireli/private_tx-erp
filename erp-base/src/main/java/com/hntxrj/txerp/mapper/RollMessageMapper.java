package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.RollMessageVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RollMessageMapper {
    //添加滚动信息
    void addRollMessage(Integer uid, String content, String compid, Byte type, String beginTime,
                        String endTime, String currentTime);

    //删除滚动信息
    void removeRollMessage(Integer id);

    //修改滚动信息
    void updateRollMessage(Integer id, String compid, String content, String beginTime, String endTime,
                           Byte type, String currentTime);

    //查询滚动信息
    List<RollMessageVO> getRollMessage(String compid, String currentTime);
}
