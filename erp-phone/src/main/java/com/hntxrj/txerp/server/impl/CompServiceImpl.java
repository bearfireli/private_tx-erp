package com.hntxrj.txerp.server.impl;

import com.hntxrj.txerp.dao.UserCompDao;
import com.hntxrj.txerp.entity.UserComp;
import com.hntxrj.txerp.server.CompService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

/**
 * @author lhr
 * @create 2018/2/3
 */
@Service
@Scope("prototype")
public class CompServiceImpl implements CompService {


    private final UserCompDao compDao;

    @Autowired
    public CompServiceImpl(UserCompDao compDao) {
        this.compDao = compDao;
    }

    @Override
    public Page<UserComp> getUserCompList(String compName, Integer page) {

        PageRequest pageRequest =
                PageRequest.of(page - 1, 10
                        , Sort.by(Sort.Direction.DESC, "compid"));
        Page<UserComp> userCompPage;
        if (compName != null && !"".equals(compName)) {
            userCompPage = compDao.findByLongnameLike("%" + compName + "%", pageRequest);
        } else {
            userCompPage = compDao.findAll(pageRequest);
        }

        return userCompPage;
    }

    @Override
    public UserComp getByCompId(String compid) throws SQLException {

        return compDao.findById(compid).orElseThrow(SQLException::new);
    }

    @Override
    public UserComp addComp(UserComp userComp) {
        return compDao.save(userComp);
    }

    @Override
    public void updateComp(UserComp userComp) {
        compDao.save(userComp);
    }
}
