package com.hntxrj.txerp.controller;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.web.ResultVO;
import com.hntxrj.txerp.entity.base.Feedback;
import com.hntxrj.txerp.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }


    @PostMapping("/addFeedback")
    public ResultVO addFeedback(Feedback feedback) throws ErpException {
        return ResultVO.create(feedbackService.addFeedback(feedback));
    }

    @PostMapping("/getFeedback")
    public ResultVO getFeedback(Integer pid, Integer page, Integer pageSize) {
        return ResultVO.create(feedbackService.getFeedbackList(pid, page, pageSize));
    }

}
