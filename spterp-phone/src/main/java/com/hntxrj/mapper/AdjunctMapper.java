package com.hntxrj.mapper;

import com.hntxrj.entity.Adjunct;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AdjunctMapper extends Mapper<Adjunct> {

    List<Adjunct> getAdjunctList(String compid, int type, String adjunctKey);


    int insert(Adjunct adjunct);


    Adjunct getAdjunctByFileName(String fileUid);

    void deleteById(Integer adjunctId);
}
