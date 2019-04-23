package com.hntxrj.txerp.service.impl;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.entity.base.Journalism;
import com.hntxrj.txerp.entity.base.QJournalism;
import com.hntxrj.txerp.entity.base.User;
import com.hntxrj.txerp.repository.JournalismRepository;
import com.hntxrj.txerp.repository.UserRepository;
import com.hntxrj.txerp.service.JournalismService;
import com.hntxrj.txerp.service.UserService;
import com.hntxrj.txerp.vo.JournalismVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import com.hntxrj.txerp.vo.PageVO;
import com.querydsl.jpa.impl.JPAQuery;

import java.io.*;
import java.util.*;
import java.util.List;

@Service
@Slf4j
public class JournalismServiceImpl extends BaseServiceImpl implements JournalismService {

    private final JournalismRepository journalismRepository;
    private JPAQueryFactory queryFactory;
    private final UserService userService;
    private final UserRepository userRepository;

    public JournalismServiceImpl(JournalismRepository journalismRepository, EntityManager entityManager, UserService userService, UserRepository userRepository) {
        super(entityManager);
        this.journalismRepository = journalismRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        queryFactory = getQueryFactory();
    }

    @Value("${app.user.headerPath}")
    private String headerUploadPath;

    /*添加新闻*/
    @Override
    @Transactional(rollbackFor = Throwable.class)
    public Journalism addJournalism(Journalism journalism, String token) throws ErpException {
        if (token == null || "".equals(token)) {
            throw new ErpException(ErrEumn.TOKEN_IS_NULL);
        }

        User user = userService.tokenGetUser(token);

        if (journalism == null) {
            throw new ErpException(ErrEumn.JOURNALISM_NEWS_NULL);
        }
        if (journalism.getTitle() == null) {
            throw new ErpException(ErrEumn.JOURNALISM_TITLE_NULL);
        }
        if (journalism.getContent() == null) {
            throw new ErpException(ErrEumn.JOURNALISM_CONTENT_NULL);
        }
        if (journalism.getImg() ==null ){
            throw new ErpException(ErrEumn.JOURNALISM_IMG_NULL);
        }
        journalism.setCreateUser(user.getUid());
        return journalismRepository.save(journalism);
    }

