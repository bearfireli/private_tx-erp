package com.hntxrj.txerp.service;


import com.hntxrj.txerp.entity.base.Enterprise;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.vo.EnterpriseDropDownVO;
import com.hntxrj.txerp.vo.EnterpriseInformationVO;
import com.hntxrj.txerp.vo.PageVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface EnterpriseService {


    List<EnterpriseDropDownVO> getDropDown(String token, String keyword) throws ErpException;

    /**
     * 查询企业
     *
     * @param token    登录用户令牌
     * @param epName   企业名称
     * @param epType   企业类型
     * @param epStatus 企业状态
     * @param page     页码
     * @param pageSize 每页数量
     * @return pagevo对象
     */
    PageVO<Enterprise> selectEnterprise(String token, String epName,
                                        Integer epType,
                                        Integer epStatus,
                                        Integer delete,
                                        long page,
                                        long pageSize) throws ErpException;


    Enterprise addEnterprise(String token, Enterprise enterprise) throws ErpException;

    Enterprise updateEnterprise(Enterprise enterprise, Integer eidCode) throws ErpException;

    void deleteEnterprise(Integer eid) throws ErpException;

    Enterprise getEnterprise(Integer eid) throws ErpException;

    List<Enterprise> getEnterprise(Integer[] eids);

    /**
     * 设置收款码
     *
     * @param eid       企业id
     * @param imageFile 二维码图片文件
     * @param type      收款码类型，1微信，2支付宝
     */
    Enterprise setCollectionCode(Integer eid, MultipartFile imageFile, Integer type) throws ErpException;

    /*读取地址
     * @param eid       企业id
     * @param type      收款码类型，1微信，2支付宝
     * */
    void getCollectionCode(Integer eid, Integer type, HttpServletResponse response) throws ErpException;


    String uploadFeedbackImg(MultipartFile multipartFile) throws ErpException;

    void getFeedbackImg(Integer eid, Integer type, HttpServletResponse response) throws ErpException;

    Enterprise saveCollectionCode(Integer eid, String imageFile, Integer type) throws ErpException;

    void getimage(String fileName, HttpServletResponse response) throws ErpException;

    //上传企业头像
    Map<String,String> uploadEnterpriseLogo(MultipartFile image) throws ErpException;

    //获取企业头像
    void getEnterpriseLogo(String imgUrl, HttpServletResponse response) throws ErpException;

    //手机erp获取企业信息
    EnterpriseInformationVO getEnterpriseInformation(Integer eid);

    //手机erp修改企业信息
    void updateEnterpriseInformation(Integer eid, String epName, String epShortName, String logoUrl);
}
