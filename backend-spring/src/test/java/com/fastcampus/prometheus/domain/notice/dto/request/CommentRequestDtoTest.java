package com.fastcampus.prometheus.domain.notice.dto.request;

import com.fastcampus.prometheus.domain.notice.entity.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CommentRequestDtoTest{

    @Test
    public void testToEntity() {
        // Arrange
        String testComment = "This is a test comment";
        String testCommenter = "Test Commenter";

        CommentRequestDto commentRequestDto = CommentRequestDto.builder()
                .comment(testComment)
                .commenter(testCommenter)
                .build();

        // Act
        Comment comment = commentRequestDto.toEntity();

        // Assert
        assertNotNull(comment);
        assertEquals(testComment, comment.getComment());
        assertEquals(testCommenter, comment.getCommenter());
    }
}
