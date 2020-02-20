package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.RecipientVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MsgMapper {

    List<RecipientVO> getRecipoentList(String compid, Integer typeId);
}
