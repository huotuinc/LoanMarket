package com.huotu.loanmarket.web.base;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by allan on 29/10/2017.
 */
public abstract class AbstractWebTestBase {
    @Autowired
    protected WebApplicationContext context;

    /**
     * mock请求
     **/
    @Autowired
    protected MockHttpServletRequest request;

    /**
     * mockMvc等待初始化
     **/
    protected MockMvc mockMvc;

    protected abstract void createMockMVC();

    @Before
    public void initTest() {
        MockitoAnnotations.initMocks(this);
        createMockMVC();
    }
}
