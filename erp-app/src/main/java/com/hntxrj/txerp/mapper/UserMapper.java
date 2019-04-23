package com.hntxrj.txerp.mapper;

import com.hntxrj.txerp.entity.base.User;
import com.hntxrj.txerp.entity.base.UserAccount;
import com.hntxrj.txerp.vo.AuthGroupVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> details(Integer userId);

    List<AuthGroupVO> findbyId(Integer userId);

    UserAccount type(Integer userId);
}
