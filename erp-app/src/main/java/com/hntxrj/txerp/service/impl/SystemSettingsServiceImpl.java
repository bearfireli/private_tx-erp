package com.hntxrj.txerp.service.impl;

import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.service.SystemSettingsService;
import com.hntxrj.txerp.service.UserService;
import com.hntxrj.txerp.entity.base.QEnterprise;
import com.hntxrj.txerp.entity.base.QSystemSettings;
import com.hntxrj.txerp.entity.base.SystemSettings;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.repository.SystemSettingsRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;


@Service
public class SystemSettingsServiceImpl implements SystemSettingsService {

    private final SystemSettingsRepository systemsettingsRepository;
    private JPAQueryFactory queryFactory;
    private final UserService userService;

    @Autowired
    public SystemSettingsServiceImpl(EntityManager entityManager,
                                     SystemSettingsRepository systemsettingsRepository,
                                     UserService userService) {
        this.systemsettingsRepository = systemsettingsRepository;
        this.queryFactory = new JPAQueryFactory(entityManager);
        this.userService = userService;
    }

    /*修改设置模块*/
    @Override
    public SystemSettings updateSystemSettings(SystemSettings systemsettings) throws Exception{
        if(StringUtils.isEmpty(systemsettings.getSettingValue())){
            throw new ErpException(ErrEumn.SETTING_VALUE_NOT_NULL);
        }
        if(systemsettings.getEnterprise() == null || systemsettings.getEnterprise() == 0){
            throw new ErpException(ErrEumn.SETTING_ENTERPRISE_NOT_NULL);
        }
        if(systemsettings.getCreateUid() == null || systemsettings.getCreateUid() == 0){
            throw new ErpException(ErrEumn.SETTING_CREATEUID_NOT_NULL);
        }
        return systemsettingsRepository.save(systemsettings);
    }

    /**
     * 设置代号settingcode
     * 企业identerprise
     * 查询设置系统模块
     *
     * @param settingCode
     * @param enterprise
     * @param page
     * @param pageSize
     */
    @Override
    public PageVO<SystemSettings> selectSystemSettings(String token, String settingCode,
                                                           Integer enterprise, long page, long pageSize) {
        QSystemSettings qSystemSettings = QSystemSettings.systemSettings;
        QEnterprise qEnterprise = QEnterprise.enterprise;
        BooleanBuilder builder = new BooleanBuilder();
        if (!(settingCode == null && StringUtils.isEmpty(settingCode))) {
            builder.and(qSystemSettings.settingCode.like("%" + settingCode + "%"));
        }
        try {
            if (!(enterprise == 0 && userService.userIsSupperAdmin(token))) {
              //查全表
            } else {
                builder.and(qSystemSettings.enterprise.like("%" + enterprise + "%"));

            }
        } catch (ErpException e) {
            e.printStackTrace();
        }
        JPAQuery<SystemSettings> systemSettingslist = queryFactory.select(
                Projections.bean(
                        SystemSettings.class,
                        qSystemSettings.settingCode,
                        qSystemSettings.enterprise,
                        qSystemSettings.settingValue,
                        qSystemSettings.createTime,
                        qSystemSettings.updateUid,
                        qSystemSettings.createUid,
                        qSystemSettings.updateTime
                )
        ).from(qSystemSettings)
                .leftJoin(qEnterprise).on(qEnterprise.eid.eq(qSystemSettings.enterprise))
                .where(builder)
                .orderBy(qSystemSettings.settingValue.asc())
                .offset((page - 1) * pageSize)
                .limit(pageSize);
        PageVO<SystemSettings> pageVO = new PageVO<>();
        pageVO.init(systemSettingslist, pageSize);
        return pageVO;
    }

    /*添加设置模块*/
    @Override
    public SystemSettings addSystemSettings(SystemSettings systemsettings) throws Exception{
        if(StringUtils.isEmpty(systemsettings.getSettingValue())){
            throw new ErpException(ErrEumn.SETTING_VALUE_NOT_NULL);
        }
        if(systemsettings.getEnterprise() == null || systemsettings.getEnterprise() == 0){
            throw new ErpException(ErrEumn.SETTING_ENTERPRISE_NOT_NULL);
        }
        if(systemsettings.getCreateUid() == null || systemsettings.getCreateUid() == 0){
            throw new ErpException(ErrEumn.SETTING_CREATEUID_NOT_NULL);
        }
        return systemsettingsRepository.save(systemsettings);
    }

    /*删除设置模块信息
     * 设置代号settingcode
     * 企业Id enterprise
     * */
    @Override
    public  SystemSettings deleteSystemSetting(String settingCode) throws ErpException {
        SystemSettings systemSettings = null;
        if (settingCode != null && !settingCode.equals("")) {
            systemSettings = systemsettingsRepository.findById(settingCode).orElseThrow(() ->
                    new ErpException(ErrEumn.SETTING_ENTERPRISE_NOT_NULL));
           systemsettingsRepository.delete(systemSettings);
        }
       return systemSettings;
    }
}
