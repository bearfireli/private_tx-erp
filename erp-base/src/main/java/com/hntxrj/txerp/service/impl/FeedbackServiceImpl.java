package com.hntxrj.txerp.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.core.util.EntityTools;
import com.hntxrj.txerp.entity.base.Feedback;
import com.hntxrj.txerp.mapper.FeedbackMapper;
import com.hntxrj.txerp.repository.FeedbackRepository;
import com.hntxrj.txerp.service.FeedbackService;
import com.hntxrj.txerp.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    @Value("${app.feedback.imgFilePath}")
    private String feedbackImageFilePath;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, FeedbackMapper feedbackMapper) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackMapper = feedbackMapper;
    }

    @Override
    public Feedback addFeedback(Feedback feedback) throws ErpException {
        EntityTools.setEntityDefaultValue(feedback);
        feedback.setCreateTime(new Timestamp(System.currentTimeMillis()));
        try {
            feedback = feedbackRepository.saveAndFlush(feedback);
        } catch (Exception e) {
            throw new ErpException(ErrEumn.add_feedback_error);
        }
        return feedback;
    }

    @Override
    public PageVO<Feedback> getFeedbackList(Integer pid, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize, "create_time desc");
        List<Feedback> feedbackList = feedbackMapper.getFeedbackByPid(pid);
        PageInfo<Feedback> pageInfo = new PageInfo<>(feedbackList);
        PageVO<Feedback> pageVO = new PageVO<>();
        pageVO.format(pageInfo);
        return pageVO;
    }

    @Override
    public String uploadFeedbackImg(MultipartFile multipartFile) throws ErpException {
        String fileName = UUID.randomUUID().toString();
        File dir = new File(feedbackImageFilePath);
        dir.mkdirs();
        File file = new File(feedbackImageFilePath + fileName);
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
        File file = new File(feedbackImageFilePath + fileName);
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


}
