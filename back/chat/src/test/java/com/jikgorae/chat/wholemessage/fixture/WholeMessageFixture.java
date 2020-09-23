package com.jikgorae.chat.wholemessage.fixture;

import com.jikgorae.chat.wholemessage.application.WholeMessageRequest;

public class WholeMessageFixture {
    public static WholeMessageRequest requestOf(Long roomId) {
        return new WholeMessageRequest(roomId, 99L, "NICKNAME", "AVATAR", "MESSAGE");
    }
}
