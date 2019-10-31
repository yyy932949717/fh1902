package com.fh.shop.api.util;


import com.fh.shop.api.common.SytemConst;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class Email {

    public static void email(String email,String title,String content) {
        Properties properties = new Properties();

        properties.put("mail.transport.protocol", "smtp");// 连接协议

        properties.put("mail.smtp.host", SytemConst.SMTP_QQ_COM);// 主机名

        properties.put("mail.smtp.port", "465");// 端口号

        properties.put("mail.smtp.auth", "true");

        properties.put("mail.smtp.ssl.enable", "true");//设置是否使用ssl安全连接  ---一般都使用

        properties.put("mail.debug", "true");//设置是否显示debug信息  true 会在控制台显示相关信息

        //得到回话对象

        Session session = Session.getInstance(properties);

        // 获取邮件对象

        Message message = new MimeMessage(session);



        try {
            //设置发件人邮箱地址
            message.setFrom(new InternetAddress(SytemConst.SYSTEM_QQ_COM));
            //设置收件人地址

            message.setRecipients(Message.RecipientType.TO,new InternetAddress[] { new InternetAddress(email) });

            //设置邮件标题

            message.setSubject(title);//此处为邮件标题，课自行改

            //设置邮件内容

            message.setContent(content,"text/html;charset=UTF-8");//此处为邮件内容课自行改

            //得到邮差对象

            Transport transport = session.getTransport();

            //连接自己的邮箱账户

            transport.connect(SytemConst.SYSTEM_QQ_COM, SytemConst.SYSTEM_EIMAL_PASSWORD);//密码为刚才得到的授权码

            //发送邮件
            transport.sendMessage(message, message.getAllRecipients());
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

}
