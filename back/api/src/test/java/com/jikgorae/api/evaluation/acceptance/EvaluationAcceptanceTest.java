package com.jikgorae.api.evaluation.acceptance;

import static com.jikgorae.api.fixture.EvaluationFixture.*;
import static com.jikgorae.api.security.oauth2.authentication.AuthorizationExtractor.*;
import static org.junit.jupiter.api.DynamicTest.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.jikgorae.api.AcceptanceTest;
import com.jikgorae.api.evaluation.presentation.EvaluationController;
import com.jikgorae.api.fixture.MemberFixture;
import com.jikgorae.api.member.application.AuthTokenResponse;
import com.jikgorae.api.security.web.AuthorizationType;

public class EvaluationAcceptanceTest extends AcceptanceTest {
    private AuthTokenResponse token;

    /**
     * Feature: 평가 관리
     * <p>
     * Scenario: 평가를 관리한다.
     * <p>
     * When 평가를 한다.
     * Then 평가가 추가된다.
     */
    @DisplayName("평가를 관리한다")
    @TestFactory
    Stream<DynamicTest> manageEvaluation() {
        token = joinAndLogin(MemberFixture.MEMBER1);

        return Stream.of(
                dynamicTest("평가 추가", this::postEvaluation)
        );
    }

    private void postEvaluation() throws Exception {
        String request = objectMapper.writeValueAsString(EVALUATION_REQUEST);

        mockMvc.perform(
                MockMvcRequestBuilders.post(EvaluationController.EVALUATION_API_URI)
                        .header(AUTHORIZATION, String.format("%s %s", AuthorizationType.BEARER,
                                token.getAccessToken()))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());
    }

}
