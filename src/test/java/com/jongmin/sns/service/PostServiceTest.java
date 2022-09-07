package com.jongmin.sns.service;

import com.jongmin.sns.domain.Post;
import com.jongmin.sns.domain.user.User;
import com.jongmin.sns.dto.UserDto;
import com.jongmin.sns.dto.request.post.PostCreateRequest;
import com.jongmin.sns.dto.request.post.PostModifyRequest;
import com.jongmin.sns.exception.ErrorCode;
import com.jongmin.sns.exception.SnsApplicationException;
import com.jongmin.sns.fixture.PostFixture;
import com.jongmin.sns.repository.PostRepository;
import com.jongmin.sns.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private UserRepository userRepository;

    @Test
    void 포스트작성_성공한_경우() {
        String title = "title";
        String content = "content";
        String userName = "name";
        PostCreateRequest request = PostCreateRequest.of(title, content);
        UserDto userDto = UserDto.of(userName, null);

        // mocking
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(mock(User.class)));
        when(postRepository.save(any())).thenReturn(mock(Post.class));

        assertDoesNotThrow(() -> postService.create(request.toDto(userDto)));
    }

    @Test
    void 포스트작성시_가입된_유저가_존재하지_않는_경우() {
        String title = "title";
        String content = "content";
        String userName = "name";
        PostCreateRequest request = PostCreateRequest.of(title, content);
        UserDto userDto = UserDto.of(userName, null);

        // mocking
        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(postRepository.save(any())).thenReturn(mock(Post.class));

        SnsApplicationException e = assertThrows(SnsApplicationException.class, () -> postService.create(request.toDto(userDto)));
        assertThat(ErrorCode.USER_NOT_FOUND).isEqualTo(e.getErrorCode());
    }

    @Test
    void 포스트수정_성공한_경우() {
        String title = "title";
        String content = "content";
        String userName = "name";
        long postId = 1L;
        PostModifyRequest request = PostModifyRequest.of(title, content);
        UserDto userDto = UserDto.of(userName, null);

        // mocking
        Post post = PostFixture.get(postId, 1L, userName);
        User user = post.getUser();
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(postRepository.saveAndFlush(any())).thenReturn(post);

        assertDoesNotThrow(() -> postService.modify(postId, request.toDto(userDto)));
    }

    @Test
    void 포스트수정_포스트가_존재하지_않는_경우() {
        String title = "title";
        String content = "content";
        String userName = "name";
        long postId = 1L;
        PostModifyRequest request = PostModifyRequest.of(title, content);
        UserDto userDto = UserDto.of(userName, null);
        // mocking

        Post post = PostFixture.get(postId, 1L, userName);
        User user = post.getUser();
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        SnsApplicationException e = assertThrows(SnsApplicationException.class, () -> postService.modify(postId, request.toDto(userDto)));
        assertThat(ErrorCode.POST_NOT_FOUND).isEqualTo(e.getErrorCode());
    }

    @Test
    void 포스트수정시_권한이_없는_경우() {
        String title = "title";
        String content = "content";
        String userName = "name";
        long postId = 1L;
        PostModifyRequest request = PostModifyRequest.of(title, content);
        UserDto userDto = UserDto.of(userName, null);

        // mocking
        Post post = PostFixture.get(postId, 1L, userName);

        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(mock(User.class)));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        SnsApplicationException e = assertThrows(SnsApplicationException.class, () -> postService.modify(postId, request.toDto(userDto)));
        assertThat(ErrorCode.INVALID_PERMISSION).isEqualTo(e.getErrorCode());
    }

    @Test
    void 포스트삭제_성공한_경우() {
        String userName = "name";
        long postId = 1L;

        // mocking
        Post post = PostFixture.get(postId, 1L, userName);
        User user = post.getUser();
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        willDoNothing().given(postRepository).delete(post);

        assertDoesNotThrow(() -> postService.delete(userName, postId));
    }

    @Test
    void 포스트삭제시_포스트가_존재하지_않는_경우() {
        String userName = "name";
        long postId = 1L;

        Post post = PostFixture.get(postId, 1L, userName);
        User user = post.getUser();
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        SnsApplicationException e = assertThrows(SnsApplicationException.class, () -> postService.delete(userName, postId));
        assertThat(ErrorCode.POST_NOT_FOUND).isEqualTo(e.getErrorCode());
    }

    @Test
    void 포스트삭제시_권한이_없는_경우() {
        String userName = "name";
        long postId = 1L;

        Post post = PostFixture.get(postId, 1L, userName);
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(mock(User.class)));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        SnsApplicationException e = assertThrows(SnsApplicationException.class, () -> postService.delete(userName, postId));
        assertThat(ErrorCode.INVALID_PERMISSION).isEqualTo(e.getErrorCode());
    }

    @Test
    void 피드_목록_요청_성공한_경우() {
        Pageable pageable = mock(Pageable.class);
        when(postRepository.findAll(pageable)).thenReturn(Page.empty());
        assertDoesNotThrow(() -> postService.list(pageable));
    }

    @Test
    void 내_피드_목록_요청_성공한_경우() {
        Pageable pageable = mock(Pageable.class);
        User user = mock(User.class);

        when(userRepository.findByUserName(any())).thenReturn(Optional.of(user));
        when(postRepository.findAllByUser(user, pageable)).thenReturn(Page.empty());
        assertDoesNotThrow(() -> postService.myList("", pageable));
    }

}
