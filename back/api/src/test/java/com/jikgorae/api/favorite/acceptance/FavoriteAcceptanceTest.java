package com.jikgorae.api.favorite.acceptance;

import static com.jikgorae.api.favorite.presentation.FavoriteController.*;
import static com.jikgorae.api.fixture.MemberFixture.*;
import static com.jikgorae.api.fixture.OrganizationFixture.*;
import static com.jikgorae.api.security.oauth2.authentication.AuthorizationExtractor.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.jikgorae.api.AcceptanceTest;
import com.jikgorae.api.article.application.ArticleCardResponse;
import com.jikgorae.api.favorite.application.FavoriteRequest;
import com.jikgorae.api.member.application.AuthTokenResponse;
import com.jikgorae.api.organization.application.OrganizationResponse;
import com.jikgorae.api.security.web.AuthorizationType;

public class FavoriteAcceptanceTest extends AcceptanceTest {
    private AuthTokenResponse token;

    /**
     * Feature: 찜 관리
     * <p>
     * Scenario: 찜을 관리한다.
     * <p>
     * Given 게시글이 생성되어 있다.
     * And 멤버가 생성되어 있다.
     * <p>
     * When 찜 등록을 한다.
     * Then 찜이 추가 된다.
     * <p>
     * When 찜 삭제를 한다.
     * Then 찜이 삭제 된다.
     */
    @DisplayName("찜 관리")
    @TestFactory
    @Transactional
    Stream<DynamicTest> manageFavorite() throws Exception {
        token = joinAndLogin(MEMBER2);
        OrganizationResponse organization = createOrganization(token, 송이_요청);
        createMemberOrganization(organization, token);
        Long articleId = extractId(createArticle(token, organization));

        return Stream.of(
                dynamicTest("찜 생성", () -> createFavorite(articleId)),
                dynamicTest("찜 목록 조회", () -> showFavorites(token, articleId)),
                dynamicTest("찜 삭제", () -> deleteFavorite(articleId))
        );
    }

    private String createFavorite(Long articleId) throws Exception {
        FavoriteRequest request = new FavoriteRequest(articleId);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.post(FAVORITE_API_URI)
                        .header(AUTHORIZATION, String.format("%s %s", AuthorizationType.BEARER,
                                token.getAccessToken()))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        return mvcResult.getResponse().getHeader("Location");
    }

    private void showFavorites(AuthTokenResponse token, Long articleId) throws Exception {
        OrganizationResponse organization = createOrganization(token, 중앙대학교_요청);
        createMemberOrganization(organization, token);
        Long noFavoriteArticleId = extractId(createArticle(token, organization));

        String content = mockMvc.perform(MockMvcRequestBuilders.get(FAVORITE_API_URI)
                .header(AUTHORIZATION, String.format("%s %s", AuthorizationType.BEARER,
                        token.getAccessToken()))
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<ArticleCardResponse> responses = objectMapper.readValue(content,
                objectMapper.getTypeFactory()
                        .constructCollectionType(List.class, ArticleCardResponse.class));

        assertThat(responses.stream()
                .anyMatch(response -> response.getId().equals(articleId)))
                .isTrue();
        assertThat(responses.stream()
                .noneMatch(response -> response.getId().equals(noFavoriteArticleId)))
                .isTrue();
    }

    private void deleteFavorite(Long articleId) throws Exception {
        FavoriteRequest request = new FavoriteRequest(articleId);

        mockMvc.perform(
                MockMvcRequestBuilders.delete(FAVORITE_API_URI)
                        .header(AUTHORIZATION, String.format("%s %s", AuthorizationType.BEARER,
                                token.getAccessToken()))
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }
}
