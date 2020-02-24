package com.hntxrj.txerp.server;

import com.hntxrj.txerp.entity.PushType;

import java.util.List;
import java.util.Map;

/**
 * 推送类型
 */
public interface PushInfoService {



    /**
     * 保存收信人
     * @return
     */
    boolean saveRecipient(List<String> name,String compId,int typeId  );
}
