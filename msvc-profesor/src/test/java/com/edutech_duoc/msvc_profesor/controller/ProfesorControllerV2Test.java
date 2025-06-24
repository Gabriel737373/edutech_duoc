package com.edutech_duoc.msvc_profesor.controller;

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
public class ProfesorControllerV2Test {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    public void shouldReturnAllProfesoresWhenListIsRequested(){

        ResponseEntity<String> response = testRestTemplate.getForEntity("/api/v2/profesores", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        int profesoresCount = documentContext.read("$.length()");
        assertThat(profesoresCount).isEqualTo(1000);
    }
}
