package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.ContractListVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ContractMapper {
    List<ContractListVO> contractList(String builderName, String engineeringName, String contractId,
                                      Integer saleUid, Integer contractStatus, Integer del, Integer eid);
}
