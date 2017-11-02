package com.huotu.loanmarket.web.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.huotu.loanmarket.common.SysConstant;
import com.huotu.loanmarket.common.ienum.LoanTermEnum;
import com.huotu.loanmarket.common.utils.StringUtilsExt;
import com.huotu.loanmarket.service.entity.*;
import com.huotu.loanmarket.service.repository.*;
import com.huotu.loanmarket.service.service.CategoryService;
import com.huotu.loanmarket.service.service.ProjectService;
import com.huotu.loanmarket.service.service.VerifyCodeService;
import com.huotu.loanmarket.web.base.ApiResult;
import com.huotu.loanmarket.web.base.ApiTestBase;
import com.huotu.loanmarket.web.viewmodel.ProjectListViewModel;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.Jsr310Converters;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by hxh on 2017-10-26.
 */
public class ApiControllerTest extends ApiTestBase {

    private final String requestUrl = "/rest/api";
    @Autowired
    private ProjectService projectService;
    @Autowired
    private LoanProjectRepository loanProjectRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private LoanCategoryRepository categoryRepository;
    @Autowired
    private LoanUserRepository loanUserRepository;
    private List<LoanCategory> mockCategories;
    private List<LoanProject> mockProjects;
    private List<LoanUser> mockUsers;
    @Autowired
    private LoanEquipmentRepository equipmentRepository;
    @Autowired
    private LoanUserViewLogRepository viewLogRepository;
    @Autowired
    private LoanVerifyCodeRepository verifyCodeRepository;

    @Before
    public void mockData() {
        categoryRepository.deleteAll();
        loanProjectRepository.deleteAll();
        loanUserRepository.deleteAll();
        //建立随机个分类
        for (int i = 0; i < this.nextIntInSection(5, 10); i++) {
            LoanCategory category = new LoanCategory();
            category.setName(UUID.randomUUID().toString().substring(5));

            categoryService.save(category);
        }
        mockCategories = categoryRepository.findAll();
        LocalDateTime now = LocalDateTime.now().minusMonths(1);
        //建立随机个产品
        for (int i = 0; i < nextIntInSection(30, 50); i++) {
            Date createTime = Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(now);

            LoanProject project = new LoanProject();
            project.setName(UUID.randomUUID().toString());
            double nextMoney = nextIntInSection(100, 200);
            StringBuilder loanMoney = new StringBuilder(nextMoney + ",");
            for (int j = 0; j < nextIntInSection(0, 5); j++) {
                nextMoney += nextIntInSection(100, 200);
                loanMoney.append(nextMoney).append(",");
            }
            project.setEnableMoney(loanMoney.toString().substring(0, loanMoney.toString().length() - 1));
            int nextDeadline = nextIntInSection(5, 10);
            StringBuilder deadline = new StringBuilder(nextDeadline + ",");
            for (int j = 0; j < nextIntInSection(0, 5); j++) {
                nextDeadline += nextIntInSection(5, 10);
                deadline.append(nextDeadline).append(",");
            }
            project.setDeadline(deadline.toString().substring(0, deadline.toString().length() - 1));
            project.setDeadlineUnit(LoanTermEnum.DAY);
            project.setInterestRate(nextDoubleInSection(0, 1));
            project.setCreateTime(createTime);
            project.setIsHot(nextIntInSection(0, 1));
            project.setIsNew(nextIntInSection(0, 1));
            project.setApplyType(nextIntInSection(0, 1));
            project.setContact(UUID.randomUUID().toString().substring(5));
            project.setPhone(UUID.randomUUID().toString().substring(10));
            project.setMaxMoney(nextMoney);
            StringBuilder categories = new StringBuilder(",");
            for (int j = 0; j < nextIntInSection(1, mockCategories.size()); j++) {
                categories.append(mockCategories.get(j).getCategoryId()).append(",");
            }
            project.setCategories(categories.toString());
            projectService.save(project);

            now = now.plusDays(1);
        }
        mockProjects = loanProjectRepository.findAll(new Sort(Sort.Direction.DESC, "createTime"));

        //几个用户
        for (int i = 0; i < nextIntInSection(5, 10); i++) {
            LoanUser loanUser = new LoanUser();
            loanUser.setAccount(randomMobile());
            loanUser.setCreateTime(new Date());
            loanUserRepository.save(loanUser);
        }
        mockUsers = loanUserRepository.findAll(new Sort(Sort.Direction.DESC, "createTime"));
    }

