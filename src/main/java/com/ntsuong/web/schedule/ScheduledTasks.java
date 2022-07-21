package com.ntsuong.web.schedule;

import com.google.gson.Gson;
import com.ntsuong.web.component.EmailService;
import com.ntsuong.web.dto.response.mail.ExtraResetPassword;
import com.ntsuong.web.model.MailSystem;
import com.ntsuong.web.service.MailSystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ScheduledTasks {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Autowired
    private MailSystemService mailSystemService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private MessageSource messageSource;

    @Scheduled(cron = "0 */5 * ? * *")
    public void resetPasswordScheduleTaskWithCronExpression() {
        String baseurl = messageSource.getMessage("baseUrl", null, new Locale("vi", "vi-VN"));
        log.info("Send email to producers to inform quantity sold items");
        List<MailSystem> mailSystems = mailSystemService.findByStatus(0);
        mailSystems.forEach(x->{
            if(x.getTimeExpired()>=System.currentTimeMillis()) {
                Gson gson = new Gson();
                ExtraResetPassword extra = gson.fromJson(x.getExtra(), ExtraResetPassword.class);
                extra.setLink(String.format("%s/%s/%s/%s",baseurl,"redirect/user/change-password", System.currentTimeMillis(), x.getId()));
                extra.setLogo("https://drive.google.com/uc?export=view&id=1vgVDuTePkxCwTR76ACki-rO5hqgDhRGj");
                Map<String, Object> templateModel = new HashMap<>();
                templateModel.put("model", extra);
                try {
                    if(x.getType().equals("RESET_PASSWORD")) {
                        emailService.sendMessageUsingThymeleafTemplate(x.getToEmail(), x.getSubject(),
                                templateModel, "mail-templates/reset-password");
                    }else if(x.getType().equals("CHANGED_PASSWORD_SUCCESS")){
                        emailService.sendMessageUsingThymeleafTemplate(x.getToEmail(), x.getSubject(),
                                templateModel, "mail-templates/changed-password-success");
                    }else {
                        emailService.sendMessageUsingThymeleafTemplate(x.getToEmail(), x.getSubject(),
                                templateModel, "error/500");
                    }
                    x.setStatus(2);
                    x.setCountSent(x.getCountSent() + 1);
                    mailSystemService.saveAndFlush(x);
                    TimeUnit.SECONDS.sleep(5);
                } catch (MessagingException e) {
                    x.setStatus(-1);
                    x.setCountSent(x.getCountSent() + 1);
                    mailSystemService.saveAndFlush(x);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }else{
                x.setStatus(-2);
                mailSystemService.saveAndFlush(x);
            }
        });

    }

}
