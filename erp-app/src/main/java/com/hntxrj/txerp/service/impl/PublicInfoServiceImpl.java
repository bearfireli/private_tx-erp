package com.hntxrj.txerp.service.impl;

import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.service.PublicInfoService;
import com.hntxrj.txerp.entity.base.PublicInfo;
import com.hntxrj.txerp.entity.base.QPublicInfo;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.repository.PublicInfoRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * (PublicInfo)表服务实现类
 *
 * @author lhr
 * @since 2018-08-14 13:19:08
 */

@Service
public class PublicInfoServiceImpl extends BaseServiceImpl implements PublicInfoService {

    private final PublicInfoRepository publicInfoRepository;
    private JPAQueryFactory queryFactory;


    @Autowired
    public PublicInfoServiceImpl(
            PublicInfoRepository publicInfoRepository, EntityManager entityManager) {
        super(entityManager);

        this.publicInfoRepository = publicInfoRepository;
        queryFactory = getQueryFactory();
    }


    @Override
    public PublicInfo addPublicInfo(PublicInfo publicInfo) {
        return publicInfoRepository.save(publicInfo);
    }

    @Override
    public PublicInfo updatePublicInfo(PublicInfo publicInfo) {

        return publicInfoRepository.save(publicInfo);
    }

    @Override
    public PublicInfo deletePublicInfo(Integer id) throws ErpException {

        Optional<PublicInfo> optionalPublicInfo = publicInfoRepository.findById(id);
        if (optionalPublicInfo.isPresent()) {
            PublicInfo publicInfo = optionalPublicInfo.get();
            publicInfo.setDelete(1);
            return publicInfoRepository.save(publicInfo);
        } else {
            throw new ErpException(ErrEumn.PUBLIC_INFO_NOTEXIST);
        }
    }

    @Override
    public List<PublicInfo> getPublicInfo(
            @NotNull Integer fid, String name, @NotNull Integer delete,
            @NotNull Integer status) {
        QPublicInfo qPublicInfo = QPublicInfo.publicInfo;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qPublicInfo.fid.eq(fid)
                .and(qPublicInfo.delete.eq(delete)));
        if (status != 99) {
            // 代号99表示查询全部
            builder.and(qPublicInfo.status.eq(status));
        }
        if (name != null) {
            builder.and(qPublicInfo.name.like("%" + name + "%"));
        }

        return queryFactory.selectFrom(qPublicInfo)
                .where(builder)
                .orderBy(qPublicInfo.value.asc())
                .fetch();
    }
}