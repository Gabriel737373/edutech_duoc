package com.edutech.msvc.reporte.controllers;


import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReporteControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void shouldReturnAllReportesWhenListIsRequested() {
        ResponseEntity<String> response = testRestTemplate.getForEntity("/api/v1/reportes", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext context = JsonPath.parse(response.getBody());
        int reportesCount=context.read("$.length()");
        assertThat(reportesCount).isGreaterThan(1000);
    }
}
