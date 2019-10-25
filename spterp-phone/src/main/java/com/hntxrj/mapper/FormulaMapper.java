package com.hntxrj.mapper;

import com.hntxrj.entity.Data;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface FormulaMapper {

    List<Map<String, Object>> getFormulaList(String compid, String eppCode, String builderCode,
                                             String placing, String taskId, Integer taskStatus,
                                             String startTime, String endTime);


    Map<String, Object> getFormulaByTaskId(String compid, String taskId);

    List<Map<String, Object>> getStirIdFormulaStatus(String compid, String taskId);

    List<Map<String, Object>> getStirIdFormulaStatusByTaskIds(String compid, List<String> taskIds);

    Map<String, Object> getFormulaInfo(String compid, String taskId, String stirId);


}
