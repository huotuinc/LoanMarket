package com.huotu.loanmarket.web;

import com.huotu.loanmarket.web.controller.filter.ParamFilter;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by hxh on 2017-10-26.
 */
public class SpringWebTest {
    /**
     * 选配 只有在SecurityConfig起作用的情况下
     **/
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired(required = false)
    private FilterChainProxy springSecurityFilter;
    @Autowired
    protected WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;
    protected ParamFilter paramFilter = new ParamFilter();

    @Before
    public void setup() {
        if (springSecurityFilter != null)
            this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                    .addFilters(springSecurityFilter)
                    .build();
        else
            this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                    .addFilters(paramFilter)
                    .build();
    }
}
