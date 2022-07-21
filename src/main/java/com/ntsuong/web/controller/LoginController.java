package com.ntsuong.web.controller;

import com.google.gson.Gson;
import com.ntsuong.web.dto.response.ChangePasswordDto;
import com.ntsuong.web.dto.response.mail.ExtraResetPassword;
import com.ntsuong.web.model.MailSystem;
import com.ntsuong.web.model.User;
import com.ntsuong.web.service.MailSystemService;
import com.ntsuong.web.service.UserService;
import com.ntsuong.web.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author  Nguyen Thanh Suong
 * @version 1.0
 * @since   2022-06-01
 */
@Slf4j
@Controller
public class LoginController {

    @Autowired
    private MailSystemService mailSystemService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value="/login.do")
    public  String loginPage(@RequestParam(value = "error",
    required = false) String error,
        @RequestParam(value = "logout", required = false) String logout ,
                         Model model){
        String errorMessage = null;
        if(error != null){
            errorMessage = "Username or Password is incorrect !!";
        }
        if(logout != null) {
            errorMessage = "You have been successfully logged out !!";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }

    @RequestMapping(value="/logout.do", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login.do?logout=true";
    }

    @RequestMapping(value="/redirect/user/change-password/{timestamp}/{id}", method = RequestMethod.GET)
    public String changedPassword(Model model,
                                  @PathVariable(value = "timestamp") Long timestamp,
                                  @PathVariable(value = "id") UUID id){
        Optional<MailSystem> mailSystemOptional = mailSystemService.findById(id);
        if(mailSystemOptional.isPresent()){
            MailSystem mailSystem = mailSystemOptional.get();
            if(mailSystem.getType().equals("RESET_PASSWORD") && mailSystem.getTimeExpired()>System.currentTimeMillis()){
                ChangePasswordDto changePasswordDto = new ChangePasswordDto();
                changePasswordDto.setId(id);
                changePasswordDto.setTimestamp(timestamp);
                model.addAttribute("changePassword", changePasswordDto); // Mail Id
                return "changed-password";
            }else{
                return "redirect:/error/403";
            }
        }else{
            return "redirect:/error/403";
        }

    }

    @RequestMapping(value="/redirect/user/update-password.do", method = RequestMethod.POST)
    public String changedPassword(@Valid @ModelAttribute("changePassword") ChangePasswordDto changePasswordDto,
                                  BindingResult errors, Model model){

        if(!errors.hasErrors()) {
            Optional<MailSystem> mailSystemOptional = mailSystemService.findById(changePasswordDto.getId());
            if(mailSystemOptional.isPresent()) {
                MailSystem mailSystem = mailSystemOptional.get();
                if(mailSystem.getType().equals("RESET_PASSWORD") && mailSystem.getTimeExpired()>System.currentTimeMillis()){
                    Gson gson = new Gson();
                    ExtraResetPassword extra = gson.fromJson(mailSystem.getExtra(), ExtraResetPassword.class);
                    Optional<User> userOptional = userService.findById(UUID.fromString(extra.getUserId()));
                    if(userOptional.isPresent()){
                        User user = userOptional.get();
                        user.setPassword(new BCryptPasswordEncoder().encode(changePasswordDto.getPassword()));
                        userService.saveAndFlush(user);
                        return "redirect:/redirect/success/changedPasswordSuccess/"+user.getId();
                    }else{
                        return "redirect:/error/403";
                    }
                }else{
                    return "redirect:/error/403";
                }
            }
        }
        model.addAttribute("changePassword", changePasswordDto);
        return ((errors.hasErrors()) ? "changed-password"
                : "redirect:/error/403");
    }

    @RequestMapping(value="/redirect/success/changedPasswordSuccess/{id}", method = RequestMethod.GET)
    public String changedPasswordSuccess(@PathVariable(value = "id") UUID id){
            long timestamp = System.currentTimeMillis();
            Gson gson = new Gson();
            Optional<User> userOptional = userService.findById(id);
            if(userOptional.isPresent()) {
                User user = userOptional.get();
                MailSystem mailSystem = new MailSystem();
                mailSystem.setToEmail(user.getEmail());
                mailSystem.setFromEmail(StringUtils.NOREPLY_ADDRESS);
                mailSystem.setSubject(messageSource.getMessage("cms.mail.user.reset.changedSuccess", null, new Locale("vi", "VN")));
                mailSystem.setStatus(0); // -2: Expire ; -1: Don't Send; 0: Waiting ; 1: Sending ; 2: Success; 3: Done
                mailSystem.setType("CHANGED_PASSWORD_SUCCESS");
                mailSystem.setCreated(new Date());
                mailSystem.setCountSent(0);
                mailSystem.setTimestamp(timestamp);
                //long expired = TimeUnit.MILLISECONDS.convert(30, TimeUnit.MINUTES);
                mailSystem.setTimeExpired(timestamp);
                ExtraResetPassword extra = new ExtraResetPassword();
                extra.setUserId(String.valueOf(id));
                extra.setFullName(String.format("%s %s %s", user.getFirstName(), user.getMiddleName(), user.getLastName()));
                extra.setUsername(user.getUsername());
                extra.setLogo("/static/img/brand-white.svg");
                mailSystem.setExtra(gson.toJson(extra));

                mailSystemService.saveAndFlush(mailSystem);
                return "success/changed-success";
            }else{
                return "redirect:/error/403";
            }

    }
}
