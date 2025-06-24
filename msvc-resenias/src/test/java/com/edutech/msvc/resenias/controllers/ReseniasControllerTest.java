package com.edutech.msvc.resenias.controllers;


import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReseniasControllerTest {
    @Autowired
    TestRestTemplate TestRestTemplate;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldReturnAllReseniasWhenListIsRequested(){
        ResponseEntity<String> response = testRestTemplate.getForEntity("/api/v2/resenias", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int cursosCount = documentContext.read("$.length()");
        assertThat(cursosCount).isEqualTo(1000);
    }
}
