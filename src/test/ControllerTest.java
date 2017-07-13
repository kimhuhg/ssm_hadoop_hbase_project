package test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by Shinelon on 2017/7/12.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration()
@ContextConfiguration(locations = {"classpath:/test/configurationFiles/dispatcher-servlet.xml", "classpath:/test/configurationFiles/applicationContext.xml"})
//当然 你可以声明一个事务管理 每个单元测试都进行事务回滚 无论成功与否  默认是回滚的即true ,所以可以不声明
//@Rollback(value = true)
//记得要在XML文件中声明事务哦~~~我是采用注解的方式
//@Transactional
public class ControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void testUrlGoMybatisTest() throws Exception {
        mockMvc.perform(get("/helloWorld/goMybatisTest"))
                .andExpect(redirectedUrl("/mybatisTest.html"))
                .andDo(print())
                .andReturn();
    }

    @Test
    public void testUrlStringResult() throws Exception{
        mockMvc.perform(get("/helloWorld/stringResult"))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(content().string("吴文涛"))
                .andDo(print())
                .andReturn();

    }

    @Test
    public void testUrlTestException() throws Exception{
        mockMvc.perform(get("/helloWorld/testException"))
                .andExpect(status().isNotFound())
                .andExpect(redirectedUrl("/error.html"))
                .andDo(print())
                .andReturn();

    }

    @Test
    public void testUrlFrom() throws Exception {
        //它存在 多种返回情况，比如 输入正确的时候 返回输入用户信息，输入错误的时候返回错误信息
//        MvcResult mvcResult1 = mockMvc.perform(post("/helloWorld/form")
//                .param("id", "1")
//                .param("username", "wwt")
//                .param("password", "123456")
//                .param("confirm", "123456"))
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//                .andExpect(jsonPath("id").value(1))
//                .andExpect(jsonPath("username").value("wwt"))
//                .andExpect(jsonPath("password").value("123456"))
//                .andDo(print())
//                .andReturn();
        MvcResult mvcResult2 = mockMvc.perform(post("/helloWorld/form")
                .param("id", "")
                .param("username", "")
                .param("password", "123456")
                .param("confirm", "123456"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].fieldName").value("id"))
//                .andExpect(jsonPath("$[0].fieldName").value("id"))
//                .andExpect(jsonPath("$[0].message").value("用户id不为空"))
//                .andExpect(jsonPath("$[1].fieldName").value("username"))
//                .andExpect(jsonPath("$[1].message").value("用户名不为空"))
                .andDo(print())
                .andReturn();
    }

}