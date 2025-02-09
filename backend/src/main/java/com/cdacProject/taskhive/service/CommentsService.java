package com.cdacProject.taskhive.service;

import com.cdacProject.taskhive.model.Comments;

import java.util.List;

public interface CommentsService {

    Comments createComments(Long issueId, Long userId, String contents) throws Exception;

    void deleteComment(Long commentId, Long userId) throws Exception;

    List<Comments> findCommentByIssueId(Long issueID);

}