    /**
     * 上传图片
     * @param files
     * @param token
     * @return
     * @throws ErpException
     */
    @Override
    public String setHeader(MultipartFile files, String token ) throws ErpException {
        User user = userService.tokenGetUser(token);
        if (user==null){
            throw new ErpException(ErrEumn.USER_NO_EXIT);
        }
        String reName ="";

        if (files ==null){
            throw new ErpException(ErrEumn.JOURNALISM_IMG_NULL);
        }else{
            log.info("【文件上传路径】path={}", headerUploadPath);
            String[] fileNameSplits = files.getOriginalFilename().split("\\.");
            reName = UUID.randomUUID().toString() +
                    (fileNameSplits.length != 0 ? "." + fileNameSplits[fileNameSplits.length - 1] : "");
            String filePath = headerUploadPath + reName;
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(filePath)));
                out.write(files.getBytes());
                out.flush();
                out.close();
            } catch (Exception e) {
                log.error("【上传文件失败】errorMsg={}", e.getMessage());
                throw new ErpException(ErrEumn.UPLOAD_FILE_ERROR);
            }

        }
        return reName;

    }

    /**
     * 读取图片
     * @param img
     * @param response
     * @throws ErpException
     * @throws IOException
     */
    @Override
    public void getimg(String img, HttpServletResponse response) throws ErpException, IOException {
        String fileName = "default.png";
        if (img !=null && !img.equals("")){
            fileName = img ;
        }
        File file = new File(headerUploadPath + fileName);
        if (!file.exists()) {
//            throw new ErpException(ErrEumn.NOT_FOUNDNOT_FILE);
            fileName = "default.png";
           file = new File(headerUploadPath + fileName);
        }
        OutputStream outputStream = response.getOutputStream();
        IOUtils.copy(new FileInputStream(file), outputStream);


    }


    /*查询列表新闻*/
    @Override
    public PageVO<JournalismVO> selectJournalism(Journalism journalism, String token, HttpServletResponse response, long page, long pageSize) throws ErpException, IOException {
        QJournalism qJournalism = QJournalism.journalism;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(journalism.getTitle() != null && !"".equals(journalism.getTitle())){
            booleanBuilder.and(qJournalism.title.like("%"+journalism.getTitle()+"%"));
        }
        JPAQuery<Journalism> journalism1 =  queryFactory.selectFrom(qJournalism) .where(booleanBuilder)
                .offset((page - 1) * pageSize)
                .limit(pageSize);
        List<Journalism> journalisms = journalism1.fetch();

        List<JournalismVO> journalismVO = new ArrayList<>();
        for (Journalism j : journalisms) {
            JournalismVO journalismVOs = new JournalismVO();
            BeanUtils.copyProperties(j, journalismVOs);
            //把用户ｉｄ转换为用户名
            Integer uid = j.getCreateUser();
            User user = findById(uid);
            journalismVOs.setUsername(user.getUsername());
            journalismVO.add(journalismVOs);
        }
        PageVO<JournalismVO> pageVO = new PageVO<>();
        pageVO.init(journalism1.fetchCount(),page,journalismVO);

        return pageVO;
    }

    @Override
    public User findById(Integer userId) throws ErpException {

        Optional<User> userOption = userRepository.findById(userId);
        User user = userOption.orElseThrow(() -> new ErpException(ErrEumn.USER_NO_EXIT));

        return user;
    }

    /*查询列表新闻*/
    @Override
    public List<Journalism> Journalismlist(Journalism journalism, String token) throws ErpException {
        QJournalism qJournalism = QJournalism.journalism;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if(journalism.getTitle() != null && !"".equals(journalism.getTitle())){
            booleanBuilder.and(qJournalism.title.like("%"+journalism.getTitle()+"%"));
        }
        List<Journalism> journalism1 =  queryFactory.selectFrom(qJournalism).where(booleanBuilder).fetch();
        String fileName = "";
        for (Journalism j: journalism1) {
            if (j.getImg() != null && !j.getImg().equals("")) {
                fileName = j.getImg();
            }else{
                fileName = "default.png";
            }
            j.setImg(fileName);
        }
        return journalism1;
    }
    /*查询详情新闻*/
    @Override
    public Journalism getJournalism(Integer id) throws ErpException{
        QJournalism qJournalism = QJournalism.journalism;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qJournalism.id.eq(id));
        Journalism journalism  =queryFactory.select(
                Projections.bean(
                        Journalism.class,
                        qJournalism.img,
                        qJournalism.title,
                        qJournalism.content
                )
        ).from(qJournalism)
                .where(builder)
                .fetchOne();
        return journalism;
    }

    /*修改新闻*/
    @Override
    public Journalism updateJournalism(Journalism journalism)throws ErpException {
        Journalism journalismold;
        Optional<Journalism> optionalJournalism = journalismRepository.findById(journalism.getId());
        if (optionalJournalism.isPresent()) {
            journalismold = optionalJournalism.get();
        } else {
            throw new ErpException(ErrEumn.NOT_FOUNDNOT_FILE);
        }

        if(journalism.getTitle() != null && !journalism.getTitle().equals("")){
            journalism.setTitle(journalism.getTitle());
        }
        if(journalism.getContent() != null && !journalism.getContent().equals("")){
            journalism.setContent(journalism.getContent());
        }
        if(journalism.getImg() != null && !journalism.getImg().equals("")){
            journalism.setImg(journalism.getImg());
        }
        if (journalismold.getCreateTime() !=null && !journalismold.getCreateTime().equals("")){
            journalism.setCreateTime(journalismold.getCreateTime());
        }
        if (journalismold.getCreateUser() !=null && !journalismold.getCreateUser().equals("")){
            journalism.setCreateUser(journalismold.getCreateUser());
        }
        if (journalismold.getClickRate() !=null && !journalismold.getClickRate().equals("")){
            journalism.setClickRate(journalismold.getClickRate());
        }
        journalism.setUpdateTime(
                new Date());

        return journalismRepository.save(journalism);
    }

    /*删除新闻*/
    @Override
    public Journalism deleteJournalism(Integer id) throws ErpException{
        Journalism journalism;
        Optional<Journalism> optionalJournalism = journalismRepository.findById(id);
        if (optionalJournalism.isPresent()) {
            journalism = optionalJournalism.get();
        } else {
            throw new ErpException(ErrEumn.NOT_FOUNDNOT_FILE);
        }
        journalismRepository.delete(journalism);
        return null;
    }
}
