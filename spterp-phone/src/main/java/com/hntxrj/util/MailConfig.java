package com.hntxrj.util;

import javax.mail.Session;
import java.util.Properties;

/**
 * 功能: 邮箱发送配置
 *
 * @Auther 李帅
 * @Data 2017-09-07.上午 11:09
 */
public class MailConfig {


    /**
     *  获取邮件session
     * @return
     */
    public static Session getEmailSession(){
        Properties props = new Properties();
        //        接收邮件服务器：imap.qq.com，使用SSL，端口号993
        //        发送邮件服务器：smtp.qq.com，使用SSL，端口号465或587
        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        props.setProperty("mail.transport.protocol", "smtp");
        // 开启debug调试
        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名     smtp.qq.com
        props.setProperty("mail.host", "smtp.qq.com");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");

        // 设置环境信息
        Session session = Session.getInstance(props);

        return session;
    }
}
