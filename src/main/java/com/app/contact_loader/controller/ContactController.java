package com.app.contact_loader.controller;

import com.app.contact_loader.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RestController
public class ContactController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String URL = "/contacts";

    @Autowired
    private ContactService service;

    @GetMapping(value = URL, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void getContacts(@RequestParam(value = "nameFilter") String regex,
                            HttpServletResponse response) throws IOException {
        log.info("Start assembling data.");

            PrintWriter printWriter = response.getWriter();
            response.setCharacterEncoding("UTF8");
            response.setContentType("application/json");
            service.getAll(regex, printWriter);

        log.info("Finish assembling data.");
    }
}
