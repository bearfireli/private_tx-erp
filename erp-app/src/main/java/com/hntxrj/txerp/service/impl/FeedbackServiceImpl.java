package com.hntxrj.txerp.service.impl;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.exception.ErrEumn;
import com.hntxrj.txerp.core.util.EntityTools;
import com.hntxrj.txerp.entity.base.Feedback;
import com.hntxrj.txerp.repository.FeedbackRepository;
import com.hntxrj.txerp.service.FeedbackService;
import com.hntxrj.txerp.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
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
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by("createTime desc"));
        Page<Feedback> feedbackPage = feedbackRepository.findAllByPid(pid, pageRequest);
        PageVO<Feedback> pageVO = new PageVO<>();
        pageVO.init(feedbackPage.getTotalElements(), page, feedbackPage.getContent());
        return pageVO;
    }
}
