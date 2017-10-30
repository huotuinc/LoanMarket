package com.huotu.loanmarket.web.base;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;

/**
 * Created by allan on 29/10/2017.
 */
public abstract class AbstractWebTestBase {
    protected final Random random = new Random();
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

    /**
     * 产生在区间内的随机正整数，[min,max]
     *
     * @param min 最小数
     * @param max 最大数
     * @return 产生的随机数
     */
    protected int nextIntInSection(int min, int max) {
        if (min > max) {
            throw new IllegalStateException("最小数不能超过最大数");
        }
        return random.nextInt(max - min + 1) + min;
    }

    /**
     * 产生在区间内的随机小数, [min,max)
     *
     * @param min 最小数
     * @param max 最大数
     * @return 随机小数
     */
    protected double nextDoubleInSection(int min, int max) {
        if (min >= max) {
            throw new IllegalStateException("最小数必须小于最大数");
        }
        return random.nextDouble() * (max - min) + min;
    }

    /**
     * <p>位数不足无法保证其唯一性,需要客户端代码自行校验唯一性.</p>
     * <p>具体的区间是10000000000-19999999999</p>
     *
     * @return 获取一个随机的手机号码
     */
    protected String randomMobile() {
        String p1 = String.valueOf(100000 + random.nextInt(100000));
        //还有5位 而且必须保证5位
        String p2 = String.format("%05d", random.nextInt(100000));
        return p1 + p2;
    }
}
