package com.hntxrj.txerp.dao;

import com.hntxrj.txerp.entity.ContractDetail;
import com.hntxrj.txerp.entity.ContractDetailKey;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContractDetailDao {
    int deleteByPrimaryKey(ContractDetailKey key);

    int insert(ContractDetail record);

    int insertSelective(ContractDetail record);

    ContractDetail selectByPrimaryKey(ContractDetailKey key);

    int updateByPrimaryKeySelective(ContractDetail record);

    int updateByPrimaryKey(ContractDetail record);
}