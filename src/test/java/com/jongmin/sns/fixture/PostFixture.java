package com.jongmin.sns.fixture;

import com.jongmin.sns.domain.Post;
import com.jongmin.sns.dto.PostDto;
import com.jongmin.sns.dto.UserDto;

public class PostFixture {

    public static Post get(Long postId, Long userId, String userName) {
        UserDto userDto = UserDto.of(userId, userName, null, null, null, null, null);
        PostDto postDto = PostDto.of(postId, null, null, userDto, null, null, null);
        return postDto.toEntity();
    }

}
