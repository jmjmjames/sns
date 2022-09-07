package com.jongmin.sns.service;

import com.jongmin.sns.domain.Comment;
import com.jongmin.sns.domain.Like;
import com.jongmin.sns.domain.Post;
import com.jongmin.sns.domain.alarm.Alarm;
import com.jongmin.sns.domain.alarm.AlarmArgs;
import com.jongmin.sns.domain.alarm.AlarmType;
import com.jongmin.sns.domain.user.User;
import com.jongmin.sns.dto.CommentDto;
import com.jongmin.sns.dto.PostDto;
import com.jongmin.sns.exception.ErrorCode;
import com.jongmin.sns.exception.SnsApplicationException;
import com.jongmin.sns.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final AlarmRepository alarmRepository;

    public void create(PostDto dto) {
        String userName = dto.getUserDto().getUserName();
        User user = getUserOrException(userName);

        postRepository.save(Post.of(dto.getTitle(), dto.getContent(), user));
    }

    public PostDto modify(Long postId, PostDto dto) {
        String userName = dto.getUserDto().getUserName();

        User user = getUserOrException(userName);

        Post post = getPostOrException(postId);

        if (post.getUser() != user) {
            throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userName, postId));
        }

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        return PostDto.fromEntity(postRepository.saveAndFlush(post));
    }

    public void delete(String userName, Long postId) {
        User user = getUserOrException(userName);

        Post post = getPostOrException(postId);

        if (post.getUser() != user) {
            throw new SnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userName, postId));
        }
        postRepository.delete(post);
    }

    @Transactional(readOnly = true)
    public Page<PostDto> list(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostDto::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<PostDto> myList(String userName, Pageable pageable) {
        User user = userRepository.findByUserName(userName).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not found", userName)));
        return postRepository.findAllByUser(user, pageable).map(PostDto::fromEntity);
    }

    public void like(Long postId, String userName) {
        Post post = getPostOrException(postId);

        User user = getUserOrException(userName);

        // 유저는 좋아요를 post당 한번만 가능하다.
        // check liked -> throw
        likeRepository.findByUserAndPost(user, post).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.ALREADY_LIKED, String.format("userName %s already like post %d", userName, postId));
        });

        // like save
        likeRepository.save(Like.of(user, post));

        // alarm save
        alarmRepository.save(Alarm.of(post.getUser(), AlarmType.NEW_LIKE_ON_POST, new AlarmArgs(user.getId(), postId)));
    }

    @Transactional(readOnly = true)
    public long likeCount(Long postId) {
        Post post = getPostOrException(postId);
        // count like
        return likeRepository.countByPost(post);
    }

    public void comment(Long postId, String userName, String comment) {
        User user = getUserOrException(userName);
        Post post = getPostOrException(postId);

        // comment save
        commentRepository.save(Comment.of(user, post, comment));

        // alarm save
        alarmRepository.save(Alarm.of(post.getUser(), AlarmType.NEW_COMMENT_ON_POST, new AlarmArgs(user.getId(), postId)));
    }

    @Transactional(readOnly = true)
    public Page<CommentDto> getComments(Long postId, Pageable pageable) {
        Post post = getPostOrException(postId);
        return commentRepository.findAllByPost(post, pageable).map(CommentDto::fromEntity);
    }


    private User getUserOrException(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not found", userName)));
    }

    private Post getPostOrException(Long postId) {
        return postRepository.findById(postId).orElseThrow(() ->
                new SnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not found", postId)));
    }

}
