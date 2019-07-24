package com.hntxrj.txerp.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.mapper.EnterpriseMapper;
import com.hntxrj.txerp.entity.base.Enterprise;
import com.hntxrj.txerp.entity.base.QEnterprise;
import com.hntxrj.txerp.entity.base.User;
import com.hntxrj.txerp.repository.EnterpriseRepository;
import com.hntxrj.txerp.service.EnterpriseService;
import com.hntxrj.txerp.service.UserService;
import com.hntxrj.txerp.vo.EnterpriseDropDownVO;
import com.hntxrj.txerp.vo.PageVO;
import com.hntxrj.txerp.vo.UserVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;


@Service
@Slf4j
public class EnterpriseServiceImpl extends BaseServiceImpl implements EnterpriseService {

    private final EnterpriseRepository enterpriseRepository;
    private JPAQueryFactory queryFactory;
    private final UserService userService;
    @Value("${app.pay.imgFilePath}")
    private String imgFilePath;

    private final EnterpriseMapper enterpriseMapper;


    @Autowired
    public EnterpriseServiceImpl(EnterpriseRepository enterpriseRepository
            , EntityManager entityManager, UserService userService, EnterpriseMapper enterpriseMapper) {
        super(entityManager);
        this.userService = userService;
        this.enterpriseMapper = enterpriseMapper;
        queryFactory = getQueryFactory();
        this.enterpriseRepository = enterpriseRepository;
    }


    @Override
    public List<EnterpriseDropDownVO> getDropDown(String token, String keyword) throws ErpException {

        QEnterprise qEnterprise = QEnterprise.enterprise;

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        UserVO userVO = userService.tokenCanUse(token);
        log.info("【获取企业验证】isSupperAdmin={}", userService.userIsSupperAdmin(userVO.getUid()));
        if (!userService.userIsSupperAdmin(userVO.getUid())) {
            booleanBuilder.and(qEnterprise.eid.in(userService.getEnterpriseIdsByToken(token)));
        }


        // default select not delete enterprise
        List<Enterprise> enterprises = queryFactory.selectFrom(qEnterprise)
                .where(qEnterprise.epName.like("%" + keyword + "%"))
                .where(qEnterprise.delete.eq(0))
                .where(booleanBuilder)
                .orderBy(qEnterprise.epName.asc())
                .fetch();

        List<EnterpriseDropDownVO> enterpriseDropDownVOS = new ArrayList<>();

        enterprises.forEach(enterprise -> makeDropDownVO(enterpriseDropDownVOS, enterprise));
        log.info("【企业下拉】enterprise={}", enterpriseDropDownVOS);
        return enterpriseDropDownVOS;
    }

    @Override
    public PageVO<Enterprise> selectEnterprise(String token, String epName,
                                               Integer epType,
                                               Integer epStatus,
                                               Integer delete,
                                               long page,
                                               long pageSize) throws ErpException {
        QEnterprise qEnterprise = QEnterprise.enterprise;

        User user = userService.tokenGetUser(token);
        BooleanBuilder builder = new BooleanBuilder();
        if (epName != null && !"".equals(epName)) {
            builder.and(qEnterprise.epName.like("%" + epName + "%"));
        }

        if (epType != null) {
            builder.and(qEnterprise.epType.eq(epType));
        }

        if (epStatus != null) {
            builder.and(qEnterprise.epStatus.eq(epStatus));
        }

        if (delete != null) {
            builder.and(qEnterprise.delete.eq(delete));
        }


        if (!userService.userIsSupperAdmin(token)) {
            builder.and(qEnterprise.eid.in(userService.getEnterpriseIdsByToken(token)));
        }


        JPAQuery<Enterprise> enterpriseJPAQuery = queryFactory.selectFrom(qEnterprise)
                .where(builder)
                .offset((page - 1) * pageSize)
                .limit(pageSize);

        List<Enterprise> enterprises = enterpriseJPAQuery.fetch();

        PageVO<Enterprise> pageVO = new PageVO<>();

        pageVO.init(enterpriseJPAQuery.fetchCount(), page, enterprises);

        return pageVO;
    }


    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Enterprise addEnterprise(Enterprise enterprise) throws ErpException {
        // 查询企业简称或企业全称是否在数据库中为删除内容中存在，如果存在就抛出异常
        if (enterpriseNameExist(enterprise.getEpName(), enterprise.getEpShortName())) {
            throw new ErpException(ErrEumn.ENTERPRISE_NAME_EXIST);
        }

        Enterprise savedEnterprise = enterpriseRepository.save(enterprise);
        // 添加spterp中的企业
        OkHttpClient client = new OkHttpClient();
        String url = "https://dev.erp.hntxrj.com/comp/addComp";
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("compid", String.valueOf(savedEnterprise.getEid()))
                .addFormDataPart("compName", savedEnterprise.getEpName())
                .addFormDataPart("compShortName", savedEnterprise.getEpShortName())
                .addFormDataPart("securityKey", "adsfbnhjkwegbrw")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            if (response.body() != null) {
                log.info("【老版本企业添加】val={}", response.body().string());
            }
        } catch (IOException e) {
            log.error("【老版本企业添加失败】err={}", e.getMessage());
            e.printStackTrace();
        }


