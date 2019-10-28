package com.hntxrj.txerp.service;
import com.hntxrj.txerp.entity.base.SystemSettings;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.PageVO;

public interface SystemSettingsService {

    /**
     * 设置代号settingcode
     * 企业identerprise
     * 查询设置系统模块
     */
    PageVO<SystemSettings> selectSystemSettings(String token, String settingCode,
                                                Integer enterprise, long page, long pageSize);
    /*添加设置模块*/
    SystemSettings addSystemSettings(SystemSettings systemsettings) throws Exception;
    /*删除设置模块信息
    * 设置代号settingcode
    * 企业Id enterprise
    * */
    SystemSettings deleteSystemSetting(String settingCode) throws ErpException;
    /*修改设置模块*/
    SystemSettings updateSystemSettings(SystemSettings systemsettings) throws Exception;

}
