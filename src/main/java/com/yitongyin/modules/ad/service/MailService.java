package com.yitongyin.modules.ad.service;


import com.yitongyin.modules.ad.entity.MailSenderModal;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 *
 * 邮件发送接口
 *
 * @author futurebox
 *
 */
public interface MailService {

    /**
     *
     * 文本类型邮件发送业务接口
     *
     * @param toEmail 收件人
     * @param subject 标题
     * @param body 内容
     * @return
     */
    MailSenderModal sendSimple(
            @NotNull String toEmail,
            @NotNull String subject,
            @NotNull String body);

    /**
     *
     * Mime类型邮件发送业务接口
     *
     * @param toEmail 收件人
     * @param subject 标题
     * @param mailBody 内容
     * @param photos 是否带有附件或静态文件
     * @param isAttachment 是否为附件,false为资源，true为附件
     * @return
     */
    MailSenderModal sendMime(
            @NotNull String toEmail,
            @NotNull String subject,
            @NotNull String mailBody,
            @Nullable Map<String, String> photos,
            @Nullable boolean isAttachment);
}