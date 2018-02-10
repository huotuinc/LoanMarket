package com.huotu.loanmarket.web.controller.user;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.model.PageListView;
import com.huotu.loanmarket.service.model.user.UserInviteVo;
import com.huotu.loanmarket.service.model.user.UserListVo;
import com.huotu.loanmarket.service.model.user.UserSearcher;
import com.huotu.loanmarket.service.repository.order.OrderRepository;
import com.huotu.loanmarket.service.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author helloztt
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private OrderRepository orderRepository;

    @RequestMapping
    public String userList(UserSearcher userSearcher, Model model) {
        Page<UserListVo> userListVoPage = userService.getUserList(userSearcher);
        model.addAttribute("userList", userListVoPage.getContent());
        model.addAttribute("totalRecord", userListVoPage.getTotalElements());
        model.addAttribute("totalPage", userListVoPage.getTotalPages());
        model.addAttribute("pageIndex", userSearcher.getPageIndex());
        return "/user/user_list";
    }

    @RequestMapping(value = "/{userId}/invite")
    public String inviteDetail(@PathVariable long userId
            , @RequestParam(required = false, defaultValue = "true") Boolean isAuthSuccess
            , @RequestParam(required = false, defaultValue = "1") int pageIndex
            , @RequestParam(required = false, defaultValue = Constant.PAGE_SIZE_STR) int pageSize
            , Model model) {
        PageListView<UserInviteVo> userInviteVoPage = userService.getMyInviteList(userId, isAuthSuccess,false, pageIndex, pageSize);
        //还要统计征信查询次数
        if(!CollectionUtils.isEmpty(userInviteVoPage.getList())){
            List<Long> userIdList = userInviteVoPage.getList().stream().map(UserInviteVo::getUserId).collect(Collectors.toList());
            List<Object[]> userOrderCountList = orderRepository.countByUserId(userIdList);
            for (Object[] userOrderCount : userOrderCountList) {
                userInviteVoPage.getList().stream().filter(p -> p.getUserId().equals(Long.valueOf(userOrderCount[0].toString())))
                        .forEach(p -> p.setOrderCount(Integer.valueOf(userOrderCount[1].toString())));
            }
        }
        model.addAttribute("inviteList", userInviteVoPage.getList());
        model.addAttribute("totalRecord", userInviteVoPage.getTotalCount());
        model.addAttribute("totalPage", userInviteVoPage.getPageCount());
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("userId", userId);
        model.addAttribute("isAuthSuccess", isAuthSuccess);
        model.addAttribute("authSuccessCount", userService.countByMyInvite(userId, true));
        model.addAttribute("authFailureCount", userService.countByMyInvite(userId, false));
        return "/user/user_invite_list";
    }


}
