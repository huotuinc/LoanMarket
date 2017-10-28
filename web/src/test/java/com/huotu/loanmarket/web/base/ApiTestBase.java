package com.huotu.loanmarket.web.base;


import com.huotu.loanmarket.web.config.WebTestConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * API测试
 * Created by allan on 29/10/2017.
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WebTestConfig.class})
@WebAppConfiguration
@Transactional
public class ApiTestBase extends AbstractWebTestBase {

    @Override
    protected void createMockMVC() {
        mockMvc = webAppContextSetup(context)
                .build();
    }
}
