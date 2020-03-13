package com.hntxrj.txerp.repository;

import com.hntxrj.txerp.entity.base.UserLogin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserLoginRepository
        extends JpaRepository<UserLogin, String> {

    //根据用户id查询用户登录对象集合
    List<UserLogin> findAllByUserId(Integer userId);

    /**
     * 根据用户id和loginUa查询用户登录对象集合
     *
     * @param userId 用户id
     * @param loginUa 用户登录的项目来源（用户是从哪个项目登录的）
     * */
    List<UserLogin> findAllByUserIdAndLoginUa(Integer userId,String loginUa);

    //根据token获取用户
    UserLogin findByUserToken(String userToken);

    //根据用户id删除用户
    @Transactional
    void deleteAllByUserId(Integer userId);

    /**
     * 根据用户id和loginUa删除用户
     *
     * @param userId 用户id
     * @param loginUa 用户登录的项目来源（用户是从哪个项目登录的）
     * */
    @Transactional
    void deleteAllByUserIdAndLoginUa(Integer userId,String loginUa);


}
