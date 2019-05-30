package com.hntxrj.txerp.controller;

import com.hntxrj.txerp.core.exception.ErpException;
import com.hntxrj.txerp.core.web.ResultVO;
import com.hntxrj.txerp.entity.base.Feedback;
import com.hntxrj.txerp.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

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
    public ResultVO getFeedback(Integer pid,
                                @RequestParam(defaultValue = "1") Integer page,
                                @RequestParam(defaultValue = "10") Integer pageSize) {
        return ResultVO.create(feedbackService.getFeedbackList(pid, page, pageSize));
    }

    @PostMapping("/uploadPicture")
    public ResultVO uploadPicture(MultipartFile image) throws ErpException {
        return ResultVO.create(feedbackService.uploadFeedbackImg(image));
    }

    @PostMapping("/getFeedboackPicture")
    public void getFeedbackPicture(String fileName, HttpServletResponse response) throws ErpException {
        feedbackService.getFeedbackImg(fileName, response);
    }
}
