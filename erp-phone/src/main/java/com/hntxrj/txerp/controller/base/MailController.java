package com.hntxrj.txerp.controller.base;

import com.hntxrj.txerp.util.MailConfig;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.*;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.List;

/**
 * 功能:   发送邮件
 *
 * @Auther 李帅
 * @Data 2017-09-06.上午 11:51
 */
@RestController
@Scope("prototype")
@EnableAsync
public class MailController {

    @RequestMapping(value = "/mail.do", method = RequestMethod.POST)
    public void senEmail(String title, String content, List<String> imgList, String senTo) throws Exception {
        Session session = MailConfig.getEmailSession();
        //创建邮件的正文
        MimeBodyPart text = new MimeBodyPart();
        // setContent(“邮件的正文内容”,”设置邮件内容的编码方式”)
        text.setContent(content, "text/html;charset=utf-8");
        //关系   正文和图片的
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);
//        // 创建图片
//        List<MultipartFile> files = multiReq.getFiles("file");
//        MultipartFile file = null;
        for (int i = 0; i < imgList.size(); ++i) {
            String path = imgList.get(i);
            if (!path.isEmpty()) {
                MimeBodyPart img = new MimeBodyPart();
                DataHandler dh = null;
                dh = new DataHandler(new FileDataSource(new File(path)));
                img.setDataHandler(dh);
                img.setFileName(MimeUtility.encodeText(path.substring(path.lastIndexOf("/") + 1)));
                img.setContentID("a" + i);
                mm.addBodyPart(img);
            }
        }
        mm.setSubType("related");//设置正文与图片之间的关系
        //图班与正文的 body
        MimeBodyPart all = new MimeBodyPart();
        all.setContent(mm);
        MimeMultipart mm2 = new MimeMultipart();
        mm2.addBodyPart(all);
        mm2.setSubType("mixed");

        // 创建邮件对象
        Message msg = new MimeMessage(session);
        try {
            msg.setSubject("来自 Spterp 反馈 ：" + title);
            // 设置邮件内容
//            msg.setText(content);
            msg.setContent(mm2);
            // 设置发件人
            msg.setFrom(new InternetAddress("mz135@vip.qq.com"));
            msg.saveChanges();
            Transport transport = session.getTransport();
            // 连接邮件服务器 yspelkknxbikbdhc
            transport.connect("mz135@vip.qq.com", "yspelkknxbikbdhc");
            // 发送邮件
            transport.sendMessage(msg, new Address[]{new InternetAddress(senTo)});
            // 关闭连接
            transport.close();
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
    }

}
