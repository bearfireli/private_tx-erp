package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.AuthGroupVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description 权限组Mapper
 * @Author 陈世强
 * @e-mail chenshiqiang@wisfaith.net
 * @Date 16:44 2019-11-11
 * @Param
 * @return
 **/
@Mapper
public interface AuthGroupMapper {
    /**
     * 获取初始化权限组
     *
     * @return
     */
    List<AuthGroupVO> getInitAuthGroup();
}
