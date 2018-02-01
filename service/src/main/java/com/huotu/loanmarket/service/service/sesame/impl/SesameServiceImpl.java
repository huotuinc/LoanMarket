package com.huotu.loanmarket.service.service.sesame.impl;

import com.huotu.loanmarket.service.entity.sesame.Industry;
import com.huotu.loanmarket.service.repository.sesame.IndustryRepository;
import com.huotu.loanmarket.service.service.sesame.SesameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author hxh
 * @Date 2018/1/30 16:17
 */
@Service
public class SesameServiceImpl implements SesameService {
    @Autowired
    private IndustryRepository industryRepository;


    @Override
    public Industry save(Industry industry) {
        return industryRepository.save(industry);
    }
}
