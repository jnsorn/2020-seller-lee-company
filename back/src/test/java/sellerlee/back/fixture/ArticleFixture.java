/**
 * @author kouz95
 */
package sellerlee.back.fixture;

import static sellerlee.back.fixture.MemberFixture.*;
import static sellerlee.back.fixture.TagFixture.*;

import java.util.Arrays;

import sellerlee.back.article.application.ArticleRequest;
import sellerlee.back.article.domain.Article;
import sellerlee.back.article.domain.Category;
import sellerlee.back.article.domain.Photos;
import sellerlee.back.article.domain.Tags;
import sellerlee.back.article.domain.TradeState;

public class ArticleFixture {
    public static final Article ARTICLE1 = new Article(
            51L,
            "test title1",
            Tags.of(TAG_FIXTURE),
            Category.PC,
            "test contents1",
            1234L,
            TradeState.ON_SALE,
            Photos.of("testUri1", "testUri2"),
            MEMBER1);

    public static final Article ARTICLE2 = new Article(
            52L,
            "test title1",
            Tags.of(TAG_FIXTURE),
            Category.PC,
            "test contents1",
            1234L,
            TradeState.ON_SALE,
            Photos.of("testUri1", "testUri2"),
            MEMBER1);

    public static final Article ARTICLE3 = new Article(
            53L,
            "test title1",
            Tags.of(TAG_FIXTURE),
            Category.PC,
            "test contents1",
            1234L,
            TradeState.ON_SALE,
            Photos.of("testUri1", "testUri2"),
            MEMBER1);

    public static final ArticleRequest ARTICLE_CREATE_REQUEST_FIXTURE = new ArticleRequest(
            "TEST_TITLE",
            10_000L,
            "디지털/가전",
            "TEST_CONTENTS",
            Arrays.asList(TAG_FIXTURE, TAG_FIXTURE2),
            Arrays.asList("testUri1", "testUri2"),
            MEMBER1.getId());
}