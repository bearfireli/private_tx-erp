package com.hntxrj.txerp.service;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.entity.base.Feedback;
import com.hntxrj.txerp.vo.PageVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface FeedbackService {

    Feedback addFeedback(Feedback feedback) throws ErpException;

    PageVO<Feedback> getFeedbackList(Integer pid, Integer page, Integer pageSize);

    String uploadFeedbackImg(MultipartFile multipartFile) throws ErpException;

    void getFeedbackImg(String fileName, HttpServletResponse response) throws ErpException;
}
