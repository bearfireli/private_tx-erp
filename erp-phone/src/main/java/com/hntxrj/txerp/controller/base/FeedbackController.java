package com.hntxrj.txerp.controller.base;

import com.hntxrj.txerp.server.FeedbackService;
import com.hntxrj.txerp.util.jdbc.GetMsg;
import com.hntxrj.txerp.vo.JsonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 功能:  反馈功能控制
 *
 * @Auther 李帅
 * @Data 2017-09-07.上午 11:14
 */
@RestController
@Scope("prototype")
@EnableAsync
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * 反馈
     *
     * @param compid   企业ID
     * @param empname  用户名
     * @param title    标题
     * @param content  内容
     * @param request
     * @param multiReq
     * @return
     * @throws IOException
     */
    @RequestMapping("/sendFeedback.do")
    public JsonVo sendFeedback(String compid, String empname, String title, String content, HttpServletRequest request, MultipartHttpServletRequest multiReq) throws IOException {
        // TODO:重构方法
        JsonVo vo = new JsonVo();
        /* 接收文件 */
        List<MultipartFile> files = multiReq.getFiles("file");
        MultipartFile file = null;
        List<String> imgListPath = new ArrayList<>();
        /* 创建文件 */
        String realPath = request.getRealPath("/");
        String upPath = "upload/";
        File pPath = new File(realPath, upPath);
        if (!pPath.exists()) {
            pPath.mkdirs();
        }
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                SimpleDateFormat sim = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                String destPath = realPath + upPath + sim.format(new Date()) + file.getOriginalFilename();
                System.out.println(destPath);
                BufferedInputStream bis = new BufferedInputStream(file.getInputStream());
                BufferedOutputStream bof = new BufferedOutputStream(new FileOutputStream(destPath));
                byte[] bt = new byte[1024 * 10 * 5];
                while (bis.read(bt) != -1) {
                    bof.write(bt);
                }
                bof.flush();
                imgListPath.add(destPath);
                //关闭流
                bis.close();
                bof.close();
            }
        }
        try {
            new MailController().senEmail(title, content, imgListPath, "2388399752@qq.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
        GetMsg.getJsonMsg(vo, feedbackService.sendFeedback(compid, empname, title, content));
        return vo;
    }
}
