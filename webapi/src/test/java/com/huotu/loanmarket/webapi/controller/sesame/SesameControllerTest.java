package com.huotu.loanmarket.webapi.controller.sesame;

import com.huotu.loanmarket.common.Constant;
import com.huotu.loanmarket.service.entity.user.User;
import com.huotu.loanmarket.service.enums.AppCode;
import com.huotu.loanmarket.webapi.controller.base.BaseTest;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Author hxh
 * @Date 2018/1/31 11:50
 */
public class SesameControllerTest extends BaseTest {
    private static final String BASE_URL = "http://localhost:8080/api/sesame";

    @Test
    public void sesameTest() throws Exception {
        User user = mockUser();
        mockMvc.perform(post(BASE_URL + "/verifyIdAndName")
                .param("name", "胡轩浩")
                .param("idCardNum", "330724199409192914")
                .header(Constant.APP_USER_ID_KEY, user.getUserId())
                .header(Constant.APP_MERCHANT_ID_KEY, Constant.MERCHANT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(AppCode.SUCCESS.getCode()));
    }

    @Test
    public void getAuthenticationUrlTest() throws Exception {
        User user = mockUser();
        mockMvc.perform(post(BASE_URL + "/getSesameUrl")
                .param("name", "王剑南")
                .param("idCardNum", "321324198901095211")
                .header(Constant.APP_USER_ID_KEY, user.getUserId())
                .header(Constant.APP_MERCHANT_ID_KEY, Constant.MERCHANT_ID))
                .andExpect(status().isOk());
    }

    @Test
    public void authorizationTest() throws Exception {
        // TODO: 2017-12-13 涉及透传参数和sign秘钥解析有待线上测试
        mockMvc.perform(post(BASE_URL + "/rollBack/" + 1)
                .param("params", "qH0y29gdStFEPX%2Fm2AsNwx9k4nBaFbaUbyAXR2QsyDHNPQlDTj2J6AY3sfudVomVr3KNoct%2FTud6ILsjYnz%2FE2OyvxrMyojPmaJ5uIWVC6jDNmRa8gqRj1kTn%2Fl1cGnHWfl04Ov923wToVavDBVYijwIqq1dkhbFy89A02z1kz5v%2BRqClvYaXS4HRgjwtA20PjzZI4v8Rh4ExPI86cYj18BM94HGNjyu163yMpNUXjeK1JBPOE5E3ghlSW1cCpBLiCgick6rXq09Dz2XD5Hh81QR7q2AIrbF5N2IjZf4J93XyqCjYzDu2M0EhNs4rJzpKMhIjWihPSQTbRFPeJToog%3D%3D")
                .param("sign", "Mk7aUpWCpr16S7T9FvW2y6TXXPOhgFCgYITkESXjQP2iPFDftXPocEnIbTMRZ8EhCntMgIE%2F8CgYswNfTFVg4crwNjeDTjfVk1oYmLKCZRxyKojBRSNPtAOxxUFqT5DZNDXMgkNxK5QEo5mwob0XnQbLeaQuI8T9Rb7Eug3EaBw%3D"))
                .andExpect(status().isOk());
    }
}
