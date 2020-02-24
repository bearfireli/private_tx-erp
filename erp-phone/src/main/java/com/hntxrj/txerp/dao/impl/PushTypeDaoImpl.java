package com.hntxrj.txerp.dao.impl;

import com.hntxrj.txerp.dao.PushTypeDao;
import com.hntxrj.txerp.entity.PushType;
import com.hntxrj.txerp.util.jdbc.sql.SqlBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 功能:   推送类型Dao实现类
 *
 * @Auther 陈世强
 */
@Repository
@Scope("prototype")
public class PushTypeDaoImpl implements PushTypeDao {

    private final SqlBuilder sqlBuilder;

    @Autowired
    public PushTypeDaoImpl(SqlBuilder sqlBuilder) {
        this.sqlBuilder = sqlBuilder;
    }


    @Value("${app.spterp.erptype}")
    public Integer erpType;


    /**
     * 获取推送类型
     *
     * @return
     */

    @Override
    public List<PushType> getPushType() {
        SqlBuilder sql = sqlBuilder.table("push_type", PushType.class)
                .where("recStatus", "1")
                .select();

        return (List<PushType>) sql.getResult();
    }
}