    @Test
    public void InitTest() throws Exception {
        equipmentRepository.deleteAll();
        String appVersion = UUID.randomUUID().toString().substring(5);
        String osVersion = UUID.randomUUID().toString().substring(5);
        String osType = "IOS";
        //没有传userId
        mockMvc.perform(post(requestUrl + "/user/init")
                .param("appVersion", appVersion)
                .param("osVersion", osVersion)
                .param("osType", osType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(5000));
        //登录情况下，有传userId
        LoanUser expectedUser = mockUsers.get(nextIntInSection(0, mockUsers.size() - 1));
        mockMvc.perform(post(requestUrl + "/user/init")
                .param("appVersion", appVersion)
                .param("osVersion", osVersion)
                .param("osType", osType)
                .param("userId", String.valueOf(expectedUser.getUserId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(2000))
                .andExpect(jsonPath("$.data.userId").value(expectedUser.getUserId()));
        LoanEquipment equipment = equipmentRepository.findAll().get(0);
        assertEquals(appVersion, equipment.getAppVersion());
        assertEquals(osVersion, equipment.getOsVersion());
        assertEquals(osType, equipment.getOsType());
    }

    /**
     * 分组按条件搜索产品列表
     */
    @Test
    public void projectListTest() throws Exception {
        MvcResult mvcResult;
        String url = requestUrl + "/project/list";

        //无筛选条件测试
        mvcResult = mockMvc.perform(post(url)
                .param("pageSize", String.valueOf(SysConstant.API_DEFALUT_PAGE_SIZE)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(2000))
                .andExpect(jsonPath("$.data.totalRecord").value(mockProjects.size()))
                .andReturn();

        ApiResult<ProjectListViewModel> apiResult = JSON.parseObject(mvcResult.getResponse().getContentAsString(), new TypeReference<ApiResult<ProjectListViewModel>>() {
        });

        int actualPageCount = mockProjects.size() / SysConstant.API_DEFALUT_PAGE_SIZE;
        if (mockProjects.size() % SysConstant.API_DEFALUT_PAGE_SIZE != 0) {
            actualPageCount++;
        }
        assertEquals(apiResult.getData().getTotalPage(), actualPageCount);

        //加入筛选条件topnum
        int topNum = random.nextInt(mockProjects.size()) + 1;
        mvcResult = mockMvc.perform(post(url)
                .param("topNum", String.valueOf(topNum)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(2000))
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding(StringUtilsExt.ENCODING_UTF8);

        apiResult = JSON.parseObject(mvcResult.getResponse().getContentAsString(), new TypeReference<ApiResult<ProjectListViewModel>>() {
        });

        assertEquals(topNum, apiResult.getData().getList().size());

//        Collections.sort(mockProjects, (o1, o2) -> {
//            if (o1.getCreateTime().getTime() < o2.getCreateTime().getTime()) {
//                return 0;
//            }
//            if (o1.getCreateTime().getTime() > o2.getCreateTime().getTime()) {
//                return 1;
//            }
//            return -1;
//        });
        // TODO: 29/10/2017 手动排序居然不行

        int randomIndex = random.nextInt(topNum);
        assertEquals(mockProjects.get(randomIndex).getName(),
                apiResult.getData().getList().get(randomIndex).getName());

        //加入筛选条件
        int isNew = nextIntInSection(0, 1);
        int isHot = nextIntInSection(0, 1);
        int categoryId = mockCategories.get(nextIntInSection(0, mockCategories.size() - 1)).getCategoryId();

        mvcResult = mockMvc.perform(post(url)
                .param("isNew", String.valueOf(isNew))
                .param("isHot", String.valueOf(isHot))
                .param("categoryId", String.valueOf(categoryId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(2000))
                .andReturn();
        mvcResult.getResponse().setCharacterEncoding(StringUtilsExt.ENCODING_UTF8);
        apiResult = JSON.parseObject(mvcResult.getResponse().getContentAsString(), new TypeReference<ApiResult<ProjectListViewModel>>() {
        });

        List<LoanProject> expectedList = mockProjects.stream().filter(p ->
                p.getIsNew() == isNew
                        && p.getIsHot() == isHot
                        && p.getCategories().contains("," + categoryId + ","))
                .collect(Collectors.toList());
        assertEquals(expectedList.size(), apiResult.getData().getTotalRecord());
    }

    /**
     * 获取所有分类
     */
    @Test
    public void projectCategoryTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(requestUrl + "/project/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(2000))
                .andReturn();

        ApiResult<List<LoanCategory>> apiResult = JSON
                .parseObject(mvcResult.getResponse().getContentAsString(), new TypeReference<ApiResult<List<LoanCategory>>>() {
                });

        assertEquals(mockCategories.size(), apiResult.getData().size());
    }

    @Test
    public void projectDetailTest() throws Exception {
        viewLogRepository.deleteAll();
        int projectIndex = nextIntInSection(0, mockProjects.size() - 1);
        LoanProject expectedProject = mockProjects.get(projectIndex);

        //未登录情况
        mockMvc.perform(post(requestUrl + "/project/detail")
                .param("projectId", String.valueOf(expectedProject.getLoanId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(2000))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.loanId").value(expectedProject.getLoanId()))
                .andReturn();

        List<LoanUserViewLog> viewLogs = viewLogRepository.findAll();
        assertEquals(viewLogs.size(), 0);

        //登录情况下
        LoanUser expectedUser = mockUsers.get(nextIntInSection(0, mockUsers.size() - 1));

        mockMvc.perform(post(requestUrl + "/project/detail")
                .param("userId", String.valueOf(expectedUser.getUserId()))
                .param("projectId", String.valueOf(expectedProject.getLoanId())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(2000))
                .andExpect(jsonPath("$.data").isNotEmpty())
                .andExpect(jsonPath("$.data.loanId").value(expectedProject.getLoanId()))
                .andReturn();

        viewLogs = viewLogRepository.findAll();
        assertEquals(viewLogs.size(), 1);
        assertEquals(expectedUser.getUserId().intValue(), viewLogs.get(0).getUserId());
    }

    @Test
    public void loginTest() throws Exception {
        String mobile = randomMobile();

        int randomCode = nextIntInSection(1000, 9999);
        String verifyCode = String.valueOf(randomCode);
        //测试验证码
        LoanVerifyCode mockCode = new LoanVerifyCode();
        mockCode.setMobile(mobile);
        mockCode.setCode(verifyCode);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime invalidTime = now.plusMinutes(10);
        mockCode.setSendTime(Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(now));
        mockCode.setInvalidTime(new Date());
        verifyCodeRepository.save(mockCode);
        mockMvc.perform(post(requestUrl + "/user/login")
                .param("mobile", mobile)
                .param("verifyCode", verifyCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(5000));

        //验证通过
        mockCode.setInvalidTime(Jsr310Converters.LocalDateTimeToDateConverter.INSTANCE.convert(invalidTime));
        verifyCodeRepository.save(mockCode);

        //没有用户的情况下
        mockMvc.perform(post(requestUrl + "/user/login")
                .param("mobile", mobile)
                .param("verifyCode", verifyCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(2000))
                .andExpect(jsonPath("$.data.account").value(mobile));

        LoanUser expectedUser = loanUserRepository.findByAccount(mobile);

        assertNotNull(expectedUser);
        assertEquals(expectedUser.getAccount(), mobile);

        //有用户的情况下
        mockMvc.perform(post(requestUrl + "/user/login")
                .param("mobile", mobile)
                .param("verifyCode", verifyCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(2000))
                .andExpect(jsonPath("$.data.account").value(mobile));
    }

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Test
    public void sendVerifyCodeTest() throws Exception {
        String testMobile = "15558039061";

        mockMvc.perform(post(requestUrl + "/sendVerifyCode")
                .param("mobile", testMobile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value(2000));

//        verifyCodeService.send("18324499921", "哎哟，媳妇儿");
    }
}
