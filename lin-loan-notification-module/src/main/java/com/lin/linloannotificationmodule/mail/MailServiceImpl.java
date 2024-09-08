package com.lin.linloannotificationmodule.mail;

import com.lin.linloannotificationmodule.config.listener.KafkaConsumerService;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.lin.commonsshared.utils.AppUtils.APP_NAME;

@Component
public class MailServiceImpl implements ApplicationListener<MailEvent> {
    @Autowired
    private MailConfig mailConfig;
    @Autowired
    private TemplateEngine templateEngine;

    private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);


    @Override
    public void onApplicationEvent(MailEvent event) {

        HashMap<String,String> data = event.getData();
        Map<String, Object> otpVariables = new HashMap<>();
        otpVariables.put("user_name", data.get("email"));
        otpVariables.put("company_name", "LIN-LOAN");
        otpVariables.put("otp", data.get("OTP"));
        this.sendEmail("LIN LOAN REGISTRATION ALERT", data.get("email"), otpVariables, "otp");
    }

    public void sendEmail(String subject, String to, Map<String, Object> templateVariables, String templateName) {
        try {
            MimeMessage mimeMessage = mailConfig.javaMailSender().createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            LOGGER.info("FROM {}", mailConfig.username);
            messageHelper.setFrom(mailConfig.username, APP_NAME);
            messageHelper.setSubject(subject);
            messageHelper.setTo(to);

            Context context = new Context();
            context.setVariables(templateVariables);

            String mailContent = templateEngine.process(templateName, context);

            messageHelper.setText(mailContent, true);
            mailConfig.javaMailSender().send(mimeMessage);

            LOGGER.info("Email sent successfully to: {}", to);
        } catch (MessagingException | UnsupportedEncodingException | jakarta.mail.MessagingException e) {
            LOGGER.info("Error occurred while sending email to {} {} ",  to  , e.getMessage());
        }
    }


}
