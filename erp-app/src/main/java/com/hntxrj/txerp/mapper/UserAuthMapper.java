package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.vo.AuthMenuVO;

import java.util.List;

public interface UserAuthMapper {

    List<AuthMenuVO> getAuthMenu(Integer groupId);

}
