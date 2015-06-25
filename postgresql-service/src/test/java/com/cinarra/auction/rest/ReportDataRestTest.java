package com.cinarra.auction.rest;

import com.cinarra.auction.common.profiles.Embedded;
import com.cinarra.auction.config.PostgresqlServiceConfig;
import com.cinarra.auction.config.RepositoriesTestConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes = {RepositoriesTestConfig.class, PostgresqlServiceConfig.class}, initializers = ConfigFileApplicationContextInitializer.class)
@ActiveProfiles(Embedded.profile)
public class ReportDataRestTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void testHome() throws Exception {
        this.mvc.perform(get("/day")).andExpect(status().isOk())
                .andExpect(content().string(containsString("totalCost")));
    }

    @Test
    public void testFindByDay() throws Exception {
        this.mvc.perform(
                get("/day/123_2015-05-25"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("totalCost")))
                .andExpect(jsonPath("totalCost", equalTo(79.3)));
    }

    @Test
    public void testFindByWeek() throws Exception {
        this.mvc.perform(
                get("/week/123_2015-05-25"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("totalCost")))
                .andExpect(jsonPath("totalCost", equalTo(134.96)));
    }

    @Test
    public void testFindByMonth() throws Exception {
        this.mvc.perform(
                get("/month/123_2015-05-01"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("totalCost")))
                .andExpect(jsonPath("totalCost", equalTo(170.24)));
    }

}