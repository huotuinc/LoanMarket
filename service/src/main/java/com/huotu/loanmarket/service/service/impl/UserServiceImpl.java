package com.huotu.loanmarket.service.service.impl;

import com.huotu.loanmarket.common.utils.CookieHelper;
import com.huotu.loanmarket.service.base.AbstractCrudService;
import com.huotu.loanmarket.service.entity.LoanUser;
import com.huotu.loanmarket.service.repository.LoanUserRepository;
import com.huotu.loanmarket.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

/**
 * @author hxh
 * @date 2017-10-27
 */
@Service
public class UserServiceImpl extends AbstractCrudService<LoanUser, Integer> implements UserService {

    private final LoanUserRepository loanUserRepository;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    public UserServiceImpl(LoanUserRepository repository) {
        super(repository);
        loanUserRepository = repository;
    }

}
