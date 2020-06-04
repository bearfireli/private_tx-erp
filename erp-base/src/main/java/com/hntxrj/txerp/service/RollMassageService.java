package com.hntxrj.txerp.service;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.RollMessageVO;

import java.util.List;

public interface RollMassageService {

    //添加滚动消息信息
    void addRollMessage(String token, String content, String compid, Byte type, String beginTime, String endTime) throws ErpException;

    //删除滚动消息
    void removeRollMessage(Integer id);

    //修改滚动消息
    void updateRollMessage(Integer id, String compid, String content, String beginTime, String endTime, Byte type);

    //获取滚动消息
    List<RollMessageVO> getRollMessage(String compid);
}
