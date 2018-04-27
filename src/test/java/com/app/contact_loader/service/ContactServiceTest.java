package com.app.contact_loader.service;

import com.app.contact_loader.TimingRules;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletResponse;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ContactServiceTest {

    public static final String REGEX = ".*[aei].*$";

    @Autowired
    ContactServiceImpl service;

    @Rule
    public Stopwatch stopwatch = TimingRules.STOPWATCH;

    @Test
    public void testGetAll() throws Exception {
        HttpServletResponse response = new MockHttpServletResponse();
        service.getAll(REGEX, response.getWriter());
    }
}