        return savedEnterprise;
    }

    /**
     * @param enterprise
     * @param eidCode    新ｉｄ
     * @return
     * @throws ErpException
     */
    @Override
    public Enterprise updateEnterprise(Enterprise enterprise, Integer eidCode) throws ErpException {

        Enterprise enterpriseOld = new Enterprise();
        Integer eid = enterprise.getEid();
        //判断新ｉｄ与老id 是否一致 如果不一致则说明修改了ｉｄ．
        if (!enterprise.getEid().equals(eidCode)) {
//            enterprise.setEid(eidcode);
            // 判断新修改的ｉｄ是否已经存在，主要用与ｉｄ不能重复
            Optional<Enterprise> optionalEnterprise =
                    enterpriseRepository.findById(eidCode);
            // 如果存在则不能修改，在前台提示．
            if (optionalEnterprise.isPresent()) {
                enterpriseOld = optionalEnterprise.get();
                throw new ErpException(ErrEumn.ENTERPRISE_id_EXIST);
            } else {
                //判断新ｉｄ是否为空
                if (eidCode != null && !"".equals(eidCode)) {
                    enterprise.setEid(eidCode);
                }
                //进行修改操作，并返回
                int request = enterpriseMapper.updateId(enterprise, eid);
            }
        } else {
            if (enterprise.getEid() != null && !"".equals(enterprise.getEid())) {
                enterprise.setEid(enterprise.getEid());
            }
            //禁止编辑企业全称和简称
            if (enterprise.getEpLink() != null
                    && !"".equals(enterprise.getEpLink())) {
                enterprise.setEpLink(enterprise.getEpLink());
            }
            if (enterprise.getEpStatus() != null) {
                enterprise.setEpStatus(enterprise.getEpStatus());
            }
            if (enterprise.getEpRemark() != null
                    && !"".equals(enterprise.getEpRemark())) {
                enterprise.setEpRemark(enterprise.getEpRemark());
            }


            if (enterprise.getDelete() != null) {
                enterprise.setDelete(enterprise.getDelete());
            }

        }
        return enterpriseRepository.save(enterprise);
    }

    @Override
    public void deleteEnterprise(Integer eid) throws ErpException {
        Enterprise enterpriseOld;
        // 企业是否存在
        Optional<Enterprise> optionalEnterprise = enterpriseRepository.findById(eid);
        if (optionalEnterprise.isPresent()) {
            enterpriseOld = optionalEnterprise.get();
        } else {
            throw new ErpException(ErrEumn.ENTERPRISE_NOEXIST);
        }
        enterpriseOld.setDelete(enterpriseOld.getDelete() == 1 ? 0 : 1);
        enterpriseRepository.save(enterpriseOld);
    }

    @Override
    public Enterprise getEnterprise(Integer eid) throws ErpException {
        return enterpriseRepository.findById(eid).orElseThrow(() ->
                new ErpException(ErrEumn.ENTERPRISE_NOEXIST));
    }

    @Override
    public List<Enterprise> getEnterprise(Integer[] eids) {
        log.info("【getEnterprise】result={}", enterpriseRepository.findAllById(Arrays.asList(eids)));
        return enterpriseRepository.findAllById(Arrays.asList(eids));
    }

    //支付
    @Override
    public Enterprise setCollectionCode(Integer eid, MultipartFile imageFile, Integer type) throws ErpException {
        Enterprise enterprise = enterpriseRepository.findById(eid)
                .orElseThrow(() -> new ErpException(ErrEumn.ENTERPRISE_NOEXIST));
        String[] originalFilename = imageFile.getOriginalFilename().split("\\.");
        String fileName = UUID.randomUUID().toString()
                + (originalFilename.length != 0 ? "." + originalFilename[originalFilename.length - 1] : "");
        String filePath = imgFilePath + fileName;
        File imgfile = new File(filePath);
        File file = imgfile.getParentFile();
        if (!file.exists()) {
            imgfile.getParentFile().mkdirs();
        }
        try {
            imageFile.transferTo(imgfile);
        } catch (Exception e) {
            throw new ErpException(ErrEumn.TO_TRANSFARTO_IMGFILE_FAIL);
        }
        JSONArray array = JSONArray.parseArray(enterprise.getCollectionCode());
        if (array == null || array.size() > 0) {
            array = new JSONArray();
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("fileName", fileName);
        jsonObject.put("filePath", filePath);
        jsonObject.put("imgfile", imgfile);
        jsonObject.put("type", type);
        array.add(jsonObject);
        enterprise.setCollectionCode(String.valueOf(array));
        enterpriseRepository.save(enterprise);
        return enterprise;
    }

    //读取地址
    @Override
    public void getCollectionCode(Integer eid, Integer type, HttpServletResponse response) throws ErpException {
        Enterprise enterprise = enterpriseRepository.findById(eid)
                .orElseThrow(() -> new ErpException(ErrEumn.ENTERPRISE_NOEXIST));
        String fileName = "";
        if (enterprise.getCollectionCode() != null && !enterprise.getCollectionCode().equals("")) {
            fileName = enterprise.getCollectionCode();
        } else {
            fileName = "defualt.jpg";
        }
        File file = new File(imgFilePath + fileName);
        if (!file.exists()) {
            throw new ErpException(ErrEumn.NOT_FOUNDNOT_FILE);
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("img/*");
        try {
            OutputStream os = response.getOutputStream();
            byte[] bis = new byte[1024];
            InputStream inputStream = new FileInputStream(file);
            while (-1 != inputStream.read(bis)) {
                os.write(bis);
            }
        } catch (Exception e) {
            throw new ErpException(ErrEumn.IMGAGDFILE_FAIL);
        }
    }

    @Override
    public void getPaymentImage(String  enterprise,Integer type, HttpServletResponse response) throws ErpException {
        int eid =Integer.parseInt(enterprise);
        Enterprise enterpriseid = enterpriseRepository.findById(eid)
                .orElseThrow(() -> new ErpException(ErrEumn.ENTERPRISE_NOEXIST));
        String fileName = "";
        if (enterpriseid.getCollectionCode() != null && !enterpriseid.getCollectionCode().equals("")) {
            fileName = enterpriseid.getCollectionCode();
        } else if (type ==1){
            fileName = "weixin.png";
        }else if (type ==2){
            fileName = "zhifubao.jpg";
        }
        File file = new File(imgFilePath + fileName);
        if (!file.exists()) {
            throw new ErpException(ErrEumn.NOT_FOUNDNOT_FILE);
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("img/*");
        try {
            OutputStream os = response.getOutputStream();
            byte[] bis = new byte[1024];
            InputStream inputStream = new FileInputStream(file);
            while (-1 != inputStream.read(bis)) {
                os.write(bis);
            }
        } catch (Exception e) {
            throw new ErpException(ErrEumn.IMGAGDFILE_FAIL);
        }
    }

    @Override
    public String uploadFeedbackImg(MultipartFile multipartFile) throws ErpException {
        String fileName = UUID.randomUUID().toString();
        File dir = new File(imgFilePath);
        dir.mkdirs();
        File file = new File(imgFilePath + fileName);
        try {
            file.createNewFile();
            IOUtils.copy(multipartFile.getInputStream(), new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErpException(ErrEumn.UPLOAD_FILE_ERROR);
        }
        return fileName;
    }

    @Override
    public void getFeedbackImg(String fileName, HttpServletResponse response) throws ErpException {
        File file = new File(imgFilePath + fileName);
        if (!file.exists()) {
            throw new ErpException(ErrEumn.NOT_FOUNDNOT_FILE);
        }
        try {
            IOUtils.copy(new FileInputStream(file), response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ErpException(ErrEumn.DOWNLOAD_FILE_ERROR);
        }

    }

    /**
     * use Enterprise obj make dropdown obj
     *
     * @param list       store the container of the enterprise drop down
     * @param enterprise one enterprise obj
     */
    private void makeDropDownVO(
            List<EnterpriseDropDownVO> list,
            Enterprise enterprise) {
        EnterpriseDropDownVO enterpriseDropDownVO =
                new EnterpriseDropDownVO();
        BeanUtils.copyProperties(enterprise, enterpriseDropDownVO);

        list.add(enterpriseDropDownVO);
    }

    /**
     * 检测企业全称和简称是否存在。
     *
     * @param name      全称
     * @param shortName 简称
     * @return 存在返回true，不存在返回false
     */
    private boolean enterpriseNameExist(String name, String shortName) {
        QEnterprise qEnterprise = QEnterprise.enterprise;
        List<Enterprise> enterprises = queryFactory.selectFrom(qEnterprise)
                .where(
                        qEnterprise.epName.eq(name)
                                .or(
                                        qEnterprise.epShortName.eq(shortName)
                                )
                )
                .where(qEnterprise.delete.eq(0))
                .orderBy(qEnterprise.epName.asc())
                .fetch();
        return enterprises.size() > 0;
    }
}
