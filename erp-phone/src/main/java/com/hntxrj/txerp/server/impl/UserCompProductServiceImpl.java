package com.hntxrj.txerp.server.impl;

import com.hntxrj.txerp.dao.UserCompProductDao;
import com.hntxrj.txerp.entity.UserComp;
import com.hntxrj.txerp.entity.UserCompProduct;
import com.hntxrj.txerp.server.UserCompProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLException;


/**
 *
 * @author lhr
 * @create 2018/2/26
 */
@Service
public class UserCompProductServiceImpl implements UserCompProductService {
    private final UserCompProductDao userCompProductDao;

    @Autowired
    public UserCompProductServiceImpl(UserCompProductDao userCompProductDao) {
        this.userCompProductDao = userCompProductDao;
    }


    @Override
    public Page<UserCompProduct> findAll(Pageable pageable) {
        return userCompProductDao.findAll(pageable);
    }

    @Override
    public Page<UserCompProduct> findByCompId(String compid, Pageable pageable) {
        UserComp userComp = new UserComp();
        userComp.setCompid(compid);
        return userCompProductDao.findByComp(userComp, pageable);
    }

    @Override
    public UserCompProduct findById(Integer id) throws SQLException {
        return userCompProductDao.findById(id).orElseThrow(SQLException::new);
    }

    @Override
    public UserCompProduct addProduct(UserCompProduct userCompProduct) {
        return userCompProductDao.save(userCompProduct);
    }

    @Override
    public UserCompProduct updateProduct(UserCompProduct userCompProduct) {
        return userCompProductDao.save(userCompProduct);
    }

    @Override
    public UserCompProduct findByProductIdAndCompId(Integer productId, String compid) {
        UserComp comp = new UserComp();
        comp.setCompid(compid);
        return userCompProductDao.findByProductIdAndComp(productId, comp);
    }
}
