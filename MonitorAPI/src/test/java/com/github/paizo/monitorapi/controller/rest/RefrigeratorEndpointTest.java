package com.github.paizo.monitorapi.controller.rest;

import com.github.paizo.monitorapi.model.Refrigerator;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

@RunWith(SpringRunner.class)
@WebMvcTest(RefrigeratorEndpoint.class)
public class RefrigeratorEndpointTest extends AbstractEndpointTest<Refrigerator> {

    @PostConstruct
    public void post() {
        this.endpoint = "refrigerator";
    }

}
