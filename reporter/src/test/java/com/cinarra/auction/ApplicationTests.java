package com.cinarra.auction;

import com.cinarra.auction.common.profiles.Embedded;
import com.cinarra.auction.common.profiles.TestScenario;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@ActiveProfiles(profiles = {Embedded.profile, TestScenario.profile})
public class ApplicationTests {

    @Test
    public void contextLoads() {
    }

}