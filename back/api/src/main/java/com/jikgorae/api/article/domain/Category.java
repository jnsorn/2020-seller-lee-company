package com.jikgorae.api.article.domain;

import java.util.Arrays;

public enum Category {
    PC("디지털/가전"),
    FURNITURE("가구/인테리어"),
    TODDLER_ITEM("유아동/유아도서"),
    LIFE("생활/가공식품"),
    SPORTS("스포츠/레저"),
    WOMEN_ITEM("여성잡화"),
    WOMEN_CLOTHES("여성의류"),
    MAN_ITEM("남성패션/잡화"),
    GAME("게임/취미"),
    BEAUTY("뷰티/미용"),
    COMPANION_ANIMAL_ITEM("반려동물용품"),
    BOOK("도서/티켓/음반"),
    ETC("기타 중고물품");

    private final String categoryName;

    Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public static Category fromString(String categoryName) {
        return Arrays.stream(values())
                .filter(v -> v.categoryName.equals(categoryName))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                String.format("잘못된 카테고리 : %s.", categoryName)));
    }

    public String getCategoryName() {
        return categoryName;
    }
}
