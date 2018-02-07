package com.huotu.loanmarket.web.controller.user;

import com.huotu.loanmarket.service.model.user.UserListVo;
import com.huotu.loanmarket.service.model.user.UserSearcher;
import com.huotu.loanmarket.service.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author helloztt
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping
    public String userList(UserSearcher userSearcher, Model model) {
        Page<UserListVo> userListVoPage = userService.getUserList(userSearcher);
        model.addAttribute("userList", userListVoPage.getContent());
        model.addAttribute("totalRecord", userListVoPage.getTotalElements());
        model.addAttribute("totalPage", userListVoPage.getTotalPages());
        model.addAttribute("pageIndex", userSearcher.getPageIndex());
        return "/user/user_list";
    }


}
