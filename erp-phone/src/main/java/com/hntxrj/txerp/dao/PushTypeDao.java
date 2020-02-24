package com.hntxrj.txerp.dao;



import com.hntxrj.txerp.entity.PushType;

import java.util.List;

/**
 * 功能:  推送类型Dao接口
 *
 * @Auther 陈世强
 */
public interface PushTypeDao {

    /**
     *   获取推送类型
     * @return
     */

    List<PushType> getPushType();
}
