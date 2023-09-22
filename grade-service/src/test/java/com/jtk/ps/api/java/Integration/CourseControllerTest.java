package com.jtk.ps.api.java.Integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.jtk.ps.api.GradeServiceApplication;
import com.jtk.ps.api.dto.ComponentAndCriteriasDto;
import com.jtk.ps.api.dto.ComponentCourseDto;
import com.jtk.ps.api.dto.CourseFormResponseDto;
import com.jtk.ps.api.dto.CriteriaEvaluationFormDto;
import com.jtk.ps.api.dto.EvaluationFormResponseDto;
import com.jtk.ps.api.dto.RecapitulationCourseDto;
import com.jtk.ps.api.dto.Response;
import com.jtk.ps.api.dto.ResponseList;
import com.jtk.ps.api.dto.TypeOfAspectEvaluationDto;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = GradeServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Execution(value = ExecutionMode.CONCURRENT)
public class CourseControllerTest extends IntegrationTest{
    
    @Test
    void getAllCourseFormTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<ResponseList<CourseFormResponseDto>> responseEntity = restTemplate.exchange(
                createURLWithPort("/grade/courses/form"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotEquals(0, Objects.requireNonNull(responseEntity.getBody()).getData().size());
    }

    @Test
    void getDetailCourseTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);
        int idForm = 16;

        ResponseEntity<Response<CourseFormResponseDto>> responseEntity = restTemplate.exchange(
                createURLWithPort("/grade/courses/form/"+idForm),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody().getData());
    }

    @Test
    void getEvaluationFormByProdiIdTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<ResponseList<EvaluationFormResponseDto>> responseEntity = restTemplate.exchange(
                createURLWithPort("/grade/courses/criteria/evaluation-form/1"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotEquals(0, Objects.requireNonNull(responseEntity.getBody()).getData().size());
    }

    @Test
    void getAspectEvaluationForm(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<ResponseList<CriteriaEvaluationFormDto>> responseEntity = restTemplate.exchange(
                createURLWithPort("/grade/courses/criteria/evaluation-form/aspect?formType=Self Assessment&prodiId=1"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotEquals(0, Objects.requireNonNull(responseEntity.getBody()).getData().size());
    }

    @Test
    void getTypeAspectEvaluationFormTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<ResponseList<TypeOfAspectEvaluationDto>> responseEntity = restTemplate.exchange(
                createURLWithPort("/grade/courses/criteria/evaluation-form/aspect/type?formType=Self Assessment&prodiId=1"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotEquals(0, Objects.requireNonNull(responseEntity.getBody()).getData().size());
    }

    @Test
    void getComponentByCourseFormTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<ResponseList<ComponentCourseDto>> responseEntity = restTemplate.exchange(
                createURLWithPort("/grade/courses/component/course-form/16"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotEquals(0, Objects.requireNonNull(responseEntity.getBody()).getData().size());
    }

    @Test
    void getCriteriaComponentByCourseFormIdTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<ResponseList<ComponentAndCriteriasDto>> responseEntity = restTemplate.exchange(
                createURLWithPort("/grade/courses/component/criteria/form/16"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotEquals(0, Objects.requireNonNull(responseEntity.getBody()).getData().size());
    }

    @Test
    void getRecapitulationCourseByYearAndProdiTest(){
        accessToken = getAccessToken("COMMITTEE");
        headers.add("COOKIE", accessToken);

        ResponseEntity<ResponseList<RecapitulationCourseDto>> responseEntity = restTemplate.exchange(
                createURLWithPort("/grade/courses/recapitulation?prodiId=1&year=2023"),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotEquals(0, Objects.requireNonNull(responseEntity.getBody()).getData().size());
    }
}
