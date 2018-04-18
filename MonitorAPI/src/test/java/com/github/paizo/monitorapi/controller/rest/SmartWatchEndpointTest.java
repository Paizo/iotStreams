package com.github.paizo.monitorapi.controller.rest;

import com.github.paizo.monitorapi.model.SmartWatch;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

@RunWith(SpringRunner.class)
@WebMvcTest(SmartWatchEndpoint.class)
public class SmartWatchEndpointTest extends AbstractEndpointTest<SmartWatch> {

    @PostConstruct
    public void post() {
        this.endpoint = "smartwatch";
    }

}
