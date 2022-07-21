package com.ntsuong.web.controller.admin;

import com.ntsuong.web.dto.response.user.UserResponse;
import com.ntsuong.web.model.User;
import com.ntsuong.web.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author  Nguyen Thanh Suong
 * @version 1.0
 * @since   2022-06-01
 */
@Controller
public class ProfileController {

    @RequestMapping("/admin/user/profile.do")
    public String profile(Model model){
        User currentUser= SecurityUtils.getCurrentUser();

        UserResponse userResponse = new UserResponse();
        userResponse.setId(currentUser.getId());
        userResponse.setFirstName(currentUser.getFirstName());
        userResponse.setUsername(currentUser.getUsername());
        userResponse.setAvatarUrl(currentUser.getAvatarUrl()!=null ? currentUser.getAvatarUrl() : "https://drive.google.com/uc?export=view&id=1iPIA2--D-x1-K5QVopdn7qiX37TbXPmf");
        userResponse.setFullName(String.format("%s %s %s", currentUser.getFirstName(),
                currentUser.getMiddleName(), currentUser.getLastName()));
        userResponse.setEmail(currentUser.getEmail());
        userResponse.setAddress(currentUser.getAddress());
        userResponse.setCareer(currentUser.getCareer());
        userResponse.setPhone(currentUser.getPhone());

        model.addAttribute("currentUser", userResponse);
        return "admin/user/profile";
    }

}
